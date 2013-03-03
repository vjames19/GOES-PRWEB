package edu.uprm.ece.hydroclimate.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.google.gson.Gson;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.degrib.Degribber;
import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.download.Downloader;
import edu.uprm.ece.hydroclimate.download.DownloaderFactory;
import edu.uprm.ece.hydroclimate.email.Emailer;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;
import edu.uprm.ece.hydroclimate.upload.GoesUploader;
import edu.uprm.ece.hydroclimate.utils.GoesUtils;

/**
 * Defines the different stages of the automation <strong> Download, Degrib,
 * matlab, upload, email <strong>
 * 
 * @author Victor J.
 * 
 */
public class AutomateGoes {

	private GoesProperties properties;
	private static final Logger logger = Logger.getLogger(AutomateGoes.class);
	private Date date;
	private Date executionDate;
	private DirectoryManager manager;
	private static final String LOG_NAME_FORMAT = "log_%tY%tm%td.log";

	/**
	 * Constructs the automation with todays date and the supplied properties
	 * file
	 * 
	 * @param propertiesPath
	 * @throws IOException
	 */
	public AutomateGoes(String propertiesPath) throws IOException {
		this(propertiesPath, new Date());
	}

	public AutomateGoes(String propertiesPath, Date date) throws IOException {
		loadProperties(propertiesPath);
		manager = new DirectoryManager(this.properties.getGoesDir());
		logger.info("Working Directory " + manager.getRootDirectory().getCanonicalPath());
		this.date = date;
		executionDate = new Date();
		configureFileAppender();

	}

	private void configureFileAppender() throws IOException {
		File logDir = manager.getLogDirectory();
		File log = new File(logDir, format(this.executionDate, LOG_NAME_FORMAT));
		FileAppender fa = new FileAppender(new PatternLayout(
				properties.getLogLayout()), log.getCanonicalPath());
		fa.setThreshold(Level.DEBUG);
		fa.setAppend(false);
		fa.activateOptions();
		// Enumeration<Appender> a = Logger.getRootLogger().getAllAppenders();
		// while(a.hasMoreElements()){
		// System.out.println(a.nextElement().getName());
		// }
		// FileAppender fa = (FileAppender)
		// Logger.getRootLogger().getAppender("file");
		// fa.setFile(log.getAbsolutePath());
		// fa.activateOptions();

		Logger.getRootLogger().addAppender(fa);
	}

	private void loadProperties(String properties) throws FileNotFoundException {
		File props = new File(properties);
		Gson gson = new Gson();
		this.properties = gson.fromJson(new FileReader(props),
				GoesProperties.class);

	}

	public void makeDirs() {
		logger.info("working dir date " + date);
		manager.createDirectoriesForDate(date);
	}

	public void download() {

		String absolute = getWorkingDirectory().getAbsolutePath();
		for (Download download : properties.getDownloads()) {
			Date workDate = DateUtils.addDays(date, download.getDateOffset());
			Download tempDownload = new Download(download);
			tempDownload.setUrl(format(workDate, download.getUrl()));
			tempDownload.setSaveIn(FilenameUtils.concat(absolute,
					format(workDate, download.getSaveIn())));
			Downloader downloader = DownloaderFactory
					.getDownloader(tempDownload);

			if (downloader != null) {
				// TODO: wait for download
				if (downloader.exists()) {
					try {
						downloader.download();
					} catch (IOException e) {
						logger.error("Error downloading " + download, e);
					}
				}
			} else {
				logger.error("Couldn't find a downloader for the following download "
						+ download);
			}

		}
	}

	public void degrib() {
		File dir = getWorkingDirectory();
		Degribber degrib = properties.getDegribber();
		degrib.setDegribDir(dir);
		degrib.setOutputDir(dir);
		for (DegribVariable variable : degrib.getVariables()) {
			variable.setOutputName(format(this.date, variable.getOutputName()));
			degrib.degrib(variable);
		}

	}

	public void matlab() throws ExecuteException, IOException {
		CommandLine cmd = CommandLine.parse(properties.getMatlabCmd());
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(new File(properties
				.getMatlabWorkingDirectory()));
		executor.execute(cmd);

	}

	public boolean waitForFinishedFile() {

		File workingDir = manager.getOutputDirectory(date);
		boolean result = waitForFile(workingDir.getAbsolutePath(),
				properties.getFinishedFileName());
		if (!result) {
			logger.error("Couldn't find the matlab file ");
		}
		return result;
	}

	public boolean waitForFile(String directory, String fileName) {
		LogMF.info(logger, "Waiting for file, looking for {0} in {1}",
				fileName, directory);
		FileAlterationObserver observer = new FileAlterationObserver(new File(
				directory));
		FileCreatedListener listener = new FileCreatedListener(fileName);
		observer.addListener(listener);
		long timeToWait = GoesUtils.convertSecondsToMillis(properties
				.getWaitInterval());
		int tries = 0;
		int numberOfTries = properties.getTimesToWait();
		observer.checkAndNotify();
		while (!listener.isFoundFile()) {
			try {
				Thread.sleep(timeToWait);
			} catch (InterruptedException ignore) {
			}
			if (tries > numberOfTries) {
				return false;
			}
			observer.checkAndNotify();

		}

		return true;
	}

	public void upload() {
		GoesUploader uploader = new GoesUploader(properties.getFtp(),
				properties.getVariables(), manager.getOutputDirectory(date));
		try {
			uploader.connect();
			uploader.uploadVariables();

		} catch (IOException e) {
			logger.error("IOException trying to upload files", e);
		} finally {
			uploader.disconnect();
		}

	}

	private String format(Date date, String format) {
		return GoesUtils.stringFormatTime(format, date);
	}

	public void emailLog() throws IOException, EmailException {
		File logDirectory = manager.getLogDirectory();
		Emailer emailer = new Emailer(properties.getEmail());
//		 String logMessage = FileUtils.readFileToString(new File(logDirectory,
//		 format(this.executionDate, LOG_NAME_FORMAT)));
//		 if (logMessage.contains("ERROR")) {
//		 emailer.sendEmail("GOES-PRWEB LOG for " + date
//		 + " contains an error", logMessage);
//		 }

		emailer.sendEmailWithAttachment(
				"GOES-PRWEB LOG for " + executionDate,
				"Log",
				FileUtils.getFile(logDirectory,
						this.format(executionDate, LOG_NAME_FORMAT)));
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private File getWorkingDirectory() {
		return manager.getDirectory(date);
	}

}

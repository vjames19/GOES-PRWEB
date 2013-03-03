package edu.uprm.ece.hydroclimate.upload;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.properties.FtpProperties;
import edu.uprm.ece.hydroclimate.utils.FtpUtils;
/**
 * Uploads to an FTP server following that each variable will be under the 
 * specified root directory.
 * 
 * <p> Expected usage:
 * <p>
 * <code>GoesUploader u = new GoesUploader(...);<br>
 * u.connect();<br>
 * u.upload...();<br>
 * u.disconnect();<br>
 *  </code>
 * @author Victor J.
 *
 */
public class GoesUploader {

	
	private FtpProperties ftpProps;
	private static final Logger logger = Logger.getLogger(GoesUploader.class);
	/**
	 * List containing all the variables to upload
	 */
	private List<String> variables;
	
	/**
	 * Directory where all the files to upload are located
	 */
	private File fileDirectory;
	private FTPClient ftp;

	public GoesUploader(FtpProperties props, List<String> variables,
			File fileDirectory) {
		this.ftpProps = props;
		this.variables = variables;
		this.fileDirectory = fileDirectory;
		
	}

	/**
	 * Connect to the FTP Server
	 * 
	 * @exception IOException
	 *             if anything goes wrong connecting to the server
	 */
	public void connect() throws IOException {
		ftp = FtpUtils.getFtpClient(ftpProps);
		ftp.enterLocalPassiveMode();
		ftp.changeWorkingDirectory(ftpProps.getRootdir());
	}

	/**
	 * Disconnect from the FTP Server
	 */
	public void disconnect() {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			logger.trace("Error trying to disconnect the ftp", e);
		}
		ftp = null;

	}

	/**
	 * Upload all the variables data to the server. Using the following wildcard
	 * expression variable*
	 * 
	 * @exception IOException if there is something wron with the connection
	 */
	public void uploadVariables() throws IOException {

		LogMF.info(logger, "Starting to upload for all the following variables {0}", variables);
		// upload all the files for each variable
		for (String variable : variables) {
			uploadVariable(variable);
		}
		
		

	}

	/**
	 * Uploads just the data for the supplied variable
	 * 
	 * @param variable
	 *            Variable for the data to uploaded
	 * @throws IOException if there is something wrong with the connection
	 */
	public void uploadVariable(String variable) throws IOException {
		LogMF.info(logger,
				"Going to upload data for the following variable {0}", variable);
		String variableWithWildcard = variable + "*";
		File[] files = getFilesToUpload(variableWithWildcard);
		if(files.length == 0){
			LogMF.info(logger, "Nothing to upload for {0}", variable);
			return;
		}
		
		
		LogMF.debug(logger, "Changing directory to {0}", variable);
		ftp.changeWorkingDirectory(variable);
		LogMF.debug(logger, "Changed directory? {0}", ftp.getReplyString());

		
		
		
		// upload each file
		for (File fileToUpload : files) {

			LogMF.debug(logger, "Going to upload the following file {0}",
					fileToUpload);

			try {
				upload(fileToUpload, fileToUpload.getName());
			} catch (FTPConnectionClosedException e) {
				logger.error("Connection closed exception, going to reconnect",
						e);
				//reconnect
				try {
					connect();
					upload(fileToUpload, fileToUpload.getName());
				} catch (IOException e1) {
					logger.error(
							"Error trying to upload file  "
									+ fileToUpload.getAbsolutePath(), e);
					throw e;
				}
			} catch (IOException e) {
				logger.error("Exception trying to upload " + fileToUpload, e);
			}
		}

		ftp.changeToParentDirectory();
		LogMF.debug(logger, "{0}", ftp.getReplyString());
		LogMF.info(logger, "Finished uploading for the following variable {0}",
				variable);

	}

	/**
	 * Uploads a file to the server. The fileToUpload must not be a directory
	 * 
	 * @param fileToUpload
	 * @param name
	 *            Name for the file on the server
	 * @throws FTPConnectionClosedException
	 *             if the connection is closed unexpectedly
	 * @throws IOException
	 *             If the file cannot be read or is a directory or there was an
	 *             error sending the command to the server.
	 */
	private void upload(File fileToUpload, String name)
			throws FTPConnectionClosedException, IOException {
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(fileToUpload);
			ftp.storeFile(name, in);
			LogMF.debug(logger, "Stored file got message from ftp {0}",
					ftp.getReplyString());
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Filters the files in the fileDir based on the wildcard.
	 * 
	 * @param wildcard
	 *            The wildcard being used to get the files
	 * @return The files based on the wildcard
	 */
	public File[] getFilesToUpload(String wildcard) {
		FileFilter filter = new WildcardFileFilter(wildcard);
		return fileDirectory.listFiles(filter);
	}

}

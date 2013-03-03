package edu.uprm.ece.hydroclimate.main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.main.process.CompleteProcess;
import edu.uprm.ece.hydroclimate.main.process.DownloadAndDecompressOnlyProcess;
import edu.uprm.ece.hydroclimate.main.process.GoesProcess;
import edu.uprm.ece.hydroclimate.main.process.MatlabAndUploadProcess;
import edu.uprm.ece.hydroclimate.main.process.UploadOnlyProcess;

/**
 * CLI for the system
 * 
 * @author Victor J.
 * 
 */
public class CLI {
	private static final Logger logger = Logger.getLogger(CLI.class);

	@SuppressWarnings("static-access")
	private static Options createOptions() {
		Options options = new Options();
		Option download = new Option("download", false,
				"Download all the files from the specified dates");
		Option upload = new Option("upload", false,
				"upload all the files from the specified dates");
		Option process = new Option("process", false,
				"Process and upload from the specified dates");
		Option properties = OptionBuilder.withArgName("propertiesFile")
				.hasArg().withDescription("Properties file **required")
				.create("properties");
		Option start = OptionBuilder
				.withArgName("startDate")
				.hasArg()
				.withDescription(
						"specify the start date as yyyy/mm/ddd **required")
				.create("start");

		Option end = OptionBuilder
				.withArgName("endDate")
				.hasArg()
				.withDescription(
						"specify the end date as yyyy/mm/ddd **required")
				.create("end");

		Option help = new Option("help", false, "this message");

		options.addOption(help);
		options.addOption(download);
		options.addOption(upload);
		options.addOption(process);
		options.addOption(properties);
		options.addOption(start);
		options.addOption(end);
		return options;
	}

	/**
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 * @throws org.apache.commons.cli.ParseException
	 * @throws EmailException
	 */
	public static void main(String[] args) throws ParseException, IOException,
			org.apache.commons.cli.ParseException, EmailException {
		// download

		Options options = createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine cli = parser.parse(options, args);
		HelpFormatter helpFormatter = new HelpFormatter();
		final String dateFormat = "yyyy/MM/dd";

		StopWatch sw = new StopWatch();
		sw.start();
		if (!cli.hasOption("properties") || !cli.hasOption("start")
				|| !cli.hasOption("end")) {
			helpFormatter.printHelp("java -cp goes.jar Driver", options);
			System.exit(1);
		} else {
			Date start, end;
			String properties = cli.getOptionValue("properties");
			SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
			String startOptionValue = cli.getOptionValue("start");

			// Case where the user specifies today and the automation needs to
			// start from the day before
			// since the data comes one day late
			if (startOptionValue.equalsIgnoreCase("today")) {
				start = DateUtils.addDays(new Date(), -1);
				end = start;
			} else {// Running a large simulation and thus we're going to use
					// the dates specified
				start = dateFormatter.parse(startOptionValue);
				end = dateFormatter.parse(cli.getOptionValue("end"));
			}
			LogMF.info(logger, "Going to work from {0} to {1}", start, end);

			AutomateGoes goes = new AutomateGoes(properties);
			if (cli.hasOption("download")) {
				process(start, end, new DownloadAndDecompressOnlyProcess(goes),
						goes);
			} else if (cli.hasOption("upload")) {
				process(start, end, new UploadOnlyProcess(goes), goes);
			} else if (cli.hasOption("process")) {
				process(start, end, new MatlabAndUploadProcess(goes), goes);
			} else {
				process(start, end, new CompleteProcess(goes), goes);
			}

			sw.stop();
			logger.info("Finished in " + sw.toString());
			goes.emailLog();

		}

	}

	/**
	 * Runs the process through the specified dates
	 * 
	 * @param start
	 * @param end
	 * @param p
	 * @param goes
	 */
	private static void process(Date start, Date end, GoesProcess p,
			AutomateGoes goes) {
		while (!start.after(end)) {
			logger.info("Working for date: " + start);
			goes.setDate(start);
			p.run();
			start = DateUtils.addDays(start, 1);
			logger.info("Finished working for this date");
		}

	}

}

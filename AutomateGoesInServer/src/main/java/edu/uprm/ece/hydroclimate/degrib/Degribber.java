package edu.uprm.ece.hydroclimate.degrib;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

/**
 * Degribs GRIB files using the executable provided
 * <p>
 * Expected usage: specify the inputdir of files and the outputdir
 * </p>
 * <code>
 * Degribber d = new Degribber(variables);<br>
 * d.setOutputDir(output);<br>
 * d.setInputDir(input);<br>
 * d.degribVariables();<br>
 * </code>
 * 
 * @author Victor J.
 * 
 */
public class Degribber {
	private static String exeLocation;
	private File outputDir;
	private static Logger logger = Logger.getLogger(Degribber.class);
	private static final String INPUT_FILE = "inputFile",
			OUTPUT_FILE = "outputFile", MESSAGE = "message";
	private static final String[] ARGUMENTS = { "${inputFile}", "-out",
			"${outputFile}", "-C", "-msg", "${message}", "-Csv", "-Unit", "m",
			"-Decimal", "2" };

	@SuppressWarnings("unchecked")
	public File degribFile(File inputFile, int message, DegribVariable variable)
			throws ExecuteException, IOException {
		return degribFile(inputFile, message, variable.getOutputName());
	}

	public File degribFile(File inputFile, int message, String outputName)
			throws IOException {
		final Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(INPUT_FILE, inputFile);
		map.put(MESSAGE, message);
		String output = outputName;
		output += message;
		File outputFile = new File(outputDir, output);
		map.put(OUTPUT_FILE, outputFile);
		CommandLine cmd = new CommandLine(exeLocation);
		cmd.addArguments(ARGUMENTS);
		cmd.setSubstitutionMap(map);
		logger.debug(cmd.toString());
		DefaultExecutor executor = new DefaultExecutor();
		final byte[] buffer = new byte[1024];
		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
		ExecuteStreamHandler streamHandler = executor.getStreamHandler();
		int exitCode = -1;
		try {
			streamHandler.setProcessOutputStream(is);
			executor.setStreamHandler(streamHandler);
			exitCode = executor.execute(cmd);
		} catch (IOException e) {
			logger.error("IOException degribbing ", e);
		} finally {
			LogMF.info(logger, "Degrib output {0}", IOUtils.toString(is));
			IOUtils.closeQuietly(is);
		}
		return exitCode != 0 ? null : outputFile;
	}

	public static boolean degrib(File inputFile, File outputFile,
			String exeLocation, int message) {
		final Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(INPUT_FILE, inputFile);
		map.put(MESSAGE, message);
		map.put(OUTPUT_FILE, outputFile);

		CommandLine cmd = new CommandLine(exeLocation);
		cmd.addArguments(ARGUMENTS);
		cmd.setSubstitutionMap(map);
		logger.debug(cmd.toString());
		DefaultExecutor executor = new DefaultExecutor();
		final byte[] buffer = new byte[1024];
		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
		ExecuteStreamHandler streamHandler = executor.getStreamHandler();
		int exitCode = -1;
		try {
			streamHandler.setProcessOutputStream(is);
			executor.setStreamHandler(streamHandler);
			exitCode = executor.execute(cmd);
		} catch (IOException e) {
			logger.error("IOException degribbing ", e);
		} finally {
			try {
				LogMF.info(logger, "Degrib output {0}", IOUtils.toString(is));
			} catch (IOException ignore) {

			}
			IOUtils.closeQuietly(is);
		}
		return exitCode == 0;
	}

}

package edu.uprm.ece.hydroclimate.degrib;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

/**
 * Degribs GRIB files using the executable provided
 * <p>Expected usage: specify the inputdir of files and the outputdir</p>
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
	private List<DegribVariable> variables;
	private String exeLocation;
	private File outputDir;
	private File degribDir;
	@SuppressWarnings("rawtypes")
	private Map map = new HashMap();
	private static Logger logger = Logger.getLogger(Degribber.class);
	private static final String INPUT_FILE = "inputFile", OUTPUT_FILE ="outputFile", MESSAGE = "message";
	
	private static String[] ARGUMENTS= { "${inputFile}", "-out", "${outputFile}"
			,"-C","-msg","${message}","-Csv","-Unit", "m", "-Decimal", "2"};

	public Degribber() {
	}

	public List<DegribVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<DegribVariable> variables) {
		this.variables = variables;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public void degribTheVariables() {
		for (DegribVariable variable : variables) {
			degrib(variable);
		}
	}

	public void degrib(DegribVariable variable) {
		Collection<File> gribs = getGribFiles(variable);
		for(File grib: gribs){
			for(Integer message: variable.getMessages()){
				int returnCode;
				try {
					returnCode = degribFile(grib,message, variable);
					if(returnCode != 0){
						logger.error("Error trying to degrib got error code != 0 " + grib.getAbsolutePath());
						
					}
				} catch (IOException e) {
					logger.error("IOException trying to degrib " + grib.getAbsolutePath(),e);
				} 
				
			}
		}
	}
	
	public Collection<File> getGribFiles(DegribVariable variable){
		return FileUtils.listFiles(degribDir, new WildcardFileFilter(variable.getName()), null);
		
	}
	
	@SuppressWarnings("unchecked")
	private int degribFile(File inputFile, int message, DegribVariable variable) throws ExecuteException, IOException{
		map.put(INPUT_FILE, inputFile);
		map.put(MESSAGE, message);
		String output = variable.getOutputName();
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
		streamHandler.setProcessOutputStream(is);
		executor.setStreamHandler(streamHandler);
		int exitCode = executor.execute(cmd);
		
		LogMF.info(logger, "Degrib output {0}", IOUtils.toString(is));
		IOUtils.closeQuietly(is);
		return exitCode;
	}

	public File getDegribDir() {
		return degribDir;
	}

	public void setDegribDir(File degribDir) {
		this.degribDir = degribDir;
	}
	
	

}

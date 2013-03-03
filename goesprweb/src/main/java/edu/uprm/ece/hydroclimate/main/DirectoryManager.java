package edu.uprm.ece.hydroclimate.main;

import java.io.File;
import java.util.Date;

import edu.uprm.ece.hydroclimate.utils.GoesUtils;

/**
 * Creates all the directories needed by the system. Use it to get specific directories.
 * @author Victor J.
 *
 */
public class DirectoryManager {

	private File rootDirectory;
	private static String DIRECTORY_FORMAT = "%tY/%tm/%td/";
	
	public DirectoryManager(String rootDir){
		rootDirectory = new File(rootDir);
		create(rootDirectory);
		create(getLogDirectory());
	}
	
	public File createDirectoriesForDate(Date date){
		File dir = getDirectory(date);
		getInputDirectory(date);
		getOutputDirectory(date);
		return dir;
	}

	public File getDirectory(Date date) {
		String formatted = GoesUtils.stringFormatTime(DIRECTORY_FORMAT, date);
		File dir = new File(rootDirectory, formatted);
		create(dir);
		return dir;
	}
	
	public File getInputDirectory(Date date){
		File dir = getDirectory(date);
		File input = new File(dir, "INPUT/");
		create(input);
		return input;
		
	}
	
	public File getOutputDirectory(Date date){
		File output = new File(getDirectory(date), "OUTPUT/");
		create(output);
		return output;
		
	}
	
	
	public File getRootDirectory(){
		return rootDirectory;
	}
	
	public File getLogDirectory(){
		return new File(getRootDirectory(), "LOGS/");
	}
	
	private void create(File dir){
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	


}

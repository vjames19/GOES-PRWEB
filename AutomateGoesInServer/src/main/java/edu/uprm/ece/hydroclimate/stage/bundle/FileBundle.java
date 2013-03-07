package edu.uprm.ece.hydroclimate.stage.bundle;

import java.io.File;
import java.util.Date;

public class FileBundle extends DataBundle<File> {

	public FileBundle(File file, Date date){
		super(file, date);
	}
	
	public FileBundle(FileBundle bundle){
		this(bundle.data.getAbsoluteFile(), bundle.date);
	}
	
	

}

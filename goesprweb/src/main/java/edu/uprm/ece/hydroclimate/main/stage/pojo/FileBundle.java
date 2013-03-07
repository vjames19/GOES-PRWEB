package edu.uprm.ece.hydroclimate.main.stage.pojo;

import java.io.File;
import java.util.Date;

public class FileBundle extends DataBundle<File> {

	public FileBundle(File file, Date date){
		super(file, date);
	}

}

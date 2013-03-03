package edu.uprm.ece.hydroclimate.decompress;

import java.io.File;
import java.io.IOException;

public class GzipDecompressor implements Decompressor {
	private final static int BUFFER = 1024;
	@Override
	public void decompress(File file, File outputDirectory) throws IOException {
		if(!outputDirectory.exists()){
			outputDirectory.mkdirs();
		}
		
	
		

	}

	

}

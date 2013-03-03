package edu.uprm.ece.hydroclimate.decompress;

import java.io.File;
import java.io.IOException;

/** 
 * Archive decompressor
 * @author Victor J.
 *
 */
public interface Decompressor {
	public void decompress(File fileToDecompress, File outputFile) throws IOException;
}

package edu.uprm.ece.hydroclimate.download;

import java.io.File;
import java.io.IOException;

/**
 * Interface for making downloads, in can be implemented to download from any source.
 * @author Victor J.
 *
 */
public interface Downloader {
	/**
	 * Verify if the download exists.
	 * @return true if exists
	 */
	public boolean exists();
	
	/**
	 * Makes the download
	 * @return the file downloaded
	 * @throws IOException
	 */
	public File download() throws IOException;
	
	//Setters and getters
	public void setDownload(Download download);
	public Download getDownload();
	
}

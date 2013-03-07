package edu.uprm.ece.hydroclimate.download;

import org.apache.log4j.Logger;

/**
 * Create downloaders based on the download
 * 
 * @author Victor J.
 * 
 */
public class DownloaderFactory {

	private static Logger logger = Logger.getLogger(Downloader.class);

	/**
	 * Gets a downloader based on the download specified downloader.
	 * 
	 * @param download
	 *            Used to find the Downloader
	 * 
	 * @return Downloader initialized with the supplied download.Null if it
	 *         doesn't find the Downloader class.
	 */
	public static Downloader getDownloader(Download download) {
		Class<?> clazz;
		Downloader downloader = null;
		String name = download.getDownloaderClass();
		try {
			clazz = Class.forName(download.getDownloaderClass());
			downloader = (Downloader) clazz.newInstance();
			downloader.setDownload(download);
		} catch (ClassNotFoundException e) {
			logger.error("Error trying to instantiate the following class "
					+ name, e);
		} catch (InstantiationException e) {
			logger.error("Error trying to instantiate the following class "
					+ name, e);
		} catch (IllegalAccessException e) {
			logger.error("Error trying to instantiate the following class "
					+ name, e);
		}

		return downloader;
	}

	// /**
	// * Empty implementation of the {@link Downloader} interface
	// *
	// * @author Victor J.
	// *
	// */
	// public static class NullDownloader implements Downloader {
	//
	// @Override
	// public boolean exists() {
	//
	// return false;
	// }
	//
	// @Override
	// public void download() throws IOException {
	//
	// }
	//
	// @Override
	// public void setDownload(Download download) {
	//
	// }
	//
	// @Override
	// public Download getDownload() {
	// return null;
	// }
	//
	// }

}

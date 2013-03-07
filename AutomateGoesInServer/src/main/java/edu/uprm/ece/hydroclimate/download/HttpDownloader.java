package edu.uprm.ece.hydroclimate.download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.utils.HttpUtils;

/**
 * Downloads from a URL
 * 
 * @author Victor J.
 * 
 */
public class HttpDownloader implements Downloader {

	private Download download;
	private static Logger logger = Logger.getLogger(HttpDownloader.class);

	public HttpDownloader() {
	}

	public HttpDownloader(Download download) {
		this.download = download;
	}

	@Override
	public boolean exists() {
		URL url = null;
		try {
			url = new URL(download.getUrl());
			LogMF.debug(logger,
					"Going to verify if the following url exists {0}", url);
			return HttpUtils.urlExists(url);
		} catch (MalformedURLException e) {
			LogMF.debug(logger, "Malformed url {0}", url);
		}

		return false;

	}

	@Override
	public File download() throws IOException {
		File destination = new File(download.getSaveIn());
		URL url = new URL(download.getUrl());
		LogMF.debug(
				logger,
				"Going to download from the following url {0} saving it in {1}",
				url, destination.getAbsolutePath());
		FileUtils.copyURLToFile(url, destination);
		LogMF.info(logger, "Downloaded the file to {0}", destination);
		return destination;

	}

	@Override
	public void setDownload(Download download) {
		this.download = download;

	}

	@Override
	public Download getDownload() {
		return this.download;
	}

	@Override
	public File call() throws Exception {
		// TODO Auto-generated method stub
		return download();
	}

}

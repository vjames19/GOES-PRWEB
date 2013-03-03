package edu.uprm.ece.hydroclimate.download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.utils.HttpUtils;

/**
 * Downloader which changes the last two digits of the url, trying to match which file is on the server.
 * 
 * <p>e.g. The file on the server is http://goes.edu/goes1233</p>
 * <pre>The client of this class will supply http://goes.edu/goes12 as the url</pre>
 * Then the class will iterate from 0 to 60 trying to identify which is the correct url
 * @author Victor J.
 *
 */
public class LastDigitsChangerDownloader implements Downloader {

	private Download download;
	private URL url;
	private static Logger logger = Logger
			.getLogger(LastDigitsChangerDownloader.class);
	
	
	public LastDigitsChangerDownloader(){}
	
	public LastDigitsChangerDownloader(Download download){
		this.download = download;
	}

	@Override
	public boolean exists() {
		URL url = getValidUrl();
		if (url != null)
			this.url = url;
	
		return url != null;
	}

	@Override
	public File download() throws IOException {
		if (this.url == null) {
			if (!exists())
				return null;
		}
		File destination = new File(download.getSaveIn());
		LogMF.info(logger, "Downloading from url {0}", url.toExternalForm());
		HttpUtils.downloadToFile(url, destination);
		this.url = null;
		return destination;
	}

	private URL getValidUrl() {
		String url = download.getUrl();
		LogMF.debug(logger, "Got the following url to validate {0}", url);
		if(url == null)
			return null;
		URL workingUrl = null;
		for (int i = 20; i <= 60; i++) {
			String tempUrl = url + String.format("%02d", i);;

			try {
				workingUrl = new URL(tempUrl);
			} catch (MalformedURLException e) {
				// do nothing when its malformed
			}
			LogMF.debug(logger, "Going to verify the following url {0}",
					workingUrl.toExternalForm());

			if (HttpUtils.urlExists(workingUrl)) {
				return workingUrl;
			}
		}
		
		return null;
	}
	
	@Override
	public void setDownload(Download download) {
		this.download = download;
		
	}

	@Override
	public Download getDownload() {
		return this.download;
	}
	
}

package edu.uprm.ece.hydroclimate.utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	public static boolean urlExists(URL url) {
		if (url == null) {
			return true;
		}

		HttpURLConnection conn = null;
		int code = -1;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			code = conn.getResponseCode();

		} catch (IOException e) {
			LogMF.debug(logger, "Exception trying to verify the url {0}", e);

		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return code == HttpURLConnection.HTTP_OK;

	}

	public static void downloadToFile(URL url, File file) throws IOException {
		LogMF.debug(logger, "Going to download from {0}", url.toExternalForm());
		FileUtils.copyURLToFile(url, file);
		logger.debug("Downloaded");
	}

}

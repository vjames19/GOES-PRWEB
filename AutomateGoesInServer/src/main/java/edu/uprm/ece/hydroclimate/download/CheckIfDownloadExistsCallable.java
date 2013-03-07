package edu.uprm.ece.hydroclimate.download;

import java.util.concurrent.Callable;

public class CheckIfDownloadExistsCallable implements Callable<Boolean> {

	private Downloader downloader;

	public CheckIfDownloadExistsCallable(Downloader downloader) {
		this.downloader = downloader;
	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		return downloader.exists();
	}

}

package edu.uprm.ece.hydroclimate.stage.bundle;

import java.util.Date;

import edu.uprm.ece.hydroclimate.download.Download;

public class DownloadBundle extends DataBundle<Download> {

	
	public DownloadBundle(Download d, Date date){
		super(d, date);
	}
	
	public DownloadBundle(DownloadBundle bundle){
		this(new Download(bundle.data), bundle.date);
	}



	
}

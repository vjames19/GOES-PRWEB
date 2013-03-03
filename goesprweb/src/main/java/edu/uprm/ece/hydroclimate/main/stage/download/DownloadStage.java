package edu.uprm.ece.hydroclimate.main.stage.download;

import java.io.File;
import java.io.IOException;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.stage.BaseStage;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.download.Downloader;
import edu.uprm.ece.hydroclimate.download.DownloaderFactory;

@ConsumedTypes(Download.class)
@ProducedTypes(File.class)
public class DownloadStage extends BaseStage {


	private static final Logger logger = Logger.getLogger(DownloadStage.class);
	@Override
	public void process(Object obj) throws StageException {
		Download download = (Download) obj;
		Downloader downloader = DownloaderFactory.getDownloader(download);
		if(downloader == null){
			return;
		}
		logger.info("Going to download " + download);
		try {
			downloader.download();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

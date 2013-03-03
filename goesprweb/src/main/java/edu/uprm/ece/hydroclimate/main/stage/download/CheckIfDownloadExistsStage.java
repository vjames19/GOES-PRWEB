package edu.uprm.ece.hydroclimate.main.stage.download;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.download.Downloader;
import edu.uprm.ece.hydroclimate.download.DownloaderFactory;
import edu.uprm.ece.hydroclimate.main.stage.GoesStage;

@ConsumedTypes(Download.class)
@ProducesConsumed
public class CheckIfDownloadExistsStage extends GoesStage {


	@Override
	public void process(Object obj) throws StageException {
		Download download =(Download) obj;
		Downloader downloader = DownloaderFactory.getDownloader(download);
		if(downloader.exists()){
			this.emit(download);
		}
		//TODO: wait for download to appear
	}
}

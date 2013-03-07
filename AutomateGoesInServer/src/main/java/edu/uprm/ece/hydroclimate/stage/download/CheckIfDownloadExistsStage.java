package edu.uprm.ece.hydroclimate.stage.download;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.download.Downloader;
import edu.uprm.ece.hydroclimate.download.DownloaderFactory;
import edu.uprm.ece.hydroclimate.stage.GoesStage;
import edu.uprm.ece.hydroclimate.stage.bundle.DownloadBundle;

@ConsumedTypes(DownloadBundle.class)
@ProducesConsumed
public class CheckIfDownloadExistsStage extends GoesStage {

	@Override
	public void process(Object obj) throws StageException {
		DownloadBundle db = (DownloadBundle) obj;
		Download download = db.getData();
		Downloader downloader = DownloaderFactory.getDownloader(download);
		if (downloader.exists()) {
			this.emit(db);
		}
		// TODO: wait for download to appear
	}
}

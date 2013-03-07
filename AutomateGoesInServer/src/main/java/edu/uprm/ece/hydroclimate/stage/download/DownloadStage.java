package edu.uprm.ece.hydroclimate.stage.download;

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
import edu.uprm.ece.hydroclimate.stage.bundle.DownloadBundle;
import edu.uprm.ece.hydroclimate.stage.bundle.FileBundle;

@ConsumedTypes(DownloadBundle.class)
@ProducedTypes(FileBundle.class)
public class DownloadStage extends BaseStage {

	private static final Logger logger = Logger.getLogger(DownloadStage.class);

	@Override
	public void process(Object obj) throws StageException {
		DownloadBundle db = (DownloadBundle) obj;
		Download download = db.getData();
		Downloader downloader = DownloaderFactory.getDownloader(download);
		if (downloader == null) {
			logger.error("Couldn't find a downloader for " + download);
			return;
		}
		logger.info("Going to download " + download);
		try {
			File file = downloader.download();
			this.emit(new FileBundle(file, db.getDate()));
		} catch (IOException e) {
			logger.error("Error downloading", e);
		}
	}

}

package edu.uprm.ece.hydroclimate.stage.download;

import java.util.List;

import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.stage.GoesStage;
import edu.uprm.ece.hydroclimate.stage.bundle.DataBundle;
import edu.uprm.ece.hydroclimate.stage.bundle.DownloadBundle;

@ConsumedTypes(DataBundle.class)
@ProducedTypes(DownloadBundle.class)
public class DownloadSupplierStage extends GoesStage {

	@Override
	public void process(Object obj) throws StageException {
		DataBundle<?> db = (DataBundle<?>) obj;
		List<Download> downloads = properties.getDownloads();
		for (Download download : downloads) {
			this.emit(new DownloadBundle(new Download(download), db.getDate()));
		}
	}

}

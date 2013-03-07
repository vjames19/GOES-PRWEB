package edu.uprm.ece.hydroclimate.stage.download;

import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.stage.GoesStage;
import edu.uprm.ece.hydroclimate.stage.bundle.DownloadBundle;
import edu.uprm.ece.hydroclimate.utils.GoesUtils;

@ConsumedTypes(DownloadBundle.class)
@ProducesConsumed
public class FormatDownloadsStage extends GoesStage {

	private static final Logger logger = Logger
			.getLogger(FormatDownloadsStage.class);

	@Override
	public void process(Object obj) throws StageException {
		// TODO Auto-generated method stub
		DownloadBundle downloadBundle = (DownloadBundle) obj;
		Date date = downloadBundle.getDate();
		String absolute = manager.getDirectory(date).getAbsolutePath();
		Download download = downloadBundle.getData();
		Date temp = DateUtils.addDays(date, download.getDateOffset());
		download.setUrl(format(temp, download.getUrl()));
		download.setSaveIn(FilenameUtils.concat(absolute,
				format(temp, download.getSaveIn())));
		this.emit(new DownloadBundle(download, date));

	}

	private String format(Date date, String format) {
		return GoesUtils.stringFormatTime(format, date);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

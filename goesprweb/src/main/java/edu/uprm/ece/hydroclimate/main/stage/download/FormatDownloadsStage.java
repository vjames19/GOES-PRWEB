package edu.uprm.ece.hydroclimate.main.stage.download;

import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.download.Download;
import edu.uprm.ece.hydroclimate.main.stage.GoesStage;
import edu.uprm.ece.hydroclimate.utils.GoesUtils;

@ConsumedTypes(Download.class)
@ProducesConsumed
public class FormatDownloadsStage extends GoesStage{


	private static final Logger logger = Logger.getLogger(FormatDownloadsStage.class);
	@Override
	public void process(Object obj) throws StageException {
		// TODO Auto-generated method stub
		
		Date date = GoesStage.getDate();
		String absolute = manager.getDirectory(date).getAbsolutePath();
		List<Download> downloads = properties.getDownloads();
		for(Download download: downloads){
			Download tempDownload = new Download(download);
			
			Date temp = DateUtils.addDays(date, tempDownload.getDateOffset());
			tempDownload.setUrl(format(temp, download.getUrl()));
			tempDownload.setSaveIn(FilenameUtils.concat(absolute,
					format(temp, download.getSaveIn())));
			logger.info("Emmitting " + tempDownload);
			this.emit(tempDownload);
		}
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

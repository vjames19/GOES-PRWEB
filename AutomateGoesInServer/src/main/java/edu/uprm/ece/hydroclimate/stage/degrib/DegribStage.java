package edu.uprm.ece.hydroclimate.stage.degrib;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.degrib.Degribber;
import edu.uprm.ece.hydroclimate.stage.GoesStage;
import edu.uprm.ece.hydroclimate.stage.bundle.DegribBundle;
import edu.uprm.ece.hydroclimate.utils.GoesUtils;

@ConsumedTypes(DegribBundle.class)
@ProducesConsumed
public class DegribStage extends GoesStage {

	private static final Logger logger = Logger.getLogger(DegribStage.class);
	private String exeLocation;

	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);

	}

	@Override
	public void process(Object obj) throws StageException {
		DegribBundle db = (DegribBundle) obj;
		File input = db.getData();
		File outputDir = manager.getDirectory(db.getDate());
		int message = db.getMessage();
		String name = GoesUtils.stringFormatTime(db.getVariable()
				.getOutputName() + message, db.getDate());
		File outputFile = FileUtils.getFile(outputDir, name);
		boolean result = Degribber.degrib(input, outputFile, getExeLocation(),
				message);
		if (result) {
			this.emit(outputFile);
		}
	}

	public String getExeLocation() {
		return exeLocation;
	}

	public void setExeLocation(String exeLocation) {
		this.exeLocation = exeLocation;
	}

}

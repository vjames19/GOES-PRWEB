package edu.uprm.ece.hydroclimate.main.stage.degrib;

import java.io.File;
import java.util.Date;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.degrib.Degribber;
import edu.uprm.ece.hydroclimate.main.stage.GoesStage;
import edu.uprm.ece.hydroclimate.main.stage.pojo.DegribBundle;
import edu.uprm.ece.hydroclimate.main.stage.pojo.FileBundle;
import edu.uprm.ece.hydroclimate.utils.GoesUtils;

@ConsumedTypes(FileBundle.class)
@ProducesConsumed
public class DegribStage extends GoesStage {

	

	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);

		

	}

	@Override
	public void process(Object obj) throws StageException {
		DegribBundle db = (DegribBundle) obj;
		Degribber degribber = properties.getDegribber();
		Date date = db.getDate();
		File workingDir = manager.getDirectory(date);
		degribber.setDegribDir(workingDir);
		degribber.setOutputDir(workingDir);
		DegribVariable var = db.getVariable();
		String outputName = GoesUtils.stringFormatTime(var.getOutputName(), date);
		for(int message : var.getMessages()){
			File file = degribber.degribFile(db.getData(), message,outputName);
			if(file != null){
				this.emit(file);
			}
		}
	
	}

}

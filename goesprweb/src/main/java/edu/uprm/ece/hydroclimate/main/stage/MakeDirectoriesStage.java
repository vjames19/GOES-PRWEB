package edu.uprm.ece.hydroclimate.main.stage;

import java.io.File;
import java.util.Date;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;

import edu.uprm.ece.hydroclimate.main.stage.pojo.DataBundle;
import edu.uprm.ece.hydroclimate.main.stage.pojo.FileBundle;


@ConsumedTypes(Date.class)
@ProducedTypes(FileBundle.class)
public class MakeDirectoriesStage extends GoesStage {

	
	@Override
	public void init(StageContext context) {

		super.init(context);

		
	}
	
	@Override
	public void process(Object obj) throws StageException {
		Date date = (Date) obj;
		if(date == null){
			return;
		}
		File file = manager.createDirectoriesForDate(date);
		this.emit(new FileBundle(file, date));
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

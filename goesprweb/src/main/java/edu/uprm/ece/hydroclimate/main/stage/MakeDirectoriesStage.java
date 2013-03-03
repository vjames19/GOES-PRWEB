package edu.uprm.ece.hydroclimate.main.stage;

import java.util.Date;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;


@ConsumedTypes(Date.class)
@ProducesConsumed
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
		manager.createDirectoriesForDate(date);
		GoesStage.setDate(date);
		this.emit(obj);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

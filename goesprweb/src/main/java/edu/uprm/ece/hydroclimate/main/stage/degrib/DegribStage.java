package edu.uprm.ece.hydroclimate.main.stage.degrib;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import edu.uprm.ece.hydroclimate.main.stage.GoesStage;

@ConsumedTypes(File.class)
@ProducesConsumed
public class DegribStage extends GoesStage {

	private IOFileFilter wildcardFF;
	
	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);

	}

	@Override
	public void process(Object obj) throws StageException {
		
	}

}

package edu.uprm.ece.hydroclimate.stage.degrib;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.stage.GoesStage;
import edu.uprm.ece.hydroclimate.stage.bundle.DegribBundle;

@ConsumedTypes(DegribBundle.class)
@ProducesConsumed
public class DegribMessageSeparatorStage extends GoesStage {

	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);
	}

	@Override
	public void process(Object obj) throws StageException {
		DegribBundle db = (DegribBundle) obj;
		DegribVariable var = db.getVariable();
		for (Integer message : var.getMessages()) {
			DegribBundle temp = new DegribBundle(db);
			temp.setMessage(message);
			this.emit(temp);

		}
	}

}

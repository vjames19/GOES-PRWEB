package edu.uprm.ece.hydroclimate.stage;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.stage.BaseStage;

import edu.uprm.ece.hydroclimate.DirectoryManager;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;

public class GoesStage extends BaseStage {

	protected GoesProperties properties;
	protected DirectoryManager manager;

	
	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);
		GoesEnvironment environment = GoesEnvironment.getGoesEnvironment();
		properties = environment.getProperties();
		manager = environment.getManager();
	}

//	public static synchronized Date getDate() {
//		return date;
//	}
//	public static synchronized void setDate(Date date) {
//		GoesStage.date = date;
//	}

}

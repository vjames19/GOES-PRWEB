package edu.uprm.ece.hydroclimate.main.stage;

import java.util.Date;

import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.stage.BaseStage;

import edu.uprm.ece.hydroclimate.main.DirectoryManager;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;

public class GoesStage extends BaseStage {

	protected GoesProperties properties;
	protected DirectoryManager manager;
	private static Date date;
	
	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);
		GoesEnvironment environment = GoesEnvironment.getGoesEnvironment();
		properties = environment.getProperties();
		manager = environment.getManager();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static synchronized Date getDate() {
		return date;
	}
	public static synchronized void setDate(Date date) {
		GoesStage.date = date;
	}

}

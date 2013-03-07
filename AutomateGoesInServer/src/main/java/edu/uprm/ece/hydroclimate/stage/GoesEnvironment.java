package edu.uprm.ece.hydroclimate.stage;

import edu.uprm.ece.hydroclimate.DirectoryManager;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;

public class GoesEnvironment {

	private GoesProperties properties;
	private DirectoryManager manager;
	private static GoesEnvironment environment = null;
	
	private GoesEnvironment(){}
	
	public static GoesEnvironment getGoesEnvironment(){
		if(environment == null){
			environment = new GoesEnvironment();
		}
		
		return environment;
	}
	public GoesProperties getProperties() {
		return properties;
	}
	public void setProperties(GoesProperties properties) {
		this.properties = properties;
	}
	public DirectoryManager getManager() {
		return manager;
	}
	public void setManager(DirectoryManager manager) {
		this.manager = manager;
	}


}

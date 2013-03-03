package edu.uprm.ece.hydroclimate.main.process;

import edu.uprm.ece.hydroclimate.main.AutomateGoes;
/**
 * 
 * @author Victor J.
 *
 */
public abstract class GoesProcess implements Runnable{

	protected AutomateGoes goes;
	
	public GoesProcess(AutomateGoes goes){
		this.goes = goes;
	}

	public abstract void run();


}

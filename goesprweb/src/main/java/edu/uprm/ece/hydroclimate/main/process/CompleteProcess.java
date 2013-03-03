package edu.uprm.ece.hydroclimate.main.process;

import java.io.IOException;

import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.main.AutomateGoes;

/**
 * Runs the whole process download, format conversion, matlab, upload
 * 
 * @author Victor J.
 * 
 */
public class CompleteProcess extends GoesProcess {

	private static final Logger logger = Logger
			.getLogger(CompleteProcess.class);

	public CompleteProcess(AutomateGoes goes) {
		super(goes);
	}

	@Override
	public void run() {
		 goes.makeDirs();
		 goes.download();
		 goes.degrib();
		 try {
		 goes.matlab();
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 logger.error("IOException running the matlab command");
		 return;
		 }
		if (goes.waitForFinishedFile()) {
			goes.upload();
		}

	}

}

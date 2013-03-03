package edu.uprm.ece.hydroclimate.main.process;

import java.io.IOException;

import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.main.AutomateGoes;

public class MatlabAndUploadProcess extends GoesProcess {

	public MatlabAndUploadProcess(AutomateGoes goes) {
		super(goes);

	}
	public static final Logger logger = Logger.getLogger(MatlabAndUploadProcess.class);
	@Override
	public void run() {
		try {
			goes.matlab();
		} catch (IOException e) {
			logger.error("IOException running matlab", e);
		}
		goes.waitForFinishedFile();
		goes.upload();
		
	}

	


}

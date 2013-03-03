package edu.uprm.ece.hydroclimate.main.process;

import edu.uprm.ece.hydroclimate.main.AutomateGoes;

public class UploadOnlyProcess extends GoesProcess {

	public UploadOnlyProcess(AutomateGoes goes) {
		super(goes);

	}

	@Override
	public void run() {
		goes.upload();
	}

	

}

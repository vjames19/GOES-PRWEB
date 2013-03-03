package edu.uprm.ece.hydroclimate.main.process;

import edu.uprm.ece.hydroclimate.main.AutomateGoes;

public class DownloadAndDecompressOnlyProcess extends GoesProcess {

	public DownloadAndDecompressOnlyProcess(AutomateGoes goes) {
		super(goes);
	}

	@Override
	public void run() {
		goes.makeDirs();
		goes.download();
//		goes.degrib();
	}

	
}

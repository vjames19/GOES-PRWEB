package edu.uprm.ece.hydroclimate.properties;

import java.util.List;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.download.Download;

/**
 * All the properties needed by the system to run.
 * 
 * @author Victor J.
 * 
 */
public class GoesProperties {

	private List<Download> downloads;
	private List<DegribVariable> degribVariables;
	private String goesDir;
	private String matlabCmd;
	private String matlabWorkingDirectory;
	private long waitInterval;
	private int timesToWait;
	private List<String> variables;
	private EmailProperties email;

	public GoesProperties() {
	}

	public long getWaitInterval() {
		return waitInterval;
	}

	public void setWaitInterval(long waitInterval) {
		this.waitInterval = waitInterval;
	}

	public int getTimesToWait() {
		return timesToWait;
	}

	public void setTimesToWait(int timesToWait) {
		this.timesToWait = timesToWait;
	}

	public String getGoesDir() {
		return goesDir;
	}

	public void setGoesDir(String goesDir) {
		this.goesDir = goesDir;
	}

	public String getMatlabCmd() {
		return matlabCmd;
	}

	public void setMatlabCmd(String matlabCmd) {
		this.matlabCmd = matlabCmd;
	}

	public List<Download> getDownloads() {
		return downloads;
	}

	public void setDownloads(List<Download> downloads) {
		this.downloads = downloads;
	}

	public String getMatlabWorkingDirectory() {
		return matlabWorkingDirectory;
	}

	public void setMatlabWorkingDirectory(String matlabWorkingDirectory) {
		this.matlabWorkingDirectory = matlabWorkingDirectory;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}

	public List<DegribVariable> getDegribVariables() {
		return degribVariables;
	}

	public void setDegribVariables(List<DegribVariable> degribVariables) {
		this.degribVariables = degribVariables;
	}

	public EmailProperties getEmail() {
		return email;
	}

	public void setEmail(EmailProperties email) {
		this.email = email;
	}

}

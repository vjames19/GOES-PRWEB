package edu.uprm.ece.hydroclimate.properties;

import java.util.Collections;
import java.util.List;

import edu.uprm.ece.hydroclimate.degrib.Degribber;
import edu.uprm.ece.hydroclimate.download.Download;

/**
 * All the properties needed by the system to run.
 * 
 * @author Victor J.
 * 
 */
public class GoesProperties {

	private Degribber degribber;
	private List<Download> downloads;
	private FtpProperties ftp;
	private String goesDir;
	private String matlabCmd;
	private String matlabWorkingDirectory;
	private long waitInterval;
	private int timesToWait;
	private String logLayout;
	private String finishedFileName;
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

	public Degribber getDegribber() {
		return degribber;
	}

	public void setDegribber(Degribber degrib) {
		this.degribber = degrib;
	}

	public FtpProperties getFtp() {
		return ftp;
	}

	public void setFtp(FtpProperties ftp) {
		this.ftp = ftp;
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

	public EmailProperties getEmail() {
		return email;
	}

	public void setEmail(EmailProperties email) {
		this.email = email;
	}

	public List<Download> getDownloads() {
		return downloads;
	}

	public void setDownloads(List<Download> downloads) {
		this.downloads = downloads;
	}

	public String getLogLayout() {
		return logLayout;
	}

	public void setLogLayout(String logLayout) {
		this.logLayout = logLayout;
	}

	public String getMatlabWorkingDirectory() {
		return matlabWorkingDirectory;
	}

	public void setMatlabWorkingDirectory(String matlabWorkingDirectory) {
		this.matlabWorkingDirectory = matlabWorkingDirectory;
	}

	public String getFinishedFileName() {
		return finishedFileName;
	}

	public void setFinishedFileName(String finishedFileName) {
		this.finishedFileName = finishedFileName;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}
	
	public  void createGoesProperties(){
		this.degribber = new Degribber();
		this.downloads = Collections.emptyList();
		this.email = new EmailProperties();
		this.finishedFileName = "";
		this.ftp = new FtpProperties();
		this.variables = Collections.emptyList();
		
	}

}

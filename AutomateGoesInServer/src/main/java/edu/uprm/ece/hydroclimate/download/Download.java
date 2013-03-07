package edu.uprm.ece.hydroclimate.download;
//TODO verify if saveIn needs to be changed!
public class Download {


	
	private String url;
	/**
	 * Where to save the downloaded file
	 */
	private String saveIn;
	/**
	 * If an offset is specified is going to be added to yesterdays data
	 * in which the Automation is run.
	 */
	private int dateOffset;
	/**
	 * Fully qualified name of the Downloader Class
	 */
	private String downloaderClass;
	
	
	public Download(){}
	public Download(Download download){
		this.url = new String(download.url);
		this.saveIn = new String(download.saveIn);
		this.dateOffset = download.dateOffset;
		this.downloaderClass = new String(download.downloaderClass);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSaveIn() {
		return saveIn;
	}

	public void setSaveIn(String saveIn) {
		this.saveIn = saveIn;
	}

	public int getDateOffset() {
		return dateOffset;
	}

	public void setDateOffset(int dateOffset) {
		this.dateOffset = dateOffset;
	}

	public String getDownloaderClass() {
		return downloaderClass;
	}

	public void setDownloaderClass(String downloaderClass) {
		this.downloaderClass = downloaderClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Download [url=");
		builder.append(url);
		builder.append(", saveIn=");
		builder.append(saveIn);
		builder.append(", dateOffset=");
		builder.append(dateOffset);
		builder.append(", downloaderClass=");
		builder.append(downloaderClass);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	

}

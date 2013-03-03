package edu.uprm.ece.hydroclimate.properties;

public class FtpProperties {
	private String host;
	private String user;
	private String password;
	private int port;
	private String rootdir;
	public FtpProperties() {
		
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRootdir() {
		return rootdir;
	}
	public void setRootdir(String rootdir) {
		this.rootdir = rootdir;
	}
	
	
	
	

}

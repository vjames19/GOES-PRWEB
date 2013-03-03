package edu.uprm.ece.hydroclimate.utils;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import edu.uprm.ece.hydroclimate.properties.FtpProperties;

public class FtpUtils {

	/**
	 * 
	 * @param ftpProps
	 * @return {@link FTPClient}
	 * @throws IOException if anything goes wrong trying to connect to the server
	 */
	public static FTPClient getFtpClient(FtpProperties ftpProps) throws IOException{
		FTPClient ftp = new FTPClient();
		ftp.connect(ftpProps.getHost(), 21);
		ftp.login(ftpProps.getUser(), ftpProps.getPassword());
		return ftp;
	}

}

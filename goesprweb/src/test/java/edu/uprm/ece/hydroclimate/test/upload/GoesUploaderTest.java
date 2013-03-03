package edu.uprm.ece.hydroclimate.test.upload;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.uprm.ece.hydroclimate.properties.FtpProperties;
import edu.uprm.ece.hydroclimate.upload.GoesUploader;
import edu.uprm.ece.hydroclimate.utils.FtpUtils;

public class GoesUploaderTest {

	private GoesUploader uploader;
	public FtpProperties ftpProps; 
	private List<String> variables = Arrays.asList("actual_ET", "aquifer_recharge");
	@Before
	public void setUp() throws Exception {
		Gson gson = new Gson();
		File props = new File("src/test/resources/ftpprops.json");
		ftpProps = gson.fromJson(new FileReader(props), FtpProperties.class);
		
		uploader = new GoesUploader(ftpProps, variables, new File("src/test/resources/upload/"));
		
	}

	@After
	public void tearDown() throws Exception {
		uploader = null;
	}

	@Test
	public void testUpload() throws IOException {
		uploader.uploadVariables();
		File fileDir = new File("src/test/resources/upload/");
		
		FTPClient ftp = FtpUtils.getFtpClient(ftpProps);
		ftp.enterLocalPassiveMode();
		ftp.changeWorkingDirectory(ftpProps.getRootdir());
		
		for(String variable: variables){
			ftp.changeWorkingDirectory(variable);
			FilenameFilter filter = new WildcardFileFilter(variable + "*");
			File[] files = fileDir.listFiles(filter);
			System.out.println(Arrays.toString(files));
			String[] ftpFiles = ftp.listNames();
			System.out.println(Arrays.toString(ftpFiles));
			List<String> names = Arrays.asList(ftpFiles);
			for(File file: files){
				String name = file.getName();
				assertTrue(names.contains(name));
			}
			
			ftp.changeToParentDirectory();
			
		}
		
		
		
	}

	@Test
	public void testGetFilesToUpload() {
		
		File[] files = uploader.getFilesToUpload(variables.get(0) + "*");
		System.out.println(Arrays.toString(files));
		assertTrue(files.length ==1);
		for(File file : files){
			assertTrue(file.getName().startsWith(variables.get(0)));
		}
		System.out.println(Arrays.toString(files));
	}
	
	@Test
	public void testNoFilesReturned(){
		File[] files = uploader.getFilesToUpload("fkdnfdn*");
		assertTrue(files.length == 0);
	}
	
	

}

package hydroclimate.ece.uprm.test.upload;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import hydroclimate.ece.uprm.upload.FtpProperties;
import hydroclimate.ece.uprm.upload.GoesUploader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class GoesUploaderTest {

	private GoesUploader uploader;
	private List<String> variables = Arrays.asList("actual_ET");
	@Before
	public void setUp() throws Exception {
		Gson gson = new Gson();
		File props = new File("src/test/resources/ftpprops.json");
		FtpProperties ftpProps = gson.fromJson(new FileReader(props), FtpProperties.class);
		
		uploader = new GoesUploader(ftpProps, variables, new File("src/test/resources/upload"));
		
	}

	@After
	public void tearDown() throws Exception {
		uploader = null;
	}

	//@Test
	public void testUpload() throws IOException {
		uploader.upload();
	}

	@Test
	public void testGetFilesToUpload() {
		
		File[] files = uploader.getFilesToUpload(variables.get(0) + "*");
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

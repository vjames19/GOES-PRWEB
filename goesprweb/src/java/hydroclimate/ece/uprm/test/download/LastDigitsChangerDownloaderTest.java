package hydroclimate.ece.uprm.test.download;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hydroclimate.ece.uprm.download.LastDigitsChangerDownloader;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LastDigitsChangerDownloaderTest extends DownloaderTest {

	@Before
	public void setUp() throws Exception {
		
		this.downloader = this.getDownloader(LastDigitsChangerDownloader.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIfExists() {
		setDownload("lastdigitschangerdownload.json");
		assertTrue(downloader.exists());
	}
	@Test 
	public void testNotExists() {
		setDownload("downloadNotExists.json");
		assertFalse(downloader.exists());
	}
	
	@Test
	public void testDownload() throws IOException{
		setDownload("lastdigitschangerdownload.json");
		downloader.download();
		File downloaded = new File(download.getSaveIn());
		assertTrue(downloaded.exists());
	}
	


}

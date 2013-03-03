package hydroclimate.ece.uprm.test.download;

import hydroclimate.ece.uprm.download.Download;
import hydroclimate.ece.uprm.download.Downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import com.google.gson.Gson;

public class DownloaderTest {

	protected Download download;
	protected Downloader downloader;
	
	public DownloaderTest(){}
	
	protected  Reader getResourceFromDownloadFolder(String name) throws FileNotFoundException{
		String resource = "src/test/resources/download/" + name;
		return new FileReader(new File(resource));
		
	}
	
	protected Download getDownload(String name) throws FileNotFoundException{
		Gson gson = new Gson();
		Reader reader = getResourceFromDownloadFolder(name);
		return gson.fromJson(reader, Download.class);
	}
	
	protected Downloader getDownloader( Class<? extends Downloader> clazz){
		Downloader dl;
		try {
			dl = clazz.newInstance();
			return dl;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	protected void setDownload(String name){
		try {
			setDownload(getDownload(name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void setDownload(Download download){
		this.download = download;
		downloader.setDownload(download);
		
	}

}

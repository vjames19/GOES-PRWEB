package edu.uprm.ece.hydroclimate.test.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uprm.ece.hydroclimate.main.DirectoryManager;

public class DirectoryManagerTest {

	private DirectoryManager manager;
	private static final String rootDir = "src/test/resources/directory";
	private Date date = new Date(10216156);
	@Before
	public void setUp() throws Exception {
		manager = new DirectoryManager(rootDir);
	}

	@After
	public void tearDown() throws Exception {
		
		manager = null;
	}

	@Test
	public void testRootDir() {
		File root = manager.getRootDirectory();
		File expected = new File(rootDir);
		
		assertTrue(root.equals(expected));
	}
	
	@Test
	public void testCreateDirectories(){
		manager.createDirectoriesForDate(date);
		File dir = manager.getInputDirectory(date);
		assertTrue(dir.exists());
		dir = manager.getOutputDirectory(date);
		assertTrue(dir.exists());
		
		
	}
	
	@Test
	public void testCreateLogDir(){
		assertTrue(manager.getLogDirectory().exists());
		File expected = new File(rootDir + "/LOGS/");
		System.out.println(manager.getLogDirectory().getAbsolutePath());
		System.out.println(expected.getAbsolutePath());
		assertTrue(expected.equals(manager.getLogDirectory()));
		
	}

}

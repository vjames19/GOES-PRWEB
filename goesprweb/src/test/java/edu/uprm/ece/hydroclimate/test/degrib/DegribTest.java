package edu.uprm.ece.hydroclimate.test.degrib;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.degrib.Degribber;

public class DegribTest {

	private Degribber degrib;
	private File degribFile = new File("src/test/resources/degrib/degrib.json");
	private List<DegribVariable> variables;
	private File ioDir = new File("src/test/resources/degrib/");

	@Before
	public void setUp() throws Exception {
		Gson gson = new Gson();
		degrib = gson.fromJson(new FileReader(degribFile), Degribber.class);
		variables = degrib.getVariables();
		
		degrib.setDegribDir(ioDir);
		degrib.setOutputDir(ioDir);
	}
	
	

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testFilesReturned() {
		Collection<File> files = degrib.getGribFiles(variables.get(0));
		for (File file : files) {
			assertTrue(FilenameUtils.wildcardMatch(file.getName(), variables
					.get(0).getName()));
		}

	}
	
	@Test
	public void testDegribVariables(){
		degrib.degribTheVariables();
		Collection<File> files = FileUtils.listFiles(ioDir, new WildcardFileFilter("wind*"), null);
		assertTrue(files.size() == 9);
	}

}

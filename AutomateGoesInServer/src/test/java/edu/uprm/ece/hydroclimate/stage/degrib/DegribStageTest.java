package edu.uprm.ece.hydroclimate.stage.degrib;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.stage.AbstractGoesStageTest;

public class DegribStageTest extends AbstractGoesStageTest {

	
	public DegribStageTest(String testName) throws JsonSyntaxException,
			JsonIOException, FileNotFoundException {
		super(testName);
		// TODO Auto-generated constructor stub
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		DegribStage s = new DegribStage();
	
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test() {
		
	}

}

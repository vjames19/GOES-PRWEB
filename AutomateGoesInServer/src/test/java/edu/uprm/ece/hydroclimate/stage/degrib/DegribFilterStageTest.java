package edu.uprm.ece.hydroclimate.stage.degrib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.commons.pipeline.Stage;
import org.apache.commons.pipeline.StageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.stage.AbstractGoesStageTest;
import edu.uprm.ece.hydroclimate.stage.bundle.DegribBundle;
import edu.uprm.ece.hydroclimate.stage.bundle.FileBundle;

public class DegribFilterStageTest extends AbstractGoesStageTest {

	public DegribFilterStageTest(String testName) throws JsonSyntaxException,
			JsonIOException, FileNotFoundException {
		super(testName);
	}



	@Before
	protected void setUp() throws Exception {
		super.setUp();
		stage = new DegribFilterStage();
		init(stage);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		stage = null;
	}

	@Test
	public void testTheFilterProcess() throws StageException {
		File testFile = new File(
				"/AutomateGoesInServer/src/test/resources/stage/airtemp_20130304.bin");
		FileBundle test = new FileBundle(testFile, new Date());
		stage.process(test);

		assertEquals("Size not equal to 1", 1, testFeeder.receivedValues.size());
		DegribBundle emitted = (DegribBundle) testFeeder.receivedValues.get(0);

		assertEquals("Files are not equal", testFile, emitted.getData());

		testFile = new File("winde3u89y43.bin");
		stage.process(new FileBundle(testFile, null));
		assertEquals("Size less than not equal to 2", 2,
				testFeeder.receivedValues.size());
		emitted = (DegribBundle) testFeeder.receivedValues.get(1);
		assertEquals("Files are not equal", testFile, emitted.getData());

	}

	@Test
	public void testWithWrongData() throws StageException {

		File testFile = new File("dsj_airtemp_sjnf.bin");
		stage.process(new FileBundle(testFile, null));
		assertEquals("Size less than not equal to 0", 0,
				testFeeder.receivedValues.size());

	}

}

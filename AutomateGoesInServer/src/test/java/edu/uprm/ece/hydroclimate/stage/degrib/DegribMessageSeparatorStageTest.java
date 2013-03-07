package edu.uprm.ece.hydroclimate.stage.degrib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pipeline.StageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.stage.AbstractGoesStageTest;
import edu.uprm.ece.hydroclimate.stage.bundle.DegribBundle;

public class DegribMessageSeparatorStageTest extends AbstractGoesStageTest {

	public DegribMessageSeparatorStageTest(String testName)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		super(testName);
		// TODO Auto-generated constructor stub
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		stage = new DegribMessageSeparatorStage();
		init(stage);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testMessageEmmitted() throws StageException {
		assertTrue("Need variables to be able to test", properties.getDegribVariables().size() > 0);
		DegribVariable var = properties.getDegribVariables().get(0);
		int expected = var.getMessages().size();
		Set<Integer> expectedMessages = new HashSet<Integer>(var.getMessages());
		Set<Integer> receivedMessages = new HashSet<Integer>(var.getMessages().size());
		File testFile = new File("jdnajfsndf");
		Date testDate = new Date();
		stage.process(new DegribBundle(testFile,var,testDate));
		assertEquals("Size is not the one expected", expected, testFeeder.receivedValues.size());
		for(Object obj: testFeeder.receivedValues){
			DegribBundle  db = (DegribBundle) obj;
			assertEquals("File is not equal to " + testFile, testFile, db.getData());
			assertEquals("Date is not equal", testDate, db.getDate());
			receivedMessages.add(db.getMessage());
		}
		
		assertEquals("Messages emmitted are not equal", expectedMessages, receivedMessages);
		System.out.println(expectedMessages);
		System.out.println(receivedMessages);
		

	}

}

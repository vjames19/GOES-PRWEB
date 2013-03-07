package edu.uprm.ece.hydroclimate.stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.pipeline.Stage;
import org.apache.commons.pipeline.stage.AbstractStageTest;
import org.junit.After;
import org.junit.Before;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.DirectoryManager;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;

public class AbstractGoesStageTest extends AbstractStageTest {
	//TODO: test for received and emitted values
	protected Stage stage;
	protected GoesProperties properties;
	public AbstractGoesStageTest(String testName) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		super(testName);
		String properties = "src/test/resources/goesproperties.json";
		File props = new File(properties);
		Gson gson = new Gson();
		this.properties = gson.fromJson(new FileReader(props),
				GoesProperties.class);
		
		GoesEnvironment env = GoesEnvironment.getGoesEnvironment();
		env.setManager(new DirectoryManager(this.properties.getGoesDir()));
		env.setProperties(this.properties);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		stage = null;
	}




}

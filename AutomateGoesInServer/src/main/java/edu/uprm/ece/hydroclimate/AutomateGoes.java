package edu.uprm.ece.hydroclimate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.pipeline.Feeder;
import org.apache.commons.pipeline.Pipeline;
import org.apache.commons.pipeline.PipelineCreationException;
import org.apache.commons.pipeline.config.DigesterPipelineFactory;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.properties.GoesProperties;
import edu.uprm.ece.hydroclimate.stage.GoesEnvironment;

/**
 * Hello world!
 * 
 */

//TODO: make inmutable  classes
public class AutomateGoes {

	// private DirectoryManager manager;
	// private ExecutorService pool;
	// private GoesProperties props;
	// private Date date;
	// public AutomateGoes(String rootDirectory){
	// manager = new DirectoryManager(rootDirectory);
	// }
	//
	// public void makeDirectories(){
	// manager.createDirectoriesForDate(date)
	// }
	//

	public static void main(String[] args) throws ParseException, MalformedURLException, JsonSyntaxException, JsonIOException, FileNotFoundException, PipelineCreationException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
		Date start = dateFormatter.parse("2013/03/04"), end = dateFormatter
				.parse("2013/03/04");

		File configFile = new File("src/test/resources/conf.xml");
		//File configFile = new File("src/test/resources/conf_degrib.xml");
		// Log log = LogFactory.getLog("");
		// log.debug("Starting");
		String properties = "src/test/resources/goesproperties.json";
		File props = new File(properties);
		Gson gson = new Gson();
		GoesProperties goesproperties = gson.fromJson(new FileReader(props),
				GoesProperties.class);

		Logger logger = Logger.getRootLogger();

		logger.debug("Starting");
		GoesEnvironment env = GoesEnvironment.getGoesEnvironment();
		env.setManager(new DirectoryManager(goesproperties.getGoesDir()));
		env.setProperties(goesproperties);

		DigesterPipelineFactory factory = new DigesterPipelineFactory(
				configFile.toURI().toURL());
		Pipeline pipeline = factory.createPipeline();
		Feeder feeder = pipeline.getSourceFeeder();
		// pipeline.setEnv(key, value)
		// pipeline.setEnv("goesProperties", goesproperties );
		//
		// pipeline.setEnv("directoryManager", new
		// DirectoryManager(goesproperties.getGoesDir()));

		while (!start.after(end)) {
			feeder.feed(start);
			//feeder.feed(new FileBundle(new File("src/test/resources/stage/"), start));
			start = DateUtils.addDays(start, 1);
		}

		System.out.println("Pipeline created, about to begin processing...");
		StopWatch sw = new StopWatch();
		sw.start();
		pipeline.run();
		System.out.println("Executing matlab...");
		sw.stop();
		System.out.println(sw.toString());

	}

//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
}

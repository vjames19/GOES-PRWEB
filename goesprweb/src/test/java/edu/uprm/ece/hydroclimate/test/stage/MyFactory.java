package edu.uprm.ece.hydroclimate.test.stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.pipeline.Feeder;
import org.apache.commons.pipeline.Pipeline;
import org.apache.commons.pipeline.PipelineCreationException;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.config.DigesterPipelineFactory;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.uprm.ece.hydroclimate.main.DirectoryManager;
import edu.uprm.ece.hydroclimate.main.stage.GoesEnvironment;
import edu.uprm.ece.hydroclimate.properties.GoesProperties;


public class MyFactory {

	/**
	 * @param args
	 * @throws PipelineCreationException 
	 * @throws StageException 
	 * @throws MalformedURLException 
	 * @throws FileNotFoundException 
	 * @throws JsonIOException 
	 * @throws JsonSyntaxException 
	 */
	public static void main(String[] args) throws PipelineCreationException, StageException, MalformedURLException, JsonSyntaxException, JsonIOException, FileNotFoundException {
		File configFile = new File("src/test/resources/conf.xml");
//		Log log = LogFactory.getLog("");
//		log.debug("Starting");
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
		Date today = new Date();
		today = DateUtils.addDays(today, -7);
        DigesterPipelineFactory factory = new DigesterPipelineFactory(configFile.toURI().toURL());
        Pipeline pipeline = factory.createPipeline();
        Feeder feeder = pipeline.getSourceFeeder();
//        pipeline.setEnv(key, value)
//        pipeline.setEnv("goesProperties", goesproperties );
//       
//        pipeline.setEnv("directoryManager", new DirectoryManager(goesproperties.getGoesDir()));
        StopWatch sw = new StopWatch();
        sw.start();
        for(int i=0; i< 2; i++){
        	feeder.feed(DateUtils.addDays(today,i));
        }
        
        System.out.println("Pipeline created, about to begin processing...");
        System.out.println(pipeline.getStages());
        pipeline.start();
        pipeline.finish();
        sw.stop();
        System.out.println(sw.toString());
        
        
	}

}

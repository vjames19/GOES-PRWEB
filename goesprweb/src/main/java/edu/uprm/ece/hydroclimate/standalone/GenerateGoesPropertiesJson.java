package edu.uprm.ece.hydroclimate.standalone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.uprm.ece.hydroclimate.properties.GoesProperties;

public class GenerateGoesPropertiesJson {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GoesProperties props = new GoesProperties();
		props.createGoesProperties();
		GsonBuilder builder = new GsonBuilder();
		Gson gson =builder.setPrettyPrinting().serializeNulls().create();
		
		File  output = new File("src/test/resources/goesproperties.json");
		FileWriter writer = new FileWriter(output);
		gson.toJson(props, writer);
		IOUtils.closeQuietly(writer);
		
	}

}

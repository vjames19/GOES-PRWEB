package edu.uprm.ece.hydroclimate.standalone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.google.gson.Gson;

public class ParseCsvToJsonWeightedLocation {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File output = new File("src/main/resources/weightedLoc.json");
		File input = new File("src/main/resources/solar.csv");
		
		
		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(input)));
		String[] nextLine = null;
		
		WeightedLocations locs = new WeightedLocations();
		List<WeightedLocation> l = new LinkedList<WeightedLocation>();
		locs.setLocations(l);
		
		while((nextLine = reader.readNext()) != null){
			if(nextLine[2].equalsIgnoreCase("nan")){
				continue;
			}
			
			l.add(new WeightedLocation(Double.parseDouble(nextLine[0]),Double.parseDouble(nextLine[1]),Double.parseDouble(nextLine[2]) ));
		}
		
		Gson gson = new Gson();
		FileWriter writer = new FileWriter(output);
		gson.toJson(locs, writer);
		reader.close();
		writer.close();
	}

}

package edu.uprm.ece.hydroclimate.standalone;

public class WeightedLocation {

	private double lat;
	private double lon;
	private double weight;
	
	public WeightedLocation (){}
	
	

	public WeightedLocation(double lat, double lon, double weight) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.weight = weight;
	}



	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	};
	
	
	

}

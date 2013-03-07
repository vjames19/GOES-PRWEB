package edu.uprm.ece.hydroclimate.stage.bundle;

import java.util.Date;

public class DataBundle<T> {

	protected T data;
	protected Date date;
	
	public DataBundle(){
		
	}
	
	
	public DataBundle(T data, Date date){
		this.setDate(date);
		this.setData(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = new Date(date.getTime());
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataBundle [data=");
		builder.append(data);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
	
}

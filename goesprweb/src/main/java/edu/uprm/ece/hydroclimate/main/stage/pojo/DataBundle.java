package edu.uprm.ece.hydroclimate.main.stage.pojo;

import java.util.Date;

public class DataBundle<T> {

	private T data;
	private Date date;
	
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
		this.date = date;
	}
}

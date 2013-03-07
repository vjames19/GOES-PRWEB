package edu.uprm.ece.hydroclimate.main.stage.pojo;

import java.io.File;
import java.util.Date;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;

public class DegribBundle extends DataBundle<File> {

	private DegribVariable variable;
	public DegribBundle(File file, DegribVariable variable, Date date){
		super(file,date);
		this.setVariable(variable);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public DegribVariable getVariable() {
		return variable;
	}
	public void setVariable(DegribVariable variable) {
		this.variable = variable;
	}

}

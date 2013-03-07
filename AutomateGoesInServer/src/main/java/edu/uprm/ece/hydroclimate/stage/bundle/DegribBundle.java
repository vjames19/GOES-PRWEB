package edu.uprm.ece.hydroclimate.stage.bundle;

import java.io.File;
import java.util.Date;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;

public class DegribBundle extends DataBundle<File> {

	private DegribVariable variable;
	private int message;

	public DegribBundle(File file, DegribVariable variable, Date date) {
		this(file, variable, date, 0);
	}

	public DegribBundle(File file, DegribVariable variable, Date date,
			int message) {
		super(file, date);
		this.setVariable(variable);
		this.setMessage(message);

	}

	public DegribBundle(DegribBundle db) {
		this(db.getData().getAbsoluteFile(), new DegribVariable(
				db.getVariable()), db.getDate(), db.getMessage());

	}

	public DegribVariable getVariable() {
		return variable;
	}

	public void setVariable(DegribVariable variable) {
		this.variable = variable;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DegribBundle [variable=");
		builder.append(variable);
		builder.append(", message=");
		builder.append(message);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}

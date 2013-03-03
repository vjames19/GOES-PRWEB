package edu.uprm.ece.hydroclimate.degrib;

import java.util.List;

public class DegribVariable {
	
	private String name;
	private String outputName;
	private List<Integer> messages;
	
	public DegribVariable(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public List<Integer> getMessages() {
		return messages;
	}

	public void setMessages(List<Integer> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DegribVariable [name=");
		builder.append(name);
		builder.append(", outputName=");
		builder.append(outputName);
		builder.append(", messages=");
		builder.append(messages);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}

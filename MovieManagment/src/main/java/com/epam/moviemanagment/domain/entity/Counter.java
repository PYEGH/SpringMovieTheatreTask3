package com.epam.moviemanagment.domain.entity;

public class Counter {
	private int id;
	private String type;
	private String additionalInfo;
	private int value;

	
	public Counter() {
		
	}

	public Counter(int id, String type, String additionalInfo, int value) {
		super();
		this.id = id;
		this.type = type;
		this.additionalInfo = additionalInfo;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}

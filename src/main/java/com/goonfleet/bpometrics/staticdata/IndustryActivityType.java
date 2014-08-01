package com.goonfleet.bpometrics.staticdata;

public enum IndustryActivityType {

	/*
	 * 0	None
		1	Manufacturing
		2	Researching Technology
		3	Researching Time Productivity
		4	Researching Material Productivity
		5	Copying
		6	Duplicating
		7	Reverse Engineering
		8	Invention
	 */
	
	NONE(0),
	MANUFACTURING(1),
	RESEARCH_TECH(2),
	RESEARCH_TIME(3),
	RESEARCH_MATERIAL(4),
	COPYING(5),
	DUPLICATING(6),
	REVERSE_ENGINEERING(7),
	INVENTION(8);
	
	private IndustryActivityType(int activityId){
		this.id = activityId;
	}
	
	private int id;
	
	public int getId(){
		return id;
	}
}

package com.goonfleet.bpometrics.prices;

public enum GoonmetricsRegions {
	JITA("60003760"),
	DEKLEIN("61000072");
	
	
	private String regiondId;
	
	private GoonmetricsRegions(String regionId){
		this.regiondId = regionId;
	}
	
	public String getRegionId(){
		return regiondId;
	}
}

package com.goonfleet.bpometrics.staticdata;

public interface ItemTypeCsvFilter {
	public boolean acceptCsvLine(String[] csvLine);
}
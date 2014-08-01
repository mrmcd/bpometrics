package com.goonfleet.bpometrics.prices;

import com.goonfleet.bpometrics.staticdata.ItemType;

public class GoonmetricsApiStubs {
	
	
	
	public static final String PRICE_DATA_REGION_ID_STR = "REGION_ID";
	public static final String PRICE_DATA_ITEM_LIST_STR = "ITEM_LIST";
	
	public static final String PRICE_DATA = "http://goonmetrics.com/api/price_data/?station_id="+PRICE_DATA_REGION_ID_STR+"&type_id="+PRICE_DATA_ITEM_LIST_STR;
	
	public static String buildApiQueryString(GoonmetricsRegions region, ItemType... items){
		
		StringBuilder itemList = new StringBuilder();
		for (ItemType item : items){
			itemList.append(item.getTypeId()).append(",");
		}
		
		String resultString = PRICE_DATA.replaceAll(PRICE_DATA_REGION_ID_STR, region.getRegionId()).replaceAll(PRICE_DATA_ITEM_LIST_STR, itemList.toString());
		return resultString;
	}
}

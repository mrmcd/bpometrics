package com.goonfleet.bpometrics.staticdata;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class ItemType {
	
	private int typeId;
	private int groupId;	
	private String typeName;	
	private double volume;
	private int portionSize;
		
	public ItemType(int typeId, int groupId, String typeName, double volume, int portionSize) {
		this.typeId = typeId;
		this.groupId = groupId;
		this.typeName = typeName;
		this.volume = volume;
		this.portionSize = portionSize;
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public double getVolume() {
		return volume;
	}
	
	public int getPortionSize() {
		return portionSize;
	}
	
	public String toString(){
		return "id="+typeId+": { name=" + typeName + ", group=" + groupId + ", volume=" + volume + " m3, portion=" + portionSize + " }";
	}
	
	public static final int TYPE_ID_INDEX = 0;
	public static final int GROUP_ID_INDEX = 1;
	public static final int TYPE_NAME_INDEX = 2;
	public static final int VOLUME_INDEX = 5;
	public static final int PORTION_SIZE_INDEX = 7;
	public static final int MARKET_GROUP_INDEX = 11;
	
	
	public static List<ItemType> loadMarketableTypes(FileReader csvFile, ItemTypeCsvFilter filter) throws IOException{
		LinkedList<ItemType> itemsList = new LinkedList<ItemType>();
		
		CSVReader reader = new CSVReader(csvFile);
		String[] nextLine = reader.readNext();
		
		//skip header
		nextLine = reader.readNext();
		
		while (nextLine != null){
			if (filter.acceptCsvLine(nextLine)) {				
				//passes filter, load into list
				int typeId = Integer.parseInt(nextLine[TYPE_ID_INDEX]);
				int groupId = Integer.parseInt(nextLine[GROUP_ID_INDEX]);
				String name = nextLine[TYPE_NAME_INDEX];
				double volume = Double.parseDouble(nextLine[VOLUME_INDEX]);
				int portion = Integer.parseInt(nextLine[PORTION_SIZE_INDEX]);
				ItemType item = new ItemType(typeId,groupId,name,volume,portion);
				itemsList.add(item);
			}				
			//next line
			nextLine = reader.readNext();
		}
		reader.close();
		return itemsList;
	}
	
	public static Map<Integer, ItemType> assembleItemTypeMap(List<ItemType> itemList){
		Map<Integer, ItemType> outputMap = new HashMap<Integer, ItemType>(itemList.size());
		for (ItemType item : itemList){
			outputMap.put(item.getTypeId(), item);
		}
		return outputMap;
	}
	
	public static void main(String[] args) throws IOException{
		System.out.println("Quick test of loading marketable items from csv dump:");
		FileReader file = new FileReader("src/main/resources/invTypes.csv");
		List<ItemType> types = loadMarketableTypes(file, new MarketableItemFilter());
		System.out.println("loaded " + types.size() + " type records.");		
		System.out.println("Item  @ 666 : " + types.get(666));
		System.out.println("Item  @ 236 : " + types.get(236));
		System.out.println("Item  @ 5681 : " + types.get(5681));
		System.out.println("Item  @ 3324 : " + types.get(3324));
		
		System.out.println("Assembling lookup map...");
		Map<Integer, ItemType> map = assembleItemTypeMap(types);
		
		System.out.println("Item ID 1157 : " + map.get(1157));
	}
	
	public static class MarketableItemFilter implements ItemTypeCsvFilter {
		public boolean acceptCsvLine(String[] csvLine) {
			String marketGroup = csvLine[MARKET_GROUP_INDEX];
			return ( (marketGroup != null ) && (!marketGroup.equals("")) ); //accept if it has a market group
		}		
	}
}

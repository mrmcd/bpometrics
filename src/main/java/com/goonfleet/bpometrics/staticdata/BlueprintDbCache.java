package com.goonfleet.bpometrics.staticdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

public class BlueprintDbCache {

	private Set<Integer> blueprintItemTypeIds;
	private Map<Integer, BlueprintRecipe> blueprintProductionMap;
	private Map<Integer, ItemType> marketItemList;	
	private IndustryActivityType dbActivityType;
	
	public BlueprintDbCache(File invTypesCsv, File industryActivityCsv, 
							File industryActivityMaterialsCsv, File industryActivityProductsCsv,
							IndustryActivityType dbActivityType) throws IOException{
		
		this.dbActivityType = dbActivityType;
		
		//initialize market items
		initMarketItems(invTypesCsv);
		
		//initialize blueprint id set
		initBlueprintIdSet(industryActivityCsv);
		
		//initialize blueprint recipe map
		initRecipeMap(invTypesCsv);
		
		//populate materials and products
		populateMaterials(industryActivityMaterialsCsv);
		populateProducts(industryActivityProductsCsv);
	}
	
	private BlueprintRecipe queryBlueprint(int blueprintItemId){
		return blueprintProductionMap.get(blueprintItemId);
	}
	
	private void initMarketItems(File invTypesCsv) throws IOException{
		List<ItemType> itemsList = ItemType.loadMarketableTypes(new FileReader(invTypesCsv), new ItemType.MarketableItemFilter());
		marketItemList = ItemType.assembleItemTypeMap( itemsList );
	}
	
	/*
	 * TYPEID,TIME,ACTIVITYID
		681,600,1
		681,210,3
		681,210,4

	 * 
	 */
	
	private static final int IA_TYPEID_INDEX = 0;
	private static final int IA_ACTIVITY_ID_INDEX = 2;
	
	private void initBlueprintIdSet(File industryActivityCsv) throws IOException{
		blueprintItemTypeIds = new HashSet<Integer>();
		
		CSVReader reader = new CSVReader(new FileReader(industryActivityCsv));
		String[] line = reader.readNext();
		line = reader.readNext(); //skip header
		while (line != null){
			if (dbActivityType.getId() == Integer.parseInt(line[IA_ACTIVITY_ID_INDEX])){
				blueprintItemTypeIds.add(Integer.parseInt(line[IA_TYPEID_INDEX]));
			}
			line = reader.readNext();
		}
		reader.close();
	}
	
	private class BlueprintItemTypeFilter implements ItemTypeCsvFilter {

		public boolean acceptCsvLine(String[] csvLine) {
			int typeId = Integer.parseInt(csvLine[ItemType.TYPE_ID_INDEX]);
			return blueprintItemTypeIds.contains(typeId);
		}
		
	}
	
	private void initRecipeMap(File invTypesCsv) throws IOException{		
		List<ItemType> blueprintList = ItemType.loadMarketableTypes(new FileReader(invTypesCsv), new BlueprintItemTypeFilter());
		blueprintProductionMap = new HashMap<Integer, BlueprintRecipe>(blueprintList.size());
		for (ItemType bpItem : blueprintList){
			BlueprintRecipe recipe = new BlueprintRecipe(bpItem, dbActivityType);
			blueprintProductionMap.put(bpItem.getTypeId(), recipe);
		}
	}
		
	/*
	 * TYPEID,ACTIVITYID,MATERIALTYPEID,QUANTITY,CONSUME
		681,1,38,86,1
		682,1,38,133,1
		683,1,34,22222,1
		683,1,35,8000,1
		683,1,36,2444,1
	 */
	private static final int IAM_BP_ID_INDEX = 0;
	private static final int IAM_ACTIVITY_TYPE_ID_INDEX = 1;
	private static final int IAM_MATERIAL_ID_INDEX = 2;
	private static final int IAM_QUANTITY_INDEX = 3;
	private static final int IAM_CONSUMED_INDEX = 4;
	
	private void populateMaterials(File industryActivityMaterialsCsv) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(industryActivityMaterialsCsv));
		String[] line = reader.readNext();
		line = reader.readNext();
		while (line != null){
			int activityType = Integer.parseInt(line[IAM_ACTIVITY_TYPE_ID_INDEX]);
			if ( activityType == dbActivityType.getId() ){
				int blueprintId = Integer.parseInt(line[IAM_BP_ID_INDEX]);
				int materialId = Integer.parseInt(line[IAM_MATERIAL_ID_INDEX]);
				int quantity = Integer.parseInt(line[IAM_QUANTITY_INDEX]);
				boolean consumed = "1".equals(line[IAM_CONSUMED_INDEX]);
				
				//get recipe object
				BlueprintRecipe bp = blueprintProductionMap.get(blueprintId);
				//get material object
				ItemType item = marketItemList.get(materialId);
				//add to recipe
				bp.addMaterialInput(item, quantity, consumed);
			}
			line = reader.readNext();
		}
		reader.close();
	}
	
	
	/*
	 * 
	 * TYPEID,ACTIVITYID,PRODUCTTYPEID,QUANTITY
		681,1,165,1
		682,1,166,1
		683,1,582,1
		684,1,583,1
		684,8,11177,1
	 */
	
	private static final int IAP_BP_ID_INDEX = 0;
	private static final int IAP_ACTIVITY_TYPE_INDEX = 1;
	private static final int IAP_PRODUCT_TYPE_INDEX = 2;
	private static final int IAP_QUANTITY_INDEX = 3;
	
	private void populateProducts(File industryActivityProductsCsv) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(industryActivityProductsCsv));
		String[] line = reader.readNext();
		line = reader.readNext();
		while (line != null){
			int activityType = Integer.parseInt(line[IAP_ACTIVITY_TYPE_INDEX]);
			if ( activityType == dbActivityType.getId() ){
				int blueprintId = Integer.parseInt(line[IAP_BP_ID_INDEX]);
				int productId = Integer.parseInt(line[IAP_PRODUCT_TYPE_INDEX]);
				int quantity = Integer.parseInt(line[IAP_QUANTITY_INDEX]);
				
				//get recipe object
				BlueprintRecipe bp = blueprintProductionMap.get(blueprintId);
				//get material object
				ItemType item = marketItemList.get(productId);
				//add to recipe
				bp.addProductOutput(item, quantity);
			}
			line = reader.readNext();
		}
		reader.close();
	}
	
	public static void main(String[] args) throws IOException{
		System.out.println("Testing Industry blueprint constructor database.");
		System.out.print("Loading industry db into memory...");
		File itemCsv = new File("src/main/resources/invTypes.csv");
		File activityCsv = new File("src/main/resources/industryActivity.csv");
		File activityMaterialsCsv = new File("src/main/resources/industryActivityMaterials.csv");
		File activityProductsCsv = new File("src/main/resources/industryActivityProducts.csv");
		
		BlueprintDbCache bpoDb = new BlueprintDbCache(itemCsv, activityCsv, activityMaterialsCsv, activityProductsCsv, IndustryActivityType.MANUFACTURING);
		
		System.out.println("done.");
		
		System.out.println("Enter the BPO or BPC item id to query materials and output. Enter -1 to exit.");
		
		String query = readTextPrompt("Blueprint DB ID", "1157");
		while(!"-1".equals(query)){
			int bpId = 0;
			try{
				bpId = Integer.parseInt(query);
			}catch (IllegalArgumentException e){
				System.out.println("Not a valid integer");
			}
			
			BlueprintRecipe recipe = bpoDb.queryBlueprint(bpId);
			if (recipe != null){
				System.out.println(recipe.generateProductionReport());
			}else{
				System.out.println("Blueprint Not Found");
			}
			
			query = readTextPrompt("Blueprint DB ID", query);
		}
	}
	
	private static BufferedReader lineReader;
	private static String readTextPrompt(String prompt, String defaultValue) throws IOException{
		if (lineReader == null){
			lineReader = new BufferedReader(new InputStreamReader(System.in));
		}
		if (defaultValue != null){
			System.out.print(">"+prompt+" ["+defaultValue+"]: ");
		}else{
			System.out.print(">"+prompt+": ");
		}
		String resp = lineReader.readLine();
		if (resp == null){
			return defaultValue;
		}
		resp = resp.trim();
		if (resp.length() == 0){
			return defaultValue;
		}
		
		return resp;		
	}
}

package com.goonfleet.bpometrics.staticdata;

import java.util.LinkedList;
import java.util.List;



public class BlueprintRecipe {

	private ItemType blueprintId;	
	private IndustryActivityType activityType;
	
	private List<MaterialInput> materials;
	private List<ProductOutput> products;
		
	public BlueprintRecipe(ItemType blueprintId, IndustryActivityType activityType) {
		super();
		this.blueprintId = blueprintId;
		this.activityType = activityType;
		this.materials = new LinkedList<BlueprintRecipe.MaterialInput>();
		this.products = new LinkedList<BlueprintRecipe.ProductOutput>();
	}
	
	
	
	public ItemType getBlueprintId() {
		return blueprintId;
	}



	public IndustryActivityType getActivityType() {
		return activityType;
	}


	public String generateProductionReport(){
		StringBuilder builder = new StringBuilder();
		builder.append("Production Report for ").append(blueprintId).append("\n");
		builder.append("Materials:\n");
		for (MaterialInput input : materials){
			builder.append("........ ").append(input.getItemType()).append(" * ").append(input.getAmount());
			if (! input.isConsumed() ){
				builder.append(" *** Reusable *** ");
			}
			builder.append("\n");
		}
		
		builder.append("Result:\n");
		for (ProductOutput output : products){
			builder.append("........ ").append(output.getItemType()).append(" * ").append(output.getQuantity()).append("\n");
		}
		
		return builder.toString();
	}
	

	/**** Inputs / Materials ***/
	
	public void addMaterialInput(ItemType itemType, int amount, boolean consumed){
		materials.add(new MaterialInput(itemType, amount, consumed));
	}
	

	public static class MaterialInput {				
		private ItemType itemType;
		private int amount;
		private boolean consumed;
		
		public MaterialInput(ItemType itemType, int amount, boolean consumed) {
			this.itemType = itemType;
			this.amount = amount;
			this.consumed = consumed;
		}
		
		public ItemType getItemType() {
			return itemType;
		}
		public int getAmount() {
			return amount;
		}
		public boolean isConsumed() {
			return consumed;
		}
	}
	
	/**** Products / Outputs ****/
	
	public void addProductOutput(ItemType itemType, int quantity){
		products.add(new ProductOutput(itemType, quantity));
	}

	public static class ProductOutput {				
		private ItemType itemType;
		private int quantity;
		
		
		public ProductOutput(ItemType itemType, int quantity) {
			super();
			this.itemType = itemType;
			this.quantity = quantity;
		}
		
		public ItemType getItemType() {
			return itemType;
		}
		
		public int getQuantity() {
			return quantity;
		}
		
	}
}

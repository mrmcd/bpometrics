package com.goonfleet.bpometrics.prices;

import com.goonfleet.bpometrics.staticdata.ItemType;

public class SimpleMarketCalculator {
	
	private ItemType item;
	private int quantity;
	private double shippingCost;
	
	private RegionItemPrice homePrice;
	private RegionItemPrice awayPrice;

	private boolean buying;

	public SimpleMarketCalculator(ItemType item, int quantity, double shippingCost,
			RegionItemPrice homePrice, RegionItemPrice awayPrice, boolean buying) {
		
		this.item = item;
		this.quantity = quantity;
		this.shippingCost = shippingCost;
		this.homePrice = homePrice;
		this.awayPrice = awayPrice;
		this.buying = buying;
	}

	public ItemType getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public RegionItemPrice getHomePrice() {
		return homePrice;
	}

	public RegionItemPrice getAwayPrice() {
		return awayPrice;
	}

	public boolean isBuying() {
		return buying;
	}
	
	public RegionItemPrice calcBestCostRegion(){
		if (buying){
			return calcBestBuy();
		}else{
			return calcBestSell();
		}
	}
	
	public double getBestISKAmount(){
		RegionItemPrice bestRegion = calcBestCostRegion();
		boolean isHome = bestRegion.getRegion() == homePrice.getRegion();
		if (buying){
			return (isHome) ? getHomeCost() : getAwayCost();
		}else{
			return (isHome) ? getHomeRevenue() : getAwayRevenue();
		}
	}
	
	public String printMarketReport(){
		RegionItemPrice bestRegion = calcBestCostRegion(); 
		StringBuilder builder = new StringBuilder();
		builder.append("ITEM: ").append(homePrice.getItemType().getTypeName()).append(" * ").append(quantity).append("\n");
		builder.append("    ").append(homePrice.getRegion()).append(": ").append(homePrice.printInside())
			   .append(" TOTAL: ").append( buying ? getHomeCost() : getHomeRevenue() ).append(" ISK\n");
		builder.append("    ").append(awayPrice.getRegion()).append(": ").append(awayPrice.printInside())
			   .append(" TOTAL: ").append( buying ? getAwayCost() : getAwayRevenue() ).append(" ISK\n");
		builder.append("    USE: ").append(bestRegion.getRegion());
		return builder.toString();
	}
	
	private RegionItemPrice calcBestBuy(){
		if ( homePrice.getSellQty() < quantity ){
			//if not enough qty on the sell, assume you just import
			return awayPrice; 
		}else{
			//note: this 'away' (Jita) always has enough inventory
			double homeCost = getHomeCost();
			double awayCost = getAwayCost();
			return (homeCost <= awayCost) ? homePrice : awayPrice;
		}
	}
	
	private RegionItemPrice calcBestSell(){
		if ( homePrice.getBuyQty() < quantity){
			//if not enough qty on the buy, assume you just export
			return awayPrice; 
		}else{
			//note: this 'away' (Jita) always has enough demand
			double homeRev = getHomeRevenue();
			double awayRev = getAwayRevenue();
			return (homeRev >= awayRev) ? homePrice : awayPrice;
		}
	}
	
	private double getHomeCost(){
		return homePrice.getSellMin() * quantity;
	}
	
	private double getHomeRevenue(){
		return homePrice.getBuyMax() * quantity;
	}
	
	private double getAwayCost(){
		return awayPrice.getSellMin() * quantity + (item.getVolume() * shippingCost * quantity);
	}
	
	private double getAwayRevenue(){
		return awayPrice.getBuyMax() * quantity - (item.getVolume() * shippingCost * quantity);
	}
}

package com.goonfleet.bpometrics.prices;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.goonfleet.bpometrics.staticdata.ItemType;
import com.goonfleet.bpometrics.staticdata.ItemType.MarketableItemFilter;

public class RegionItemPrice {

	private ItemType itemType;
	private GoonmetricsRegions region;
	
	private double buyMax;
	private long buyQty;
	
	private double sellMin;
	private long sellQty;
	
	private BigDecimal weeklyVolume;
	
	
	
	public RegionItemPrice(ItemType itemType, GoonmetricsRegions region, double buyMax, long buyQty, double sellMin, long sellQty, BigDecimal weeklyVolume) {		
		this.itemType = itemType;
		this.region = region;
		this.buyMax = buyMax;
		this.buyQty = buyQty;
		this.sellMin = sellMin;
		this.sellQty = sellQty;
		this.weeklyVolume = weeklyVolume;
	}
	
	
	
	// --------------------------------

	public ItemType getItemType() {
		return itemType;
	}

	public GoonmetricsRegions getRegion(){
		return region;
	}

	public double getBuyMax() {
		return buyMax;
	}



	public long getBuyQty() {
		return buyQty;
	}



	public double getSellMin() {
		return sellMin;
	}



	public long getSellQty() {
		return sellQty;
	}



	public BigDecimal getWeeklyVolume() {
		return weeklyVolume;
	}

	public String printInside(){
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append(" BUY ").append(buyQty).append(" @ ").append(buyMax).append(" / ");
		builder.append(" SELL ").append(sellQty).append(" @ ").append(sellMin).append(" }");
		return builder.toString();
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Id=").append(itemType.getTypeId()).append(", name=").append(itemType.getTypeName()).append(" Region=").append(region.name()).append(" ").append(printInside());		
		return builder.toString();
	}
	
	public static Map<Integer,RegionItemPrice> queryItemPrices(GoonmetricsRegions region, ItemType... items) throws DocumentException, IOException {
		String queryUrl = GoonmetricsApiStubs.buildApiQueryString(region, items);
		System.out.println("Query url = " + queryUrl);
		SAXReader reader = new SAXReader();
		Document pricedoc = reader.read(new URL(queryUrl));
		Map<Integer, RegionItemPrice> priceResults = new HashMap<Integer, RegionItemPrice>(items.length);
		
		for(ItemType item : items){
			Node node = pricedoc.selectSingleNode("//price_data/type[@id='"+item.getTypeId()+"']");
			RegionItemPrice rgprice = parseDomTypeNode(item, region, node);			
			priceResults.put(item.getTypeId(), rgprice);
		}
		return priceResults;
	}
	
	private static RegionItemPrice parseDomTypeNode(ItemType item, GoonmetricsRegions region, Node node){
		double buyMax = Double.parseDouble(node.selectSingleNode("buy/max").getStringValue());
		long buyQty = Long.parseLong(node.selectSingleNode("buy/listed").getStringValue());
		
		double sellMin = Double.parseDouble(node.selectSingleNode("sell/min").getStringValue());
		long sellQty = Long.parseLong(node.selectSingleNode("sell/listed").getStringValue());
		
		BigDecimal volume = new BigDecimal(node.selectSingleNode("all/weekly_movement").getStringValue());
		
		return new RegionItemPrice(item, region, buyMax, buyQty, sellMin, sellQty, volume);
	}
	
	public static void main(String[] args) throws IOException, DocumentException{
		System.out.println("Testing API regional pricing query");
		FileReader file = new FileReader("src/main/resources/invTypes.csv");
		List<ItemType> types = ItemType.loadMarketableTypes(file, new MarketableItemFilter());
		
		Map<Integer, ItemType> itemMap = ItemType.assembleItemTypeMap(types);
		
		Map<Integer, RegionItemPrice> priceMap = queryItemPrices(GoonmetricsRegions.JITA, itemMap.get(34),itemMap.get(35),itemMap.get(36),itemMap.get(37),itemMap.get(3683));
		for (RegionItemPrice price : priceMap.values() ){
			System.out.println(price);
		}
	}
}

package com.goonfleet.bpometrics.prices;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.goonfleet.bpometrics.staticdata.BlueprintRecipe;
import com.goonfleet.bpometrics.staticdata.BlueprintRecipe.MaterialInput;
import com.goonfleet.bpometrics.staticdata.BlueprintRecipe.ProductOutput;

public class PriceComputationService {

	public static List<SimpleMarketCalculator> calculateMaterialsSimple(BlueprintRecipe recipe, double shippingCost, Map<Integer,RegionItemPrice> homePrices, Map<Integer,RegionItemPrice> awayPrices){		
		List<SimpleMarketCalculator> result = new LinkedList<SimpleMarketCalculator>();
		for(MaterialInput input : recipe.getMaterials()){
			SimpleMarketCalculator calc = new SimpleMarketCalculator(input.getItemType(), input.getAmount(), shippingCost, 
																	 homePrices.get(input.getItemType().getTypeId()), 
																	 awayPrices.get(input.getItemType().getTypeId()), true);
			result.add(calc);
		}
		
		return result;
	}
	
	public static List<SimpleMarketCalculator> calculateProductsSimple(BlueprintRecipe recipe, double shippingCost, Map<Integer,RegionItemPrice> homePrices, Map<Integer,RegionItemPrice> awayPrices){		
		List<SimpleMarketCalculator> result = new LinkedList<SimpleMarketCalculator>();
		for(ProductOutput output : recipe.getProducts()){
			SimpleMarketCalculator calc = new SimpleMarketCalculator(output.getItemType(), output.getQuantity(), shippingCost, 
																	 homePrices.get(output.getItemType().getTypeId()), 
																	 awayPrices.get(output.getItemType().getTypeId()), false);
			result.add(calc);
		}
		
		return result;
	}
}

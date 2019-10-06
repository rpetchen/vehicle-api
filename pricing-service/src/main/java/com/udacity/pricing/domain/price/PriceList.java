package com.udacity.pricing.domain.price;

import java.util.ArrayList;
import java.util.List;

public class PriceList {
	private List<Price> prices;

	public PriceList() {
		prices = new ArrayList<>();
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	
}

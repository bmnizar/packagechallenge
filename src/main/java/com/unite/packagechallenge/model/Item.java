package com.unite.packagechallenge.model;

import lombok.Getter;
import lombok.Setter;

/**
 ** @BMN 2021
 **
 **/
@Getter
@Setter
public class Item {
	private double weight;
	private long cost;

	@Override
	public int hashCode() {
		return ((Double) weight).hashCode() * ((Long) cost).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Item objItem = (Item) obj;

		return ((this.getCost() == objItem.cost) && (this.getWeight() == objItem.weight));
	}
}

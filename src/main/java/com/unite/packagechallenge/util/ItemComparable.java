package com.unite.packagechallenge.util;

import java.util.Comparator;

import com.unite.packagechallenge.model.Item;

/**
 ** @BMN 2021
 **
 **/
public class ItemComparable implements Comparator<Item> {

	@Override
	public int compare(Item o1Item, Item o2Item) {
		if (o1Item.getCost() > o2Item.getCost()) {
			return 1;
		}
		if (o1Item.getCost() < o2Item.getCost()) {
			return -1;
		}
		if (o1Item.getCost() == o2Item.getCost()) {
			if (o1Item.getWeight() > o2Item.getWeight()) {
				return -1;
			} else {
				if (o1Item.getWeight() < o2Item.getWeight()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

}

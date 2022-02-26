package com.unite.packagechallenge.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.unite.packagechallenge.model.Item;

public class AppHelper {
	private AppHelper() {

	}

	public static Map<Integer, Item> createNewMapWithoutItem(Map<Integer, Item> items, Integer item) {
		TreeMap<Integer, Item> clone = new TreeMap<>();
		for (Map.Entry<Integer, Item> entryyy : items.entrySet()) {
			Integer key = entryyy.getKey();
			if (key.equals(item)) {
				continue;
			}
			Item val = entryyy.getValue();

			clone.put(key, val);
		}
		return clone;
	}

	public static Set<Integer> toTreeSet(Integer cpt) {
		Set<Integer> arrayList = new TreeSet<>();
		arrayList.add(cpt);
		return arrayList;
	}

 

}

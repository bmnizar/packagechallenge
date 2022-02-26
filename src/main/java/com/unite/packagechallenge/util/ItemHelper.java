package com.unite.packagechallenge.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.unite.packagechallenge.model.Item;
import com.unite.packagechallenge.model.SingleFileStructure;

/**
 ** @BMN 2021
 **
 **/
public class ItemHelper {
	private ItemHelper() {
	}

	/**
	 * Remove all the items that have a size that is more than the maximum allowed
	 * sizer per package
	 * 
	 * @param singleFileStructure
	 */
	public static void removeAllTheExceedWeighItems(SingleFileStructure singleFileStructure) {
		TreeMap<Integer, Item> items = singleFileStructure.getItems();
		double maximumWeightOfThePackage = singleFileStructure.getMaximumWeightOfThePackage();
		ArrayList<Integer> toBeDeletedItemsKeys = new ArrayList<>();
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {

			Item item = entry.getValue();
			if (item.getWeight() > maximumWeightOfThePackage) {
				toBeDeletedItemsKeys.add(entry.getKey());
			}

		}
		for (Integer toBeDeletedFromMapKey : toBeDeletedItemsKeys) {
			items.remove(toBeDeletedFromMapKey);
		}
	}

	/**
	 * After getting all the possible Combinaison .We need to sort them By Cost.For
	 * that we use a TreeMap that has the cost as a Key
	 * 
	 * @param items
	 * @param possibleCombinaison
	 * @return
	 */
	public static Set<Integer> orderPossibleCombainaison(SortedMap<Integer, Item> items,
			Set<Set<Integer>> possibleCombinaison) {
		/**
		 * We will use the ItemComparable to compare between items in the package .The
		 * role of ItemComparable is to compare between items.In case it finds that two
		 * items have the same cost .It will take the one with the lowest weight
		 */
		TreeMap<Item, Set<Integer>> sortedPossibleCombainaisonByCost = new TreeMap<>(new ItemComparable());
		for (Set<Integer> singleListPossibleCombainaison : possibleCombinaison) {
			long totalCost = 0;
			double totalWeight = 0;
			for (Integer singleListPossible : singleListPossibleCombainaison) {

				Item item = items.get(singleListPossible);
				totalCost = totalCost + item.getCost();
				totalWeight = totalWeight + item.getWeight();
			}
			Item itemOfTheTotalPackageForThisPossibleCombainaison = new Item();
			itemOfTheTotalPackageForThisPossibleCombainaison.setCost(totalCost);
			itemOfTheTotalPackageForThisPossibleCombainaison.setWeight(totalWeight);
			sortedPossibleCombainaisonByCost.put(itemOfTheTotalPackageForThisPossibleCombainaison,
					singleListPossibleCombainaison);
		}
		/**
		 * Because by default the TreeMap is ordering ascending .We inverse the order to
		 * get the first element the most one that has the biggest cost
		 */
		NavigableMap<Item, Set<Integer>> descendingMap = sortedPossibleCombainaisonByCost.descendingMap();
		return descendingMap.firstEntry().getValue();

	}

	public static Double getTotalWeightOfItemsTOAdd(Map<Integer, Item> items, Set<Integer> keySett) {
		Double toBeReturned = 0D;
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			Integer key = entry.getKey();
			Item item = entry.getValue();
			if (keySett.contains(key)) {
				toBeReturned = toBeReturned + item.getWeight();
			}

		}
		return toBeReturned;
	}

}

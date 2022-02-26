package com.unite.packagechallenge;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.unite.packagechallenge.model.Item;
import com.unite.packagechallenge.model.SingleFileStructure;
import com.unite.packagechallenge.util.AppHelper;
import com.unite.packagechallenge.util.ItemHelper;

/**
 ** @BMN 2021
 **
 **/
public class PackageProcessor {

	private static final String NA = "-";

	private PackageProcessor() {

	}

	/**
	 * Loop over the items in the package .Each time when looping over the items ,we
	 * will have the pivotal item and then we will make all the possible combination
	 * with this pivotal item (so we will have an O(n^n) complexity for each pivotal
	 * item). From that pivotal item (we are calling it here pivotalItemSet),each
	 * time we found a combination that can be put inside the package.We update that
	 * pivotal item to include the new combination and put the result in a Deque and
	 * then go recursively with this pivotal item . Once we exit the reccursive loop
	 * ,we pop up the Deque to retrieve the previous pivotal item to continue our
	 * O(n) complexity parsing with it
	 * 
	 * @param singleFileStructure
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object processLine(SingleFileStructure singleFileStructure) {
		ItemHelper.removeAllTheExceedWeighItems(singleFileStructure);
		TreeMap<Integer, Item> items = singleFileStructure.getItems();
		if (items.size() == 0) {
			return NA;
		}

		double maximumWeightOfThePackage = singleFileStructure.getMaximumWeightOfThePackage();
		PackageValidator packageValidator = PackageValidator.getInstance(items, maximumWeightOfThePackage);
		Set<Set<Integer>> possibleCombinaison = new HashSet<>();
		for (Map.Entry<Integer, Item> entryCpt : items.entrySet()) {
			Integer pivotalItem = entryCpt.getKey();
			Set<Integer> pivotalItemSet = AppHelper.toTreeSet(pivotalItem);
			/**
			 * it's evident that each singleton item is a possible combination
			 */
			possibleCombinaison.add(new TreeSet<>(pivotalItemSet));
			Deque<Set<Integer>> stack = new ArrayDeque();
			stack.push(pivotalItemSet);
			deepLoopingForPossibleCombainaison(items, stack, possibleCombinaison, packageValidator);

		}
		return ItemHelper.orderPossibleCombainaison(items, possibleCombinaison);

	}

	/**
	 * 
	 * @param items
	 * @param stack
	 * @param possibleCombinaison
	 * @param packageValidator
	 */
	private static void deepLoopingForPossibleCombainaison(Map<Integer, Item> items, Deque<Set<Integer>> deque,
			Set<Set<Integer>> possibleCombinaison, PackageValidator packageValidator) {
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			Integer secondKey = entry.getKey();
			/**
			 * To get the pivotal item .Look at the Deque without peeking it up.
			 */
			Set<Integer> keySetFromDeque = deque.peek();
			Set<Integer> keySett = new HashSet<>(keySetFromDeque);
			keySett.add(secondKey);
			Map<Integer, Item> clone = AppHelper.createNewMapWithoutItem(items, secondKey);
			if (clone.isEmpty()) {
				return;
			}
			boolean isValid = packageValidator.validateItemsToBeAddingToThePackage(keySett);
			if (isValid) {
				possibleCombinaison.add(new HashSet<>(keySett));
			}
			deque.push(keySett);
			deepLoopingForPossibleCombainaison(clone, deque, possibleCombinaison, packageValidator);
			/**
			 * We will peek up and remove the item from the Deque to return to the original Pivotal Item
			 */
			deque.pop();

		}

	}

}

package com.unite.packagechallenge;

import java.util.Map;
import java.util.Set;

import com.unite.packagechallenge.model.Item;
import com.unite.packagechallenge.util.ItemHelper;

/**
 ** @BMN 2021
 **
 **/
public class PackageValidator {
	private double maximumWeightOfThePackage;
	private Map<Integer, Item> items;

	private PackageValidator() {

	}

	/**
	 * A singleton instance that represents the PackageValidator .So any items will
	 * be added to the package must be checked with this validator
	 * 
	 * @param items
	 * @param maximumWeightOfThePackage
	 * @return
	 */
	public static PackageValidator getInstance(Map<Integer, Item> items, double maximumWeightOfThePackage) {
		PackageValidator packageValidator = new PackageValidator();
		packageValidator.maximumWeightOfThePackage = maximumWeightOfThePackage;
		packageValidator.items = items;
		return packageValidator;
	}

	/**
	 * Validate the items that will be added to the package.Ensure they don't exceed
	 * the maximum allowed weight
	 * 
	 * @param keySett
	 * @return
	 */
	public boolean validateItemsToBeAddingToThePackage(Set<Integer> keySett) {
		/**
		 * If more than 15.That's it we will not add more .
		 * We could make this number configurable in our application
		 */
		if (keySett.size() >= 15) {
			return false;
		}
		Double totalWeightOfThePackageToAdd = ItemHelper.getTotalWeightOfItemsTOAdd(items, keySett);
		return totalWeightOfThePackageToAdd <= maximumWeightOfThePackage;
	}

}

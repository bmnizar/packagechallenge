package com.unite.packagechallenge.service.impl;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.unite.packagechallenge.AppRuntimeException;
import com.unite.packagechallenge.model.Item;
import com.unite.packagechallenge.model.SingleFileStructure;
import com.unite.packagechallenge.service.LineStructureExtractor;

/**
 ** @BMN 2021
 **
 **/
public class LineStructureExtractorImpl implements LineStructureExtractor {

	private static final String ITEM_PATTERN = "(\\(\\d+,\\d+(\\.?\\d+)?,€\\d+\\)(\\s+)*)";

	/**
	 * Extract the SingleFileStructure from the line.Due to limitation with mixing
	 * repeated group and standard group.We will first extract the maximum total
	 * weight then we will use the Pattern to extract the items
	 * 
	 * @param line : the line that represent the package
	 */
	@Override
	public SingleFileStructure extractLineStructure(String line) {

		// TODO: It looks like java does not allow mixed groups of repeated and not
		// repeated groups.In fact,we found the reg expression for the repeated group
		// but adding the line number along with : like for example 81: (we will call it
		// the standard non
		// repeated group ) does not work.Need to check it back
		// if java support mixed repeated group as well as non repeated group together
		// and then we can make a single Reg Pattern.
		int indexOfDoublePoint = line.indexOf(":");
		Integer maximumWeightOfThePackage = Integer.valueOf(line.substring(0, indexOfDoublePoint).trim());
		if (maximumWeightOfThePackage > 100) {
			throw new AppRuntimeException("Your maximum weight of your package is more than 100");
		}
		line = line.substring(indexOfDoublePoint + 1);
		line = StringUtils.stripStart(line, null);

		Pattern r = Pattern.compile(ITEM_PATTERN);
		TreeMap<Integer, Item> items = new TreeMap<>();
		// Now create matcher object.
		Matcher m = r.matcher(line);
		while (m.find()) {

			String itemString = m.group(1).trim();
			// We could also use another Reg Expression pattern here to split the item parts   but splitting is not as
			// heavy .Instead Reg Expression Pattern here will cost more in performance
			String itemElements = itemString.substring(1, itemString.length() - 1);
			String[] split = itemElements.split(",");
			Item item = new Item();
			Double weight = Double.valueOf(split[1]);
			if (weight > 100) {
				throw new AppRuntimeException("Your maximum Item weight can not be more than 100");
			}
			item.setWeight(weight);
			Long costOfItem = Long.valueOf(split[2].substring(1));
			if (costOfItem > 100) {
				throw new AppRuntimeException("Your maximum Item cost can not be more than 100");
			}
			item.setCost(costOfItem);
			Integer itemNumber = Integer.valueOf(split[0]);
			items.put(itemNumber, item);

		}
		SingleFileStructure singleFileStructure = new SingleFileStructure();
		singleFileStructure.setMaximumWeightOfThePackage(maximumWeightOfThePackage);
		singleFileStructure.setItems(items);
		return singleFileStructure;
	}

}

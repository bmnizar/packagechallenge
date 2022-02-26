package com.unite.packagechallenge.it;

import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import com.unite.packagechallenge.PackageProcessor;
import com.unite.packagechallenge.model.Item;
import com.unite.packagechallenge.model.SingleFileStructure;
import com.unite.packagechallenge.service.LineStructureExtractor;
import com.unite.packagechallenge.service.impl.LineStructureExtractorImpl;

/**
 ** @BMN 2021
 **
 **/
public class TestPackageChallenge {
	public static final String SINGLE_LINE = "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
	public static final String INVALID_ITEM_COST = "75 : (1,85.31,€229) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
	public static final String INVALID_ITEM_WEIGHT = "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,269.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
	public static final String SINGLE_LINE_HAS_MORE_THAN_MAXIMUM_WEIGHT = "8 : (1,15.3,€34)";
	public static final String NORMAL_LINE_TO_PROCESS = "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";
	public static final String SINGLE_LINE_PACKAGE_HAS_MORE_THAN_THE_MAXIMUM = "556 : (1,90.72,€13) (2,33.80,€40)";
	public static final String SAME_COST_DIFFERENT_WEIGHT = "10 : (1,5,€10) (2,9,€10)";

	@Test()
	public void testExtractLineStructure() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		SingleFileStructure singleFileStructure = lineStructureExtractor.extractLineStructure(SINGLE_LINE);
		Assert.assertEquals(75D, singleFileStructure.getMaximumWeightOfThePackage(), 0D);
		TreeMap<Integer, Item> items = singleFileStructure.getItems();
		Assert.assertEquals(9, items.size());
		Assert.assertEquals(16, items.get(3).getCost());
	}

	@Test()
	public void testSameCostDifferntWeight() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		SingleFileStructure singleFileStructure = lineStructureExtractor
				.extractLineStructure(SAME_COST_DIFFERENT_WEIGHT);
		Object processLineResult = PackageProcessor.processLine(singleFileStructure);
		Assert.assertEquals("[1]", processLineResult.toString());
	}

	@Test()
	public void testInvalidItemCost() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		try {
			lineStructureExtractor.extractLineStructure(INVALID_ITEM_COST);
		} catch (Exception ex) {
			Assert.assertEquals("Your maximum Item cost can not be more than 100", ex.getMessage());
		}
	}

	@Test()
	public void testProcessItemHasMoreThanMaximumWeight() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		SingleFileStructure singleFileStructure = lineStructureExtractor
				.extractLineStructure(SINGLE_LINE_HAS_MORE_THAN_MAXIMUM_WEIGHT);
		Object processLineResult = PackageProcessor.processLine(singleFileStructure);
		Assert.assertEquals("-", processLineResult);
	}

	@Test()
	public void testPackageHasMoreThanTheAllowedMaximum() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();

		try {
			lineStructureExtractor.extractLineStructure(SINGLE_LINE_PACKAGE_HAS_MORE_THAN_THE_MAXIMUM);
		} catch (Exception ex) {
			Assert.assertEquals("Your maximum weight of your package is more than 100", ex.getMessage());
		}

	}

	@Test()
	public void testProcessNormalLine() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		SingleFileStructure singleFileStructure = lineStructureExtractor.extractLineStructure(NORMAL_LINE_TO_PROCESS);
		Object processLineResult = PackageProcessor.processLine(singleFileStructure);
		Assert.assertEquals("[8, 9]", processLineResult.toString());
	}

	@Test()
	public void testInvalidItemWeight() {
		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		try {
			lineStructureExtractor.extractLineStructure(INVALID_ITEM_WEIGHT);
		} catch (Exception ex) {
			Assert.assertEquals("Your maximum Item weight can not be more than 100", ex.getMessage());
		}
	}
}

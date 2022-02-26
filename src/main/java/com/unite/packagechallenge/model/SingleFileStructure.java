package com.unite.packagechallenge.model;

import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

/**
 * Each SingleFileStructure represent the line in the text file.
 ** @BMN 2021
 **
 **/
@Getter
@Setter 
public class SingleFileStructure {

	private double maximumWeightOfThePackage;
	private TreeMap<Integer, Item> items = new TreeMap<>();
}

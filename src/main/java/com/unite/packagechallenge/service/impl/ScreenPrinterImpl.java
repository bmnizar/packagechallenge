package com.unite.packagechallenge.service.impl;

import java.util.Iterator;
import java.util.Set;

import com.unite.packagechallenge.service.ScreenPrinter;

/**
 ** @BMN 2021
 **
 **/
public class ScreenPrinterImpl implements ScreenPrinter {

	@SuppressWarnings("rawtypes")
	@Override
	public void printResultForASingleLine(Object result) {
		if (result instanceof Set) {
			Set setResult = (Set) result;
			int setResultSize = setResult.size();
			int cpt = 0;
			for (Iterator iterator = setResult.iterator(); iterator.hasNext();) {
				Object object = iterator.next();
				if (cpt < setResultSize-1) {
					System.out.print(object + ",");
				} else {
					System.out.print(object);
				}
				cpt++;

			}
		} else {
			System.out.print(result.toString());
		}

	}

}

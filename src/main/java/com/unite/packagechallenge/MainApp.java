package com.unite.packagechallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this program will calculate the optimal package distribution for having the
 * maximum cost per Line. It would be very easy to use an opta planner solution
 * that will resolve this issue.For that,we have developed a generic interface
 * that we can later add the opta planner implementation
 ** 
 * @BMN 2021
 **
 **/
public class MainApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		args = new String[] { "d:\\sampleInput.txt" };
		if (args.length == 0) {
			LOGGER.error("Usage:No file has been added as a parameter.Please add one ");
			return;
		}
		PackageChallengeApp.process(args);
	}

}

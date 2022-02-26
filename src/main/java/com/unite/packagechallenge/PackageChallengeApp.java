package com.unite.packagechallenge;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unite.packagechallenge.model.SingleFileStructure;
import com.unite.packagechallenge.service.LineStructureExtractor;
import com.unite.packagechallenge.service.ScreenPrinter;
import com.unite.packagechallenge.service.impl.LineStructureExtractorImpl;
import com.unite.packagechallenge.service.impl.ScreenPrinterImpl;

/**
 ** @BMN 2021
 **
 **/
public class PackageChallengeApp {
	private static final String LINE_SEPERATOR = "\n";
	private static final Logger LOGGER = LoggerFactory.getLogger(PackageChallengeApp.class);

	private PackageChallengeApp() {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void process(String[] args) {
		String fileContent;
		try {
			fileContent = FileUtils.readFileToString(new File(args[0]), StandardCharsets.UTF_8);
		} catch (IOException e) {
			LOGGER.error("Unable to read the file {} ", args[0]);
			return;
		}
		String[] lines = fileContent.split(LINE_SEPERATOR);

		LineStructureExtractor lineStructureExtractor = new LineStructureExtractorImpl();
		List finalResultToPrint = new ArrayList();
		for (String singleLine : lines) {

			SingleFileStructure singleFileStructure = lineStructureExtractor.extractLineStructure(singleLine);
			Object processLineResult = PackageProcessor.processLine(singleFileStructure);
			finalResultToPrint.add(processLineResult);
		}
		/**
		 * Now prints the results to the screen
		 */
		ScreenPrinter screenPrinter = new ScreenPrinterImpl();
		for (Object result : finalResultToPrint) {
			screenPrinter.printResultForASingleLine(result);
			System.out.println();
		}

	}

}

package com.qa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class JsonReader {

	public static JSONObject getTestDataJSON(String testClass) {
		JSONObject testDataFile = null;
		try {
			File file = new File("src/main/java/com/qa/testdata/" + testClass + ".json");
			String content = FileUtils.readFileToString(file, "utf-8");
			testDataFile = new JSONObject(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return testDataFile;
	}

}
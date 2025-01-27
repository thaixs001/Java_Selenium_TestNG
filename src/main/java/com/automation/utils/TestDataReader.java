package com.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestDataReader {
    private static JsonNode testData;

    static {
        try {
            File testDataFile = new File("src/main/resources/testdata/testdata.json");
            ObjectMapper mapper = new ObjectMapper();
            testData = mapper.readTree(testDataFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data file.", e);
        }
    }

    public static JsonNode getTestData(String key) {
        return testData.get(key);
    }
}

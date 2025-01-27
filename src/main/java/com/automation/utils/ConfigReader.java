package com.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private static JsonNode configData;

    static {
        try {
            File configFile = new File("src/main/resources/configs/config.json");
            ObjectMapper mapper = new ObjectMapper();
            configData = mapper.readTree(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }

    public static JsonNode getConfig(String key) {
        return configData.get(key);
    }
}

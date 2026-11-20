package com.qa.framework.utils;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Loads JSON arrays from src/test/resources/testdata. */
public final class JsonUtils {

    private JsonUtils() {
    }

    public static JSONArray readArray(String fileName) {
        String path = "testdata/" + fileName;
        try (InputStream in = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalArgumentException("Test data file not found: " + path);
            }
            return (JSONArray) new JSONParser().parse(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("Could not read " + path, e);
        }
    }
}

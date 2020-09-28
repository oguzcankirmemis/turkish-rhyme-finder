package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;

public class DictionaryHelper {
	public String loadDictionary(Path dictionaryPath) {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> map;
		try {
			map = mapper.readValue(dictionaryPath.toFile(), Map.class);
		} catch (JsonParseException e) {
			System.out.println("Cannot parse dictionary json file: " + e.getMessage());
			return null;
		} catch (JsonMappingException e) {
			System.out.println("Cannot map dictionary json file: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("Dictionary json file input error: " + e.getMessage());
			return null;
		}	
		return null;
	}
}

package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;

// TODO: Implement dictionary loading and dictionary utilities
// TODO: add unit testing
public class DictionaryHelper {
	public static Dictionary loadDictionary(Path dictionaryPath) {
		ObjectMapper mapper = new ObjectMapper();
		List<Word> words;
		try {
			words = new ArrayList<>(Arrays.asList(mapper.readValue(dictionaryPath.toFile(), Word[].class)));
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
		Dictionary dict = new Dictionary(words, true);
		return dict;
	}
}

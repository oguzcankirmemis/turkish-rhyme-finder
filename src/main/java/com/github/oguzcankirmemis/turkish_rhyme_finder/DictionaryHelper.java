package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;

// TODO: add unit testing
public class DictionaryHelper {
	public static Dictionary loadRawDictionary(Path dictionaryPath, boolean addVerbsWithoutInfinitives) {
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
		Dictionary dict = new Dictionary(words, addVerbsWithoutInfinitives);
		System.out.println("Successfully loaded raw dictionary!");
		return dict;
	}
	
	public static Dictionary loadDictionary(Path dictionaryPath) {
		Dictionary dict;
		try {
			FileInputStream fileInput = new FileInputStream(dictionaryPath.toFile());
			ObjectInputStream objectIn = new ObjectInputStream(fileInput);
			dict  = (Dictionary) objectIn.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find dictionary file: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("Input error while loading dictionary: " + e.getMessage());
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found while loading dictionary: " + e.getMessage());
			return null;
		}
		System.out.println("Successfully loaded dictionary!");
		return dict;
	}
	
	public static void saveRawDictionary(Dictionary dict, Path savePath) {
		ObjectMapper mapper = new ObjectMapper();
		List<Word> words = dict.getWords();
		try {
			mapper.writeValue(savePath.toFile(), words);
		} catch (JsonGenerationException e) {
			System.out.println("Cannot generate dictionary json: " + e.getMessage());
			return;
		} catch (JsonMappingException e) {
			System.out.println("Cannot map dictionary json: " + e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println("Dictionary json file output error: " + e.getMessage());
			return;
		}
		System.out.println("Dictionary saved successfully at: " +
				savePath.toAbsolutePath().toString());
	}
	
	public static void saveDictionary(Dictionary dict, Path searchTreesPath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(searchTreesPath.toFile());
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(dict);
			objectOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot save search trees in given path: " + e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println("Output error while saving search trees: " + e.getMessage());
			return;
		}
		System.out.println("Search trees saved successfully at: " + 
				searchTreesPath.toAbsolutePath().toString());
	}
}

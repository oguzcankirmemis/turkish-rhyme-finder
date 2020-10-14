package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Path dictPath = Paths.get("./src/main/resources/test-dict.json");
    	Dictionary dict = DictionaryHelper.loadDictionary(dictPath);
    	for (Word w : dict.getWords()) {
    		System.out.println(w.toStringWithMeanings());
    	}
    }
}

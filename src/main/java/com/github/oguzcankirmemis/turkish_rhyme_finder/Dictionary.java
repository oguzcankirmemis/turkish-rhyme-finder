package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.util.List;

public class Dictionary {
	List<Word> words;
	
	public Dictionary(List<Word> words) {
		this.words = words;
	}
	
	public List<Word> getWords() {
		return words;
	}
}

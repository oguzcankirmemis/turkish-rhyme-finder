package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.util.ArrayList;
import java.util.List;

import com.github.oguzcankirmemis.turkish_rhyme_finder.DictionarySearchTree.SearchModus;

// TODO: add unit tests for whole class
public class Dictionary {
	private static final int PERCENTAGE_LOGGING_THRESHOLD = 10;
	
	List<Word> words;
	DictionarySearchTree wholeWord;
	DictionarySearchTree wholeWordReversed;
	DictionarySearchTree onlyVowels;
	DictionarySearchTree onlyVowelsReversed;
	
	public Dictionary() {
		words = new ArrayList<>();
	}
	
	public Dictionary(List<Word> words, boolean addVerbsWithoutInfinitive) {
		this.words = words;
		if (addVerbsWithoutInfinitive) {
			addVerbsWithoutInfinitiveAttachments();
		}
	}
	
	public List<Word> getWords() {
		return words;
	}
	
	public DictionarySearchTree getSearchTree(boolean isReversed, SearchModus modus) {
		switch(modus) {
			case OnlyVowels:
				if (isReversed) {
					if (onlyVowelsReversed == null) {
						onlyVowelsReversed = new DictionarySearchTree(isReversed, modus, this);
					}
					return onlyVowelsReversed;
				} else {
					if (onlyVowels == null) {
						onlyVowels = new DictionarySearchTree(isReversed, modus, this);
					}
					return onlyVowels;
				}
			case WholeWord:
			default:
				if (isReversed) {
					if (wholeWordReversed == null) {
						wholeWordReversed = new DictionarySearchTree(isReversed, modus, this);
					}
					return wholeWordReversed;
				} else  {
					if (wholeWord == null) {
						wholeWord = new DictionarySearchTree(isReversed, modus, this);
					}
					return wholeWord;
				}
		}
	}
	
	private void addVerbsWithoutInfinitiveAttachments() {
		System.out.println("Finding and adding verbs without " +
				"their attachments, this might take a while...");
		int processCounter = 1;
		int addedNewWords = 0;
		int wordsInitLen = words.size();
		List<Word> newWords = new ArrayList<Word>();
		for (int i = 0; i < wordsInitLen; i++) {
			while (wordsInitLen >= PERCENTAGE_LOGGING_THRESHOLD && (i + 1) / ((double) wordsInitLen) >= 
					(((double) PERCENTAGE_LOGGING_THRESHOLD) / 100) * processCounter) {
				System.out.println(processCounter * 10 + "% processed...");
				processCounter++;
			}
			Word w = words.get(i);
			if (w.isVerb()) {
				Word verb = w.getVerbWithoutAttachment();
				newWords.add(verb);
				addedNewWords++;
			}
		}
		System.out.println("Found " + addedNewWords + 
				" in total, adding them all to dictionary");
		words.addAll(newWords);
		System.out.println("Added " + addedNewWords + 
				" verbs without their attachments in total.");
	}
}

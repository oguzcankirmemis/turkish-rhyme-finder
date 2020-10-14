package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.oguzcankirmemis.turkish_rhyme_finder.DictionarySearchTree.SearchModus;

// TODO: add unit tests for whole class
public class Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final int PERCENTAGE_LOGGING_THRESHOLD = 10;
	private static final int DEFAULT_SEARCH_DEPTH = 50;
	
	private List<Word> words;
	private DictionarySearchTree wholeWord;
	private DictionarySearchTree wholeWordReversed;
	private DictionarySearchTree onlyVowels;
	private DictionarySearchTree onlyVowelsReversed;
	
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
	
	public DictionarySearchTree getSearchTree(SearchModus modus) {
		switch(modus) {
		case OnlyVowelsReversed:
			return onlyVowelsReversed;
		case OnlyVowels:
			return onlyVowels;
		case WholeWordReversed:
			return wholeWordReversed;
		case WholeWord:
		default:
			return wholeWord;
		}
	}
	
	public DictionarySearchTree generateSearchTree(SearchModus modus) {
		switch(modus) {
		case OnlyVowelsReversed:
			if (onlyVowelsReversed == null) {
				onlyVowelsReversed = new DictionarySearchTree(modus, this);
			}
			return onlyVowelsReversed;
		case OnlyVowels:
			if (onlyVowels == null) {
				onlyVowels = new DictionarySearchTree(modus, this);
			}
			return onlyVowels;
		case WholeWordReversed:
			if (wholeWordReversed == null) {
				wholeWordReversed = new DictionarySearchTree(modus, this);
			}
			return wholeWordReversed;
		case WholeWord:
		default:
			if (wholeWord == null) {
				wholeWord = new DictionarySearchTree(modus, this);
			}
			return wholeWord;
		}
	}
	
	public DictionarySearchTree resetSearchTree(SearchModus modus) {
		switch(modus) {
		case OnlyVowelsReversed:
			return onlyVowelsReversed = new DictionarySearchTree(modus, this);
		case OnlyVowels:
			return onlyVowels = new DictionarySearchTree(modus, this);
		case WholeWordReversed:
			return wholeWordReversed = new DictionarySearchTree(modus, this);
		case WholeWord:
		default:
			return wholeWord = new DictionarySearchTree(modus, this);
		}
	}
	
	public void resetSearchTrees() {
		resetSearchTree(SearchModus.OnlyVowelsReversed);
		resetSearchTree(SearchModus.OnlyVowels);
		resetSearchTree(SearchModus.WholeWordReversed);
		resetSearchTree(SearchModus.WholeWord);
	}
	
	public boolean deleteSearchTree(SearchModus modus) {
		switch(modus) {
		case OnlyVowelsReversed:
			if (onlyVowelsReversed != null) {
				onlyVowelsReversed = null;
				return true;
			}
		case OnlyVowels:
			if (onlyVowels != null) {
				onlyVowels = null;
				return true;
			}
		case WholeWordReversed:
			if (wholeWordReversed != null) {
				wholeWordReversed = null;
				return true;
			}
		case WholeWord:
		default:
			if (wholeWord != null) {
				wholeWord = null;
				return true;
			}
		}
		return false;
	}
	
	public List<Word> searchWord(SearchModus modus, String searchInput, int depth) {
		DictionarySearchTree dst = generateSearchTree(modus);
		return dst.searchWords(searchInput, depth);
	}
	
	public List<Word> searchWord(SearchModus modus, String searchInput) {
		return searchWord(modus, searchInput, DEFAULT_SEARCH_DEPTH);
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

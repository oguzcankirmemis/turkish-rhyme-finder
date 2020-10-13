package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.util.List;
import java.util.ArrayList;

public class DictionarySearchTree {
	private static final char ROOT_CHARACTER = '*';
	private static final char[] VOWELS = new char[] {'a', 'e', 'ı', 'i', 'u', 'ü', 'o', 'ö'};
	
	private DictionarySearchTree root;
	private List<DictionarySearchTree> elements;
	private char character;
	private int level;
	private boolean isReversed;
	private List<Word> words;
	private SearchModus modus;
	
	enum SearchModus {
		WholeWord,
		OnlyVowels
	}
	
	private DictionarySearchTree(DictionarySearchTree root, char character) {
		this.root = root;
		elements = new ArrayList<DictionarySearchTree>();
		this.character = character;
		level = this.root.getLevel() + 1;
		isReversed = this.root.isReversed();
		words = null;
		modus = this.root.getModus();
	}
	
	public DictionarySearchTree(boolean isReversed, SearchModus modus) {
		root = null;
		elements =  new ArrayList<DictionarySearchTree>();
		character = ROOT_CHARACTER;
		level = 0;
		this.isReversed = isReversed;
		words = null;
		this.modus = modus;
	}
	
	public DictionarySearchTree(boolean isReversed, SearchModus modus, Dictionary dictionary) {
		root = null;
		elements =  new ArrayList<DictionarySearchTree>();
		character = ROOT_CHARACTER;
		level = 0;
		this.isReversed = isReversed;
		words = null;
		this.modus = modus;
		addDictionary(dictionary);
	}
	
	public boolean isReversed() {
		return isReversed;
	}
	
	public int getLevel() {
		return level;
	}
	
	public SearchModus getModus() {
		return modus;
	}
	
	private char getCharacter() {
		return character;
	}
	
	private List<Word> getWords() {
		if (words == null) {
			return new ArrayList<Word>();
		} else {
			return words;
		}
	}
	
	private static boolean isVowel(char character) {
		for (char v : VOWELS) {
			if (v == character) {
				return true;
			}
		}
		return false;
	}
	
	private void addWord(Word w, char[] characters, int index) {
		if (index > characters.length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (index == characters.length) {
			if (words == null) {
				words = new ArrayList<Word>();
			}
			words.add(w);
			return;
		}
		if (modus == SearchModus.OnlyVowels) {
			if (!isVowel(characters[index])) {
				addWord(w, characters, index + 1);
				return;
			}
		}
		for (DictionarySearchTree tree : elements) {
			if (tree.getCharacter() == characters[index]) {
				tree.addWord(w, characters, index++);
				return;
			}
		}
		DictionarySearchTree subtree = new DictionarySearchTree(this, characters[index]);
		elements.add(subtree);
		subtree.addWord(w, characters, index + 1);
	}
	
	public void addWord(Word w) {
		if (isReversed) {
			addWord(w, w.getCharacters(), 0);
		} else {
			addWord(w, w.getReversedCharacters(), 0);
		}
	}
	
	public void addDictionary(Dictionary dictionary) {
		for (Word w : dictionary.getWords()) {
			addWord(w);
		}
	}
	
	private List<Word> searchWords(List<Word> results, char[] prefix, int index, int depth) {
		if (depth < level) {
			return results;
		}
		if (modus == SearchModus.OnlyVowels) {
			if (!isVowel(prefix[index])) {
				return searchWords(results, prefix, index + 1, depth);
			}
		}
		for (DictionarySearchTree subtree : elements) {
			if (subtree.getCharacter() == prefix[index]) {
				results.addAll(subtree.getWords());
				return subtree.searchWords(results, prefix, index + 1, depth);
			}
		}
		return results;
	}
	
	public List<Word> searchWords(String prefix, int depth) {
		if (prefix.length() < 1) {
			return null;
		}
		List<Word> results = new ArrayList<Word>();
		if (isReversed) {
			StringBuilder sb = new StringBuilder(prefix);
			return searchWords(results, sb.reverse().toString().toCharArray(), 0, depth);
		}
		return searchWords(results, prefix.toCharArray(), 0, depth);
	}
}

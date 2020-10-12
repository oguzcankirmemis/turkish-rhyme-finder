package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.util.List;

// TODO: add unit tests for whole class
public class Dictionary {
	private static final int PERCENTAGE_LOGGING_THRESHOLD = 10;
	
	List<Word> words;
	
	public Dictionary(List<Word> words) {
		this.words = words;
	}
	
	public List<Word> getWords() {
		return words;
	}
	
	public void addVerbsWithoutAttachments() {
		System.out.println("Finding and adding verbs without " +
				"their attachments, this might take a while...");
		int processCounter = 1;
		int addedNewWords = 0;
		int wordsInitLen = words.size();
		for (int i = 0; i < wordsInitLen; i++) {
			if (wordsInitLen >= PERCENTAGE_LOGGING_THRESHOLD && ((double) wordsInitLen) / i >= 
					((double) PERCENTAGE_LOGGING_THRESHOLD) / processCounter) {
				processCounter++;
				System.out.println(processCounter * 10 + "% processed...");
			}
			Word w = words.get(i);
			if (w.isVerb()) {
				Word verb = w.getVerbWithoutAttachment();
				words.add(verb);
				addedNewWords++;
			}
		}
		System.out.println("Added " + addedNewWords + 
				" verbs without their attachments in total.");
	}
}

package com.github.oguzcankirmemis.turkish_rhyme_finder;

public class NotVerbException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotVerbException(Word w) {
		super("The word: " + w.getWord() + " is not recognized as a verb");
	}
}

package com.github.oguzcankirmemis.turkish_rhyme_finder;

public class Word {
	private final static int INFINITIVE_LENGTH = 3;
	private final static String INFINITIVE1 = "mek";
	private final static String INFINITIVE2 = "mak";
	
	private String word;
	private String[] meanings;
	
	public Word() {
		word = "";
		meanings = new String[0];
	}
	
	public Word(String word, String[] meanings) {
		this.word = word.toLowerCase();
		this.meanings = meanings;
		for (int i = 0; i < meanings.length; i++) {
			meanings[i] = meanings[i].toLowerCase();
		}
	}
	
	public String getWord() {
		return word;
	}
	
	public String[] getMeanings() {
		return meanings;
	}
	
	// TODO: add unit test for this method
	public char[] getCharacters() {
		return word.toCharArray();
	}
	
	public char[] getReversedCharacters() {
		char[] reversedChars = new char[word.length()];
		for (int i = 0; i < reversedChars.length; i++) {
			reversedChars[i] = word.charAt(reversedChars.length - 1 - i);
		}
		return reversedChars;
	}
	
	private boolean isVowel(char c) {
		return c == 'a' || c == 'e' || c == 'ı' || c == 'i' || 
				c == 'u' || c == 'ü' || c == 'o' || c == 'ö';
	}
	
	public char[] getReversedVowels() {
		StringBuilder sb = new StringBuilder();
		for (int i = word.length() - 1; i >= 0; i--) {
			if (isVowel(word.charAt(i))) {
				sb.append(word.charAt(i));
			}
		}
		char[] reversedVowels = new char[sb.length()];
		sb.getChars(0, sb.length(), reversedVowels, 0);
		return reversedVowels;
	}
	
	public boolean isVerb() {
		String attachment = word.substring(word.length() - INFINITIVE_LENGTH, word.length());
		if (!attachment.equals(INFINITIVE1) && !attachment.equals(INFINITIVE2)) {
			return false;
		} else {
			if (meanings.length == 0) {
				return true;
			} else {
				for (int i = 0; i < meanings.length; i++) {
					String meaning = meanings[i];
					String meaningAttachment = meaning.substring(
							meaning.length() - 3, meaning.length());
					if (meaningAttachment.equals(INFINITIVE1) || meaningAttachment.equals(INFINITIVE2)) {
						return true;
					}
				}
				return false;
			}
		}
	}
	
	public Word getVerbWithoutAttachment() {
		if (!this.isVerb()) {
			throw new NotVerbException(this);
		}
		return new Word(word.substring(0, word.length() - INFINITIVE_LENGTH), meanings);
	}
	
	@Override
	public String toString() {
		return word;
	}
	
	public String toStringWithMeanings() {
		StringBuilder sb = new StringBuilder();
		sb.append(word);
		sb.append(": ");
		for (int i = 0; i < meanings.length; i++) {
			sb.append(meanings[i]);
			if (i != meanings.length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}

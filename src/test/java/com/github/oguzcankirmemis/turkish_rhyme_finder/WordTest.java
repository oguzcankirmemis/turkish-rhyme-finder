package com.github.oguzcankirmemis.turkish_rhyme_finder;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordTest {
	private String word = "balkon";
	private String reversedVowels = "oa";
	private String meaning1 = "bir yapının genellikle dışarıya doğru çıkmış, " +
			"çevresi duvar veya parmaklıkla çevrili bölümü";
	private String meaning2 = "tiyatro, sinema vb. büyük salonlarda asma kat";
	private String meaning3 = "vücudun göğüs veya göbek bölümü";
	
	private String verb = "ballanmak";
	private String verbWithoutAttachment = "ballan";
	private String verbMeaning1 = "lore ipsum";
	private String verbMeaning2 = "bal bulaşmak, bal sürülmek";
	private String verbMeaning3 = "test";
	
	private Word getTestWord() {
		List<String> meanings = new ArrayList<String>();
		meanings.add(meaning1);
		meanings.add(meaning2);
		meanings.add(meaning3);
		return new Word(word, meanings);
	}
	
	private Word getTestVerb() {
		List<String> meanings = new ArrayList<String>();
		meanings.add(verbMeaning1);
		meanings.add(verbMeaning2);
		meanings.add(verbMeaning3);
		return new Word(verb, meanings);
	}
	
	@Test
	public void shouldGetWord() {
		Word w = getTestWord();
		Assertions.assertEquals(word, w.getWord());
	}
	
	@Test
	public void shouldGetMeanings() {
		Word w = getTestWord();
		List<String> meanings = w.getMeanings();
		Assertions.assertEquals(3, meanings.size());
		Assertions.assertEquals(meaning1, meanings.get(0));
		Assertions.assertEquals(meaning2, meanings.get(1));
		Assertions.assertEquals(meaning3, meanings.get(2));
	}
	
	@Test
	public void shouldGetReversedCharacters() {
		Word w = getTestWord();
		char[] rc = w.getReversedCharacters();
		StringBuilder sb = new StringBuilder();
		for (int i = rc.length - 1; i >= 0; i--) {
			sb.append(rc[i]);
		}
		Assertions.assertEquals(word, sb.toString());
	}
	
	@Test
	public void shouldGetReversedVowels() {
		Word w = getTestWord();
		char[] rv = w.getReversedVowels();
		StringBuilder sb = new StringBuilder();
		sb.append(rv);
		Assertions.assertEquals(reversedVowels, sb.toString());
	}
	
	@Test
	public void shouldIdentifyNotVerb() {
		Word w = getTestWord();
		Assertions.assertFalse(w.isVerb());
	}
	
	@Test
	public void shouldIdentifyVerb() {
		Word w = getTestVerb();
		Assertions.assertTrue(w.isVerb());
	}
	
	@Test
	public void shouldThrowNotVerbException() {
		Word w = getTestWord();
		Assertions.assertThrows(NotVerbException.class, () -> { w.getVerbWithoutAttachment(); });
	}
	
	@Test
	public void shouldGetVerbWithoutAttachment() {
		Word w = getTestVerb();
		List<String> meanings = w.getMeanings();
		Assertions.assertEquals(verbWithoutAttachment, w.getVerbWithoutAttachment().getWord());
		Assertions.assertEquals(3, meanings.size());
		Assertions.assertEquals(verbMeaning1, meanings.get(0));
		Assertions.assertEquals(verbMeaning2, meanings.get(1));
		Assertions.assertEquals(verbMeaning3, meanings.get(2));
	}
	
	@Test
	public void shouldGetShortStringRepresentation() {
		Word w = getTestWord();
		Assertions.assertEquals(word, w.toString());
	}
	
	@Test
	public void shouldGetLongStringRepresentation() {
		Word w = getTestWord();
		Assertions.assertEquals(word + ": " + meaning1 + ", " +
				meaning2 + ", " + meaning3, w.toStringWithMeanings());	
	}
}

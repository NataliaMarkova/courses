package ua.epamcourses.natalia_markova.project2.controller;

import ua.epamcourses.natalia_markova.project2.model.*;
import ua.epamcourses.natalia_markova.project2.model.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public class TextParserImpl implements TextParser {

    private List<Character> characters = new ArrayList<>();
    private Map<String, SentenceElement> elements = new HashMap<>();
    private List<Sentence> sentences = new ArrayList<>();

    private static String lineSeparator =  System.getProperty("line.separator");

    @Override
    public Text parse(String str) {
        sentences = new ArrayList<>();
        str.replaceAll(" {2,}", "");
        Pattern pattern = Pattern.compile("\\. \"?|(\\.){3,}( ?\")?$|\\.\"|(\\?)+(!)*(\")?|(!)+(\")?|" + lineSeparator);
        Matcher matcher = pattern.matcher(str);
        int startIndex = 0;
        while (matcher.find()) {
            String sentence = str.substring(startIndex, matcher.start()).trim();
            addSentence(sentence, matcher.group().replaceAll(lineSeparator, ""));
            startIndex = matcher.end();
        }
        String sentence = str.substring(startIndex).trim();
        addSentence(sentence, "");
        return new Text(sentences);
    }

    private void addSentence(String str, String endOfSentence) {
        Sentence sentence = getSentence(str, endOfSentence);
        if (!sentence.isEmpty()) {
            sentences.add(sentence);
        }
    }

    private Sentence getSentence(String str, String endOfSentence) {
        List<SentenceElement> sentenceElements = new ArrayList<>();
        if (str.isEmpty()) {
            return new Sentence(sentenceElements);
        }
        Pattern pattern = Pattern.compile(" |\\.|;|,| - |\\(|\\)|<|>|\"|'");
        Matcher matcher = pattern.matcher(str);

        int startIndex = 0;
        while (matcher.find()) {
            String word = str.substring(startIndex, matcher.start());
            addSentenceElement(sentenceElements, word);
            if (sentenceElements.size() > 0) {
                addSentenceElement(sentenceElements, matcher.group());
        }
            startIndex = matcher.end();
        }
        addSentenceElement(sentenceElements, str.substring(startIndex));
        if (sentenceElements.size() > 0) {
            addSentenceElement(sentenceElements, endOfSentence);
        }
        return new Sentence(sentenceElements);
    }

    private void addSentenceElement(List<SentenceElement> sentenceElements, String str) {
        if (str.isEmpty()) {
            return;
        }
        sentenceElements.add(getSentenceElement(str));
    }

    private SentenceElement getSentenceElement(String str) {
        SentenceElement sentenceElement = elements.get(str);
        if (sentenceElement == null) {
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                Character ch = getCharacter(str.charAt(i));
                chars.add(ch);
            }
            SentenceElementType type = getElementType(str);
            sentenceElement = new SentenceElement(chars, type);
            elements.put(str, sentenceElement);
        }
        return sentenceElement;
    }

    private SentenceElementType getElementType(String str) {
        Pattern pattern = Pattern.compile(" |;|,| - |\\(|\\)|\\.|!|\\?");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return SentenceElementType.PUNCTUATION_MARK;
        }
        pattern = Pattern.compile("[a-zA-Z\\u0400-\\u044F](\\w})*");
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            return SentenceElementType.WORD;
        }
        return SentenceElementType.SYMBOL;
    }

    private Character getCharacter(char ch) {
        for (Character character : characters) {
            if (character.getCh() == ch) {
                return character;
            }
        }
        Character character = new Character(ch);
        characters.add(character);
        return character;
    }

}

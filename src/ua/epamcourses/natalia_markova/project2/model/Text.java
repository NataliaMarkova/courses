package ua.epamcourses.natalia_markova.project2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yack on 17.05.2016.
 */
public class Text {
    private List<Sentence> sentences = new ArrayList<>();

    public Text(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void orderSentencesByQtyOfWords() {
        sentences.sort(new Sentence.OrderByQtyOfWords());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Sentence sentence : sentences) {
            str.append(sentence.toString()).append(System.getProperty("line.separator"));
        }
        return str.toString();
    }
}

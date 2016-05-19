package ua.epamcourses.natalia_markova.project2.model;

import java.util.Comparator;
import java.util.List;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public class Sentence {
    private List<SentenceElement> elements;

    public Sentence(List<SentenceElement> elements) {
        this.elements = elements;
    }

    static class OrderByQtyOfWords implements Comparator<Sentence> {

        @Override
        public int compare(Sentence o1, Sentence o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null && o2 != null) {
                return -1;
            } else if (o1 != null && o2 == null) {
                return 1;
            } else {
                int res = o1.getQtyOfWords() - o2.getQtyOfWords();
                if (res == 0) {
                    res = o1.toString().compareTo(o2.toString());
                }
                return res;
            }
        }
    }

    public int getQtyOfWords() {
        int res = 0;
        for (SentenceElement element: elements) {
            if (element.getClass() == Word.class) {
                res++;
            }
        }
        return res;
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    @Override
    public String toString() {
        //StringBuilder str = new StringBuilder(String.valueOf(getQtyOfWords())).append(": ");
        StringBuilder str = new StringBuilder();
        for (SentenceElement element : elements) {
            str.append(element.toString());
        }
        return str.toString();
    }
}

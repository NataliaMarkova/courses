package ua.epamcourses.natalia_markova.project1.service;

import ua.epamcourses.natalia_markova.project1.model.Composition;
import ua.epamcourses.natalia_markova.project1.model.Disc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class DiscUtil {
    private DiscUtil() {

    }

    public static String getTimeAsString(int timeInSecs) {
        int min =  timeInSecs / 60;
        timeInSecs = timeInSecs - min * 60;
        return String.format("%02d:%02d", min, timeInSecs);
    }

    public static List<String> viewCompositionInformation(List<Composition> compositions) {
        List<String> info = new ArrayList<String>();
        int i = 0;
        info.add("---------------------------------------");
        for (Composition composition : compositions) {
            info.add("" + ++i + ": " + composition.toString());
        }
        info.add("---------------------------------------");
        return info;
    }

    public static List<String> viewCompositionInformation(List<Composition> compositions, Disc disc) {
        List<String> info = new ArrayList<String>();
        info.add("---------------------------------------");
        info.add(disc.getName() + ", total duration: " + getTimeAsString(disc.getTime()));
        for (String str : viewCompositionInformation(compositions)) {
            info.add(str);
        }
        return info;
    }

}

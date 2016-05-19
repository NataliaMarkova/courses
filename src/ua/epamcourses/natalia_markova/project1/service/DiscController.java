package ua.epamcourses.natalia_markova.project1.service;

import ua.epamcourses.natalia_markova.project1.model.Composition;
import ua.epamcourses.natalia_markova.project1.model.Disc;

import java.util.List;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public interface DiscController {
    boolean loadFromFile(String fileName);
    boolean saveToFile(String fileName);
    boolean loadCompositionsFromFile(String filename);

    List<String> getDiscInformation();
    List<String> getCompositions();
    boolean addCompositionToDisc(int index) throws IndexOutOfBoundsException;
    boolean removeCompositionFromDisc(int index) throws IndexOutOfBoundsException;
    void orderCompositionsByStyle();
    List<Composition> getCompositionsByTrackLength(int lenFrom, int lenTo);
    Disc getDisc();
    String getDiscName();
    void newDisc(String name);
}


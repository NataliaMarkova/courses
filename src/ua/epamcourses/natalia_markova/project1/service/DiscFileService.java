package ua.epamcourses.natalia_markova.project1.service;

import com.sun.media.sound.InvalidFormatException;
import ua.epamcourses.natalia_markova.project1.model.Composition;
import ua.epamcourses.natalia_markova.project1.model.Disc;

import java.io.IOException;
import java.util.List;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public interface DiscFileService {
    Disc loadFromFile(String filename) throws IOException;
    boolean saveToFile(Disc disc, String filename);
    List<Composition> loadCompositionsFromFile(String filename) throws IOException;
}



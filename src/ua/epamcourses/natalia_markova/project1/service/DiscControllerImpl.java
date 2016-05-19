package ua.epamcourses.natalia_markova.project1.service;

import ua.epamcourses.natalia_markova.project1.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class DiscControllerImpl implements DiscController {

    private Disc disc;
    private List<Composition> compositions;
    private DiscFileService fileService;

    public DiscControllerImpl() {
        disc = new Disc();
        compositions = new ArrayList<>();
        fileService = new DiscFileServiceImpl();
    }

    @Override
    public Disc getDisc() {
        return disc;
    }

    @Override
    public String getDiscName() {
        return disc.getName();
    }

    @Override
    public void newDisc(String name) {
        disc = new Disc(name);
    }

    @Override
    public boolean loadFromFile(String fileName) {
        try {
            disc = fileService.loadFromFile(fileName);
        } catch (Exception e) {
            return false;
        }
        disc.setIsSaved(true);
        return true;
    }

    @Override
    public boolean saveToFile(String fileName) {
        try {
            fileService.saveToFile(disc, fileName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loadCompositionsFromFile(String filename) {
        try {
            compositions = fileService.loadCompositionsFromFile(filename);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getDiscInformation() {
        return disc.getInformation();
    }

    @Override
    public List<String> getCompositions() {
        return DiscUtil.viewCompositionInformation(compositions);
    }

    @Override
    public boolean addCompositionToDisc(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= compositions.size()) {
            throw new IndexOutOfBoundsException();
        }
        Composition composition = compositions.get(index);
        return disc.addComposition(composition);
    }

    @Override
    public boolean removeCompositionFromDisc(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= disc.getCompositions().size()) {
            throw new IndexOutOfBoundsException();
        }
        Composition composition = disc.getCompositions().get(index);
        return disc.deleteComposition(composition);
    }

    @Override
    public void orderCompositionsByStyle() {
        disc.sort(new Composition.OrderByStyle());
    }

    @Override
    public List<Composition> getCompositionsByTrackLength(int lenFrom, int lenTo) {
        return disc.getCompositionsByTrackLength(lenFrom, lenTo);
    }
}

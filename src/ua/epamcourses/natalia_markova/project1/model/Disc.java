package ua.epamcourses.natalia_markova.project1.model;

import com.sun.istack.internal.NotNull;
import ua.epamcourses.natalia_markova.project1.service.DiscUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class Disc {

    private String name;
    private List<Composition> compositions;
    private boolean isSaved;

    public Disc() {
        name = "New Disc";
        compositions = new ArrayList<Composition>();
    }

    public Disc(String name) {
        this.name = name;
        compositions = new ArrayList<Composition>();
    }


    public Disc(@NotNull List<Composition> compositions) {
        this();
        this.compositions = compositions;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public boolean addComposition(Composition composition) {
        if (composition != null && compositions.contains(composition)) {
            return false;
        }
        isSaved = false;
        return compositions.add(composition);
    }

    public boolean deleteComposition(Composition composition) {
        boolean res = compositions.remove(composition);
        isSaved = (res ? false : isSaved);
        return res;
    }

    public int getTime() {
        int time = 0;
        for (Composition composition : compositions) {
            time = time + composition.getTime();
        }
        return time;
    }

    public String getTimeAsString() {
        return DiscUtil.getTimeAsString(getTime());
    }

    public void sort(Comparator<Composition> comparator) {
        compositions.sort(comparator);
        isSaved = false;
    }

    public List<Composition>  getCompositionsByTrackLength(int lenFrom, int lenTo) {
        List<Composition> list = new ArrayList<Composition>();
        for (Composition composition : compositions) {
            if (composition.getTime() >= lenFrom && composition.getTime() <= lenTo) {
                list.add(composition);
            }
        }
        return list;
    }

    public List<String> getInformation() {
        return DiscUtil.viewCompositionInformation(compositions, this);
    }

}

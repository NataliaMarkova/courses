package ua.epamcourses.natalia_markova.project1.model;

import com.sun.istack.internal.NotNull;
import ua.epamcourses.natalia_markova.project1.service.DiscUtil;

import java.util.Comparator;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class Composition {

    private String name;
    private Performer performer;
    private Album album;
    private Style style;
    private int time; // in seconds

    public Composition(@NotNull String name, @NotNull Style style) {
        this.name = name;
        this.style = style;
    }

    public Composition(@NotNull String name, Album album, @NotNull Style style, int time) {
        this.name = name;
        this.album = album;
        this.style = style;
        this.time = time;
    }

    public static class OrderByStyle implements Comparator<Composition> {
        @Override
        public int compare(Composition o1, Composition o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null && o2 != null) {
                return -1;
            } else if (o1 != null && o2 == null) {
                return 1;
            } else {
                int res = o1.getStyle().toString().compareTo(o2.getStyle().toString());
                if (res != 0) {
                    return res;
                }else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(@NotNull Style style) {
        this.style = style;
    }

    public int getTime() {
        return time;
    }

    public String getTimeAsString() {
        return DiscUtil.getTimeAsString(time);
    }

    public void setTime(int time) {
        if (time > 0) {
            this.time = time;
        }
    }

    public void setTime(int min, int sec) {
        if (min > 0 && sec > 0) {
            this.time = min * 60 + sec;
        }
    }

    @Override
    public String toString() {
        return name + ", style: " + style +
                (album != null ? ", album: " + album.toString() : "") + (performer == null ? "" : ", performer: " + performer) +
                (time == 0 ? "" : ", duration: " + getTimeAsString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        if (time != that.time) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        return style == that.style;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + time;
        return result;
    }
}

package ua.epamcourses.natalia_markova.homework.problem08.task02.film_service;

/**
 * Created by natalia_markova on 12.06.2016.
 */

enum Language {
    ENG, RUS, UKR
}

class Film {
    private String name;
    private SoundTrack soundTrack;
    private Subtitles subtitles;

    public Film(String name, SoundTrack soundTrack, Subtitles subtitles) {
        this.name = name;
        this.soundTrack = soundTrack;
        this.subtitles = subtitles;
    }

    public String getName() {
        return name;
    }

    public void show() {
        System.out.println("Showing: " + getName() + ", subtitles language: " + subtitles.getLanguage() + ", soundtrack language: " + soundTrack.getLanguage());
    }

    public void setSubtitlesLanguage(Language language) {
        if (subtitles.getLanguage() == language) {
            return;
        }
        subtitles.setLanguage(language);
        soundTrack.setLanguage(language);
    }

    public void setSoundTrackLanguage(Language language) {
        if (soundTrack.getLanguage() == language) {
            return;
        }
        soundTrack.setLanguage(language);
        subtitles.setLanguage(language);
    }
}

class SoundTrack {
    private Language language;

    public SoundTrack(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

class Subtitles {
    private Language language;

    public Subtitles(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

abstract class FilmFactory {
    public abstract Film getFilm(String name);
}

class EngFilmFactory extends FilmFactory {
    @Override
    public Film getFilm(String name) {
        return new Film(name, new SoundTrack(Language.ENG), new Subtitles(Language.ENG));
    }
}

class RusFilmFactory extends FilmFactory {
    @Override
    public Film getFilm(String name) {
        return new Film(name, new SoundTrack(Language.RUS), new Subtitles(Language.RUS));
    }
}

class UkrFilmFactory extends FilmFactory {
    @Override
    public Film getFilm(String name) {
        return new Film(name, new SoundTrack(Language.UKR), new Subtitles(Language.UKR));
    }
}

public class FilmAbstractFactory {

    FilmFactory getFilmFactory(Language language) {
        switch (language) {
            case ENG: return new EngFilmFactory();
            case RUS: return new RusFilmFactory();
            case UKR: return new UkrFilmFactory();
        }
        return null;
    }

    public static void main(String[] args) {
        FilmAbstractFactory abstractFactory = new FilmAbstractFactory();
        FilmFactory factory = abstractFactory.getFilmFactory(Language.ENG);
        Film film = factory.getFilm("The hollow crown");
        film.show();
        film.setSoundTrackLanguage(Language.UKR);
        film.show();
        film.setSubtitlesLanguage(Language.RUS);
        film.show();
    }
}
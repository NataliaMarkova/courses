package ua.epamcourses.natalia_markova.project1.service;

import com.sun.media.sound.InvalidFormatException;
import ua.epamcourses.natalia_markova.project1.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class DiscFileServiceImpl implements DiscFileService {

    @Override
    public Disc loadFromFile(String fileName) throws IOException {
        Disc disc = new Disc();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line != null) {
                disc.setName(line);
            }
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length != 6) {
                    reader.close();
                    throw new InvalidFormatException();
                }
                disc.addComposition(makeCompositionFromStringData(data));

            }
        } catch (IOException e) {
            throw e;
        }
        disc.setIsSaved(true);
        return disc;
    }

    private Composition makeCompositionFromStringData(String[] data) {
        String compositionName = data[0];
        Style style = Style.valueOf(data[1]);
        String albumName = data[2];
        String performerName = data[3];
        int albumYear = 0;
        if (!data[4].isEmpty()) {
            albumYear = Integer.parseInt(data[4]);
        }
        int time = 0;
        if (!data[5].isEmpty()) {
            time = Integer.parseInt(data[5]);
        }

        Composition composition = new Composition(compositionName, style);
        composition.setTime(time);

        Album album = null;
        if (!albumName.isEmpty()) {
            album = new Album(albumName);
            album.setYear(albumYear);
            composition.setAlbum(album);
        }

        Performer performer = null;
        if (!performerName.isEmpty()) {
            performer = new Performer(performerName);
            composition.setPerformer(performer);
        }

        return composition;
    }

    @Override
    public boolean saveToFile(Disc disc, String fileName) {

        StringBuilder str = new StringBuilder();
        str.append(disc.getName()).append(System.getProperty("line.separator")); // disc name
        for (Composition composition: disc.getCompositions()) {
            str.append(composition.getName()).append(";"); // name
            str.append(composition.getStyle()).append(";"); // style
            str.append(composition.getAlbum() == null ? "" : composition.getAlbum().getName()).append(";"); // album name
            str.append((composition.getPerformer() == null ? "" : composition.getPerformer().toString())).append(";"); // performer name
            str.append(composition.getAlbum() == null || composition.getAlbum().getYear() == 0 ? "" : composition.getAlbum().getYear()).append(";"); //  album release year
            str.append(composition.getTime() == 0 ? "" : composition.getTime()).append(System.getProperty("line.separator")); // track duration
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(str.toString());
        } catch (IOException e) {
            return false;
        }

        disc.setIsSaved(true);
        return true;
    }

    @Override
    public List<Composition> loadCompositionsFromFile(String filename) throws IOException{
        List<Composition> compositions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length != 6) {
                    reader.close();
                    throw new InvalidFormatException();
                }
                Composition composition = makeCompositionFromStringData(data);
                if (!compositions.contains(composition)) {
                    compositions.add(composition);
                }

            }
        } catch (IOException e) {
            throw e;
        }

        return compositions;
    }


}

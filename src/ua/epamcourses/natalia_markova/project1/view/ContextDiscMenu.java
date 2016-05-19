package ua.epamcourses.natalia_markova.project1.view;

import ua.epamcourses.natalia_markova.project1.model.Composition;
import ua.epamcourses.natalia_markova.project1.service.DiscController;
import ua.epamcourses.natalia_markova.project1.service.DiscControllerImpl;
import ua.epamcourses.natalia_markova.project1.service.DiscUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class ContextDiscMenu {

    private static DiscController discController;
    private static BufferedReader reader;
    private static final String compositionsFileName = "\\compositions.txt";

    public static void main(String[] args) throws IOException {

        discController = new DiscControllerImpl();
        reader = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";

        if (loadCompositions()) {
            System.out.println("Compositions was successfully loaded");
        } else {
            System.out.println("!! Failed to load compositions");
            System.exit(0);
        }

        while (!choice.equals("e")) {
            printMenu();
            choice = reader.readLine();
            switch (choice) {
                case "0":
                    createNewDisc();
                    break;
                case "1":
                    loadFromFile();
                    break;
                case "2":
                    saveToFile();
                    break;
                case "3":
                    viewInformation();
                    break;
                case "4":
                    sortCompositionsByStyles();
                    break;
                case "5":
                    findTracksByTiming();
                    break;
                case "6":
                    viewCompositions();
                    break;
                case "7":
                    addCompositionToDisc();
                    break;
                case "8":
                    removeCompositionFromDisc();
                    break;
            }
        }

        saveDiscIfNeeded();
        reader.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("0: create new disc");
        System.out.println("1: load disc from file");
        System.out.println("2: save disc to file");
        System.out.println("3: view disc information");
        System.out.println("4: sort disc compositions by style");
        System.out.println("5: find tracks on disc by timing");
        System.out.println("6: view all compositions");
        System.out.println("7: add composition to disc");
        System.out.println("8: remove composition from disc");
        System.out.println("e: exit");
        System.out.println("Enter you choice: ");
    }

    private static void createNewDisc() {
        saveDiscIfNeeded();
        System.out.println("Enter new disc name: ");
        try {
            String input = reader.readLine();
            discController.newDisc(input);
            System.out.println("New disc was successfully created");
        } catch (IOException e) {
            System.out.println("!! An error occurred");
        }
    }

    private static void saveDiscIfNeeded() {
        if (discController.getDisc().getCompositions().size() != 0 && !discController.getDisc().isSaved()) {
            String input = "";
            do {
                System.out.println("Current disk \"" + discController.getDiscName() + "\" is not saved! Do you want to save it? y/n");
                try {
                    input = reader.readLine();
                } catch (IOException e) {
                    System.out.println("!! An error occurred");
                }
            } while (!input.equals("y") && !input.equals("n"));
            if (input.equals("y")) {
                saveToFile();
            }
        }
    }

    private static void loadFromFile() {
        System.out.println("Enter file name:");
        try {
            String fileName = reader.readLine();
            if (discController.loadFromFile(fileName)) {
                System.out.println("Information was successfully loaded");
            } else {
                System.out.println("!! Failed to load from file");
            }
        } catch (IOException e) {
            System.out.println("!! Error occurred");
        }
    }

    private static void saveToFile() {
        System.out.println("Enter file name:");
        try {
            String fileName = reader.readLine();
            if (discController.saveToFile(fileName)) {
                System.out.println("Information was successfully saved");
            } else {
                System.out.println("!! Failed to save into file");
            }
        } catch (IOException e) {
            System.out.println("!! Error occurred");
        }
    }

    private static void viewInformation() {
        for (String str: discController.getDiscInformation()) {
            System.out.println(str);
        }
    }

    private static void sortCompositionsByStyles() {
        discController.orderCompositionsByStyle();
        viewInformation();
    }

    private static void findTracksByTiming() {

        int lenFrom = 0;
        int lenTo = 0;
        try {
            System.out.println("Enter track length from (in seconds):");
            lenFrom = Integer.parseInt(reader.readLine());
            System.out.println("Enter track length to (in seconds):");
            lenTo = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            System.out.println("!! Wrong data");
            return;
        }

        if ((lenTo < lenFrom) || (lenFrom < 0) || lenTo < 0) {
            System.out.println("!! Wrong track length parameters");
            return;
        }

        List<Composition> compositions = discController.getCompositionsByTrackLength(lenFrom, lenTo);
        for (String str: DiscUtil.viewCompositionInformation(compositions)) {
            System.out.println(str);
        }
    }

    private static boolean loadCompositions() {
        String dir = System.getProperty("user.dir");
        return discController.loadCompositionsFromFile(dir + compositionsFileName);
    }

    private static void viewCompositions() {
        for (String str: discController.getCompositions()) {
            System.out.println(str);
        }
    }

    private static void addCompositionToDisc() {
        System.out.println();
        viewCompositions();
        System.out.println();
        System.out.println("Enter composition number you want to add:");
        int index = 0;
        try {
            index = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("!! Wrong index");
            return;
        }
        catch (IOException e) {
            System.out.println("!! Error occurred");
            return;
        }
        try {
            if (discController.addCompositionToDisc(index - 1)) {
                System.out.println("Composition was successfully added to disc");
            } else {
                System.out.println("!! Composition is already on disc");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("!! Wrong index: " + index);
        }
    }

    private static void removeCompositionFromDisc() {
        System.out.println();
        viewInformation();
        System.out.println();
        System.out.println("Enter composition number you want to delete:");
        int index = 0;
        try {
            index = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("!! Wrong index");
            return;
        }
        catch (IOException e) {
            System.out.println("!! Error occurred");
            return;
        }
        try {
            if (discController.removeCompositionFromDisc(index - 1)) {
                System.out.println("Composition was successfully removed from disc");
            } else {
                System.out.println("!! Failed to remove composition");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("!! Wrong index: " + index);
        }
    }

}

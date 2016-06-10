package ua.epamcourses.natalia_markova.homework.problem02.task05;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Main {

    private static StarSystem starSystem;

    public static void main(String[] args) {

        starSystem = new StarSystem("Milky Way");
        addStar("Mercury", new String[]{});
        addStar("Venus", new String[]{});
        addStar("Earth", new String[]{"Moon"});
        addStar("Mars", new String[]{});
        addStar("Jupiter", new String[]{"Io", "Europa", "Ganymede", "Callisto"});
        addStar("Saturn", new String[]{"Titan"});
        addStar("Uranus", new String[]{});
        addStar("Neptune", new String[]{"Triton"});
        addStar("Pluto", new String[]{});

        System.out.println(starSystem);
        System.out.println();
        System.out.println("The number of planets is " + starSystem.getNumberOfPlanets());
        System.out.println();
        planetInformation(0);
        planetInformation(3);
        planetInformation(5);
    }

    private static boolean addStar(String starName, String[] moonNames) {

        Star star = new Star(starName);
        for (String moonName: moonNames) {
            star.addMoon(new Moon(moonName));
        }
        return starSystem.addStar(star);

    }

    private static void planetInformation(int index) {

        try {
            String planetInfo = starSystem.getStarName(index - 1);
            System.out.println("The planet number " + index + " is " + planetInfo);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong index  " + index);
        }
    }



}

package ua.epamcourses.natalia_markova.homework.problem08.task02.strategy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by natalia_markova on 12.06.2016.
 */

abstract class Character {
    private String name;

    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;
        return name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void action() {
        if (this instanceof Flyable && this instanceof Walkable) {
            Flyable flyable = (Flyable) this;
            int flyProbability = flyable.getProbability();
            int random = getProbability();
            if (random <= flyProbability) {
                doFly();
            } else {
                doWalk();
            }
        } else if (this instanceof Flyable) {
            doFly();
        } else {
            doWalk();
        }
    }

    private void doWalk() {
        Walkable walkable = (Walkable) this;
        walkable.walk();
    }

    private void doFly() {
        Flyable flyable = (Flyable) this;
        flyable.fly();
    }

    private static int getProbability() {
        Random random = new Random();
        return random.nextInt(101);
    }
}

abstract class CharacterWithMagic extends Character implements Flyable {
    public CharacterWithMagic(String name) {
        super(name);
    }
}

interface Walkable {
    void walk();
}

interface Flyable {
    void fly();
    default int getProbability() {
        return 100;
    }
}

class Orc extends Character implements Walkable {

    public Orc(String name) {
        super(name);
    }

    @Override
    public void walk() {
        System.out.println("ORK " + getName() + " walks");
    }
}

class Pegas extends Character implements Flyable, Walkable {
    public Pegas(String name) {
        super(name);
    }

    @Override
    public void fly() {
        System.out.println("PEGAS " + getName() + " flies");
    }

    @Override
    public int getProbability() {
        return 50;
    }

    @Override
    public void walk() {
        System.out.println("PEGAS " + getName() + " walks");
    }
}

class Wizard extends CharacterWithMagic implements Walkable {
    public Wizard(String name) {
        super(name);
    }

    @Override
    public void walk() {
        System.out.println("WIZARD " + getName() + " walks");
    }

    @Override
    public void fly() {
        System.out.println("WIZARD " + getName() + " flies");
    }

    @Override
    public int getProbability() {
        return 30;
    }
}

class Harpy extends Character implements Flyable {

    public Harpy(String name) {
        super(name);
    }

    @Override
    public void fly() {
        System.out.println("HARPY " + getName() + " flies");
    }
}

public class GameStrategy {

    public static void main(String[] args) {
        Set<Character> characters = new HashSet<>();
        characters.add(new Orc("Trudel"));
        characters.add(new Pegas("Oncho"));
        characters.add(new Wizard("Gandalf"));
        characters.add(new Harpy("Irma"));

        for (int i = 0; i<=10; i++) {
            System.out.println("Move "+ i + ": ");
            for (Character character : characters) {
                character.action();
            }
            System.out.println();
        }

    }

}

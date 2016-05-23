package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Computer extends Player {

    public Computer() {
        setName("Computer");
    }

    @Override
    public Cell move() throws GameException{
        return getOtherField().getUnHitCell(getLastHitCell());
    }
}

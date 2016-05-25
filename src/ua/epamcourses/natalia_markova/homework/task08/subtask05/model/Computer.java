package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameException;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.ShipInitializingException;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Computer extends Player {

    public Computer() {
        super();
        setName("Computer");
    }

    @Override
    public Cell move() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        Cell cell = getOtherField().getUnHitCell(getLastHitShip());
        System.out.println(getName() + ": " + cell);
        return cell;
    }
}

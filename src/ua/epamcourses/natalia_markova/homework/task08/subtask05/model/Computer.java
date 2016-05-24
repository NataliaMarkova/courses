package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Computer extends Player {

    public Computer() throws ShipInitializingException {
        super();
        setName("Computer");
    }

    @Override
    public Cell move() throws GameException{
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        Cell cell = getOtherField().getUnHitCell(getLastHitCell());
        System.out.println(getName() + ": " + cell);
        return cell;
    }
}

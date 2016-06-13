package ua.epamcourses.natalia_markova.homework.problem08.task02.grant;

/**
 * Created by natalia_markova on 12.06.2016.
 */

abstract class State {
    Grant grant;

    public State(Grant grant) {
        this.grant = grant;
    }

    public void sendForConsideration() {
        System.out.println("Send to consideration");
    }

    public void delay() {
        System.out.println("Delay");
    }

    public void decline() {
        System.out.println("Decline");
    }

    public void accept() {
        System.out.println("Accept");
    }

    public void cancel() {
        System.out.println("Cancel");
    }
}

class New extends State {

    public New(Grant grant) {
        super(grant);
    }

    @Override
    public void sendForConsideration() {
        grant.state = new InProcess(grant);
    }

    @Override
    public String toString() {
        return "New";
    }
}

class InProcess extends State {

    public InProcess(Grant grant) {
        super(grant);
    }

    @Override
    public void delay(){
        grant.state = new Delayed(grant);
    }

    @Override
    public void decline(){
        grant.state = new Declined(grant);
    }

    @Override
    public void accept() {
        grant.state = new Accepted(grant);
    }

    @Override
    public void cancel() {
        grant.state = new Cancelled(grant);
    }

    @Override
    public String toString() {
        return "InProcess";
    }
}

class Delayed extends State {

    public Delayed(Grant grant) {
        super(grant);
    }

    @Override
    public void sendForConsideration() {
        grant.state = new InProcess(grant);
    }

    @Override
    public String toString() {
        return "Delayed";
    }
}

class Declined extends State {

    public Declined(Grant grant) {
        super(grant);
    }

    @Override
    public String toString() {
        return "Declined";
    }
}

class Accepted extends State {

    public Accepted(Grant grant) {
        super(grant);
    }

    @Override
    public String toString() {
        return "Accepted";
    }
}

class Cancelled extends State {

    public Cancelled(Grant grant) {
        super(grant);
    }

    @Override
    public String toString() {
        return "Cancelled";
    }
}

class Grant {
    State state;
    String name;

    public Grant(String name) {
        this.name = name;
        this.state = new New(this);
    }

    public void sendForConsideration() {
        state.sendForConsideration();
    }

    public void delay() {
        state.delay();
    }

    public void decline() {
        state.decline();
    }

    public void accept() {
        state.accept();
    }

    public void cancel() {
        state.cancel();
    }

    @Override
    public String toString() {
        return "Grant{" +
                "state=" + state +
                ", name='" + name + '\'' +
                '}';
    }
}

public class GrantState {
    public static void main(String[] args) {
        Grant grant = new Grant("Grant1");
        grant.accept();
        System.out.println(grant);
        grant.sendForConsideration();
        System.out.println(grant);
        grant.delay();
        System.out.println(grant);
        grant.accept();
        System.out.println(grant);
    }
}

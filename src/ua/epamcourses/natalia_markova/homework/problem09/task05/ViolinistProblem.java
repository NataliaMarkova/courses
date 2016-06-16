package ua.epamcourses.natalia_markova.homework.problem09.task05;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by natalia_markova on 16.06.2016.
 */

public class ViolinistProblem {

    private Violinist[] violinists;
    private Queue<Violin> violins;
    private Queue<Bow> bows;

    public ViolinistProblem(int n, int k, int l) {
        if (l <= k + n) {
            throw new IllegalArgumentException("Quantity of violinists must be more then quantity of violins plus quantity of bows");
        }
        violinists = new Violinist[l];
        for (int i = 0; i < l; i++) {
            violinists[i] = new Violinist(i);
        }
        violins = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            violins.add(new Violin(i));
        }
        bows = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            bows.add(new Bow(i));
        }
    }

    class Violinist extends Thread {
        private int index;

        public Violinist(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "Violinist " + index;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                Violin violin;
                Bow bow;
                synchronized (violins) {
                    while (violins.size() == 0) {
                        try {
                            violins.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    violin = violins.poll();
                }
                synchronized (bows) {
                    while (bows.size() == 0) {
                        try {
                            bows.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    bow = bows.poll();
                }
                System.out.println(this + " plays the " + violin + " with the " + bow);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
                synchronized (bows) {
                    bows.add(bow);
                    bows.notify();
                }
                synchronized (violins) {
                    violins.add(violin);
                    violins.notify();
                }
            }
        }
    }

    class Violin {
        private int index;

        public Violin(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "Violin " + index;
        }
    }

    class Bow {
        private int index;

        public Bow(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "Bow " + index;
        }
    }

    public void start() throws InterruptedException {
        for (Violinist violinist: violinists) {
            violinist.start();
        }
        Thread.sleep(1000);
        for (Violinist violinist: violinists) {
            violinist.interrupt();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ViolinistProblem problem = new ViolinistProblem(5, 4, 10);
        problem.start();

    }

}

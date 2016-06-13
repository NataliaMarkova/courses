package ua.epamcourses.natalia_markova.homework.problem08.task02.bacteria_population;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by natalia_markova on 12.06.2016.
 */

enum LifeStage {
    NEW, MATURE, DEAD
}

class Bacteria {
    private static final int CYCLE_MATURE = 5;
    private static final int CYCLE_DEAD = 20;

    private int cycle;

    public Bacteria() {
        this(0);
    }

    public Bacteria(int cycle) {
        if (cycle < 0) {
            throw new IllegalArgumentException();
        }
        this.cycle = Math.max(cycle, CYCLE_DEAD);
    }

    public LifeStage getLifeStage() {
        if (cycle < CYCLE_MATURE) {
            return LifeStage.NEW;
        } else if (cycle < CYCLE_DEAD) {
            return LifeStage.MATURE;
        } else {
            return LifeStage.DEAD;
        }
    }

    public int getCycle() {
        return cycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bacteria bacteria = (Bacteria) o;

        return cycle == bacteria.cycle;

    }

    @Override
    public int hashCode() {
        return cycle;
    }
}

enum BacteriaPopulationStage {
    LAG, LOG, STATIONARY, DEATH
}

class BacteriaPopulation {

    private static final int BOUND = 50;

    private Map<Integer, Bacteria> bacteriaMap = new HashMap<>();
    private List<Bacteria> population = new ArrayList<>();
    private int newCount;
    private int matureCount;
    private int deadCount;

    public BacteriaPopulation() {
    }

    public BacteriaPopulation(List<Bacteria> population) {
        for (Bacteria bacteria: population) {
            this.population.add(getBacteria(bacteria.getCycle()));
            updateCounts(bacteria);
        }
    }

    public void addBacteria(int cycle) {
        Bacteria bacteria = getBacteria(cycle);
        updateCounts(bacteria);
        population.add(bacteria);
    }

    private void updateCounts(Bacteria bacteria) {
        if (bacteria.getLifeStage() == LifeStage.NEW) {
            newCount++;
        } else if (bacteria.getLifeStage() == LifeStage.MATURE) {
            matureCount++;
        } else {
            deadCount++;
        }
    }

    public BacteriaPopulationStage getStage() {
        if (newCount - matureCount - deadCount < BOUND) {
            return BacteriaPopulationStage.LAG;
        } else if (newCount - deadCount < BOUND && newCount > 0 && deadCount > 0) {
            return BacteriaPopulationStage.STATIONARY;
        } else if (deadCount - newCount - matureCount > BOUND) {
            return BacteriaPopulationStage.DEATH;
        } else {
            return BacteriaPopulationStage.LOG;
        }
    }

    @Override
    public String toString() {
        return "BacteriaPopulation{" +
                "newCount=" + newCount +
                ", matureCount=" + matureCount +
                ", deadCount=" + deadCount +
                '}';
    }

    public void live(int cycles) {

        for (int i = 0; i < cycles; i++) {
            List<Bacteria> newBacterias = new ArrayList<>();

            for (Bacteria bacteria : population) {
                LifeStage lifeStage = bacteria.getLifeStage();
                bacteria = getBacteria(bacteria.getCycle() + 1);
                if (lifeStage != bacteria.getLifeStage()) {
                    if (lifeStage == LifeStage.MATURE) {
                        newCount--;
                    } else if (lifeStage == LifeStage.DEAD) {
                        matureCount--;
                    }
                    updateCounts(bacteria);
                }
                newBacterias.add(bacteria);
                if (bacteria.getLifeStage() == LifeStage.MATURE) {
                    newBacterias.add(getBacteria(0));
                    newCount++;
                }
            }

            population.clear();
            population.addAll(newBacterias);
        }

    }

    private Bacteria getBacteria(int cycle) {
        if (!bacteriaMap.containsKey(cycle)) {
            bacteriaMap.put(cycle, new Bacteria(cycle));
        }
        return bacteriaMap.get(cycle);
    }

}

public class BacteriaPopulationFlyweight {

    public static void main(String[] args) throws InterruptedException {

        List<Bacteria> bacterias = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bacterias.add(new Bacteria());
        }
        BacteriaPopulation population = new BacteriaPopulation(bacterias);
        System.out.println(population);
        System.out.println(population.getStage());
        population.live(20);
        System.out.println(population);
        System.out.println(population.getStage());
        population.live(20);
        System.out.println(population);
        System.out.println(population.getStage());
        population.live(5);
        System.out.println(population);
        System.out.println(population.getStage());

    }

}

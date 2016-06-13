package ua.epamcourses.natalia_markova.homework.problem08.task02.bacteria_population;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by natalia_markova on 12.06.2016.
 */

enum LifeStage {
    NEW, MATURE, DEAD
}

class SimpleBacteria {
    private LifeStage lifeStage;

    public SimpleBacteria(LifeStage lifeStage) {
        this.lifeStage = lifeStage;
    }

    public LifeStage getLifeStage() {
        return lifeStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleBacteria bacteria = (SimpleBacteria) o;
        return lifeStage == bacteria.lifeStage;
    }

    @Override
    public int hashCode() {
        return lifeStage.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(lifeStage);
    }
}

class SimpleBacteriaFactory {
    private static Map<LifeStage, SimpleBacteria> simpleBacterias = new HashMap<>();
    private SimpleBacteriaFactory() {
    }

    public static SimpleBacteria getSimpleBacteria(LifeStage lifeStage) {
        if (simpleBacterias.containsKey(lifeStage)) {
            return simpleBacterias.get(lifeStage);
        }
        SimpleBacteria simpleBacteria = new SimpleBacteria(lifeStage);
        simpleBacterias.put(lifeStage, simpleBacteria);
        return simpleBacteria;
    }
}

class Bacteria {
    private static final int CYCLE_MATURE = 5;
    private static final int CYCLE_DEAD = 20;

    private SimpleBacteria bacteria;
    private int cycle;

    public Bacteria() {
        this(0);
    }

    public Bacteria(int cycle) {
        this.cycle = cycle;
        if (cycle < CYCLE_MATURE) {
            this.bacteria = SimpleBacteriaFactory.getSimpleBacteria(LifeStage.NEW);
        } else if (cycle < CYCLE_DEAD) {
            this.bacteria = SimpleBacteriaFactory.getSimpleBacteria(LifeStage.MATURE);
        } else {
            this.bacteria = SimpleBacteriaFactory.getSimpleBacteria(LifeStage.DEAD);
        }
    }

    public LifeStage getLifeStage() {
        return bacteria.getLifeStage();
    }

    public void live() {
        cycle++;
        if (cycle == CYCLE_MATURE) {
            bacteria = SimpleBacteriaFactory.getSimpleBacteria(LifeStage.MATURE);
        }
        if (cycle == CYCLE_DEAD) {
            bacteria = SimpleBacteriaFactory.getSimpleBacteria(LifeStage.DEAD);
        }
    }
}

enum BacteriaPopulationStage {
    LAG, LOG, STATIONARY, DEATH
}

class BacteriaPopulation {

    private static final int BOUND = 50;

    private List<Bacteria> population = new ArrayList<>();
    private int newCount;
    private int matureCount;
    private int deadCount;

    public BacteriaPopulation() {
    }

    public BacteriaPopulation(List<Bacteria> population) {
        this.population = population;
        for (Bacteria bacteria: population) {
            updateCounts(bacteria);
        }
    }

    public void addBacteria(int cycle) {
        Bacteria bacteria = new Bacteria(cycle);
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
                bacteria.live();
                if (lifeStage != bacteria.getLifeStage()) {
                    if (lifeStage == LifeStage.MATURE) {
                        newCount--;
                    } else if (lifeStage == LifeStage.DEAD) {
                        matureCount--;
                    }
                    updateCounts(bacteria);
                }
                if (bacteria.getLifeStage() == LifeStage.MATURE) {
                    newBacterias.add(new Bacteria());
                }
            }

            for (Bacteria bacteria : newBacterias) {
                population.add(bacteria);
                newCount++;
            }
        }

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
        population.live(20);
        System.out.println(population);
        System.out.println(population.getStage());

    }

}

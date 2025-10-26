package genetic.chromosome;

import java.util.List;

public abstract class Chromosome {
    protected double fitness;

    public abstract void initialize();

    public abstract List<Chromosome> crossover(Chromosome other);

    public abstract void mutate();

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public abstract int getLength();

    public abstract Chromosome copy();
}

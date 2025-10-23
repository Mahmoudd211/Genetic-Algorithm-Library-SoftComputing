package genetic.chromosome;

import java.util.List;

/**
 * Abstract base class for all chromosome types in the genetic algorithm.
 * Represents a solution in the search space.
 */
public abstract class Chromosome {
    protected double fitness;

    /**
     * Initializes the chromosome with random values.
     */
    public abstract void initialize();

    /**
     * Performs crossover with another chromosome to produce offspring.
     * @param other The other parent chromosome.
     * @return List of offspring chromosomes.
     */
    public abstract List<Chromosome> crossover(Chromosome other);

    /**
     * Applies mutation to the chromosome.
     */
    public abstract void mutate();

    /**
     * Gets the fitness value of the chromosome.
     * @return The fitness value.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness value of the chromosome.
     * @param fitness The fitness value to set.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Gets the length of the chromosome (number of genes).
     * @return The length.
     */
    public abstract int getLength();

    /**
     * Creates a copy of the chromosome.
     * @return A new instance with the same values.
     */
    public abstract Chromosome copy();
}

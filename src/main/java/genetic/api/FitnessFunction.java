package genetic.api;

import genetic.chromosome.Chromosome;

/**
 * Interface for fitness evaluation functions.
 */
public interface FitnessFunction {
    /**
     * Evaluates the fitness of a chromosome.
     * @param chromosome The chromosome to evaluate.
     * @return The fitness value (higher is better).
     */
    double evaluate(Chromosome chromosome);
}

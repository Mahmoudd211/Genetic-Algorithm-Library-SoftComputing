package genetic.selection;

import genetic.Population;
import genetic.chromosome.Chromosome;

/**
 * Interface for selection strategies in genetic algorithms.
 */
public interface SelectionStrategy {
    /**
     * Selects a chromosome from the population.
     * @param population The population to select from.
     * @return The selected chromosome.
     */
    Chromosome select(Population population);
}

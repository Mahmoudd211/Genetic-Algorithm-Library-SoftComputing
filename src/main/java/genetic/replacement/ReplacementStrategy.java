package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.List;

/**
 * Interface for replacement strategies in genetic algorithms.
 */
public interface ReplacementStrategy {
    /**
     * Replaces the population with new offspring.
     * @param currentPopulation The current population.
     * @param offspring The new offspring.
     * @return The new population.
     */
    Population replace(Population currentPopulation, List<Chromosome> offspring);
}

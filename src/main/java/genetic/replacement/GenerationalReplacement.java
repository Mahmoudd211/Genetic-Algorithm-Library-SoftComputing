package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.List;

/**
 * Generational replacement strategy: replaces the entire population with offspring.
 */
public class GenerationalReplacement implements ReplacementStrategy {
    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        return new Population(offspring, currentPopulation.getFitnessFunction());
    }
}

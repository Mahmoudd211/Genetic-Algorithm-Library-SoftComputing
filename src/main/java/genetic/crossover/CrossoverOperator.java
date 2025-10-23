package genetic.crossover;

import genetic.chromosome.Chromosome;
import java.util.List;

/**
 * Interface for crossover operators.
 */
public interface CrossoverOperator {
    /**
     * Performs crossover between two parent chromosomes.
     * @param parent1 The first parent.
     * @param parent2 The second parent.
     * @return List of offspring chromosomes.
     */
    List<Chromosome> crossover(Chromosome parent1, Chromosome parent2);
}

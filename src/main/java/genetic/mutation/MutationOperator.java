package genetic.mutation;

import genetic.chromosome.Chromosome;

/**
 * Interface for mutation operators.
 */
public interface MutationOperator {
    /**
     * Applies mutation to a chromosome.
     * @param chromosome The chromosome to mutate.
     */
    void mutate(Chromosome chromosome);
}

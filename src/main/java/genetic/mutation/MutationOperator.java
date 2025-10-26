package genetic.mutation;

import genetic.chromosome.Chromosome;

public interface MutationOperator {
    void mutate(Chromosome chromosome, int currentGeneration, int maxGenerations);
}

package genetic.api;

import genetic.chromosome.Chromosome;

public interface FitnessFunction {
    double evaluate(Chromosome chromosome);
}

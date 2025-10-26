package genetic.selection;

import genetic.Population;
import genetic.chromosome.Chromosome;

public interface SelectionStrategy {
    Chromosome select(Population population);
}

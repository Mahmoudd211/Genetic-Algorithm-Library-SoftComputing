package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.List;

public interface ReplacementStrategy {
    Population replace(Population currentPopulation, List<Chromosome> offspring);
}

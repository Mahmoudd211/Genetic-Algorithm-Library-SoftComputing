package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.List;

public class GenerationalReplacement implements ReplacementStrategy {
    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        return new Population(offspring, currentPopulation.getFitnessFunction());
    }
}

package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;

/**
 * Elitism replacement strategy: keeps the best individuals from the current population.
 */
public class Elitism implements ReplacementStrategy {
    private int eliteSize;

    public Elitism(int eliteSize) {
        this.eliteSize = eliteSize;
    }

    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        List<Chromosome> combined = new ArrayList<>(currentPopulation.getChromosomes());
        combined.addAll(offspring);
        combined.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness())); // descending
        List<Chromosome> newPop = combined.subList(0, currentPopulation.getSize());
        return new Population(newPop, currentPopulation.getFitnessFunction());
    }
}

package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;

/**
 * Steady-state replacement strategy: replaces a few individuals at a time.
 */
public class SteadyState implements ReplacementStrategy {
    private int numToReplace;

    public SteadyState(int numToReplace) {
        this.numToReplace = numToReplace;
    }

    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        List<Chromosome> newPop = new ArrayList<>(currentPopulation.getChromosomes());
        // Replace the worst numToReplace individuals with offspring
        for (int i = 0; i < numToReplace && i < offspring.size(); i++) {
            newPop.set(currentPopulation.getSize() - 1 - i, offspring.get(i));
        }
        return new Population(newPop, currentPopulation.getFitnessFunction());
    }
}

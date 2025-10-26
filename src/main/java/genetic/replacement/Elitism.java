package genetic.replacement;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;

public class Elitism implements ReplacementStrategy {
    private int eliteSize;

    public Elitism(int eliteSize) {
        this.eliteSize = eliteSize;
    }

    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        List<Chromosome> current = new ArrayList<>(currentPopulation.getChromosomes());
        current.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness())); // descending
        List<Chromosome> elites = current.subList(0, Math.min(eliteSize, current.size()));

        List<Chromosome> offspringSorted = new ArrayList<>(offspring);
        offspringSorted.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness())); // descending
        int remaining = currentPopulation.getSize() - elites.size();
        List<Chromosome> bestOffspring = offspringSorted.subList(0, Math.min(remaining, offspringSorted.size()));

        List<Chromosome> newPop = new ArrayList<>(elites);
        newPop.addAll(bestOffspring);
        return new Population(newPop, currentPopulation.getFitnessFunction());
    }
}

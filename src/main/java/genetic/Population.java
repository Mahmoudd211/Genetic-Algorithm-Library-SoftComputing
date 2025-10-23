package genetic;

import genetic.api.FitnessFunction;
import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a population of chromosomes in the genetic algorithm.
 */
public class Population {
    private List<Chromosome> chromosomes;
    private int size;
    private FitnessFunction fitnessFunction;

    public Population(int size, Chromosome prototype) {
        this.size = size;
        this.chromosomes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Chromosome chrom = prototype.copy();
            chrom.initialize();
            chromosomes.add(chrom);
        }
    }

    public Population(List<Chromosome> chromosomes) {
        this.chromosomes = new ArrayList<>(chromosomes);
        this.size = chromosomes.size();
    }

    public Population(List<Chromosome> chromosomes, FitnessFunction fitnessFunction) {
        this.chromosomes = new ArrayList<>(chromosomes);
        this.size = chromosomes.size();
        this.fitnessFunction = fitnessFunction;
    }

    /**
     * Evaluates the fitness of all chromosomes in the population.
     */
    public void evaluateFitness(genetic.api.FitnessFunction fitnessFunction) {
        for (Chromosome chrom : chromosomes) {
            chrom.setFitness(fitnessFunction.evaluate(chrom));
        }
    }

    /**
     * Sorts the population by fitness (ascending order).
     */
    public void sortByFitness() {
        chromosomes.sort(Comparator.comparingDouble(Chromosome::getFitness));
    }

    /**
     * Gets the best chromosome in the population.
     * @return The chromosome with the highest fitness.
     */
    public Chromosome getBest() {
        return chromosomes.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElse(null);
    }

    /**
     * Gets the worst chromosome in the population.
     * @return The chromosome with the lowest fitness.
     */
    public Chromosome getWorst() {
        return chromosomes.stream().min(Comparator.comparingDouble(Chromosome::getFitness)).orElse(null);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public int getSize() {
        return size;
    }

    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
        this.size = chromosomes.size();
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }
}

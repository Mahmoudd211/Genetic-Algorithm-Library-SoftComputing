package genetic;

import genetic.api.FitnessFunction;
import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public void evaluateFitness(genetic.api.FitnessFunction fitnessFunction) {
        for (Chromosome chrom : chromosomes) {
            chrom.setFitness(fitnessFunction.evaluate(chrom));
        }
    }

    public void sortByFitness() {
        chromosomes.sort(Comparator.comparingDouble(Chromosome::getFitness));
    }

    public Chromosome getBest() {
        // Uses stream to find chromosome with maximum fitness
        return chromosomes.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElse(null);
    }

    public Chromosome getWorst() {
        // Uses stream to find chromosome with minimum fitness
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

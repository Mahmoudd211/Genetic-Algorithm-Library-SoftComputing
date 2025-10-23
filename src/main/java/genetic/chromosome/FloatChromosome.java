package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Floating-point chromosome implementation using double array.
 */
public class FloatChromosome extends Chromosome {
    private double[] genes;
    private double minValue;
    private double maxValue;
    private Random random = new Random();

    public FloatChromosome(int length, double minValue, double maxValue) {
        this.genes = new double[length];
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = minValue + (maxValue - minValue) * random.nextDouble();
        }
    }

    @Override
    public List<Chromosome> crossover(Chromosome other) {
        // Single-point crossover
        FloatChromosome parent2 = (FloatChromosome) other;
        int point = random.nextInt(genes.length);
        FloatChromosome offspring1 = new FloatChromosome(genes.length, minValue, maxValue);
        FloatChromosome offspring2 = new FloatChromosome(genes.length, minValue, maxValue);

        for (int i = 0; i < genes.length; i++) {
            if (i < point) {
                offspring1.genes[i] = this.genes[i];
                offspring2.genes[i] = parent2.genes[i];
            } else {
                offspring1.genes[i] = parent2.genes[i];
                offspring2.genes[i] = this.genes[i];
            }
        }

        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }

    @Override
    public void mutate() {
        // Gaussian mutation
        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() < 0.05) {
                double stdDev = (maxValue - minValue) * 0.1; // 10% of range
                genes[i] += random.nextGaussian() * stdDev;
                genes[i] = Math.max(minValue, Math.min(maxValue, genes[i]));
            }
        }
    }

    @Override
    public int getLength() {
        return genes.length;
    }

    @Override
    public Chromosome copy() {
        FloatChromosome copy = new FloatChromosome(genes.length, minValue, maxValue);
        System.arraycopy(this.genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }

    public double[] getGenes() {
        return genes.clone();
    }
}

package genetic.mutation;

import genetic.chromosome.Chromosome;
import genetic.chromosome.FloatChromosome;
import java.util.Random;

/**
 * Gaussian mutation for float chromosomes.
 */
public class FloatGaussian implements MutationOperator {
    private double mutationRate;
    private double stdDev;
    private Random random = new Random();

    public FloatGaussian(double mutationRate, double stdDev) {
        this.mutationRate = mutationRate;
        this.stdDev = stdDev;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        if (chromosome instanceof FloatChromosome) {
            FloatChromosome floatChrom = (FloatChromosome) chromosome;
            double[] genes = floatChrom.getGenes();
            for (int i = 0; i < genes.length; i++) {
                if (random.nextDouble() < mutationRate) {
                    genes[i] += random.nextGaussian() * stdDev;
                    // Clamp to min/max if needed, but assuming FloatChromosome handles it
                }
            }
        }
    }
}

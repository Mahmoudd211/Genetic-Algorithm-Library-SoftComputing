package genetic.mutation;

import genetic.chromosome.BinaryChromosome;
import genetic.chromosome.Chromosome;
import java.util.Random;

/**
 * Bit flip mutation for binary chromosomes.
 */
public class BinaryBitFlip implements MutationOperator {
    private double mutationRate;
    private Random random = new Random();

    public BinaryBitFlip(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        if (chromosome instanceof BinaryChromosome) {
            BinaryChromosome binChrom = (BinaryChromosome) chromosome;
            boolean[] genes = binChrom.getGenes();
            for (int i = 0; i < genes.length; i++) {
                if (random.nextDouble() < mutationRate) {
                    genes[i] = !genes[i];
                }
            }
        }
    }
}

package genetic.mutation;

import genetic.chromosome.Chromosome;
import genetic.chromosome.IntegerChromosome;
import java.util.Random;

/**
 * Swap mutation for integer chromosomes.
 */
public class IntegerSwap implements MutationOperator {
    private double mutationRate;
    private Random random = new Random();

    public IntegerSwap(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        if (chromosome instanceof IntegerChromosome) {
            IntegerChromosome intChrom = (IntegerChromosome) chromosome;
            int[] genes = intChrom.getGenes();
            for (int i = 0; i < genes.length; i++) {
                if (random.nextDouble() < mutationRate) {
                    int j = random.nextInt(genes.length);
                    int temp = genes[i];
                    genes[i] = genes[j];
                    genes[j] = temp;
                }
            }
        }
    }
}

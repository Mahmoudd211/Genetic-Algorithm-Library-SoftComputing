package genetic.mutation;

import genetic.chromosome.Chromosome;
import genetic.chromosome.IntegerChromosome;
import java.util.Random;

public class IntegerRandomResetting implements MutationOperator {
    private double mutationRate;
    private Random random = new Random();

    public IntegerRandomResetting(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome, int currentGeneration, int maxGenerations) {
        if (chromosome instanceof IntegerChromosome) {
            IntegerChromosome intChrom = (IntegerChromosome) chromosome;
            int[] genes = intChrom.getGenes();
            int minValue = intChrom.getMinValue();
            int maxValue = intChrom.getMaxValue();
            for (int i = 0; i < genes.length; i++) {
                if (random.nextDouble() < mutationRate) {
                    genes[i] = random.nextInt(maxValue - minValue + 1) + minValue;
                }
            }
        }
    }
}

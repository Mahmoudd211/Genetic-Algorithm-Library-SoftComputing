package genetic.mutation;

import genetic.chromosome.Chromosome;
import genetic.chromosome.IntegerChromosome;
import java.util.Random;

public class IntegerCreepMutation implements MutationOperator {
    private double mutationRate;
    private int creepRange;
    private Random random = new Random();

    public IntegerCreepMutation(double mutationRate, int creepRange) {
        this.mutationRate = mutationRate;
        this.creepRange = creepRange;
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
                    int delta = random.nextInt(2 * creepRange + 1) - creepRange;
                    genes[i] += delta;
                    genes[i] = Math.max(minValue, Math.min(maxValue, genes[i]));
                }
            }
        }
    }
}

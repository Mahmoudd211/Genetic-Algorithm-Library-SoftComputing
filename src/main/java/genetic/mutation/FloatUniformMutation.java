package genetic.mutation;

import genetic.chromosome.Chromosome;
import genetic.chromosome.FloatChromosome;
import java.util.Random;

public class FloatUniformMutation implements MutationOperator {
    private double mutationRate;
    private Random random = new Random();

    public FloatUniformMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome, int currentGeneration, int maxGenerations) {
        if (chromosome instanceof FloatChromosome) {
            FloatChromosome floatChrom = (FloatChromosome) chromosome;
            double[] genes = floatChrom.getGenes();
            double minValue = floatChrom.getMinValue();
            double maxValue = floatChrom.getMaxValue();
            for (int i = 0; i < genes.length; i++) {
                if (random.nextDouble() < mutationRate) {
                    genes[i] = minValue + (maxValue - minValue) * random.nextDouble();
                }
            }
        }
    }
}

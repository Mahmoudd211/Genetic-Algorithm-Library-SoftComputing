package genetic.crossover;

import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Uniform crossover operator.
 */
public class UniformCrossover implements CrossoverOperator {
    private Random random = new Random();

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getLength();
        Chromosome offspring1 = parent1.copy();
        Chromosome offspring2 = parent2.copy();

        if (parent1 instanceof genetic.chromosome.FloatChromosome && parent2 instanceof genetic.chromosome.FloatChromosome) {
            genetic.chromosome.FloatChromosome p1 = (genetic.chromosome.FloatChromosome) parent1;
            genetic.chromosome.FloatChromosome p2 = (genetic.chromosome.FloatChromosome) parent2;
            genetic.chromosome.FloatChromosome o1 = (genetic.chromosome.FloatChromosome) offspring1;
            genetic.chromosome.FloatChromosome o2 = (genetic.chromosome.FloatChromosome) offspring2;

            double[] g1 = p1.getGenes();
            double[] g2 = p2.getGenes();
            double[] og1 = o1.getGenes();
            double[] og2 = o2.getGenes();

            for (int i = 0; i < length; i++) {
                if (random.nextBoolean()) {
                    og1[i] = g2[i];
                    og2[i] = g1[i];
                }
            }
        }

        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }
}

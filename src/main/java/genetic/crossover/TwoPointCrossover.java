package genetic.crossover;

import genetic.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoPointCrossover implements CrossoverOperator {
    private Random random = new Random();

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getLength();
        int point1 = random.nextInt(length);
        int point2 = random.nextInt(length - point1) + point1;
        Chromosome offspring1 = parent1.copy();
        Chromosome offspring2 = parent2.copy();

        if (parent1 instanceof genetic.chromosome.BinaryChromosome && parent2 instanceof genetic.chromosome.BinaryChromosome) {
            genetic.chromosome.BinaryChromosome p1 = (genetic.chromosome.BinaryChromosome) parent1;
            genetic.chromosome.BinaryChromosome p2 = (genetic.chromosome.BinaryChromosome) parent2;
            genetic.chromosome.BinaryChromosome o1 = (genetic.chromosome.BinaryChromosome) offspring1;
            genetic.chromosome.BinaryChromosome o2 = (genetic.chromosome.BinaryChromosome) offspring2;

            boolean[] g1 = p1.getGenes();
            boolean[] g2 = p2.getGenes();
            boolean[] og1 = o1.getGenes();
            boolean[] og2 = o2.getGenes();

            for (int i = point1; i < point2; i++) {
                og1[i] = g2[i];
                og2[i] = g1[i];
            }
        } else if (parent1 instanceof genetic.chromosome.IntegerChromosome && parent2 instanceof genetic.chromosome.IntegerChromosome) {
            genetic.chromosome.IntegerChromosome p1 = (genetic.chromosome.IntegerChromosome) parent1;
            genetic.chromosome.IntegerChromosome p2 = (genetic.chromosome.IntegerChromosome) parent2;
            genetic.chromosome.IntegerChromosome o1 = (genetic.chromosome.IntegerChromosome) offspring1;
            genetic.chromosome.IntegerChromosome o2 = (genetic.chromosome.IntegerChromosome) offspring2;

            int[] g1 = p1.getGenes();
            int[] g2 = p2.getGenes();
            int[] og1 = o1.getGenes();
            int[] og2 = o2.getGenes();

            for (int i = point1; i < point2; i++) {
                og1[i] = g2[i];
                og2[i] = g1[i];
            }
        } else if (parent1 instanceof genetic.chromosome.FloatChromosome && parent2 instanceof genetic.chromosome.FloatChromosome) {
            genetic.chromosome.FloatChromosome p1 = (genetic.chromosome.FloatChromosome) parent1;
            genetic.chromosome.FloatChromosome p2 = (genetic.chromosome.FloatChromosome) parent2;
            genetic.chromosome.FloatChromosome o1 = (genetic.chromosome.FloatChromosome) offspring1;
            genetic.chromosome.FloatChromosome o2 = (genetic.chromosome.FloatChromosome) offspring2;

            double[] g1 = p1.getGenes();
            double[] g2 = p2.getGenes();
            double[] og1 = o1.getGenes();
            double[] og2 = o2.getGenes();

            for (int i = point1; i < point2; i++) {
                og1[i] = g2[i];
                og2[i] = g1[i];
            }
        }

        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }
}

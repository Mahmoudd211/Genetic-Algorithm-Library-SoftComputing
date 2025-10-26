package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerChromosome extends Chromosome {
    private int[] genes;
    private int minValue;
    private int maxValue;
    private Random random = new Random();

    public IntegerChromosome(int length, int minValue, int maxValue) {
        this.genes = new int[length];
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }
    }

    @Override
    public List<Chromosome> crossover(Chromosome other) {
        IntegerChromosome parent2 = (IntegerChromosome) other;
        int point = random.nextInt(genes.length);
        IntegerChromosome offspring1 = new IntegerChromosome(genes.length, minValue, maxValue);
        IntegerChromosome offspring2 = new IntegerChromosome(genes.length, minValue, maxValue);

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
        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() < 0.05) {
                int j = random.nextInt(genes.length);
                int temp = genes[i];
                genes[i] = genes[j];
                genes[j] = temp;
            }
        }
    }

    @Override
    public int getLength() {
        return genes.length;
    }

    @Override
    public Chromosome copy() {
        IntegerChromosome copy = new IntegerChromosome(genes.length, minValue, maxValue);
        System.arraycopy(this.genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }

    public int[] getGenes() {
        return genes.clone();
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}

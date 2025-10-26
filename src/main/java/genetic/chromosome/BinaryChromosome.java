package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryChromosome extends Chromosome {
    private boolean[] genes;
    private Random random = new Random();

    public BinaryChromosome(int length) {
        this.genes = new boolean[length];
    }

    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextBoolean();
        }
    }

    @Override
    public List<Chromosome> crossover(Chromosome other) {
        BinaryChromosome parent2 = (BinaryChromosome) other;
        int point = random.nextInt(genes.length);
        BinaryChromosome offspring1 = new BinaryChromosome(genes.length);
        BinaryChromosome offspring2 = new BinaryChromosome(genes.length);

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
                genes[i] = !genes[i];
            }
        }
    }

    @Override
    public int getLength() {
        return genes.length;
    }

    @Override
    public Chromosome copy() {
        BinaryChromosome copy = new BinaryChromosome(genes.length);
        System.arraycopy(this.genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }

    public boolean[] getGenes() {
        return genes.clone();
    }
}

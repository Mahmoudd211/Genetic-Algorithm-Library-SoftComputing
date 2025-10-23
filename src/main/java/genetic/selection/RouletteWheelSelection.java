package genetic.selection;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.Random;

/**
 * Roulette wheel selection strategy.
 */
public class RouletteWheelSelection implements SelectionStrategy {
    private Random random = new Random();

    @Override
    public Chromosome select(Population population) {
        double totalFitness = population.getChromosomes().stream().mapToDouble(Chromosome::getFitness).sum();
        double spin = random.nextDouble() * totalFitness;
        double cumulative = 0.0;

        for (Chromosome chrom : population.getChromosomes()) {
            cumulative += chrom.getFitness();
            if (cumulative >= spin) {
                return chrom;
            }
        }
        return population.getChromosomes().get(population.getSize() - 1);
    }
}

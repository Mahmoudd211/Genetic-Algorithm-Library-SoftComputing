package genetic.selection;

import genetic.Population;
import genetic.chromosome.Chromosome;
import java.util.Random;

/**
 * Tournament selection strategy.
 */
public class TournamentSelection implements SelectionStrategy {
    private int tournamentSize;
    private Random random = new Random();

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome select(Population population) {
        Chromosome best = null;
        for (int i = 0; i < tournamentSize; i++) {
            Chromosome candidate = population.getChromosomes().get(random.nextInt(population.getSize()));
            if (best == null || candidate.getFitness() > best.getFitness()) {
                best = candidate;
            }
        }
        return best;
    }
}

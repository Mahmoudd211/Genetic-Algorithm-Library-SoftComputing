package genetic.api;

import genetic.chromosome.Chromosome;

/**
 * Interface for handling infeasible solutions.
 */
public interface InfeasibleHandler {
    /**
     * Repairs or penalizes an infeasible chromosome.
     * @param chromosome The infeasible chromosome.
     * @return The repaired or penalized chromosome.
     */
    Chromosome handle(Chromosome chromosome);
}

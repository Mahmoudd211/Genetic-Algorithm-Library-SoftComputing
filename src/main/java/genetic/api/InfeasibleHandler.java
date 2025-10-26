package genetic.api;

import genetic.chromosome.Chromosome;

public interface InfeasibleHandler {
    Chromosome handle(Chromosome chromosome);
}

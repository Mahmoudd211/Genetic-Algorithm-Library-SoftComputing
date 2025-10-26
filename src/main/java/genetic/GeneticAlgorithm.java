package genetic;

import genetic.api.FitnessFunction;
import genetic.api.InfeasibleHandler;
import genetic.chromosome.Chromosome;
import genetic.crossover.CrossoverOperator;
import genetic.mutation.MutationOperator;
import genetic.replacement.ReplacementStrategy;
import genetic.selection.SelectionStrategy;
import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private int populationSize = 50;
    private int generations = 100;
    private double crossoverProbability = 0.7;
    private double mutationProbability = 0.05;
    private SelectionStrategy selectionStrategy;
    private CrossoverOperator crossoverOperator;
    private MutationOperator mutationOperator;
    private ReplacementStrategy replacementStrategy;
    private FitnessFunction fitnessFunction;
    private InfeasibleHandler infeasibleHandler;
    private Chromosome prototype;

    // Setters for configuration
    public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }
    public void setGenerations(int generations) { this.generations = generations; }
    public void setCrossoverProbability(double crossoverProbability) { this.crossoverProbability = crossoverProbability; }
    public void setMutationProbability(double mutationProbability) { this.mutationProbability = mutationProbability; }
    public void setSelectionStrategy(SelectionStrategy selectionStrategy) { this.selectionStrategy = selectionStrategy; }
    public void setCrossoverOperator(CrossoverOperator crossoverOperator) { this.crossoverOperator = crossoverOperator; }
    public void setMutationOperator(MutationOperator mutationOperator) { this.mutationOperator = mutationOperator; }
    public void setReplacementStrategy(ReplacementStrategy replacementStrategy) { this.replacementStrategy = replacementStrategy; }
    public void setFitnessFunction(FitnessFunction fitnessFunction) { this.fitnessFunction = fitnessFunction; }
    public void setInfeasibleHandler(InfeasibleHandler infeasibleHandler) { this.infeasibleHandler = infeasibleHandler; }
    public void setPrototype(Chromosome prototype) { this.prototype = prototype; }

    public Chromosome run() {
        Population population = new Population(populationSize, prototype);
        population.evaluateFitness(fitnessFunction);

        // Evolutionary loop: for each generation, create offspring via selection, crossover, mutation, then replace population
        for (int gen = 0; gen < generations; gen++) {
            List<Chromosome> offspring = new ArrayList<>();
            while (offspring.size() < populationSize) {
                Chromosome parent1 = selectionStrategy.select(population);
                Chromosome parent2 = selectionStrategy.select(population);

                Chromosome child1 = parent1.copy();
                Chromosome child2 = parent2.copy();
                if (Math.random() < crossoverProbability) {
                    crossoverOperator.crossover(child1, child2);
                }

                if (Math.random() < mutationProbability) {
                    mutationOperator.mutate(child1, gen, generations);
                }
                if (Math.random() < mutationProbability) {
                    mutationOperator.mutate(child2, gen, generations);
                }

                if (infeasibleHandler != null) {
                    infeasibleHandler.handle(child1);
                    infeasibleHandler.handle(child2);
                }

                offspring.add(child1);
                offspring.add(child2);
            }

            population = replacementStrategy.replace(population, offspring);
            population.evaluateFitness(fitnessFunction);
        }

        return population.getBest();
    }
}

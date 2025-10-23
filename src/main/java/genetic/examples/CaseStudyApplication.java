package genetic.examples;

import genetic.GeneticAlgorithm;
import genetic.api.FitnessFunction;
import genetic.api.InfeasibleHandler;
import genetic.chromosome.Chromosome;
import genetic.chromosome.FloatChromosome;
import genetic.crossover.TwoPointCrossover;
import genetic.mutation.FloatGaussian;
import genetic.replacement.Elitism;
import genetic.selection.TournamentSelection;

/**
 * Case Study: Optimizing Smart Irrigation Schedules for Agricultural Fields
 */
public class CaseStudyApplication {
    private static final int NUM_PLOTS = 5;
    private static final double MOISTURE_THRESHOLD = 0.5; // Minimum soil moisture
    private static final double[] ABSORPTION_RATES = {0.8, 0.9, 1.0, 0.7, 0.85}; // Absorption per plot
    private static final double PENALTY_FACTOR = 100.0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Custom Fitness Function
        FitnessFunction fitnessFunction = new FitnessFunction() {
            @Override
            public double evaluate(Chromosome chromosome) {
                if (!(chromosome instanceof FloatChromosome)) return 0.0;
                FloatChromosome floatChrom = (FloatChromosome) chromosome;
                double[] irrigation = floatChrom.getGenes();

                double totalWater = 0.0;
                double penalty = 0.0;

                for (int i = 0; i < NUM_PLOTS; i++) {
                    totalWater += irrigation[i];
                    double moisture = irrigation[i] * ABSORPTION_RATES[i];
                    if (moisture < MOISTURE_THRESHOLD) {
                        penalty += (MOISTURE_THRESHOLD - moisture) * PENALTY_FACTOR;
                    }
                }

                return 1.0 / (totalWater + penalty);
            }
        };

        // Custom Infeasible Handler
        InfeasibleHandler infeasibleHandler = new InfeasibleHandler() {
            @Override
            public Chromosome handle(Chromosome chromosome) {
                if (!(chromosome instanceof FloatChromosome)) return chromosome;
                FloatChromosome floatChrom = (FloatChromosome) chromosome;
                double[] irrigation = floatChrom.getGenes();

                for (int i = 0; i < NUM_PLOTS; i++) {
                    double moisture = irrigation[i] * ABSORPTION_RATES[i];
                    if (moisture < MOISTURE_THRESHOLD) {
                        irrigation[i] += (MOISTURE_THRESHOLD - moisture) / ABSORPTION_RATES[i];
                    }
                }
                return chromosome;
            }
        };

        // GA Configuration
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulationSize(50);
        ga.setGenerations(100);
        ga.setCrossoverProbability(0.7);
        ga.setMutationProbability(0.05);
        ga.setSelectionStrategy(new TournamentSelection(3));
        ga.setCrossoverOperator(new TwoPointCrossover());
        ga.setMutationOperator(new FloatGaussian(0.05, 0.1));
        ga.setReplacementStrategy(new Elitism(5));
        ga.setFitnessFunction(fitnessFunction);
        ga.setInfeasibleHandler(infeasibleHandler);
        ga.setPrototype(new FloatChromosome(NUM_PLOTS, 0.0, 10.0)); // 0-10 liters per plot

        // Run GA
        Chromosome best = ga.run();

        long endTime = System.currentTimeMillis();

        // Output Results
        System.out.println("Optimal Irrigation Plan:");
        if (best instanceof FloatChromosome) {
            FloatChromosome bestFloat = (FloatChromosome) best;
            double[] irrigation = bestFloat.getGenes();
            double totalWater = 0.0;
            for (int i = 0; i < NUM_PLOTS; i++) {
                System.out.printf("Plot %d: %.2f liters%n", i + 1, irrigation[i]);
                totalWater += irrigation[i];
            }
            System.out.printf("Total Water Usage: %.2f liters%n", totalWater);
            System.out.printf("Best Fitness: %.4f%n", best.getFitness());
            System.out.printf("Generations: 100%n");
            System.out.printf("Runtime: %d ms%n", (endTime - startTime));
        }
    }
}

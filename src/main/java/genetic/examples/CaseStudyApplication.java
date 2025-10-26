package genetic.examples;

import genetic.GeneticAlgorithm;
import genetic.api.FitnessFunction;
import genetic.api.InfeasibleHandler;
import genetic.chromosome.Chromosome;
import genetic.chromosome.FloatChromosome;
import genetic.chromosome.IntegerChromosome;
import genetic.chromosome.BinaryChromosome;
import genetic.crossover.CrossoverOperator;
import genetic.crossover.OnePointCrossover;
import genetic.crossover.TwoPointCrossover;
import genetic.crossover.UniformCrossover;
import genetic.mutation.MutationOperator;
import genetic.mutation.BinaryBitFlip;
import genetic.mutation.IntegerRandomResetting;
import genetic.mutation.IntegerCreepMutation;
import genetic.mutation.IntegerSwap;
import genetic.mutation.FloatUniformMutation;
import genetic.mutation.FloatGaussian;
import genetic.replacement.ReplacementStrategy;
import genetic.replacement.Elitism;
import genetic.replacement.SteadyState;
import genetic.replacement.GenerationalReplacement;
import genetic.selection.SelectionStrategy;
import genetic.selection.RouletteWheelSelection;
import genetic.selection.TournamentSelection;
import java.util.Scanner;

public class CaseStudyApplication {
    private static final int NUM_PLOTS = 5;
    private static final double MOISTURE_THRESHOLD = 0.5; // Minimum soil moisture
    private static final double[] ABSORPTION_RATES = {0.8, 0.9, 1.0, 0.7, 0.85}; // Absorption per plot
    private static final double PENALTY_FACTOR = 100.0;

    private static double[] decodeChromosome(Chromosome chromosome) {
        if (chromosome instanceof FloatChromosome) {
            return ((FloatChromosome) chromosome).getGenes();
        } else if (chromosome instanceof IntegerChromosome) {
            int[] intGenes = ((IntegerChromosome) chromosome).getGenes();
            double[] doubleGenes = new double[intGenes.length];
            for (int i = 0; i < intGenes.length; i++) {
                doubleGenes[i] = (double) intGenes[i];
            }
            return doubleGenes;
        } else if (chromosome instanceof BinaryChromosome) {
            boolean[] boolGenes = ((BinaryChromosome) chromosome).getGenes();
            // Assuming each plot is represented by a certain number of bits, e.g., 8 bits per plot for 0-255 range
            int bitsPerPlot = 8;
            double[] doubleGenes = new double[NUM_PLOTS];
            for (int i = 0; i < NUM_PLOTS; i++) {
                int value = 0;
                for (int j = 0; j < bitsPerPlot; j++) {
                    if (boolGenes[i * bitsPerPlot + j]) {
                        value |= (1 << (bitsPerPlot - 1 - j));
                    }
                }
                doubleGenes[i] = value / 255.0 * 10.0; // Scale to 0-10
            }
            return doubleGenes;
        }
        return new double[0];
    }

    private static void encodeChromosome(Chromosome chromosome, double[] values) {
        if (chromosome instanceof FloatChromosome) {
            FloatChromosome floatChrom = (FloatChromosome) chromosome;
            double[] genes = floatChrom.getGenes();
            System.arraycopy(values, 0, genes, 0, values.length);
        } else if (chromosome instanceof IntegerChromosome) {
            IntegerChromosome intChrom = (IntegerChromosome) chromosome;
            int[] genes = intChrom.getGenes();
            for (int i = 0; i < values.length; i++) {
                genes[i] = (int) Math.round(values[i]);
            }
        } else if (chromosome instanceof BinaryChromosome) {
            BinaryChromosome binChrom = (BinaryChromosome) chromosome;
            boolean[] genes = binChrom.getGenes();
            int bitsPerPlot = 8;
            for (int i = 0; i < NUM_PLOTS; i++) {
                int value = (int) Math.round(values[i] / 10.0 * 255.0);
                for (int j = 0; j < bitsPerPlot; j++) {
                    genes[i * bitsPerPlot + j] = (value & (1 << (bitsPerPlot - 1 - j))) != 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for chromosome type
        System.out.println("Select Chromosome Type:");
        System.out.println("1. FloatChromosome");
        System.out.println("2. IntegerChromosome");
        System.out.println("3. BinaryChromosome");
        int chromChoice = scanner.nextInt();
        Chromosome prototype = null;
        switch (chromChoice) {
            case 1:
                prototype = new FloatChromosome(NUM_PLOTS, 0.0, 10.0);
                break;
            case 2:
                prototype = new IntegerChromosome(NUM_PLOTS, 0, 10);
                break;
            case 3:
                prototype = new BinaryChromosome(NUM_PLOTS * 8); // 8 bits per plot
                break;
            default:
                System.out.println("Invalid choice, defaulting to FloatChromosome");
                prototype = new FloatChromosome(NUM_PLOTS, 0.0, 10.0);
        }

        // Prompt for selection strategy
        System.out.println("Select Selection Strategy:");
        System.out.println("1. RouletteWheelSelection");
        System.out.println("2. TournamentSelection");
        int selChoice = scanner.nextInt();
        SelectionStrategy selectionStrategy = null;
        switch (selChoice) {
            case 1:
                selectionStrategy = new RouletteWheelSelection();
                break;
            case 2:
                selectionStrategy = new TournamentSelection(3);
                break;
            default:
                System.out.println("Invalid choice, defaulting to TournamentSelection");
                selectionStrategy = new TournamentSelection(3);
        }

        // Prompt for crossover operator
        System.out.println("Select Crossover Operator:");
        System.out.println("1. OnePointCrossover");
        System.out.println("2. TwoPointCrossover");
        System.out.println("3. UniformCrossover");
        int crossChoice = scanner.nextInt();
        CrossoverOperator crossoverOperator = null;
        switch (crossChoice) {
            case 1:
                crossoverOperator = new OnePointCrossover();
                break;
            case 2:
                crossoverOperator = new TwoPointCrossover();
                break;
            case 3:
                crossoverOperator = new UniformCrossover();
                break;
            default:
                System.out.println("Invalid choice, defaulting to TwoPointCrossover");
                crossoverOperator = new TwoPointCrossover();
        }

        // Prompt for mutation operator
        System.out.println("Select Mutation Operator:");
        System.out.println("1. BinaryBitFlip (for BinaryChromosome)");
        System.out.println("2. IntegerRandomResetting (for IntegerChromosome)");
        System.out.println("3. IntegerCreepMutation (for IntegerChromosome)");
        System.out.println("4. IntegerSwap (for IntegerChromosome)");
        System.out.println("5. FloatUniformMutation (for FloatChromosome)");
        System.out.println("6. FloatGaussian (for FloatChromosome)");
        int mutChoice = scanner.nextInt();
        MutationOperator mutationOperator = null;
        switch (mutChoice) {
            case 1:
                mutationOperator = new BinaryBitFlip(0.05);
                break;
            case 2:
                mutationOperator = new IntegerRandomResetting(0.05);
                break;
            case 3:
                mutationOperator = new IntegerCreepMutation(0.05, 1);
                break;
            case 4:
                mutationOperator = new IntegerSwap(0.05);
                break;
            case 5:
                mutationOperator = new FloatUniformMutation(0.1);
                break;
            case 6:
                mutationOperator = new FloatGaussian(0.05, 0.1);
                break;
            default:
                System.out.println("Invalid choice, defaulting to FloatGaussian");
                mutationOperator = new FloatGaussian(0.05, 0.1);
        }

        // Prompt for replacement strategy
        System.out.println("Select Replacement Strategy:");
        System.out.println("1. Elitism");
        System.out.println("2. SteadyState");
        System.out.println("3. GenerationalReplacement");
        int repChoice = scanner.nextInt();
        ReplacementStrategy replacementStrategy = null;
        switch (repChoice) {
            case 1:
                replacementStrategy = new Elitism(5);
                break;
            case 2:
                replacementStrategy = new SteadyState(5);
                break;
            case 3:
                replacementStrategy = new GenerationalReplacement();
                break;
            default:
                System.out.println("Invalid choice, defaulting to Elitism");
                replacementStrategy = new Elitism(5);
        }

        long startTime = System.currentTimeMillis();

        FitnessFunction fitnessFunction = new FitnessFunction() {
            @Override
            public double evaluate(Chromosome chromosome) {
                double[] irrigation = decodeChromosome(chromosome);

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

        InfeasibleHandler infeasibleHandler = new InfeasibleHandler() {
            @Override
            public Chromosome handle(Chromosome chromosome) {
                double[] irrigation = decodeChromosome(chromosome);

                for (int i = 0; i < NUM_PLOTS; i++) {
                    double moisture = irrigation[i] * ABSORPTION_RATES[i];
                    if (moisture < MOISTURE_THRESHOLD) {
                        irrigation[i] += (MOISTURE_THRESHOLD - moisture) / ABSORPTION_RATES[i];
                    }
                }

                encodeChromosome(chromosome, irrigation);
                return chromosome;
            }
        };


        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulationSize(50);
        ga.setGenerations(100);
        ga.setCrossoverProbability(0.7);
        ga.setMutationProbability(0.05);
        ga.setSelectionStrategy(selectionStrategy);
        ga.setCrossoverOperator(crossoverOperator);
        ga.setMutationOperator(mutationOperator);
        ga.setReplacementStrategy(replacementStrategy);
        ga.setFitnessFunction(fitnessFunction);
        ga.setInfeasibleHandler(infeasibleHandler);
        ga.setPrototype(prototype);


        Chromosome best = ga.run();

        long endTime = System.currentTimeMillis();


        System.out.println("Optimal Irrigation Plan:");
        double[] irrigation = decodeChromosome(best);
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

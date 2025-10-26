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
import genetic.codec.ChromosomeToFloat;
import java.util.Scanner;

public class CaseStudyApplication {
    private static final int NUM_PLOTS = 5;
    private static final int BITS_PER_PLOT = 8;

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

        FitnessFunction fitnessFunction = new IrrigationFitnessFunction();
        InfeasibleHandler infeasibleHandler = new IrrigationInfeasibleHandler();


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
        double[] irrigation = ChromosomeToFloat.decode(best, NUM_PLOTS, BITS_PER_PLOT);
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

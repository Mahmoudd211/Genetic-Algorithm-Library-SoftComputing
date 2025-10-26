# Soft Computing GA Library: Detailed Project Explanation

## Introduction

This project implements a modular and extensible Genetic Algorithm (GA) library in Java, designed for solving optimization problems. Genetic Algorithms are inspired by natural evolution, using principles like selection, crossover, and mutation to evolve solutions over generations. The library supports multiple chromosome types, configurable operators, and includes a real-world case study for smart irrigation optimization.

The library is built with clean code principles, minimal comments, and a focus on separation of concerns across dedicated packages. It is designed for educational purposes as part of a Soft Computing course.

## Project Structure

The project follows a Maven structure with the main code in `src/main/java/genetic/`. The directory structure is as follows:

```
src/main/java/genetic/
├── chromosome/          # Chromosome implementations
│   ├── Chromosome.java (abstract base)
│   ├── BinaryChromosome.java
│   ├── IntegerChromosome.java
│   └── FloatChromosome.java
├── api/                  # Interfaces for problem-specific functions
│   ├── FitnessFunction.java
│   └── InfeasibleHandler.java
├── selection/            # Selection operators
│   ├── SelectionStrategy.java (interface)
│   ├── RouletteWheelSelection.java
│   └── TournamentSelection.java
├── crossover/            # Crossover operators
│   ├── CrossoverOperator.java (interface)
│   ├── OnePointCrossover.java
│   ├── TwoPointCrossover.java
│   └── UniformCrossover.java
├── mutation/             # Mutation operators
│   ├── MutationOperator.java (interface)
│   ├── BinaryBitFlip.java
│   ├── IntegerRandomResetting.java
│   ├── IntegerCreepMutation.java
│   ├── IntegerSwap.java
│   ├── FloatUniformMutation.java
│   └── FloatGaussian.java
├── replacement/          # Replacement strategies
│   ├── ReplacementStrategy.java (interface)
│   ├── Elitism.java
│   ├── SteadyState.java
│   └── GenerationalReplacement.java
├── Population.java       # Population management
├── GeneticAlgorithm.java # Main GA class
└── examples/             # Case study implementation
    └── CaseStudyApplication.java
```

## Core Components

### Chromosomes

Chromosomes represent potential solutions in the GA. The library supports three types: binary, integer, and floating-point.

#### Abstract Base: Chromosome.java
```java
public abstract class Chromosome {
    protected double fitness;

    public abstract void initialize();
    public abstract List<Chromosome> crossover(Chromosome other);
    public abstract void mutate();
    public double getFitness() { return fitness; }
    public void setFitness(double fitness) { this.fitness = fitness; }
    public abstract int getLength();
    public abstract Chromosome copy();
}
```

#### BinaryChromosome.java
Represents solutions as arrays of boolean values. Useful for discrete optimization problems.
- Initialization: Randomly sets each bit to true or false.
- Crossover: Implemented in operators (see below).
- Mutation: Bit flip (see BinaryBitFlip).
- Length: Number of bits.

#### IntegerChromosome.java
Represents solutions as arrays of integers within specified bounds.
- Initialization: Random values between min and max.
- Crossover: Implemented in operators.
- Mutation: Various integer mutations (see below).
- Length: Number of genes.

#### FloatChromosome.java
Represents solutions as arrays of doubles within specified bounds.
- Initialization: Random values between min and max.
- Crossover: Implemented in operators.
- Mutation: Various float mutations (see below).
- Length: Number of genes.

### Population Management: Population.java

Manages a collection of chromosomes.
- Initialization: Creates a population of specified size using a prototype chromosome.
- Fitness Evaluation: Applies the fitness function to all chromosomes.
- Sorting: By fitness (ascending or descending).
- Best/Worst: Finds chromosomes with highest/lowest fitness.

### Evolutionary Operators

#### Selection Strategies

Selection chooses parents for reproduction based on fitness.

##### SelectionStrategy.java (Interface)
```java
public interface SelectionStrategy {
    Chromosome select(Population population);
}
```

##### RouletteWheelSelection.java
Probabilistic selection where fitter individuals have higher chance of selection.
- Calculates total fitness.
- Spins a "wheel" divided by fitness proportions.
- Returns the chromosome where the spin lands.

##### TournamentSelection.java
Selects the best from a random subset (tournament).
- Randomly picks a tournament size number of individuals.
- Returns the one with highest fitness.

#### Crossover Operators

Crossover combines genetic material from two parents to create offspring.

##### CrossoverOperator.java (Interface)
```java
public interface CrossoverOperator {
    List<Chromosome> crossover(Chromosome parent1, Chromosome parent2);
}
```

##### OnePointCrossover.java
Swaps segments after a single random point.
- Chooses a random crossover point.
- Offspring1: parent1's genes before point + parent2's genes after point.
- Offspring2: parent2's genes before point + parent1's genes after point.
- Works for all chromosome types.

##### TwoPointCrossover.java
Swaps segments between two random points.
- Chooses two random points (point1 < point2).
- Swaps genes between point1 and point2.
- Offspring1: parent1's genes outside [point1, point2) + parent2's genes inside.
- Offspring2: parent2's genes outside [point1, point2) + parent1's genes inside.
- Works for all chromosome types.

##### UniformCrossover.java
Each gene is swapped with a 50% probability.
- For each gene position, randomly decides whether to swap.
- If swap: offspring1 gets parent2's gene, offspring2 gets parent1's gene.
- Else: offspring1 gets parent1's gene, offspring2 gets parent2's gene.
- Works for all chromosome types.

#### Mutation Operators

Mutation introduces random changes to maintain diversity.

##### MutationOperator.java (Interface)
```java
public interface MutationOperator {
    void mutate(Chromosome chromosome, int currentGeneration, int maxGenerations);
}
```

##### BinaryBitFlip.java
Flips bits in binary chromosomes.
- For each bit, with mutationRate probability, flips (true->false, false->true).

##### IntegerRandomResetting.java
Resets integer genes to random values.
- For each gene, with mutationRate probability, sets to random value in [min, max].

##### IntegerCreepMutation.java
Adds small random changes to integer genes.
- For each gene, with mutationRate probability, adds random delta in [-creepRange, +creepRange].
- Clamps to bounds.

##### IntegerSwap.java
Swaps two random genes in integer chromosomes.
- For each gene, with mutationRate probability, swaps with another random gene.

##### FloatUniformMutation.java
Sets float genes to uniform random values.
- For each gene, with mutationRate probability, sets to random value in [min, max].

##### FloatGaussian.java
Adds Gaussian noise to float genes.
- For each gene, with mutationRate probability, adds Gaussian noise with stdDev.
- Time-varying: noise decreases over generations.
- Clamps to bounds.

#### Replacement Strategies

Determines how the next generation is formed from current population and offspring.

##### ReplacementStrategy.java (Interface)
```java
public interface ReplacementStrategy {
    Population replace(Population currentPopulation, List<Chromosome> offspring);
}
```

##### Elitism.java
Keeps best individuals from current population.
- Sorts current population descending by fitness.
- Takes top eliteSize elites.
- Takes top (populationSize - eliteSize) from offspring (sorted descending).
- New population: elites + best offspring.

##### SteadyState.java
Replaces worst individuals with offspring.
- Replaces the worst numToReplace individuals with the first numToReplace offspring.

##### GenerationalReplacement.java
Replaces entire population with offspring.
- New population is the offspring list.

### Main GA Class: GeneticAlgorithm.java

Orchestrates the evolutionary process.
- Configuration: Population size, generations, probabilities, operators.
- Run method: Initializes population, evaluates fitness, loops through generations (select, crossover, mutate, replace), returns best solution.

### Problem-Specific Interfaces

#### FitnessFunction.java
```java
public interface FitnessFunction {
    double evaluate(Chromosome chromosome);
}
```
Defines how to calculate fitness (higher is better).

#### InfeasibleHandler.java
```java
public interface InfeasibleHandler {
    Chromosome handle(Chromosome chromosome);
}
```
Repairs or penalizes infeasible solutions.

## Case Study: Smart Irrigation Optimization

The library is demonstrated with optimizing irrigation for 5 agricultural plots.

### Problem Description
- **Objective**: Minimize total water usage while keeping soil moisture above 0.5 for each plot.
- **Variables**: Irrigation amounts (0-10 liters) per plot.
- **Constraints**: Moisture = irrigation * absorption_rate >= 0.5.
- **Absorption Rates**: [0.8, 0.9, 1.0, 0.7, 0.85]

### GA Configuration
- **Chromosome**: FloatChromosome (5 genes, 0-10).
- **Fitness**: 1 / (total_water + penalty), where penalty = 100 * sum(max(0, 0.5 - moisture)).
- **Infeasible Handling**: Increase irrigation for under-watered plots.
- **Operators**: User-selectable via console input.

### Application Flow (CaseStudyApplication.java)
1. Prompt user for chromosome type, selection, crossover, mutation, replacement.
2. Set up GA with defaults (50 pop, 100 gen, 0.7 cross prob, 0.05 mut prob).
3. Run GA, measure time.
4. Output optimal irrigation plan, total water, best fitness, runtime.

## Usage Guide

### Prerequisites
- Java 11+
- Maven 3.6+

### Building
```bash
mvn clean compile
```

### Running Case Study
```bash
mvn exec:java -Dexec.mainClass="genetic.examples.CaseStudyApplication"
```

### Using the Library

1. **Configure GA**:
```java
GeneticAlgorithm ga = new GeneticAlgorithm();
ga.setPopulationSize(50);
ga.setGenerations(100);
ga.setCrossoverProbability(0.7);
ga.setMutationProbability(0.05);
ga.setSelectionStrategy(new TournamentSelection(3));
ga.setCrossoverOperator(new TwoPointCrossover());
ga.setMutationOperator(new FloatGaussian(0.05, 0.1));
ga.setReplacementStrategy(new Elitism(5));
```

2. **Define Problem Functions**:
```java
FitnessFunction fitness = chromosome -> {
    // Calculate fitness
    return fitnessValue;
};

InfeasibleHandler handler = chromosome -> {
    // Repair chromosome
    return repaired;
};

ga.setFitnessFunction(fitness);
ga.setInfeasibleHandler(handler);
ga.setPrototype(new FloatChromosome(length, min, max));
```

3. **Run**:
```java
Chromosome best = ga.run();
```

## Default Configuration

- Population Size: 50
- Generations: 100
- Crossover Probability: 0.7
- Mutation Probability: 0.05
- Selection: Tournament (size 3)
- Crossover: Two-Point
- Replacement: Elitism (elite 5)

## Extensibility

- Add chromosome types by extending Chromosome.
- Implement new operators by extending interfaces.
- Customize via FitnessFunction and InfeasibleHandler.

## Dependencies

- Java Standard Library (no external deps).

## License

Educational purposes, Soft Computing course.

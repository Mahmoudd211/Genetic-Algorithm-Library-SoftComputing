# Soft Computing GA Library

This project implements a modular and extensible Genetic Algorithm (GA) library in Java, designed for solving optimization problems. The library supports multiple chromosome types, configurable operators, and includes a real-world case study for smart irrigation optimization.

## Features

### Core Components
- **Chromosomes**: Support for binary, integer, and floating-point representations
- **Population Management**: Handles initialization, evaluation, and sorting
- **Evolutionary Operators**:
  - Selection: Roulette Wheel and Tournament Selection
  - Crossover: One-Point, Two-Point, and Uniform Crossover
  - Mutation: Bit-Flip (binary), Swap (integer), Gaussian (floating-point)
  - Replacement: Elitism, Steady-State, and Generational Replacement
- **Infeasible Solution Handling**: Interface for custom repair or penalty mechanisms

### Architecture
- Modular design with interfaces for extensibility
- Clean code principles with minimal comments
- Separation of concerns across dedicated packages
- Problem-specific functions (fitness, infeasibility) defined via interfaces

## Project Structure

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
│   ├── IntegerSwap.java
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

## Case Study: Smart Irrigation Optimization

The library is demonstrated with a real-world optimization problem: determining optimal irrigation schedules for agricultural fields to minimize water usage while maintaining soil moisture above critical thresholds.

- **Chromosome**: Floating-point (irrigation amounts per plot)
- **Fitness Function**: 1 / (total water + penalty for moisture violations)
- **Infeasible Handling**: Repair by increasing irrigation for under-watered plots
- **Constraints**: Limited water supply, irrigation frequency, variable soil absorption

## Usage

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Building the Project
```bash
mvn clean compile
```

### Running the Case Study
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

2. **Define Problem-Specific Functions**:
```java
FitnessFunction fitness = new FitnessFunction() {
    @Override
    public double evaluate(Chromosome chromosome) {
        // Implement fitness evaluation
        return fitnessValue;
    }
};

InfeasibleHandler handler = new InfeasibleHandler() {
    @Override
    public Chromosome handle(Chromosome chromosome) {
        // Implement repair logic
        return repairedChromosome;
    }
};

ga.setFitnessFunction(fitness);
ga.setInfeasibleHandler(handler);
ga.setPrototype(new FloatChromosome(length, minValue, maxValue));
```

3. **Run GA**:
```java
Chromosome bestSolution = ga.run();
```

## Default Configuration

- Population Size: 50
- Generations: 100
- Crossover Probability: 0.7
- Mutation Probability: 0.05
- Selection: Tournament Selection (size 3)
- Crossover: Two-Point Crossover
- Replacement: Elitism (elite size 5)

## Extensibility

The library is designed for easy extension:
- Add new chromosome types by extending the abstract `Chromosome` class
- Implement new operators by extending the respective interfaces
- Customize problem-specific functions via `FitnessFunction` and `InfeasibleHandler` interfaces

## Dependencies

- Java Standard Library (no external dependencies)

## License

This project is for educational purposes as part of a Soft Computing course.

# Refactoring Tasks for Genetic Algorithm Codebase

## Tasks
- [x] Create Codec.java in src/main/java/genetic/codec/ with generalized decode and encode methods for Binary, Integer, and Float chromosomes, independent of case study.
- [x] Create IrrigationFitnessFunction.java in src/main/java/genetic/examples/ implementing FitnessFunction interface with irrigation-specific logic.
- [x] Create IrrigationInfeasibleHandler.java in src/main/java/genetic/examples/ implementing InfeasibleHandler interface with irrigation-specific logic.
- [x] Update CaseStudyApplication.java to remove embedded decode/encode methods, fitness, and infeasible handler, and use the new Codec, IrrigationFitnessFunction, and IrrigationInfeasibleHandler classes.

package genetic.examples;

import genetic.api.FitnessFunction;
import genetic.chromosome.Chromosome;
import genetic.codec.ChromosomeToFloat;

public class IrrigationFitnessFunction implements FitnessFunction {
    private static final int NUM_PLOTS = 5;
    private static final double MOISTURE_THRESHOLD = 0.5; // Minimum soil moisture
    private static final double[] ABSORPTION_RATES = {0.8, 0.9, 1.0, 0.7, 0.85}; // Absorption per plot
    private static final double PENALTY_FACTOR = 100.0;
    private static final int BITS_PER_PLOT = 8;

    @Override
    public double evaluate(Chromosome chromosome) {
        double[] irrigation = ChromosomeToFloat.decode(chromosome, NUM_PLOTS, BITS_PER_PLOT);

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
}

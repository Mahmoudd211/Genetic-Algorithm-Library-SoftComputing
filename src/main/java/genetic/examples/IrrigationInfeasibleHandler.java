package genetic.examples;

import genetic.api.InfeasibleHandler;
import genetic.chromosome.Chromosome;
import genetic.codec.ChromosomeToFloat;

public class IrrigationInfeasibleHandler implements InfeasibleHandler {
    private static final int NUM_PLOTS = 5;
    private static final double MOISTURE_THRESHOLD = 0.5; // Minimum soil moisture
    private static final double[] ABSORPTION_RATES = {0.8, 0.9, 1.0, 0.7, 0.85}; // Absorption per plot
    private static final int BITS_PER_PLOT = 8;

    @Override
    public Chromosome handle(Chromosome chromosome) {
        double[] irrigation = ChromosomeToFloat.decode(chromosome, NUM_PLOTS, BITS_PER_PLOT);

        for (int i = 0; i < NUM_PLOTS; i++) {
            double moisture = irrigation[i] * ABSORPTION_RATES[i];
            if (moisture < MOISTURE_THRESHOLD) {
                irrigation[i] += (MOISTURE_THRESHOLD - moisture) / ABSORPTION_RATES[i];
            }
        }

        ChromosomeToFloat.encode(chromosome, irrigation, BITS_PER_PLOT);
        return chromosome;
    }
}

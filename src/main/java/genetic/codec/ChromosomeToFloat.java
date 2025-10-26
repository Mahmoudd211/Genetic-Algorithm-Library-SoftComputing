package genetic.codec;

import genetic.chromosome.Chromosome;
import genetic.chromosome.FloatChromosome;
import genetic.chromosome.IntegerChromosome;
import genetic.chromosome.BinaryChromosome;

public class ChromosomeToFloat {

    /**
     * Decodes a chromosome to an array of double values.
     * For FloatChromosome: returns the genes directly.
     * For IntegerChromosome: converts int genes to double.
     * For BinaryChromosome: interprets bits as unsigned integers and scales to 0-1 range.
     * Assumes binary genes are grouped into bytes (8 bits per value).
     */
    public static double[] decode(Chromosome chromosome, int numValues, int bitsPerValue) {
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
            double[] doubleGenes = new double[numValues];
            for (int i = 0; i < numValues; i++) {
                int value = 0;
                for (int j = 0; j < bitsPerValue; j++) {
                    if (boolGenes[i * bitsPerValue + j]) {
                        value |= (1 << (bitsPerValue - 1 - j));
                    }
                }
                doubleGenes[i] = value / (Math.pow(2, bitsPerValue) - 1); // Scale to 0-1
            }
            return doubleGenes;
        }
        return new double[0];
    }

    /**
     * Encodes an array of double values into a chromosome.
     * For FloatChromosome: sets the genes directly.
     * For IntegerChromosome: rounds doubles to ints.
     * For BinaryChromosome: scales doubles to 0-1 and converts to bits.
     * Assumes binary genes are grouped into bytes (8 bits per value).
     */
    public static void encode(Chromosome chromosome, double[] values, int bitsPerValue) {
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
            for (int i = 0; i < values.length; i++) {
                int value = (int) Math.round(values[i] * (Math.pow(2, bitsPerValue) - 1));
                for (int j = 0; j < bitsPerValue; j++) {
                    genes[i * bitsPerValue + j] = (value & (1 << (bitsPerValue - 1 - j))) != 0;
                }
            }
        }
    }
}

package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.neunet.ga.NeuralNetwork;

public class Test {
    
    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(3, new int[]{3, 2}, 2, 0.1);
        
        System.out.println("WEIGHTS:");
        for (int i = 0; i < neuralNetwork.getWeights().length; i++) {
            if (i < neuralNetwork.getWeights().length - 1) {
                System.out.printf("Hidden layer %d weights%n", i);
                System.out.println();
                printMatrix(neuralNetwork.getWeights()[i]);
            } else {
                System.out.println("Output layer weights");
                System.out.println();
                printMatrix(neuralNetwork.getWeights()[i]);
            }
        }
        
        System.out.println("\nBIASES:");
        for (int i = 0; i < neuralNetwork.getBiases().length; i++) {
            if (i < neuralNetwork.getBiases().length - 1) {
                System.out.printf("Hidden layer %d biases%n", i);
                System.out.println();
                printMatrix(neuralNetwork.getBiases()[i]);
            } else {
                System.out.println("Output layer biases");
                System.out.println();
                printMatrix(neuralNetwork.getBiases()[i]);
            }
        }
        
        System.out.println("\nSUMS:");
        for (int i = 0; i < neuralNetwork.getSums().length; i++) {
            if (i < neuralNetwork.getSums().length - 1) {
                System.out.printf("Hidden layer %d sums%n", i);
                System.out.println();
                printMatrix(neuralNetwork.getSums()[i]);
            } else {
                System.out.println("Output layer sums");
                System.out.println();
                printMatrix(neuralNetwork.getSums()[i]);
            }
        }
        
        System.out.println("\nNEURONS:");
        for (int i = 0; i < neuralNetwork.getNeurons().length; i++) {
            if (i < neuralNetwork.getNeurons().length - 1) {
                System.out.printf("Hidden layer %d neurons%n", i);
                System.out.println();
                printMatrix(neuralNetwork.getNeurons()[i]);
            } else {
                System.out.println("Output layer neurons");
                System.out.println();
                printMatrix(neuralNetwork.getNeurons()[i]);
            }
        }
    }
    
    private static void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            System.out.print("| ");
            for (int j = 0; j < matrix.getColumns(); j++) {
                System.out.printf("%.2f", matrix.getValue(i, j));
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}

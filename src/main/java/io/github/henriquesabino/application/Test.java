package io.github.henriquesabino.application;

import io.github.henriquesabino.neunet.ga.GANeuralNetwork;

public class Test {
    
    public static void main(String[] args) {
        GANeuralNetwork neuralNetwork = new GANeuralNetwork(2, new int[]{2, 2}, 2, 0.1);
        
        double[] inputs = new double[]{1, 0};
        
        double[] outputs = neuralNetwork.predict(inputs);
        
        System.out.println("Test 1:");
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i]);
            
            if (i < outputs.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();
        
        System.out.println("Testing copy function:");
        
        GANeuralNetwork neuralNetwork1 = neuralNetwork.copy();
        outputs = neuralNetwork1.predict(inputs);
        
        neuralNetwork1.mutate();
        
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i]);
            
            if (i < outputs.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();
        
        
        outputs = neuralNetwork.predict(inputs);
        System.out.println("Testing mutate:");
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i]);
            
            if (i < outputs.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();
        
        outputs = neuralNetwork1.predict(inputs);
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i]);
            
            if (i < outputs.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();
    }
}

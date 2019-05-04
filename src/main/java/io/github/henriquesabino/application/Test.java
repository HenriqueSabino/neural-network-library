package io.github.henriquesabino.application;

import io.github.henriquesabino.neunet.ga.NeuralNetwork;

import java.io.IOException;

public class Test {
    
    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, new int[]{2, 2}, 2, 0.1);
        
        double[] inputs = new double[]{1, 0};
        
        double[] outputs = neuralNetwork.predict(inputs);
        
        try {
            neuralNetwork.debugNeuNet("out/neunet.txt");
            neuralNetwork.saveNeuNet("out/neunet.nnt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
        
        System.out.println("Testing loading from file:");
        
        NeuralNetwork neuralNetwork1 = null;
        try {
            neuralNetwork1 = NeuralNetwork.loadNeuNet("out/neunet.nnt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        outputs = neuralNetwork1.predict(inputs);
        System.out.print("[");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i]);
            
            if (i < outputs.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

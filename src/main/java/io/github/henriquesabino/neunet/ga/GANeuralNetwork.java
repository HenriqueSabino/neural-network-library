package io.github.henriquesabino.neunet.ga;

import io.github.henriquesabino.neunet.NeuralNetwork;

public class GANeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = 8585436273457165001L;
    private double mutationRate;
    
    
    public GANeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double mutationRate) {
        super(inputsNum, hiddenLayersSize, outputsNum);
        this.mutationRate = mutationRate;
    }
    
    public GANeuralNetwork copy() {
        GANeuralNetwork copy = new GANeuralNetwork(inputsNum, hiddenLayersSize.clone(), outputsNum, mutationRate);
        
        for (int i = 0; i < copy.weights.length; i++) {
            copy.weights[i] = weights[i].copy();
        }
        
        for (int i = 0; i < copy.biases.length; i++) {
            copy.biases[i] = biases[i].copy();
        }
        
        return copy;
    }
    
    //Sometimes it's interesting to change the mutation rate over time
    public double getMutationRate() {
        return mutationRate;
    }
    
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}

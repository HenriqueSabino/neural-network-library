package io.github.henriquesabino.neunet.bp;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.neunet.NeuralNetwork;

public class BPNeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = -3732215973838717612L;
    private double cost, learningRate;
    
    public BPNeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double learningRate) {
        super(inputsNum, hiddenLayersSize, outputsNum);
        this.learningRate = learningRate;
        
        costs = new Matrix[netSize];
        deltaWeights = new Matrix[netSize];
        deltaBiases = new Matrix[netSize];
        cost = 0;
        
        for (int i = 0; i < neurons.length; i++) {
            costs[i] = neurons[i].copy();
            costs[i].applyForEach(x -> 0.0);
        }
        
        for (int i = 0; i < deltaWeights.length; i++) {
            deltaWeights[i] = weights[i].copy();
            deltaWeights[i].applyForEach(x -> 0.0);
        }
        
        for (int i = 0; i < deltaBiases.length; i++) {
            deltaBiases[i] = biases[i].copy();
            deltaBiases[i].applyForEach(x -> 0.0);
        }
    }
    
    private double dSig(double x) {
        return sig(x) * (1 - sig(x));
    }
    
    private double dReLU(double x) {
        if (x < 0)
            return 0.01;
        else
            return 1;
    }
    
    public double getCost() {
        return cost;
    }
}

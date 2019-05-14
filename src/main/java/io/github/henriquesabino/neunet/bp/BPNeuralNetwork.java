package io.github.henriquesabino.neunet.bp;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.math.services.Function;
import io.github.henriquesabino.neunet.NeuralNetwork;

public class BPNeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = -3732215973838717612L;
    private Matrix[] costs, deltaWeights, deltaBiases;
    private double cost, learningRate;
    
    public BPNeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double learningRate) {
        super(inputsNum, hiddenLayersSize, outputsNum);
        this.learningRate = learningRate;
        
        costs = new Matrix[netSize];
        cost = 0;
        
        deltaWeights = new Matrix[netSize];
        deltaBiases = new Matrix[netSize];
        
        for (int i = 0; i < netSize; i++) {
            deltaWeights[i] = weights[i].copy();
            deltaWeights[i].applyForEach(x -> 0.0);
            
            deltaBiases[i] = biases[i].copy();
            deltaBiases[i].applyForEach(x -> 0.0);
        }
        
        for (int i = 0; i < neurons.length; i++) {
            costs[i] = neurons[i].copy();
            costs[i].applyForEach(x -> 0.0);
        }
    }
    
    public void train(double[] inputs, double[] expected) {
        
        for (Function function : functions) {
            if (function == null) {
                throw new IllegalStateException("You must define the activation functions before " +
                        "using the neural network!");
            }
        }
        
        double[] predicted = predict(inputs);
        
        calculateCosts(expected, predicted);
        adjustWeights(inputs);
        adjustBiases();
    }
    
    private void calculateCosts(double[] expected, double[] predicted) {
        
        cost = 0;
        
        for (int i = 0; i < expected.length; i++) {
            double value = -(expected[i] - predicted[i]);
            cost += Math.pow(value, 2) / 2;
            costs[netSize - 1].setValue(i, 0, value);
        }
        
        //Looping backwards
        //We have to iterate by the columns first because they determine the neuron
        //of the previous layer that the weight is applied to
        for (int l = netSize - 2; l >= 0; l--) {
            
            Matrix derivedMat = functionApplier.getFunction(functions[l]).derivative(sums[l + 1]);
            
            for (int n = 0; n < weights[l + 1].getColumns(); n++) {
                
                double value = 0;
                
                for (int w = 0; w < weights[l + 1].getRows(); w++) {
                    value += weights[l + 1].getValue(w, n) * derivedMat.getValue(w, 0)
                            * costs[l + 1].getValue(w, 0);
                }
                
                costs[l].setValue(n, 0, value);
            }
        }
    }
    
    private void adjustWeights(double[] inputs) {
        
        for (int l = 0; l < deltaWeights.length; l++) {
            
            Matrix derivedMat = functionApplier.getFunction(functions[l]).derivative(sums[l]);
            
            for (int i = 0; i < deltaWeights[l].getRows(); i++) {
                for (int j = 0; j < weights[l].getColumns(); j++) {
                    
                    //If it is the first layer
                    if (l == 0) {
                        double value = inputs[j] * derivedMat.getValue(i, 0) * costs[l].getValue(i, 0);
                        value *= -learningRate;
                        value += deltaWeights[l].getValue(i, j);
                        deltaWeights[l].setValue(i, j, value);
                    } else {
                        double value = neurons[l - 1].getValue(j, 0) * derivedMat.getValue(i, 0) * costs[l].getValue(i, 0);
                        value *= -learningRate;
                        value += deltaWeights[l].getValue(i, j);
                        deltaWeights[l].setValue(i, j, value);
                    }
                }
            }
        }
    }
    
    private void adjustBiases() {
        
        for (int l = 0; l < deltaBiases.length; l++) {
            
            Matrix derivedMat = functionApplier.getFunction(functions[l]).derivative(sums[l]);
            
            for (int n = 0; n < deltaBiases[l].getRows(); n++) {
                double value = derivedMat.getValue(n, 0) * costs[l].getValue(n, 0);
                value *= -learningRate;
                value += deltaBiases[l].getValue(n, 0);
                deltaBiases[l].setValue(n, 0, value);
            }
        }
    }
    
    public void apply() {
        
        for (int i = 0; i < netSize; i++) {
            weights[i].add(deltaWeights[i]);
            biases[i].add(deltaBiases[i]);
            
            deltaWeights[i].applyForEach(x -> 0.0);
            deltaBiases[i].applyForEach(x -> 0.0);
        }
    }
    
    public double getCost() {
        return cost;
    }
}

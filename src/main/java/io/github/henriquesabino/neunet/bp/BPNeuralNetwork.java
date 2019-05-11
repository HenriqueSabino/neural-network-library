package io.github.henriquesabino.neunet.bp;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.neunet.NeuralNetwork;

public class BPNeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = -3732215973838717612L;
    private Matrix[] costs;
    private double cost, learningRate;
    
    public BPNeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double learningRate) {
        super(inputsNum, hiddenLayersSize, outputsNum);
        this.learningRate = learningRate;
        
        costs = new Matrix[netSize];
        cost = 0;
        
        for (int i = 0; i < neurons.length; i++) {
            costs[i] = neurons[i].copy();
            costs[i].applyForEach(x -> 0.0);
        }
    }
    
    public void train(double[] inputs, double[] expected) {
        
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
            for (int n = 0; n < weights[l + 1].getColumns(); n++) {
                
                double value = 0;
                
                for (int w = 0; w < weights[l + 1].getRows(); w++) {
                    value += weights[l + 1].getValue(w, n) * dReLU(sums[l + 1].getValue(w, 0))
                            * costs[l + 1].getValue(w, 0);
                }
                
                costs[l].setValue(n, 0, value);
            }
        }
    }
    
    private void adjustWeights(double[] inputs) {
        
        for (int l = 0; l < weights.length; l++) {
            for (int i = 0; i < weights[l].getRows(); i++) {
                for (int j = 0; j < weights[l].getColumns(); j++) {
                    
                    //If it is the first layer
                    if (l == 0) {
                        double value = inputs[j] * dReLU(sums[l].getValue(i, 0)) * costs[l].getValue(i, 0);
                        value *= -learningRate;
                        value += weights[l].getValue(i, j);
                        weights[l].setValue(i, j, value);
                    } else {
                        if (l != netSize - 1) {
                            double value = neurons[l - 1].getValue(j, 0) * dReLU(sums[l].getValue(i, 0)) * costs[l].getValue(i, 0);
                            value *= -learningRate;
                            value += weights[l].getValue(i, j);
                            weights[l].setValue(i, j, value);
                        } else {
                            double value = neurons[l - 1].getValue(j, 0) * dSig(sums[l].getValue(i, 0)) * costs[l].getValue(i, 0);
                            value *= -learningRate;
                            value += weights[l].getValue(i, j);
                            weights[l].setValue(i, j, value);
                        }
                    }
                }
            }
        }
    }
    
    private void adjustBiases() {
        
        for (int l = 0; l < biases.length; l++) {
            for (int n = 0; n < biases[l].getRows(); n++) {
                
                if (l != netSize - 1) {
                    
                    double value = dReLU(sums[l].getValue(n, 0)) * costs[l].getValue(n, 0);
                    value *= -learningRate;
                    value += biases[l].getValue(n, 0);
                    biases[l].setValue(n, 0, value);
                    
                } else {
                    double value = dSig(sums[l].getValue(n, 0)) * costs[l].getValue(n, 0);
                    value *= -learningRate;
                    value += biases[l].getValue(n, 0);
                    biases[l].setValue(n, 0, value);
                }
            }
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

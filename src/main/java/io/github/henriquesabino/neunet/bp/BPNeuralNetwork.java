package io.github.henriquesabino.neunet.bp;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.neunet.NeuralNetwork;

public class BPNeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = -3732215973838717612L;
    private double cost, learningRate;
    private Matrix[] deltaWeights, deltaBiases, costs;
    private int iterations = 0;
    
    
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
    
    public void train(double[] inputs, double[] outputs) {
        
        forwardPropag(inputs);
        cost = 0;
        double[] predicted = neurons[netSize - 1].toArray();
        
        //calculating the neural network cost
        for (int i = 0; i < predicted.length; i++) {
            double error = -(outputs[i] - predicted[i]);
            cost += Math.pow(-error, 2) / 2;
            costs[netSize - 1].setValue(i, 0, error);
        }
        
        backpropag(inputs);
    }
    
    private void backpropag(double[] inputs) {
        iterations++;
        calculateCosts();
        adjustWeights(inputs);
        adjustBiases();
    }
    
    private void adjustWeights(double[] inputs) {
        for (int l = 0; l < netSize; l++) {
            if (l == 0) {
                // for every neuron n in the first layer
                for (int n = 0; n < neurons[l].getRows(); n++) {
                    // for every weight w of the first layer
                    for (int w = 0; w < inputs.length; w++) {
                        double weightChange = -inputs[w] * dReLU(sums[l].getValue(n, 0)) * costs[l].getValue(n, 0);
                        weightChange *= learningRate;
                        weightChange += deltaWeights[l].getValue(n, w);
                        deltaWeights[l].setValue(n, w, weightChange);
                    }
                }
            } else {
                for (int n = 0; n < neurons[l].getRows(); n++) {
                    // for every weight w of the first layer
                    for (int w = 0; w < neurons[l - 1].getRows(); w++) {
                        double weightChange;
                        if (l == netSize - 1)
                            weightChange = neurons[l - 1].getValue(w, 0) * dSig(sums[l].getValue(n, 0))
                                    * costs[l].getValue(n, 0);
                        else
                            
                            weightChange = neurons[l - 1].getValue(w, 0) * dSig(sums[l].getValue(n, 0))
                                    * costs[l].getValue(n, 0);
                        
                        weightChange *= learningRate;
                        weightChange += deltaWeights[l].getValue(n, w);
                        deltaWeights[l].setValue(n, w, weightChange);
                    }
                }
            }
        }
    }
    
    private void adjustBiases() {
        
        for (int l = 0; l < netSize; l++) {
            for (int i = 0; i < biases[l].getRows(); i++) {
                
                if (l != netSize - 1) {
                    double biasChange = dReLU(sums[l].getValue(i, 0)) * costs[l].getValue(i, 0);
                    biasChange *= learningRate;
                    biasChange += deltaBiases[l].getValue(i, 0);
                    deltaBiases[l].setValue(i, 0, biasChange);
                } else {
                    double biasChange = dSig(sums[l].getValue(i, 0)) * costs[l].getValue(i, 0);
                    biasChange *= learningRate;
                    biasChange += deltaBiases[l].getValue(i, 0);
                    deltaBiases[l].setValue(i, 0, biasChange);
                }
            }
        }
    }
    
    private void calculateCosts() {
        for (int l = netSize - 2; l >= 0; l--) {
            for (int i = 0; i < neurons[l].getRows(); i++) {
                for (int j = 0; j < neurons[l + 1].getRows(); j++) {
                    
                    double value = weights[l + 1].getValue(j, i) * dReLU(sums[l + 1].getValue(j, 0))
                            * costs[l + 1].getValue(j, 0);
                    
                    value += costs[l].getValue(i, 0);
                    costs[l].setValue(i, 0, value);
                    
                }
            }
        }
    }
    
    public void applyChanges() {
        
        for (int i = 0; i < weights.length; i++) {
            deltaWeights[i].scalarDiv(iterations);
            weights[i].add(deltaWeights[i]);
        }
        
        for (int i = 0; i < biases.length; i++) {
            deltaBiases[i].scalarDiv(iterations);
            biases[i].add(deltaBiases[i]);
        }
        
        iterations = 0;
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
    
    public void setCost(double cost) {
        this.cost = cost;
    }
}

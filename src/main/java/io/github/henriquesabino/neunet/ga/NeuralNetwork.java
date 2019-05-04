package io.github.henriquesabino.neunet.ga;

import io.github.henriquesabino.math.Matrix;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class NeuralNetwork implements Serializable {
    
    private static final long serialVersionUID = 3627504448716871173L;
    private int inputsNum, hiddenLayersNum, outputsNum, netSize;
    private double mutationRate;
    private int[] hiddenLayersSize;
    
    //The results
    private Matrix[] neurons;
    private Matrix[] weights;
    private Matrix[] biases;
    //Used to store the results of the calculations before the activation function
    private Matrix[] sums;
    
    public NeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double mutationRate) {
        
        if (inputsNum == 0) {
            throw new InvalidParameterException("There must be at least one input to the neural network");
        }
        if (hiddenLayersSize.length == 0) {
            throw new InvalidParameterException("There must be at least one hidden layer");
        }
        if (outputsNum == 0) {
            throw new InvalidParameterException("The neural network must output at least one value");
        }
        if (mutationRate <= 0 || mutationRate > 1) {
            throw new InvalidParameterException("The mutation rate must be a value between the range " +
                    "(0, 1]");
        }
        
        this.inputsNum = inputsNum;
        this.hiddenLayersNum = hiddenLayersSize.length;
        this.hiddenLayersSize = hiddenLayersSize.clone();
        this.outputsNum = outputsNum;
        this.mutationRate = mutationRate;
        
        netSize = hiddenLayersNum + 1;
        
        neurons = new Matrix[netSize];
        weights = new Matrix[netSize];
        biases = new Matrix[netSize];
        sums = new Matrix[netSize];
        
        initializeMatrices();
    }
    
    private void initializeMatrices() {
        //netSize is the size of the neural network, the values from 0 to netSize - 2 are
        //the hidden layers of the neunet and the last one is the output layer
        for (int i = 0; i < hiddenLayersNum; i++) {
            if (i == 0) {
                weights[i] = new Matrix(hiddenLayersSize[i], inputsNum);
            } else {
                weights[i] = new Matrix(hiddenLayersSize[i], hiddenLayersSize[i - 1]);
            }
            
            biases[i] = new Matrix(hiddenLayersSize[i], 1);
            neurons[i] = new Matrix(hiddenLayersSize[i], 1);
            sums[i] = new Matrix(hiddenLayersSize[i], 1);
            
            weights[i].randomize();
            biases[i].randomize();
        }
        
        weights[netSize - 1] = new Matrix(outputsNum, hiddenLayersSize[hiddenLayersNum - 1]);
        biases[netSize - 1] = new Matrix(outputsNum, 1);
        neurons[netSize - 1] = new Matrix(outputsNum, 1);
        sums[netSize - 1] = new Matrix(outputsNum, 1);
        
        weights[netSize - 1].randomize();
        biases[netSize - 1].randomize();
    }
    
    //These getters are only for testing
    public Matrix[] getNeurons() {
        return neurons;
    }
    
    public Matrix[] getWeights() {
        return weights;
    }
    
    public Matrix[] getBiases() {
        return biases;
    }
    
    public Matrix[] getSums() {
        return sums;
    }
}

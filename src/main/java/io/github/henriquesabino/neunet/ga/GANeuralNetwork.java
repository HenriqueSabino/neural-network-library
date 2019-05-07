package io.github.henriquesabino.neunet.ga;

import io.github.henriquesabino.math.Matrix;
import io.github.henriquesabino.neunet.NeuralNetwork;

import java.util.List;
import java.util.Random;

public class GANeuralNetwork extends NeuralNetwork {
    
    private static final long serialVersionUID = 8585436273457165001L;
    private double mutationRate;
    
    
    public GANeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum, double mutationRate) {
        super(inputsNum, hiddenLayersSize, outputsNum);
        this.mutationRate = mutationRate;
    }
    
    public GANeuralNetwork(List<GANeuralNetwork> parents) {
        super(parents.get(0).inputsNum, parents.get(0).hiddenLayersSize, parents.get(0).outputsNum);
        this.mutationRate = parents.get(0).mutationRate;
        
        for (int i = 0; i < netSize; i++) {
            
            double rnd = Math.random();
            //randomly choosing a parent
            //because the range of rnd is [0,1) we need to multiply by the list size
            //and floor the result to get a fair distribution
            int index = (int) Math.floor(rnd * parents.size());
            
            weights[i] = parents.get(index).weights[i];
            biases[i] = parents.get(index).biases[i];
        }
        
        mutate();
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
    
    public void mutate() {
        for (Matrix weight : weights) {
            Random rnd = new Random();
            //Tweaking the values with a number between -1 and 1, with gaussian distribution
            weight.applyForEach(x -> (Math.random() < mutationRate) ? x + rnd.nextGaussian() * 2 - 1 : x);
        }
        
        for (Matrix bias : biases) {
            Random rnd = new Random();
            //Tweaking the values with a number between -1 and 1, with gaussian distribution
            bias.applyForEach(x -> (Math.random() < mutationRate) ? x + rnd.nextGaussian() * 2 - 1 : x);
        }
    }
    
    //Sometimes it's interesting to change the mutation rate over time
    public double getMutationRate() {
        return mutationRate;
    }
    
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}

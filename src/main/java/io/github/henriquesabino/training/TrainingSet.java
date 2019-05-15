package io.github.henriquesabino.training;

import java.io.Serializable;
import java.util.Arrays;

public class TrainingSet implements Serializable {
    
    private static final long serialVersionUID = -6543080970121286935L;
    private double[][] inputs;
    private double[][] outputs;
    private int size;
    
    public TrainingSet() {
    }
    
    public TrainingSet(double[][] inputs, double[][] outputs) {
        if (inputs.length != outputs.length) {
            throw new IllegalArgumentException("The number of outputs must be " +
                    "equal the number of outputs");
        }
        
        this.size = inputs.length;
        
        this.inputs = new double[inputs.length][inputs[0].length];
        this.outputs = new double[outputs.length][outputs[0].length];
        
        //cloning the arrays
        for (int i = 0; i < size; i++) {
            this.inputs[i] = inputs[i].clone();
            this.outputs[i] = outputs[i].clone();
        }
    }
    
    public TrainingSet[] split() {
        
        TrainingSet trainingSet = new TrainingSet();
        TrainingSet testingSet = new TrainingSet();
        
        shuffleSet();
        
        for (int i = 0; i < size; i++) {
            
            trainingSet.inputs[i] = Arrays.copyOfRange(inputs[i], 0, size / 2);
            trainingSet.outputs[i] = Arrays.copyOfRange(inputs[i], 0, size / 2);
            
            testingSet.inputs[i] = Arrays.copyOfRange(inputs[i], size / 2, inputs[i].length);
            testingSet.outputs[i] = Arrays.copyOfRange(inputs[i], size / 2, inputs[i].length);
        }
        
        return new TrainingSet[]{trainingSet, testingSet};
    }
    
    public void shuffleSet() {
        //Shuffles the training set keeping the inputs and outputs in order
        for (int i = 0; i < size; i++) {
            
            int index = (int) Math.floor(Math.random() * size);
            
            double[] inputTemp = inputs[i];
            double[] outputTemp = outputs[i];
            
            inputs[i] = inputs[index];
            outputs[i] = outputs[index];
            
            inputs[index] = inputTemp;
            outputs[index] = outputTemp;
        }
    }
    
    public TrainingSet getBatch(int batchSize) {
        
        double[][] batchInputs = new double[batchSize][inputs[0].length];
        double[][] batchOutputs = new double[batchSize][outputs[0].length];
        
        TrainingSet batch = new TrainingSet(batchInputs, batchOutputs);
        
        for (int i = 0; i < batchSize; i++) {
            
            int index = (int) Math.floor(Math.random() * size);
            
            batch.inputs[i] = inputs[index];
            batch.outputs[i] = outputs[index];
        }
        
        return batch;
    }
    
    public double[] getInputs(int index) {
        return inputs[index];
    }
    
    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }
    
    public double[] getOutputs(int index) {
        return outputs[index];
    }
    
    public void setOutputs(double[][] outputs) {
        this.outputs = outputs;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
}

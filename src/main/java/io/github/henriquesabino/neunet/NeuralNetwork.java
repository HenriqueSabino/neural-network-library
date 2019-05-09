package io.github.henriquesabino.neunet;

import io.github.henriquesabino.math.Matrix;

import java.io.*;
import java.security.InvalidParameterException;

public class NeuralNetwork implements Serializable {
    
    private static final long serialVersionUID = 3627504448716871173L;
    protected int inputsNum, hiddenLayersNum, outputsNum;
    protected int[] hiddenLayersSize;
    protected Matrix[] weights;
    protected Matrix[] biases;
    protected int netSize;
    //The results
    protected Matrix[] neurons;
    //Used to store the results of the calculations before the activation function
    protected Matrix[] sums;
    
    public NeuralNetwork(int inputsNum, int[] hiddenLayersSize, int outputsNum) {
        
        if (inputsNum == 0) {
            throw new InvalidParameterException("There must be at least one input to the neural network");
        }
        if (hiddenLayersSize.length == 0) {
            throw new InvalidParameterException("There must be at least one hidden layer");
        }
        if (outputsNum == 0) {
            throw new InvalidParameterException("The neural network must output at least one value");
        }
        
        this.inputsNum = inputsNum;
        this.hiddenLayersNum = hiddenLayersSize.length;
        this.hiddenLayersSize = hiddenLayersSize.clone();
        this.outputsNum = outputsNum;
        
        netSize = hiddenLayersNum + 1;
        
        neurons = new Matrix[netSize];
        weights = new Matrix[netSize];
        biases = new Matrix[netSize];
        sums = new Matrix[netSize];
        
        initializeMatrices();
    }
    
    public static NeuralNetwork loadNeuNet(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        NeuralNetwork neuralNetwork = (NeuralNetwork) ois.readObject();
        
        ois.close();
        fis.close();
        
        return neuralNetwork;
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
    
    protected void forwardPropag(double[] inputs) {
        Matrix inputsMat = Matrix.fromArrayToColumnMatrix(inputs);
        
        //the first hidden layer
        //sums = weights * inputs + biases
        sums[0] = Matrix.add(Matrix.mult(weights[0], inputsMat), biases[0]);
        neurons[0] = sums[0].copy();
        neurons[0].applyForEach(this::leakyReLU);
        
        for (int i = 1; i < netSize; i++) {
            sums[i] = Matrix.add(Matrix.mult(weights[i], neurons[i - 1]), biases[i]);
            neurons[i] = sums[i].copy();
            
            //the hidden layers use leaky ReLu, but the output layer uses sigmoid to constrain the
            //output to a value between 0 and 1
            if (i < netSize - 1) {
                neurons[i].applyForEach(this::leakyReLU);
            } else {
                neurons[i].applyForEach(this::sig);
            }
        }
    }
    
    public double[] predict(double[] inputs) {
        if (inputs == null || inputs.length != inputsNum) {
            throw new InvalidParameterException("The number of inputs must match the neural network shape");
        }
        forwardPropag(inputs);
        return neurons[netSize - 1].toArray();
    }
    
    //Saves the neural network to a file to load it afterwards
    public void saveNeuNet(String path) throws IOException {
        File file = new File(path);
        
        if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();
            
            if (!result) {
                throw new IOException("Could not create the directories " + file);
            }
        }
        if (!file.exists()) {
            boolean result = file.createNewFile();
            
            if (!result) {
                throw new IOException("Could not create the file");
            }
        }
        
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        oos.writeObject(this);
        
        fos.close();
        oos.close();
    }
    
    //Saves the neural network to a file to debug its values
    public void debugNeuNet(String path) throws IOException {
        
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        
        bw.write("HIDDEN LAYERS:");
        bw.newLine();
        
        for (int i = 0; i < netSize - 1; i++) {
            bw.write(String.format("Hidden layer %d weights", i));
            bw.newLine();
            bw.write(weights[i].toString());
            bw.newLine();
            
            bw.write(String.format("Hidden layer %d biases", i));
            bw.newLine();
            bw.write(biases[i].toString());
            bw.newLine();
            
            bw.write(String.format("Hidden layer %d activations", i));
            bw.newLine();
            bw.write(neurons[i].toString());
            bw.newLine();
        }
        
        bw.write("OUTPUT LAYER:");
        bw.newLine();
        bw.write("Output layer weights");
        bw.newLine();
        bw.write(weights[netSize - 1].toString());
        bw.newLine();
        
        bw.write("Output layer biases");
        bw.newLine();
        bw.write(biases[netSize - 1].toString());
        bw.newLine();
        
        bw.write("Output layer activations");
        bw.newLine();
        bw.write(neurons[netSize - 1].toString());
        bw.newLine();
        
        bw.close();
        fileWriter.close();
    }
    
    protected double leakyReLU(double x) {
        return Math.max(0.01 * x, x);
    }
    
    protected double sig(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}

package application;

import io.github.henriquesabino.neunet.bp.BPNeuralNetwork;
import io.github.henriquesabino.training.TrainingSet;
import processing.core.PApplet;

public class Program extends PApplet {
    
    private BPNeuralNetwork neuralNetwork = new BPNeuralNetwork(2, new int[]{4}, 1, 0.01);
    private TrainingSet trainingSet;
    
    public static void main(String[] args) {
        PApplet.main("application.Program");
    }
    
    @Override
    public void settings() {
        size(600, 600);
        neuralNetwork.setActivationFunctionsForHiddenLayer("leaky_relu");
        
        neuralNetwork.setActivationFunctionsForOutputLayer("leaky_relu");
        
        double[][] inputs = new double[4][2];
        double[][] outputs = new double[4][1];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = 2 * i + j;
                
                inputs[index] = new double[]{i, j};
                outputs[index] = new double[]{Math.abs(i - j)};
            }
        }
        
        trainingSet = new TrainingSet(inputs, outputs);
    }
    
    @Override
    public void draw() {
        loadPixels();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = x + y * width;
                
                double xInp = x / (double) width;
                double yInp = y / (double) height;
                
                pixels[index] = color((float) neuralNetwork.predict(new double[]{xInp, yInp})[0] * 255);
            }
        }
        
        updatePixels();
        neuralNetwork.train(trainingSet, 2, 500);
    }
    
    @Override
    public void mousePressed() {
        println("Cost: " + neuralNetwork.getCost());
        printArray(neuralNetwork.predict(new double[]{0, 0}));
        printArray(neuralNetwork.predict(new double[]{1, 0}));
        printArray(neuralNetwork.predict(new double[]{1, 1}));
        printArray(neuralNetwork.predict(new double[]{0, 1}));
    }
}

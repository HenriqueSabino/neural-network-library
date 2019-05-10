package application;

import io.github.henriquesabino.neunet.bp.BPNeuralNetwork;
import processing.core.PApplet;

public class Program extends PApplet {
    
    private BPNeuralNetwork neuralNetwork = new BPNeuralNetwork(2, new int[]{8, 4}, 1, 0.005);
    
    public static void main(String[] args) {
        PApplet.main("application.Program");
    }
    
    @Override
    public void settings() {
        size(400, 400);
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
        trainNeuNet();
    }
    
    @Override
    public void mousePressed() {
        println("Cost: " + neuralNetwork.getCost());
        printArray(neuralNetwork.predict(new double[]{0, 0}));
        printArray(neuralNetwork.predict(new double[]{1, 0}));
        printArray(neuralNetwork.predict(new double[]{1, 1}));
        printArray(neuralNetwork.predict(new double[]{0, 1}));
    }
    
    private void trainNeuNet() {
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    double expected = Math.abs(j - k);
                    neuralNetwork.train(new double[]{j, k}, new double[]{expected});
                }
            }
        }
    }
}

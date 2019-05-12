package io.github.henriquesabino.math.services;

import io.github.henriquesabino.math.function.*;

public class FunctionApplier {
    
    private static LeakyReLU leakyReLU = new LeakyReLU();
    private static ReLU reLU = new ReLU();
    private static Sigmoid sigmoid = new Sigmoid();
    private static Softmax softmax = new Softmax();
    
    public FunctionApplier() {
    }
    
    public ActivationFunction getFunction(Function function) {
        //the switch function does not require the break keyword here because
        //the return already breaks the statement
        switch (function) {
            case LEAKY_RELU:
                return leakyReLU;
            case RELU:
                return reLU;
            case SIGMOID:
                return sigmoid;
            case SOFTMAX:
                return softmax;
            default:
                return null;
        }
    }
}

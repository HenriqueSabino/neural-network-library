package io.github.henriquesabino.math.services;

import io.github.henriquesabino.math.function.*;

public class FunctionApplier {
    
    private LeakyReLU leakyReLU;
    private ReLU reLU;
    private Sigmoid sigmoid;
    private Softmax softmax;
    
    public FunctionApplier() {
        leakyReLU = new LeakyReLU();
        reLU = new ReLU();
        sigmoid = new Sigmoid();
        softmax = new Softmax();
    }
    
    public ActivationFunction getFunction(Function function) {
        //the switch function does not require the break keyword here because
        //the return already breaks the statement
        switch (function) {
            case LEAKY_RElU:
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

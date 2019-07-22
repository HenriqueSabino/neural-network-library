package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

import java.io.Serializable;

public class Sigmoid implements ActivationFunction, Serializable {
    
    private static final long serialVersionUID = 5948902137858687860L;
    
    @Override
    public Matrix apply(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::sigmoid);
        
        return result;
    }
    
    @Override
    public Matrix derivative(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::dSigmoid);
        
        return result;
    }
    
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    
    private double dSigmoid(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }
}

package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

import java.io.Serializable;

public class LeakyReLU implements ActivationFunction, Serializable {
    
    private static final long serialVersionUID = -3917586455088973459L;
    
    @Override
    public Matrix apply(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::leakyReLU);
        
        return result;
    }
    
    @Override
    public Matrix derivative(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::dLeakyReLU);
        
        return result;
    }
    
    private double leakyReLU(double x) {
        return Math.max(0.01 * x, x);
    }
    
    private double dLeakyReLU(double x) {
        return (x < 0) ? 0.01 : 1;
    }
}

package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

public class ReLU implements ActivationFunction {
    
    @Override
    public Matrix apply(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::reLU);
        
        return result;
    }
    
    @Override
    public Matrix derivative(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result.applyForEach(this::dReLU);
        
        return result;
    }
    
    private double reLU(double x) {
        return Math.max(0, x);
    }
    
    private double dReLU(double x) {
        return (x <= 0) ? 0 : 1;
    }
}

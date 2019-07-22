package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

import java.io.Serializable;

public class ReLU implements ActivationFunction, Serializable {
    
    private static final long serialVersionUID = -7394677888186279329L;
    
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
        return (x < 0) ? 0 : 1;
    }
}

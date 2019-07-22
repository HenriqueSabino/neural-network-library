package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

import java.io.Serializable;
import java.util.Arrays;

public class Softmax implements ActivationFunction, Serializable {
    
    private static final long serialVersionUID = -391678422395190017L;
    
    @Override
    public Matrix apply(Matrix matrix) {
        Matrix result = matrix.copy();
        
        double max = Double.MIN_VALUE;
        
        for (double x : matrix.toArray()) {
            max = (x > max) ? x : max;
        }
        
        final double maxLambda = max;
        
        result.applyForEach(x -> x - maxLambda);
        
        result.applyForEach(Math::exp);
        
        double sum = Arrays.stream(result.toArray()).reduce(0, Double::sum);
        
        result.scalarDiv(sum);
        
        return result;
    }
    
    @Override
    public Matrix derivative(Matrix matrix) {
        Matrix result = matrix.copy();
        
        result = apply(result);
        result.applyForEach(x -> x * (1 - x));
        
        return result;
    }
}

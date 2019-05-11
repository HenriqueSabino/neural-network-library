package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

import java.util.Arrays;

public class Softmax implements ActivationFunction {
    
    @Override
    public Matrix apply(Matrix matrix) {
        Matrix result = matrix.copy();
        
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

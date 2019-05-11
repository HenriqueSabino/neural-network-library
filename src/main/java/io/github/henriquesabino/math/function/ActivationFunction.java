package io.github.henriquesabino.math.function;

import io.github.henriquesabino.math.Matrix;

public interface ActivationFunction {
    
    Matrix apply(Matrix matrix);
    
    Matrix derivative(Matrix matrix);
}

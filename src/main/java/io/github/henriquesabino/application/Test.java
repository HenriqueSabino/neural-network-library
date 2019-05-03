package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

public class Test {
    
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(10, 10);
        
        matrix1.randomize();
        
        //easy implementation for a mutation algorithm
        matrix1.applyForEach(p -> (Math.random() < 0.5) ? p + Math.random() * 2 - 1 : p);
        
        printMatrix(matrix1);
    }
    
    private static void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            System.out.print("| ");
            for (int j = 0; j < matrix.getColumns(); j++) {
                System.out.printf("%.2f", matrix.getValue(i, j));
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}

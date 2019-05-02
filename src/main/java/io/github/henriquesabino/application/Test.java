package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

public class Test {
    
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(2, 2);
        
        matrix1.setValue(0, 0, 4);
        matrix1.setValue(0, 1, 3);
        matrix1.setValue(1, 0, 2);
        matrix1.setValue(1, 1, 1);
        
        
        Matrix matrix2 = new Matrix(2, 2);
        
        matrix2.setValue(0, 0, -0.5);
        matrix2.setValue(0, 1, 1.5);
        matrix2.setValue(1, 0, 1);
        matrix2.setValue(1, 1, -2);
        
        printMatrix(Matrix.mult(matrix1, matrix2));
    }
    
    private static void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            System.out.print("| ");
            for (int j = 0; j < matrix.getColumns(); j++) {
                System.out.print(matrix.getValue(i, j) + " | ");
            }
            System.out.println();
        }
    }
}

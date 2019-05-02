package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

public class Test {
    
    public static void main(String[] args) {
        Matrix matrix = new Matrix(3, 3);
        
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                matrix.setValue(i, j, i * matrix.getColumns() + j);
            }
        }
        
        printMatrix(matrix);
        System.out.println();
        
        printMatrix(Matrix.scalarMult(matrix, 0.5));
        System.out.println();
        
        matrix.scalarDiv(2);
        printMatrix(matrix);
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

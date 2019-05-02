package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

public class Test {
    
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(2, 3);
        Matrix matrix2 = new Matrix(2, 3);
        
        for (int i = 0; i < matrix1.getRows(); i++) {
            for (int j = 0; j < matrix1.getColumns(); j++) {
                matrix1.setValue(i, j, i + j);
            }
        }
        for (int i = 0; i < matrix2.getRows(); i++) {
            for (int j = 0; j < matrix2.getColumns(); j++) {
                matrix2.setValue(i, j, i - j);
            }
        }
        
        printMatrix(matrix1);
        System.out.println();
        
        printMatrix(matrix2);
        System.out.println();
        
        printMatrix(Matrix.add(matrix1, matrix2));
        System.out.println();
        
        printMatrix(Matrix.sub(matrix1, matrix2));
        System.out.println();
        
        matrix1.add(matrix2);
        printMatrix(matrix1);
        System.out.println();
        
        matrix2.sub(matrix1);
        printMatrix(matrix2);
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

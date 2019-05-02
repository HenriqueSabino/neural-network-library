package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

public class Test {
    
    public static void main(String[] args) {
        Matrix matrix = new Matrix(2, 3);
        
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                matrix.setValue(i, j, i + j);
            }
        }
        
        printMatrix(matrix);
        System.out.println("Testing copy function");
        printMatrix(matrix.copy());
        
        System.out.println("Testing hashCode function");
        System.out.println(matrix.hashCode() == matrix.copy().hashCode());
        
        System.out.println("Testing equals function");
        System.out.println(matrix.equals(matrix.copy()));
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

package io.github.henriquesabino.application;

import io.github.henriquesabino.math.Matrix;

import java.util.Arrays;

public class Test {
    
    public static void main(String[] args) {
        
        double[] arr = new double[]{1, 2, 3, 4, 5, 6};
        
        Matrix column = Matrix.fromArrayToColumnMatrix(arr);
        Matrix row = Matrix.fromArrayToRowMatrix(arr);
        
        printMatrix(column);
        System.out.println();
        printMatrix(row);
        
        System.out.println();
        
        System.out.println(Arrays.equals(column.toArray(), arr));
        System.out.println(Arrays.equals(row.toArray(), arr));
        
        System.out.println();
        
        printMatrix(Matrix.scalarDiv(column, 2));
        System.out.println();
        printMatrix(Matrix.scalarMult(row, 2));
        
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

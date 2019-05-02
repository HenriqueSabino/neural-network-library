package io.github.henriquesabino.math;

import io.github.henriquesabino.math.exception.IncompatibleMatricesException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Matrix implements Serializable {
    
    private static final long serialVersionUID = 2186517158094722057L;
    public double[][] matrix;
    private int rows;
    private int columns;
    
    public Matrix(int rows, int columns) {
        
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Matrices must have at least one " +
                    "row and one column");
        }
        
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }
    
    //region Class methods
    
    public static Matrix add(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IncompatibleMatricesException("Matrices must be of the same order " +
                    "to perform this operation");
        }
        
        Matrix result = new Matrix(a.getRows(), a.getColumns());
        
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                double value = a.getValue(i, j) + b.getValue(i, j);
                result.setValue(i, j, value);
            }
        }
        
        return result;
    }
    
    public static Matrix sub(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IncompatibleMatricesException("Matrices must be of the same order " +
                    "to perform this operation");
        }
        
        Matrix result = new Matrix(a.getRows(), a.getColumns());
        
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                double value = a.getValue(i, j) - b.getValue(i, j);
                result.setValue(i, j, value);
            }
        }
        
        return result;
    }
    
    //endregion
    
    //region Instance methods
    
    public void add(Matrix other) {
        if (getRows() != other.getRows() || getColumns() != other.getColumns()) {
            throw new IncompatibleMatricesException("Matrices must be of the same order " +
                    "to perform this operation");
        }
        
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                double value = getValue(i, j) + other.getValue(i, j);
                setValue(i, j, value);
            }
        }
    }
    
    public void sub(Matrix other) {
        if (getRows() != other.getRows() || getColumns() != other.getColumns()) {
            throw new IncompatibleMatricesException("Matrices must be of the same order " +
                    "to perform this operation");
        }
        
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                double value = getValue(i, j) - other.getValue(i, j);
                setValue(i, j, value);
            }
        }
    }
    
    public Matrix copy() {
        Matrix copy = new Matrix(rows, columns);
        
        for (int i = 0; i < copy.getRows(); i++) {
            for (int j = 0; j < copy.getColumns(); j++) {
                copy.setValue(i, j, getValue(i, j));
            }
        }
        
        return copy;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public double getValue(int row, int column) {
        return matrix[row][column];
    }
    
    public void setValue(int row, int columns, double value) {
        matrix[row][columns] = value;
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return rows == matrix1.rows &&
                columns == matrix1.columns &&
                Arrays.deepEquals(matrix, matrix1.matrix);
    }
    
    //endregion
}

/**
 * @author Eva Ackley
 * This class creates a matrix Datatype with different matrix utilities that holds doubles
 */
public class Matrix {
    double[][] matrix;
    int rows;
    int columns;

    /**
     * Constructor method for an empty matrix given dimensions
     * @param a The number of rows of the matrix
     * @param b The number of columns of the matrix
     */
    public Matrix(int a, int b) {
        matrix = new double[a][b];
        this.rows = a;
        this.columns = b;
    }

    /**
     * Constructor of a matrix given a 2-dimensional array of doubles
     * @param matrix The 2-dimensional array which is a matrix
     */
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    /**
     * Constructs a matrix given a vector, which is an n x 1 matrix
     * @param v The vector which will be represented as a matrix
     */
    public Matrix(Vector v) {
        this.matrix = new double[v.size][1];
        for (int i = 0; i < v.size; i++) {
            addValue(i,0,v.getValue(i)); //Adds the values of the vector to a matrix
        }
        this.rows = v.size;
        this.columns = 1;
    }

    /**
     * Constructor method for an initialized matrix given a set of vectors
     * @param vectors The vectors which will become a matrix, they need to have the same dimensions
     * @throws IllegalArgumentException If the vectors do not have the same dimensions, they cannot form a matrix
     * so the program throws an exception
     */
    public Matrix(Vector[] vectors) throws IllegalArgumentException{
        int size = vectors[0].size;
        for (Vector vector : vectors) {
            if (vector.getSize() != size) {
                throw new IllegalArgumentException("Vectors must have the same number of rows!");
            }
        }
        this.matrix = new double[size][vectors.length]; //Creates a matrix given vector dimensions and numbers
        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[i].size; j++) {
                addValue(j,i,vectors[i].getValue(j));
            }
        }
        this.rows = vectors[0].size;
        this.columns = vectors.length;
    }

    /**
     * Constructor method for an identity matrix of a given size, it has 1s on the main diagonal
     * @param size The number of rows/columns of the square matrix
     */
    public Matrix (int size) {
        this.matrix = new double[size][size];
        this.rows = size;
        this.columns = size;
        for (int i = 0; i < size; i++) {
            addValue(i, i, 1);
        }
    }

    /**
     * An access method for the number of columns of the matrix
     * @return The number of columns of the matrix
     */
    public int getColumns() {
        return columns;
    }

    /**
     * An access method for the number of rows of the matrix
     * @return The number of rows of the matrix
     */
    public int getRows() {
        return rows;
    }

    /**
     * Adds a double to a specific location in the matrix
     * @param row The row where the double will be inserted
     * @param column The column where the double will be inserted
     * @param value The double to be inserted at row, column
     */
    public void addValue(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    /**
     * An access method for a specific location in the matrix
     * @param row The row of the desired location
     * @param column The column of the desired location
     * @return The double stored in row, column
     */
    public double getValue(int row, int column) {
        return this.matrix[row][column];
    }

    /**
     * Matrix multiplication on the right by the other matrix
     * @param other The other matrix which will be multiplied on the left by this matrix
     * @return A matrix product of these two matrices, order matters
     * @throws IllegalArgumentException If the columns of this matrix does not match the number of rows of
     * the other matrix, multiplication is not defined
     */
    public Matrix multiplication(Matrix other) throws IllegalArgumentException {
        if (columns != other.getRows()) { //The product of these matrices are not defined
            throw new IllegalArgumentException("Matrix dimensions must match!");
        }
        Matrix result = new Matrix(rows, other.getColumns()); //New empty matrix with the corresponding dimensions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                double sum = 0; //The ij-th value in the product matrix is the dot product of the i-th row
                                //of this matrix with the j-th column of the other matrix
                for (int k = 0; k < columns; k++) {
                    sum += matrix[i][k] * other.getValue(k,j); //Dot product calculations
                }
                result.addValue(i,j,sum);
            }
        }
        return result;
    }

    /**
     * Multiplies a vector by this matrix on the left
     * @param other The vector to be multiplied
     * @return A Vector product of this multiplication
     * @throws IllegalArgumentException If the number of columns of this matrix do not match the rows
     * of the vector, the product is not defined
     */
    public Vector multiplication(Vector other) throws IllegalArgumentException {
        if (columns != other.getSize()) { //The product is not defined
            throw new IllegalArgumentException("Dimensions must match!");
        }
        Matrix v = new Matrix(other); //Creates a matrix using this vector
        Matrix result = multiplication(v); //Uses matrix multiplication method
        Vector product = new Vector(result.rows); //Return the product to a vector
        for (int i = 0; i < result.rows; i++) {
            product.setValue(i,result.getValue(i,0));
        }
        return product;
    }

    /**
     * Sets the column of the matrix to a given vector
     * @param column The index referring to the column to be changed
     * @param entry The vector which will be set as a new column of the matrix
     */
    public void editColumn(int column, Vector entry) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][column] = entry.getValue(i);
        }
    }

    /**
     * A method to transpose a matrix, which is the matrix with the columns in the same order as the rows
     * of the original matrix
     * @return The Matrix which is the transpose of this matrix
     */
    public Matrix transpose() {
        Matrix transpose = new Matrix(this.columns, this.rows);
        for (int i = 0; i < this.rows; i++) {
            Vector row = new Vector(matrix[i]); //A new vector of the given row of the matrix
            transpose.editColumn(i, row); //Adds a column to the transpose matrix, the corresponding row of this matrix
        }
        return transpose;
    }

    /**
     * Checks if this matrix is an identity matrix
     * @return True if this matrix is a square matrix with only ones on the main diagonal, false if else
     */
    public boolean isIdentity() {
        if (rows != columns) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i == j) && matrix[i][j] != 1) { //The value on the main diagonal is not one
                    return false;
                }
                else if ((i != j) && matrix[i][j] != 0) {//A value not on the main diagonal is something other than zero
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A method to find the inverse of this matrix. The matrix must be square. The inverse of a matrix
     * is the matrix that when they are multiplied in either order, the product is the corresponding
     * identity matrix
     * @return The inverse matrix of this matrix
     * @throws IllegalArgumentException If the matrix is square, or is not invertible, the program will
     * throw an exception
     */
    public Matrix inverse() throws IllegalArgumentException {
        if (rows != columns) {
            throw new IllegalArgumentException("Must be square!");
        }
        Matrix original = new Matrix(rows, columns); //Makes a copy of the original matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                original.addValue(i,j,this.getValue(i,j));
            }
        }
        Matrix inverse = new Matrix(rows); //Create an identity matrix of the corresponding size, this
                                            //will become the inverse matrix through row operations
        for (int i = 0; i < rows; i++) { //This is clearing the matrix down to echelon form
            while (matrix[i][i] == 0) {
                if (i + 1 >= rows) {
                    throw new IllegalArgumentException("This matrix is not invertible!");
                }
                rowSwap(i, i + 1); //Swaps rows if pivot position has a 0
                inverse.rowSwap(i, i + 1); //Does corresponding row operation in the inverse matrix
            }
            double factor1 = 1 / matrix[i][i];
            rowMultiplication(i, factor1); //Multiplies the current row by the inverse of the value in the pivot position
                                            //This gives a one in the pivot position
            inverse.rowMultiplication(i,factor1); //Does corresponding row operation in inverse
            for (int j = 1; j < rows - i; j++) {
                double factor2 = -1 * matrix[i+j][i]; //This is the factor in order to clear the row(s) below the current row
                rowAddition(i, i+j, factor2); //Add a multiple of the current row to the corresponding row below
                inverse.rowAddition(i,i+j,factor2); //Same row operation in inverse
            }
        }
        for (int i = rows - 1; i > 0; i--) { //Now we need to clear the matrix up to reduced echelon form
            for (int j = 1; j < rows; j++) {
                if (i - j < 0) { //Makes sure we do not have an array out of index exception
                    break;
                }
                double factor3 = -1 * matrix[i-j][i]; //The factor to clear the given row above
                rowAddition(i,i-j,factor3); //Add this row times the factor to the given row to clear
                inverse.rowAddition(i,i-j,factor3); //Corresponding row operation in inverse
            }
        }
        this.matrix = original.matrix; //Returns this matrix to the original matrix
        return inverse;
    }

    /**
     * Multiplies a given row in the matrix by a factor
     * @param row The index of the row to be multiplied
     * @param x The factor that will multiply the row
     */
    private void rowMultiplication(int row, double x) {
        for (int i = 0; i < columns; i++) {
            matrix[row][i] = matrix[row][i] * x; //Multiply each element of the row by x
        }
    }

    /**
     * Adds a multiple of a given row to the desired row
     * @param row1 The index row that will be multiplied and added, this row will remain unchanged in the matrix
     * @param row2 The index of the row that will be changed
     * @param factor The factor to multiply row1 by
     */
    private void rowAddition(int row1, int row2, double factor) {
        for (int i = 0; i < columns; i++) {
            matrix[row2][i] = matrix[row1][i] * factor + matrix[row2][i]; //Set the corresponding entry of row2 to the new value
        }
    }

    /**
     * Swaps two rows in a matrix
     * @param row1 The index of the first row to be swapped
     * @param row2 The index of the second row to be swapped
     */
    private void rowSwap(int row1, int row2) {
        double[] temp = new double[columns];
        System.arraycopy(matrix[row1], 0, temp, 0, columns); //Makes a copy of the row
        for (int i = 0; i < columns; i++) {
            matrix[row1][i] = matrix[row2][i]; //Swaps the corresponding elements
            matrix[row2][i] = temp[i];
        }
    }

    /**
     * A toString method for the Matrix class
     * @return A String which shows doubles positioned in a matrix fashion
     */
    public String toString() {
        StringBuilder representation = new StringBuilder(" ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                representation.append(matrix[i][j]).append(" ");
            }
            representation.append("\n");
        }
        return representation.toString();
    }
}

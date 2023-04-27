public class Matrix {
    double[][] matrix;
    int rows;
    int columns;

    public Matrix(int a, int b) {
        matrix = new double[a][b];
        this.rows = a;
        this.columns = b;
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    public Matrix(Vector v) {
        this.matrix = new double[v.size][1];
        for (int i = 0; i < v.size; i++) {
            addValue(i,0,v.getValue(i));
        }
        this.rows = v.size;
        this.columns = 1;
    }

    public Matrix(Vector[] vectors) {
        this.matrix = new double[vectors[0].size][vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[i].size; j++) {
                addValue(j,i,vectors[i].getValue(j));
            }
        }
        this.rows = vectors[0].size;
        this.columns = vectors.length;
    }

    public Matrix (int size) {
        this.matrix = new double[size][size];
        this.rows = size;
        this.columns = size;
        for (int i = 0; i < size; i++) {
            addValue(i, i, 1);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void addValue(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    public double getValue(int row, int column) {
        return this.matrix[row][column];
    }


    public Matrix multiplication(Matrix other) throws IllegalArgumentException {
        if (columns != other.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions must match!");
        }
        Matrix result = new Matrix(rows, other.getColumns());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < columns; k++) {
                    sum += matrix[i][k] * other.getValue(k,j);
                }
                result.addValue(i,j,sum);
            }
        }
        return result;
    }

    public Matrix multiplication(Vector other) throws IllegalArgumentException {
        if (columns != other.getSize()) {
            throw new IllegalArgumentException("Dimensions must match!");
        }
        Matrix v = new Matrix(other);
        Matrix result = multiplication(v);
        return result;
    }

    public void editColumn(Matrix transpose, int column, Vector entry) {
        for (int i = 0; i < transpose.getRows(); i++) {
            transpose.addValue(i,column,entry.getValue(i));
        }
    }

    public Matrix transpose() {
        Matrix transpose = new Matrix(this.columns, this.rows);
        for (int i = 0; i < this.rows; i++) {
            Vector row = new Vector(matrix[i]);
            editColumn(transpose, i, row);
        }
        return transpose;
    }

    public boolean isIdentity() {
        if (rows != columns) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i == j) && matrix[i][j] != 1) {
                    return false;
                }
                else if ((i != j) && matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix inverse() throws IllegalArgumentException {
        if (rows != columns) {
            throw new IllegalArgumentException("Must be square!");
        }
        Matrix inverse = new Matrix(rows);
        for (int i = 0; i < rows; i++) {
            while (matrix[i][i] == 0) {
                if (i + 1 >= rows) {
                    throw new IllegalArgumentException("This matrix is not invertible!");
                }
                rowSwap(i, i + 1);
                inverse.rowSwap(i, i + 1);
            }
            double factor1 = 1 / matrix[i][i];
            rowMultiplication(i, factor1);
            inverse.rowMultiplication(i,factor1);
            for (int j = 1; j < rows - i; j++) {
                double factor2 = -1 * matrix[i+j][i];
                rowAddition(i, i+j, factor2);
                inverse.rowAddition(i,i+j,factor2);
            }
        }
        for (int i = rows - 1; i > 0; i--) {
            for (int j = 1; j < rows; j++) {
                if (i - j < 0) {
                    break;
                }
                double factor3 = -1 * matrix[i-j][i];
                rowAddition(i,i-j,factor3);
                inverse.rowAddition(i,i-j,factor3);
            }
        }
        return inverse;
    }

    public void rowMultiplication(int row, double x) {
        for (int i = 0; i < columns; i++) {
            matrix[row][i] = matrix[row][i] * x;
        }
    }

    public void rowAddition(int row1, int row2, double factor) {
        for (int i = 0; i < columns; i++) {
            matrix[row2][i] = matrix[row1][i] * factor + matrix[row2][i];
        }
    }

    public void rowSwap(int row1, int row2) {
        double[] temp = new double[columns];
        for (int i = 0; i < columns; i++) {
            temp[i] = matrix[row1][i];
        }
        for (int i = 0; i < columns; i++) {
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp[i];
        }
    }

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

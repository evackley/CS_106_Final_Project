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

    public Matrix transpose() {
        for (int i = 0; i < this.rows; i++) {
            Vector row = new Vector(matrix[i]);
        }

        return null;
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

public class Vector {
    double[] vector;
    int size;

    public Vector(int size) {
        vector = new double[size];
        this.size = size;
    }

    public Vector(double[] vec) {
        vector = vec;
        size = vec.length;
    }

    public int getSize() {
        return this.size;
    }

    public double getValue(int index) {
        return vector[index];
    }

    public void setValue(int index, double value) {
        vector[index] = value;
    }
    public double dotProduct(Vector other) throws IllegalArgumentException{
        if (this.size != other.getSize()) {
            throw new IllegalArgumentException("Sizes must match!");
        }
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += vector[i] * other.getValue(i);
        }
        return sum;
    }
    public String toString() {
        String representation = "";
        for (int i = 0; i < size; i++) {
            representation += vector[i] + "\n";
        }
        return representation;
    }
}

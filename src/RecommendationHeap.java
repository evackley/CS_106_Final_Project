
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RecommendationHeap<Double> extends ArrayHeap {
    private ArrayList<Book> library;

    public RecommendationHeap(ArrayList<Book> library) {
        super();
        this.library = library;
    }

    public Vector calculatePotentialRatings(ArrayList<Book> readBooks) throws FileNotFoundException, IllegalArgumentException {
        int size = 5;
        if (readBooks.size() < 5) {
            size = readBooks.size();
        }
        Vector ratings = new Vector(size);
        Matrix ATranspose = new Matrix(30, size);
        for (int i = 0; i < size; i++) {
            Book current = readBooks.get(i);
            int rating = current.getPersonalRating();
            ratings.setValue(i,rating);
            BookVector bVector = new BookVector(current);
            ATranspose.editColumn(i, bVector);
        }
        Matrix A = ATranspose.transpose();
        Matrix ATransposeA = ATranspose.multiplication(A);
        Matrix inverse = ATransposeA.inverse();
        Matrix product = inverse.multiplication(ATranspose);
        return product.multiplication(ratings);
    }


}

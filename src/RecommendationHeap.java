
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RecommendationHeap<Double> extends ArrayHeap {
    private ArrayList<Book> library;
    private Vector ratingPredictions;

    public RecommendationHeap(ArrayList<Book> library, Stack<Book> readHistory) throws FileNotFoundException, IllegalArgumentException {
        super();
        this.library = library;
        ArrayList<Book> readBooks = getHistory(readHistory);
        calculatePotentialRatings(readBooks);
        loadValues();
    }

    public ArrayList<Book> getHistory(Stack<Book> readHistory) {
        ArrayList<Book> readBooks = new ArrayList<>();
        Stack<Book> temp = new Stack<>();
        while (!readHistory.isEmpty()) {
            Book book = readHistory.peek(0);
            readHistory.pop();
            readBooks.add(book);
            temp.push(book);
        }
        while (!temp.isEmpty()) {
            Book book = temp.peek(0);
            temp.pop();
            readHistory.push(book);
        }
        return readBooks;
    }


    public void calculatePotentialRatings(ArrayList<Book> readBooks) throws FileNotFoundException, IllegalArgumentException {
        int size = 5;
        if (readBooks.size() < 5) {
            size = readBooks.size();
        }
        Vector ratings = new Vector(size);
        Matrix ATranspose = new Matrix(23, size);
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
        ratingPredictions = product.multiplication(ratings);
    }

    public void loadValues() throws FileNotFoundException {
        for (Book book : library) {
            if (!book.isRead()) {
                BookVector bookVector = new BookVector(book);
                double potentialRating = bookVector.dotProduct(ratingPredictions);
                BookNode bookNode = new BookNode(book, potentialRating);
                insert(bookNode);
            }
        }
    }

}


import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Eva Ackley
 * This class creates a heap based on the potential of the user to like books based on their read history
 * The user MUST have added at LEAST 22 books to their read history in order for this method to work
 * This uses the least-squares prediction model, which requires an invertible matrix
 * The formula for the prediction vector x is (A^TA)^(-1)(A^T)b
 * Where A is the matrix containing the qualities of the books from the BookVector
 * A^T is the transpose of that matrix
 * b is the rating that the user gave to each book
 * @param <T> The ArrayHeap can technically hold any comparable datatype, but it will be used with BookNodes
 */
public class RecommendationHeap<T> extends ArrayHeap {
    private ArrayList<Book> library;
    private Vector ratingPredictions;

    /**
     * This is a constructor method for the recommendation heap, it calls the methods to do the least-squares
     * prediction model
     * @param library An arrayList of books, which is the library database
     * @param readHistory A Stack of books, the read history of the user
     * @throws FileNotFoundException The method creates BookVectors, which have the potential to throw FileNotFoundExceptions
     * @throws IllegalArgumentException Creates matrices, which have the potential to throw IllegalArgumentExceptions
     */
    public RecommendationHeap(Library library, Stack<Book> readHistory) throws FileNotFoundException, IllegalArgumentException {
        super(); //Creates a heap
        this.library = library.getLibrary();
        ArrayList<Book> readBooks = getHistory(readHistory); //Adds books from the stack history to an ArrayList
        calculatePotentialRatings(readBooks); //Find the potential rating vector
        loadValues(); //Adds each BookNode with its potential rating to the ArrayHeap
    }

    /**
     * Gets the read Books from the Stack, then returns them in the correct order
     * @param readHistory The Stack which contains readHistory
     * @return An ArrayList of Books which contains readHistory
     */
    public ArrayList<Book> getHistory(Stack<Book> readHistory) {
        ArrayList<Book> readBooks = new ArrayList<>();
        Stack<Book> temp = new Stack<>(); //Temporary stack in reverse order
        while (!readHistory.isEmpty()) { //Move books from this stack
            Book book = readHistory.peek();
            readHistory.pop();
            readBooks.add(book); //Add the book to the ArrayList
            temp.push(book); //Add book to the temporary stack
        }
        while (!temp.isEmpty()) { //Return books to the original stack
            Book book = temp.peek();
            temp.pop();
            readHistory.push(book);
        }
        return readBooks;
    }

    /**
     * This creates Vector which should predict how likely the user is to like a book based on its characteristics
     * @param readBooks A list of books that the user has read
     * @throws FileNotFoundException Creating BookVectors has the potential to throw an exception if the common words file is not found
     * @throws IllegalArgumentException If the matrix is not invertible, the program will not work. Or, if the user has not
     *                                  marked enough books as read before calling
     */
    public void calculatePotentialRatings(ArrayList<Book> readBooks) throws FileNotFoundException, IllegalArgumentException {
        int size = readBooks.size();
        if (size < 22) {
            throw new IllegalArgumentException("Please rate more books before using this method!");
        }
        Vector ratings = new Vector(size); //Create a Vector to hold the user's ratings of the book
        //This matrix will technically be the transpose of the matrix that is designated A,
        //because of the way that I conceptualize vectors
        Matrix ATranspose = new Matrix(22, size);
        for (int i = 0; i < size; i++) {
            Book current = readBooks.get(i);
            int rating = current.getPersonalRating();
            ratings.setValue(i,rating); //Add the rating for this book to the rating vector, b
            BookVector bVector = new BookVector(current);
            ATranspose.editColumn(i, bVector); //Add the BookVector to the Transpose matrix
        }
        Matrix A = ATranspose.transpose(); //Transpose the transpose to get the Matrix A
        Matrix ATransposeA = ATranspose.multiplication(A); //We need the matrix A Transpose A
        Matrix inverse = ATransposeA.inverse(); //The inverse of this matrix
        Matrix product = inverse.multiplication(ATranspose); //The inverse matrix times the Transpose
        //This vector will contain the constants that the matrix gives for each component of the book
        ratingPredictions = product.multiplication(ratings);
    }

    /**
     * An access method for the Prediction Vector.
     * @return The Prediction Vector, which holds the constants that correspond to each entry in a BookVector
     */
    public Vector getRatingPredictions() {
        return ratingPredictions;
    }

    /**
     * Adds the predicted rating for every unread book in the library to the Heap
     * @throws FileNotFoundException Creating BookVectors can throw an exception
     */
    public void loadValues() throws FileNotFoundException {
        for (Book book : library) {
            if (!book.isRead()) { //Only want to add unread books
                BookVector bookVector = new BookVector(book);
                //The potential rating for the book will be the constant from the prediction vector
                //multiplied by the corresponding value in the BookVector, then the sum is the potential rating
                double potentialRating = bookVector.dotProduct(ratingPredictions); //Uses dot product
                BookNode bookNode = new BookNode(book, potentialRating);
                insert(bookNode); //Insert a bookNode into the Heap
            }
        }
    }

    /**
     * A method to get a desired number of recommended books
     * @param size The number of desired books to recommend
     * @return An ArrayList of Books, which have the highest potential ratings
     */
    public ArrayList<Book> getRecommendations(int size) {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            BookNode max = (BookNode) this.removeMax(); //Remove the max
            books.add(max.getBook()); //Add to the list
        }
        return books;
    }
}

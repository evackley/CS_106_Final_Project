/**
 * @author Eva Ackley
 * A class to be stored in an ArrayHeap to determine the books the user likes best
 * It is comparable in order to be used in a Recommendation Heap
 */
public class BookNode implements Comparable<BookNode>{

    private Book book;
    private double potentialRating;

    /**
     * A constructor
     * @param book The Book object that the potential rating matches with
     * @param rating The potential rating the user may give the book, based on matrix projection
     */
    public BookNode(Book book, double rating) {
        this.book = book;
        this.potentialRating = rating;
    }

    /**
     * An access method for the potential rating of the book
     * @return The double calculated using the prediction matrix and recommendation vector
     */
    public double getRating() {
        return potentialRating;
    }

    public Book getBook() {
        return this.book;
    }

    /**
     * Implements the compareTo method in order to be a comparable data type
     * @param other the object to be compared.
     * @return Positive if the book has a higher potential rating than the other book, negative if this
     * book has a lower potential rating. If they have the same potential rating, they will be compared
     * based on popularity
     */
    public int compareTo(BookNode other) {
        if (Double.compare(potentialRating, other.getRating()) == 0) {
            return book.compareToPopularity(other.getBook());
        }
        return Double.compare(potentialRating, other.getRating());
    }
}

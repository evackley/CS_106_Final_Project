public class BookNode implements Comparable<BookNode>{

    private Book book;
    private double potentialRating;

    public BookNode(Book book, double rating) {
        this.book = book;
        this.potentialRating = rating;
    }

    public double getRating() {
        return potentialRating;
    }

    public int compareTo(BookNode other) {
        return Double.compare(potentialRating, other.getRating());
    }
}

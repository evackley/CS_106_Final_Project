public class ReadBook {
    private boolean read;
    private double personalRating;
    private Book book;

    public ReadBook(Book book) {
        this.book = book;
        this.read = true;
    }

    public void setPersonalRating(double rating) {
        this.personalRating = rating;
    }
    public double getPersonalRating() {
        return this.personalRating;
    }
    public Book getBook() {
        return this.book;
    }
}

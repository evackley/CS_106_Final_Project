public class Book {
    private String ISBN10;
    private String title;
    private String subtitle;
    private String[] authors;
    private String categories;
    private String thumbnail;
    private String description;
    private int published;
    private double averageRating;
    private int numPages;
    private int numRatings;

    public Book(String ISBN10, String title, String subtitle, String[] authors, String categories,
                String thumbnail, String description, int published, double averageRating, int numPages, int numRatings) {
        this.ISBN10 = ISBN10;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.description = description;
        this.published = published;
        this.averageRating = averageRating;
        this.numPages = numPages;
        this.numRatings = numRatings;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public String getCategories() {
        return this.categories;
    }

    public double getAverageRating() {
        return this.averageRating;
    }

    public String getISBN10() {
        return this.ISBN10;
    }

    public String getSubtitle() {
        return this.subtitle;
    }
    public String getThumbnail() {
        return this.thumbnail;
    }
    public int getPublished() {
        return this.published;
    }
    public int getNumPages() {
        return this.numPages;
    }

    public int getNumRatings() {
        return this.numRatings;
    }

    public int compareToAuthors(Book other) {
        String firstAuthor = authors[0];
        String[] otherAuthors = other.getAuthors();
        String otherAuthor = otherAuthors[0];
        return firstAuthor.compareTo(otherAuthor);
    }

    public int compareToPopularity(Book other) {
        return Integer.compare(numRatings, other.getNumRatings());
    }

    public int compareToTitle(Book other) {
        return title.compareTo(other.getTitle());
    }
    public String toString() {
        String representation = title + " " + subtitle + " by " + authors[0];
        for (int i = 1; i < authors.length; i++) {
            representation += "," + authors[i];
        }
        representation += "\n";
        String[] descriptionAsArray = description.split(" ");
        for (int i = 0; i < descriptionAsArray.length; i++) {
            if (i % 20 == 0) {
                representation += "\n";
            }
            representation += descriptionAsArray[i] + " ";
        }
        representation += "\n";
        return representation;
    }
}

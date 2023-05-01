public class Book {
    private String ISBN10;
    private String title;
    private String subtitle;
    private String[] authors;
    private String[] categories;
    private String thumbnail;
    private String description;
    private int published;
    private double averageRating;
    private int numPages;
    private int numRatings;

    private boolean isRead;

    private int personalRating;

    public Book(String ISBN10, String title, String subtitle, String[] authors, String[] categories,
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
        this.isRead = false;
        this.personalRating = 0;
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

    public String[] getCategories() {
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

    public int compareToCategories(Book other) {
        String category1 = this.categories[0];
        String[] otherCategories = other.getCategories();
        String category2 = otherCategories[0];
        return category1.compareTo(category2);
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead() {
        this.isRead = true;
    }

    public void setPersonalRating(int rating) {
        this.personalRating = rating;
    }

    public int getPersonalRating() {
        return this.personalRating;
    }
    public String toString() {
        StringBuilder representation = new StringBuilder(title);
        if (this.subtitle.compareTo("") != 0) {
            representation.append(" ").append(subtitle);
        }
        representation.append(" by ").append(authors[0]);
        for (int i = 1; i < authors.length; i++) {
            representation.append(", ").append(authors[i]);
        }
        representation.append("\nCategories: ").append(categories[0]);
        for (int i = 1; i < categories.length; i++) {
            representation.append(", ").append(categories[i]);
        }
        representation.append("\n");
        String[] descriptionAsArray = description.split(" ");
        for (int i = 0; i < descriptionAsArray.length; i++) {
            if (i % 20 == 0) {
                representation.append("\n");
            }
            representation.append(descriptionAsArray[i]).append(" ");
        }
        representation.append("\n");
        return representation.toString();
    }
}

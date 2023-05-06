/**
 * @author Eva Ackley
 * Date: 5/2/2023
 * Purpose: This class creates a Book Datatype which has attributes gained from dataset containing
 * information about books on GoodReads
 */
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

    /**
     * Constructor method for the Book Datatype
     * @param ISBN10 The 10 digit IBSN number for the book
     * @param title The title of the book
     * @param subtitle The subtitle of the book, will be an empty String if there is not one
     * @param authors An array of Strings which contains the name(s) of the author(s) of the book
     * @param categories An array of Strings which contain the categories of the book, similar to genre
     * @param thumbnail A url for the thumbnail of the book
     * @param description A String of the description of the book given on GoodReads
     * @param published An int which is the year the book was published, it will be 0 if the file has no year
     * @param averageRating A double which is the average of the ratings given by the users on GoodReads
     * @param numPages The number of pages in the book, will be 0 if the file has no page number
     * @param numRatings The number of ratings for the book on GoodReads
     */
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
        this.isRead = false; //During construction, the book has not been read
        this.personalRating = 0; //Personal rating defaults to 0
    }

    /**
     * An access method for the description of the book
     * @return The String which contains the description of the book
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * An access method for the title of the book
     * @return A String which is the title of the book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * An access method for the author(s) of the book
     * @return An array of Strings which contains the author(s)
     */
    public String[] getAuthors() {
        return this.authors;
    }

    /**
     * An access method for the categories of the book
     * @return An array of Strings which contains the categories which relate to the book
     */
    public String[] getCategories() {
        return this.categories;
    }

    /**
     * An access method for the average rating of the book
     * @return A double which is the average rating for the book
     */
    public double getAverageRating() {
        return this.averageRating;
    }

    /**
     * An access method for the ISBN of the book
     * @return An int which is the ISBN of the book
     */
    public String getISBN10() {
        return this.ISBN10;
    }

    /**
     * An access method for the subtitle of the book
     * @return A String which is the subtitle of the book
     */
    public String getSubtitle() {
        return this.subtitle;
    }

    /**
     * An access method for the thumbnail of the book
     * @return A String which contains the url that has the thumbnail of the book
     */
    public String getThumbnail() {
        return this.thumbnail;
    }

    /**
     * An access method for the year the book was published
     * @return An int of the year that the book was published
     */
    public int getPublished() {
        return this.published;
    }

    /**
     * An access number for the number of pages in the book
     * @return An integer of the number of pages for the book
     */
    public int getNumPages() {
        return this.numPages;
    }

    /**
     * An access method for the number of ratings of the book
     * @return An integer of the number of ratings
     */
    public int getNumRatings() {
        return this.numRatings;
    }

    /**
     * Allows two books to be compared using the last name of the first author given
     * @param other The book to compare this book to
     * @return This method will return a negative integer if the author of this book comes alphabetically
     * before the author of the other book, 0 if they are the same, and a positive integer if the author
     * of this book comes alphabetically later than the author of the other book.
     */
    public int compareToAuthors(Book other) {
        String firstAuthorFull = authors[0].toLowerCase(); //Uses the first author given, which is the first alphabetically
        String[] otherAuthors = other.getAuthors();
        String otherAuthorFull = otherAuthors[0].toLowerCase(); //Use lower case to make comparisons work better
        String[] firstAuthorNames = firstAuthorFull.split(" ");
        String firstAuthor = firstAuthorNames[firstAuthorNames.length - 1]; //Get the last name of the first author
        String[] otherAuthorNames = otherAuthorFull.split(" ");
        String otherAuthor = otherAuthorNames[otherAuthorNames.length - 1]; //Get the last name of the other author
        return firstAuthor.compareTo(otherAuthor);
    }

    /**
     * Allows two books to be compared using number of ratings
     * @param other The book to compare this book to
     * @return A negative number if this book has fewer ratings than the other book, 0 if they have
     * the same number of ratings, and a positive number if this book has more ratings than the other book.
     */
    public int compareToPopularity(Book other) {
        return Integer.compare(numRatings, other.getNumRatings());
    }

    /**
     * Allows two books to be compared using Title
     * @param other The book to compare this book to
     * @return This method will return a negative integer if the title of this book comes alphabetically
     * before the title of the other book, 0 if they are the same, and a positive integer if the title
     * of this book comes alphabetically later than the title of the other book.
     */
    public int compareToTitle(Book other) {
        return title.toLowerCase().compareTo(other.getTitle().toLowerCase());
    }

    /**
     * Allows two Book objects to be compared based on their first category
     * @param other The other book to compare this book to
     * @return This method will return a negative integer if the category of this book comes alphabetically
     * before the category of the other book, 0 if they are the same, and a positive integer if the category
     * of this book comes alphabetically later than the category of the other book.
     */
    public int compareToCategories(Book other) {
        String category1 = this.categories[0].toLowerCase();
        String[] otherCategories = other.getCategories();
        String category2 = otherCategories[0].toLowerCase();
        return category1.compareTo(category2);
    }

    /**
     * An access method for the read status of the book by the user
     * @return True if the user has read the book, false if else
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * A mutation method for the read attribute of the book, sets it to true, namely that the user
     * has read the book
     */
    public void setRead() {
        this.isRead = true; //This method will be called when a user searches for a book and selects it as read
    }

    /**
     * A mutation method for the user's personal rating of the book
     * @param rating An integer which the user wishes to rate the book from 1-5 (1 being the worst, 5 the best)
     */
    public void setPersonalRating(int rating) {
        this.personalRating = rating; //This method will be called when the user chooses what they want to rate the book they have read
    }

    /**
     * An access method for the user's personal rating of the book
     * @return An integer which represents what the user thought about the book
     */
    public int getPersonalRating() {
        return this.personalRating;
    }

    /**
     * A toString method for the Book class
     * @return A String which contains the title, subtitle, author(s), and categories of the book
     */
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
                representation.append("\n"); //This makes it so the description is readable, not super long on one line
            }
            representation.append(descriptionAsArray[i]).append(" ");
        }
        representation.append("\n");
        return representation.toString();
    }
}

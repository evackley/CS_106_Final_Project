public class Book {
    private int ISBN10;
    private String title;
    private String subtitle;
    private String[] authors;
    private String categories;
    private String thumbnail;
    private String description; //What is the best way to do this? It's pretty long
    private int published;
    private double averageRating;
    private int numPages;
    private int numRatings;

    public Book(int ISBN10, String title, String subtitle, String[] authors, String categories,
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

    public String toString() {
        String representation = title + subtitle + " by " + authors[0];
        for (int i = 1; i < authors.length; i++) {
            representation += "," + authors[i];
        }
        int descriptionLength = description.length();
        int divisor = descriptionLength / 100;
        for (int i = 0; i < divisor; i = i+100) {
            representation += "\n" + description.substring(i,i+99);
        }
        representation += description.substring(divisor * 100, descriptionLength - 1);
        return representation;
    }
}

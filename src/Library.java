import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Eva Ackley
 * Creates an ArrayList of Books from a file
 */
public class Library {
    ArrayList<Book> library;

    /**
     * A Constructor method for the Library Database
     * @param files The files that contain the data about the books
     * @throws FileNotFoundException An exception is thrown if the file is not found
     */
    public Library(String[] files) throws FileNotFoundException {
        library = new ArrayList<>();
        for (String file : files) {
            CSVReader reader = new CSVReader();
            FileReader input = new FileReader(file);
            ArrayList<String[]> myEntries = reader.read(input);
            for (String[] tokens : myEntries) {
                String ISBN10 = tokens[1];
                String title = tokens[2];
                String subtitle = tokens[3];
                String[] authors = tokens[4].split(";");
                String[] categories = tokens[5].split(";");
                String thumbnail = tokens[6];
                String description = tokens[7];
                int publish_year = 0;
                if (!tokens[8].equals("")) {
                    publish_year = Integer.parseInt(tokens[8]);
                }
                double average_rating = 0;
                if (!tokens[9].equals("")) {
                    average_rating = Double.parseDouble(tokens[9]);
                }
                int num_pages = 0;
                if (!tokens[10].equals("")) {
                    num_pages = Integer.parseInt(tokens[10]);
                }
                int ratings = 0;
                if (!tokens[11].equals("")) {
                    ratings = Integer.parseInt(tokens[11]);
                }
                //Create a book using the data from the file
                Book myBook = new Book(ISBN10, title, subtitle, authors, categories, thumbnail, description, publish_year,
                        average_rating, num_pages, ratings);
                library.add(myBook);
            }
        }
    }

    /**
     * An access method for the size of the database
     * @return The number of books stored in the database
     */
    public int getSize() {
        return library.size();
    }

    /**
     * Returns a books at a specific index
     * @param index The desired index of the book
     * @return The book at the desired index
     */
    public Book getBook(int index) {
        return library.get(index);
    }

    /**
     * An access method for the ArrayList of books
     * @return The library data set
     */
    public ArrayList<Book> getLibrary() {
        return this.library;
    }
}

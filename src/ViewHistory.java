/**
 * This class represents a view history for a collection of books.
 * It keeps track of the books that have been viewed and provides methods to add, clear, and retrieve book history.
 */
import java.io.*;
import java.util.*;

public class ViewHistory {
    private final String filename;
    private final Stack<Book> historyStack;

    /**
     * Constructs a ViewHistory object with the specified filename.
     * @param filename the name of the file to store the book history
     */
    public ViewHistory(String filename) {
        this.filename = filename;
        historyStack = new Stack<>();
        loadHistory();
    }

    /**
     * Adds a book to the history stack and saves it to the book history file.
     * @param book the book to add to the history
     */
    public void addBook(Book book) {
        historyStack.push(book);
        book.setRead();
        saveBookToHistory(book);
        saveHistory();
    }

    /**
     * Checks if the history stack contains the specified book.
     * @param book the book to check for in the history
     * @return true if the history contains the book, false otherwise
     */
    public boolean containsBook(Book book) {
        for (Book b : historyStack) {
            if (b.equals(book)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the history stack and the book history file.
     */
    public void clearHistory() {
        while (!historyStack.isEmpty()) {
            historyStack.pop();
        }
        clearBookHistory();
    }

    /**
     * Gets the history of the books as a string.
     * @return a string representation of the book history
     */
    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        for (Book book : historyStack) {
            sb.append(book.getTitle());
            sb.append(",").append(book.getPersonalRating()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Loads the book history from the file and returns it as a stack.
     * @return a stack of Book objects representing the book history
     */
    Stack<Book> loadHistory() {
        Stack<Book> loadedHistoryStack = new Stack<>();
        try {
            FileReader fileReader = new FileReader("book_history.csv");
            CSVReader csvReader = new CSVReader();
            ArrayList<String[]> lines = csvReader.read(fileReader);
            BookFinder bookFinder = new BookFinder();
            ArrayList<Book> books = bookFinder.loadBooks("Book_data/Book1.csv"); // load books from CSV file
            bookFinder.setBooks(books); // set books list in bookFinder object
            for (String[] line : lines) {
                Book book = bookFinder.getBookByTitle(line[0]);
                if (book != null) {
                    book.setPersonalRating(Integer.parseInt(line[1]));
                    book.setRead();
                    loadedHistoryStack.push(book);
                }
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadedHistoryStack;
    }


    /**
     * Saves a book to the book history file.
     * @param book the book to save to the history file
     */
    void saveBookToHistory(Book book) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(book.getTitle() +  "," + book.getPersonalRating() + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the book history file.
     */
    void clearBookHistory() {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("Book Title");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     This method saves the history of viewed books to a CSV file.
     It retrieves the titles of previously viewed books from the file, and writes any new titles to the end of the file.
     @throws IOException if an I/O error occurs while writing to or reading from the file.
     */
    void saveHistory() {
        try {
            List<String> titles = new ArrayList<>();
            FileReader fileReader = new FileReader(filename);
            CSVReader csvReader = new CSVReader();
            ArrayList<String[]> lines = csvReader.read(fileReader);
            for (String[] line : lines) {
                ArrayList<String> info = new ArrayList<>();
                titles.add(line[0]);
            }
            fileReader.close();

            FileWriter writer = new FileWriter(filename, true);
            for (Book book : historyStack) {
                if (!titles.contains(book.getTitle())) {
                    writer.write(book.getTitle() + "," + book.getPersonalRating() + "\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     This method retrieves the history of viewed books and returns it as a String.
     @return a String representation of the history of viewed books.
     @throws IOException if an I/O error occurs while reading from the file.
     */
    public String userHistory() {
        StringBuilder history = new StringBuilder();
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                history.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}

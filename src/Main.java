import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"Book_data/Book1.csv"};
        Library library = new Library(files);
        ArrayList<Book> bookLibrary = library.getLibrary();
        Stack<Book> readHistory = new Stack<>();
        for (int i = 0; i < 23; i++) {
            Book book = randomBook(library);
            book.setRead();
            book.setPersonalRating((int) (Math.random() * 5));
            readHistory.push(book);
            System.out.println(book);
            System.out.println(book.getPersonalRating());
        }
        RecommendationHeap<BookNode> recommendationHeap = new RecommendationHeap<>(bookLibrary, readHistory);
        for (int i = 0; i < 5; i++) {
            BookNode max = (BookNode) recommendationHeap.removeMax();
            System.out.println(max.toString() + max.getRating());
        }

    }

    public static Book randomBook(Library library) {
        int randomIndex = (int) (Math.random() * library.getSize());
        return library.getBook(randomIndex);
    }
}

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
        Book one = bookLibrary.get(1453);
        one.setRead();
        one.setPersonalRating(2);
        readHistory.push(one);
        Book two = bookLibrary.get(967);
        two.setRead();
        two.setPersonalRating(5);
        readHistory.push(two);
        Book three = bookLibrary.get(89);
        three.setPersonalRating(3);
        readHistory.push(three);

        RecommendationHeap recommendationHeap = new RecommendationHeap(bookLibrary, readHistory);
        System.out.println(recommendationHeap.removeMax());
    }

    public static Book randomBook(Library library) {
        int randomIndex = (int) (Math.random() * library.getSize());
        return library.getBook(randomIndex);
    }
}

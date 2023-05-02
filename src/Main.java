import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"Book_data/Book1.csv"};
        Library library = new Library(files);

        Book random = randomBook(library);

        BookVector bookVector = new BookVector(random);
        ArrayList<String> words = bookVector.getImportantWords();
        System.out.println(bookVector);
        System.out.println(random);
        for (String word : words) {
            System.out.println(word);
        }
        ArrayList<String> importantWords = bookVector.getImportantWords();
        System.out.println(random.getPersonalRating());

        double[][] ab = {{0,2},{3,4}};
        Matrix A = new Matrix(ab);
        System.out.println(A);
        Matrix Ainverse = A.inverse();
        System.out.println(Ainverse);



    }

    public static Book randomBook(Library library) {
        int randomIndex = (int) (Math.random() * library.getSize());
        return library.getBook(randomIndex);
    }
}

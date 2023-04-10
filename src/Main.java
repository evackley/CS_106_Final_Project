import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"Book_data/Book1.csv"};
        ArrayList<Book> library = loadBooks(files);
        System.out.println(library.get(0));
    }

    public static ArrayList<Book> loadBooks(String[] files) throws FileNotFoundException {
        ArrayList<Book> practice = new ArrayList<>();
        for (String file : files) {
            CSVReader reader = new CSVReader();
            FileReader input = new FileReader(file);
            ArrayList<String[]> myEntries = reader.read(input);
            for (int i = 0; i < 3549; i++) {
                String[] temp = myEntries.get(i);
                String ISBN10 = temp[1];
                String title = temp[2];
                String subtitle = temp[3];
                String[] authors = temp[4].split(";");
                String categories = temp[5];
                String thumbnail = temp[6];
                String description = temp[7];
                int publish_year = 0;
                if (!temp[8].equals("")) {
                    publish_year = Integer.parseInt(temp[8]);
                }
                double average_rating = 0;
                if (!temp[9].equals("")) {
                    average_rating = Double.parseDouble(temp[9]);
                }
                int num_pages = 0;
                if (!temp[10].equals("")) {
                    num_pages = Integer.parseInt(temp[10]);
                }
                int ratings = 0;
                if (!temp[11].equals("")) {
                    ratings = Integer.parseInt(temp[11]);
                }
                Book myBook = new Book(ISBN10, title, subtitle, authors, categories, thumbnail, description, publish_year,
                        average_rating, num_pages, ratings);
                practice.add(myBook);
            }
        }
        return practice;
    }
}

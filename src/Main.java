import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"Book_data/Book1.csv"};
        ArrayList<Book> library = loadBooks(files);

        RecommendationGraph graph = new RecommendationGraph(library);
        String[] words = graph.findCommonWords();
        graph.sortCommonWords();
        words = graph.getCommonWords();

        ArrayList<ArrayList<String>> importantWords = graph.getImportantWords();
        ArrayList<String> description = importantWords.get(0);
        for (String word : description) {
            System.out.println(word);
        }


    }

    public static Book randomBook(ArrayList<Book> library) {
        int randomIndex = (int) (Math.random() * library.size());
        return library.get(884);
    }

    public static int containsFiction(ArrayList<Book> library) {
        int count = 0;
        for (Book book : library) {
            if (book.getCategories().contains("fiction")) {
                count++;
            }
            else if(book.getCategories().contains("Fiction")) {
                count++;
            }
        }
        return count;
    }

    public static ArrayList<Book> loadBooks(String[] files) throws FileNotFoundException {
        ArrayList<Book> library = new ArrayList<>();
        for (String file : files) {
            CSVReader reader = new CSVReader();
            FileReader input = new FileReader(file);
            ArrayList<String[]> myEntries = reader.read(input);
            for (String[] tokens : myEntries) {
                String ISBN10 = tokens[1];
                String title = tokens[2];
                String subtitle = tokens[3];
                String[] authors = tokens[4].split(";");
                String categories = tokens[5];
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
                Book myBook = new Book(ISBN10, title, subtitle, authors, categories, thumbnail, description, publish_year,
                        average_rating, num_pages, ratings);
                library.add(myBook);
            }
        }
        return library;
    }
}

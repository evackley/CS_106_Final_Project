import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String file = "Book_data/Book1.csv";
        System.out.println(file);
        CSVReader reader = new CSVReader();
        FileReader input = new FileReader(file);
        ArrayList<String[]> myEntries = reader.read(input);
        ArrayList<Book> practice = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            String[] temp = myEntries.get(i);
            int ISBN10 = Integer.parseInt(temp[1]);
            String title = temp[2];
            String subtitle = temp[3];
            String[] authors = temp[4].split(";");
            String categories = temp[5];
            String thumbnail = temp[6];
            int placeHolder = temp[7].length() - 1;
            int index = 7;
            StringBuilder descript = new StringBuilder();
            while (temp[index].charAt(placeHolder) != '"') {
                descript.append(temp[index]);
                index++;
                placeHolder = temp[index].length() - 1;
            }
            String description = descript.toString();
            int publish_year = Integer.parseInt(temp[++index]);
            double average_rating = Double.parseDouble(temp[++index]);
            int num_pages = Integer.parseInt(temp[++index]);
            int ratings = Integer.parseInt(temp[++index]);
            Book myBook = new Book(ISBN10, title, subtitle, authors, categories, thumbnail, description, publish_year,
                    average_rating, num_pages, ratings);
            practice.add(myBook);
        }
        for (Book myBook : practice) {
            System.out.println(myBook);
        }
    }
}

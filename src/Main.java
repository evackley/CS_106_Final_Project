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

        double[][] hi = {{1,3,4},{1,2,3}};
        double[][] hello = {{1,3,4,5},{1,3,4,7},{4,5,6,8},{5,3,2,8}};


        Matrix one = new Matrix(hi);
        Matrix two = new Matrix(hello);
        //System.out.println(one);
        //System.out.println(two);
        //System.out.println(one.getColumns());
        //System.out.println(two.getColumns());

        double[] meh = {1, 2,4};
        double[] mehh = {4,3,4};


        Vector a = new Vector(meh);
        Vector b = new Vector(mehh);

        Vector[] test = {a,b};

        //System.out.println(a.dotProduct(b));
        double[][] m = {{1,2},{1,2}};
        double[][] n = {{3,4},{3,4}};



        Matrix man = new Matrix(test);

        //System.out.println(man);
        //System.out.println(man.transpose());

        double[][] eva = {{7,3},{2,1}};
        Matrix invert = new Matrix(eva);
        //System.out.println(invert);
        //System.out.println(invert.inverse());
        //System.out.println(invert);
        System.out.println(two.inverse());
        Matrix unInvert = new Matrix(hello);
        System.out.println(two.inverse().multiplication(unInvert));


        //System.out.println(one.multiplication(two));
        //System.out.println(one.multiplication(a));

    }

    public static Book randomBook(ArrayList<Book> library) {
        int randomIndex = (int) (Math.random() * library.size());
        return library.get(884);
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
                Book myBook = new Book(ISBN10, title, subtitle, authors, categories, thumbnail, description, publish_year,
                        average_rating, num_pages, ratings);
                library.add(myBook);
            }
        }
        return library;
    }
}

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class RecommendationGraph {
    private int[][] graph;
    private ArrayList<Book> library;
    private ArrayList<ArrayList<String>> importantWords;
    private String[] commonWords;

    public RecommendationGraph(ArrayList<Book> library) throws FileNotFoundException {
        graph = new int[library.size()][library.size()];
        this.library = library;
        this.commonWords = findCommonWords();
        this.sortCommonWords();
        this.importantWords = findImportantWords();
    }

    public String[] getCommonWords() {
        return this.commonWords;
    }

    public ArrayList<ArrayList<String>> getImportantWords() {
        return this.importantWords;
    }

    public String[] findCommonWords() throws FileNotFoundException {
        String file = "Book_data/CommonWords.csv";
        CSVReader reader = new CSVReader();
        FileReader input = new FileReader(file);
        ArrayList<String[]> myEntries = reader.read(input);
        String[] words = new String[myEntries.size()];
        for (int i = 0; i < myEntries.size(); i++) {
            String[] tokens = myEntries.get(i);
            words[i] = tokens[0].toLowerCase();
        }
        return words;
    }

    private ArrayList<ArrayList<String>> findImportantWords() {
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        for (Book book : library) {
            String description = book.getDescription().toLowerCase();
            String[] wordsInDescription = description.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            ArrayList<String> important = removeCommonWords(wordsInDescription);
            words.add(important);
        }
        return words;
    }

    public ArrayList<String> removeCommonWords(String[] description) {
        ArrayList<String> leftWords = new ArrayList<>();
        for (String word : description) {
            if(!isCommonWord(word)) {
                if (!leftWords.contains(word)) {
                    leftWords.add(word);
                }
            }
        }
        return leftWords;
    }

    public void sortCommonWords() {
        int size = commonWords.length;
        for(int i = 0; i < size - 1; i++) {
            String max = commonWords[0];
            int index = 0;
            for (int j = 1; j < size - i; j++) {
                if (commonWords[j].compareTo(max) > 0) {
                    max = commonWords[j];
                    index = j;
                }
            }
            String temp = commonWords[size - 1 - i];
            commonWords[index] = temp;
            commonWords[size - 1 - i] = max;
        }
    }

    public boolean isCommonWord(String word) {
        word = word.toLowerCase();
        int low = 0;
        int high = commonWords.length - 1;
        int mid = (low + high) / 2;
        while (low <= high) {
            if (word.compareTo(commonWords[mid]) < 0) {
                high = mid - 1;
            }
            else if (word.compareTo(commonWords[mid]) > 0) {
                low = mid + 1;
            }
            else {
                return true;
            }
            mid = (low + high) / 2;
        }
        return false;
    }

    private void loadRelations() {

    }

}

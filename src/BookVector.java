import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
public class BookVector {

    private Book book;
    private Vector vector;
    private ArrayList<String> commonWords;
    private ArrayList<String> importantWords;
    private String[] categories;


    public BookVector(Book book1) throws FileNotFoundException {
        this.book = book1;
        this.commonWords = findCommonWords();
        this.importantWords = findImportantWords();
        this.categories = book.getCategories();
        this.vector = new Vector(30);
        buildVector();
    }

    public ArrayList<String> findCommonWords() throws FileNotFoundException {
        String file = "Book_data/CommonWords.csv";
        CSVReader reader = new CSVReader();
        FileReader input = new FileReader(file);
        ArrayList<String[]> myEntries = reader.read(input);
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < myEntries.size(); i++) {
            String[] tokens = myEntries.get(i);
            words.add(tokens[0].toLowerCase());
        }
        sortWords(words);
        return words;
    }

    public void sortWords(ArrayList<String> words) {
        int size = words.size();
        for(int i = 0; i < size - 1; i++) {
            String max = words.get(0);
            int index = 0;
            for (int j = 1; j < size - i; j++) {
                if (words.get(j).compareTo(max) > 0) {
                    max = words.get(j);
                    index = j;
                }
            }
            String temp = words.get(size - 1 - i);
            words.set(index, temp);
            words.set(size - 1 - i, max);
        }
    }

    public boolean containsWord(String word, ArrayList<String> words) {
        System.out.println(word);
        word = word.toLowerCase();
        int low = 0;
        int high = words.size() - 1;
        int mid = (low + high) / 2;
        while (low <= high) {
            if (word.compareTo(words.get(mid)) < 0) {
                System.out.println(word + " " + words.get(mid));
                high = mid - 1;
            }
            else if (word.compareTo(words.get(mid)) > 0) {
                System.out.println(word + " " + words.get(mid));
                low = mid + 1;
            }
            else {
                System.out.println(word);
                return true;
            }
            mid = (low + high) / 2;
        }
        return false;
    }

    private ArrayList<String> findImportantWords() {
        String description = book.getDescription().toLowerCase();
        String[] wordsInDescription = description.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        ArrayList<String> important = removeCommonWords(wordsInDescription);
        sortWords(important);
        return important;
    }

    public ArrayList<String> removeCommonWords(String[] description) {
        ArrayList<String> leftWords = new ArrayList<>();
        for (String word : description) {
            if(!containsWord(word, commonWords)) {
                if (!leftWords.contains(word)) {
                    leftWords.add(word);
                }
            }
        }
        return leftWords;
    }

    public Book getBook() {
        return book;
    }

    public ArrayList<String> getImportantWords() {
        return this.importantWords;
    }

    public void buildVector() {
        vector.setValue(0, book.getPublished());
        vector.setValue(1, book.getNumRatings());
        vector.setValue(2, book.getNumPages());
        vector.setValue(3, book.getAverageRating());
        isTopics("christian","clergy", 4);
        String[] christianTopics = {"christian", "clergy", "miracle", "inspiration", "faith", "preacher", "pastor", "jesus", "grace",
                                    "redemption", "reverend", "god", "priest", "religion", "saint"};
        hasDescription(christianTopics, 4);
        isTopic("adventure",5);
        String[] adventureTopics = {"adventure", "action", "police", "kidnap", "marine", "spy", "espionage", "secret", "treasure",
                                    "agent"};
        hasDescription(adventureTopics, 5);
        isTopics("romance", "love", 6);
        String[] romanceTopics = {"romance", "love", "partner", "boyfriend", "girlfriend", "wife", "marriage", "husband", "beautiful",
                                    "relationship", "passion", "happy"};
        hasDescription(romanceTopics, 6);
        isTopic("fantasy",7);
        String[] fantasyTopics = {"fantasy", "kingdom", "dwarf", "witch", "magic", "prince", "king", "queen", "princess", "spell", "evil",
                                    "quest", "journey", "myth"};
        hasDescription(fantasyTopics, 7);
        isTopic("drama",8);
        String[] dramaTopics = {"moving", "emotion", "drama", "serious", "play", "impact"};
        hasDescription(dramaTopics, 8);
        isTopic("country",9);
        String[] countryTopics = {"china", "india", "ireland", "england", "america", "country", "cowboy", "western"};
        hasDescription(countryTopics, 9);
        isTopic("science fiction", 10);
        String[] scienceFictionTopics = {"science", "scientist", "moon", "robot", "virus", "research", "mission", "clone", "space",
                                        };
        isTopic("essay",11);
        isTopics("juvenile","children", 12);
        String[] childrenTopics = {"child", "kid", "boy", "girl", "son", "daughter", "school"};
        hasDescription(childrenTopics, 12);
        isTopic("war",13);
        isTopic("christmas",14);
        isTopic("fictitious character", 15);
        isTopic("history",16);
        isTopic("biography",17);
        isTopic("fairy tale", 18);
        isTopic("nonfiction",19);
        isTopic("philosophy",20);
        isTopic("travel",21);
        isTopic("fiction",22);
        isTopics("mystery", "detective",23);
        isTopic("language",24);
        isTopic("art", 25);
        isTopic("horror", 26);
        String[] award = {"prize", "award", "laureate", "bestselling"};
        hasDescription(award, 27);
    }

    public void isTopics(String one, String two, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(one) || category.toLowerCase().contains(two)) {
                vector.setValue(index, 1);
            }
        }
    }


    public void isTopic(String topic, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(topic)) {
                vector.setValue(index, 1);
            }
        }
    }

    public void hasDescription(String[] genre, int index) {
        double count = vector.getValue(index);
        for (String word : genre) {
            if (containsWord(word, importantWords)) {
                count++;
            }
        }
        vector.setValue(index, count);
    }

    public String toString() {
        String representation = "";
        for (int i = 0; i < vector.getSize(); i++) {
            representation += vector.getValue(i) + "\n";
        }
        return representation;
    }
}

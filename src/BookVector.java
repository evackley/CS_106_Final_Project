import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Eva Ackley
 * This class creates a vector based on characteristics of the book, in order to use least-squares
 * for recommendation
 */
public class BookVector extends Vector {

    private Book book;
    private ArrayList<String> commonWords;
    private ArrayList<String> importantWords;
    private String[] categories;


    /**
     * A
     * @param book1
     * @throws FileNotFoundException
     */
    public BookVector(Book book1) throws FileNotFoundException {
        super(30);
        this.book = book1;
        this.commonWords = findCommonWords();
        this.importantWords = findImportantWords();
        this.categories = book.getCategories();
        buildVector();
    }

    public ArrayList<String> findCommonWords() throws FileNotFoundException {
        String file = "Book_data/CommonWords.csv";
        CSVReader reader = new CSVReader();
        FileReader input = new FileReader(file);
        ArrayList<String[]> myEntries = reader.read(input);
        ArrayList<String> words = new ArrayList<>();
        for (String[] tokens : myEntries) {
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

    public boolean containsWord(String word, ArrayList<String> words, boolean contain) {
        word = word.toLowerCase();
        int low = 0;
        int high = words.size() - 1;
        int mid = (low + high) / 2;
        while (low <= high) {
            System.out.println(word + " " + words.get(mid));
            if (contain && words.get(mid).contains(word)) {
                System.out.println(word);
                return true;
            }
            else if (word.compareTo(words.get(mid)) < 0) {
                high = mid - 1;
            }
            else if (word.compareTo(words.get(mid)) > 0) {
                low = mid + 1;
            }
            else {
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
            if(!containsWord(word, commonWords, false)) {
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
        this.setValue(0, 1);
        this.setValue(1, book.getPublished());
        this.setValue(2, book.getNumRatings());
        this.setValue(3, book.getNumPages());
        this.setValue(4, book.getAverageRating());
        isTopics("christian","clergy", 5);
        String[] christianTopics = {"christian", "clergy", "miracle", "inspiration", "faith", "preacher", "pastor", "jesus", "grace",
                                    "redemption", "reverend", "god", "priest", "religion", "saint", "theology"};
        hasDescription(christianTopics, 5);
        isTopic("adventure",6);
        String[] adventureTopics = {"adventure", "action", "police", "kidnap", "marine", "spy", "espionage", "secret", "treasure",
                                    "agent"};
        hasDescription(adventureTopics, 6);
        isTopics("romance", "love", 7);
        String[] romanceTopics = {"romance", "love", "partner", "boyfriend", "girlfriend", "wife", "marriage", "husband", "beautiful",
                                    "relationship", "passion", "happy"};
        hasDescription(romanceTopics, 7);
        isTopic("fantasy",8);
        String[] fantasyTopics = {"fantasy", "kingdom", "dwarf", "witch", "magic", "prince", "king", "queen", "princess", "spell", "evil",
                                    "quest", "journey", "myth"};
        hasDescription(fantasyTopics, 8);
        isTopic("country",9);
        String[] countryTopics = {"china", "india", "ireland", "england", "america", "country", "cowboy", "western"};
        hasDescription(countryTopics, 9);
        isTopic("science fiction", 10);
        String[] scienceFictionTopics = {"science", "scientist", "moon", "robot", "virus", "research", "mission", "clone", "space",
                                        "time", "future", "alien", "teleport", "technology", "planet", "cyborg", "solar system", "nebula"};
        hasDescription(scienceFictionTopics, 10);
        isTopic("essay",11);
        String[] essayTopics = {"essay", "short", "critique", "thought", "idea"};
        hasDescription(essayTopics, 11);
        isTopics("juvenile","children", 12);
        String[] childrenTopics = {"child", "kid", "boy", "girl", "son", "daughter", "school"};
        hasDescription(childrenTopics, 12);
        isTopic("war",13);
        String[] warTopics = {"war", "battle", "army", "soldier", "prisoner", "revolution", "militia", "bomb"};
        hasDescription(warTopics, 13);
        isTopic("christmas",14);
        String[] christmasTopics = {"santa", "cookie", "christmas", "present", "gift", "reindeer"};
        hasDescription(christmasTopics, 14);
        isTopic("fictitious character", 15);
        isTopic("history",16);
        String[] historyTopics = {"history", "classic", "year"};
        hasDescription(historyTopics, 16);
        isTopic("biography",17);
        isTopic("fairy tale", 18);
        String[] fairyTaleTopics = {"fairy", "magic", "faerie"};
        hasDescription(fairyTaleTopics, 18);
        isTopic("nonfiction",19);
        String[] nonfictionTopics = {"true", "nonfiction", "political", "science", "math", "numbers", "history"};
        hasDescription(nonfictionTopics, 19);
        isTopic("philosophy",20);
        String[] philosophyTopics = {"philosophy", "philosopher"};
        hasDescription(philosophyTopics, 20);
        isTopic("travel",21);
        String[] travelTopics = {"travel", "voyage", "journey", "exploration"};
        hasDescription(travelTopics, 21);
        isTopic("fiction",22);
        String[] fictionTopics = {"novel", "fiction"};
        hasDescription(fictionTopics, 22);
        isTopics("mystery", "detective",23);
        String[] mysteryTopics = {"mystery", "detective", "clue", "investigation"};
        hasDescription(mysteryTopics, 23);
        isTopic("language",24);
        isTopic("art", 25);
        String[] artTopics = {"art", "paint", "draw", "music", "sing", "instrument", "canvas"};
        hasDescription(artTopics, 25);
        isTopic("horror", 26);
        String[] horrorTopics = {"ghost", "scary", "scare", "horror", "creepy"};
        hasDescription(horrorTopics, 26);
        String[] award = {"prize", "award", "laureate", "bestselling"};
        hasDescription(award, 27);
        isTopic("drama",28);
        String[] dramaTopics = {"moving", "emotion", "drama", "serious", "play", "impact"};
        hasDescription(dramaTopics, 28);
    }

    public void isTopics(String one, String two, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(one) || category.toLowerCase().contains(two)) {
                this.setValue(index, 1);
            }
        }
    }


    public void isTopic(String topic, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(topic)) {
                this.setValue(index, 1);
            }
        }
    }

    public void hasDescription(String[] genre, int index) {
        double count = this.getValue(index);
        for (String word : genre) {
            if (containsWord(word, importantWords, true)) {
                count++;
            }
        }
        this.setValue(index, count);
    }

    public String toString() {
        String representation = "";
        for (int i = 0; i < this.getSize(); i++) {
            representation += this.getValue(i) + "\n";
        }
        return representation;
    }
}

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
     * A constructor method for a BookVector
     * @param book1 The book which will have a BookVector created about it
     * @throws FileNotFoundException If the common words file is not found, an exception is thrown
     */
    public BookVector(Book book1) throws FileNotFoundException {
        super(22);
        this.book = book1;
        this.commonWords = findCommonWords(); //Loads common words
        this.importantWords = findImportantWords(); //Finds important words (excluding common words) in the book description
        this.categories = book.getCategories();
        buildVector(); //Calls the method which assigns values to the vector
    }

    /**
     * Loads the 150 most common words in the English language in order to make the descriptions
     * of the book more concise
     * @return An ArrayList of Strings of the most common words
     * @throws FileNotFoundException If the file CommonWords is not found, the program throws an exception
     */
    private ArrayList<String> findCommonWords() throws FileNotFoundException {
        String file = "Book_data/CommonWords.csv";
        CSVReader reader = new CSVReader(); //Uses the csv reader class
        FileReader input = new FileReader(file);
        ArrayList<String[]> myEntries = reader.read(input);
        ArrayList<String> words = new ArrayList<>();
        for (String[] tokens : myEntries) {
            words.add(tokens[0].toLowerCase()); //adds the words as lower case
        }
        sortWords(words);
        return words;
    }

    /**
     * Sorts an ArrayList of Strings alphabetically using selection sort
     * @param words The list of words to be sorted
     */
    private void sortWords(ArrayList<String> words) {
        int size = words.size();
        for(int i = 0; i < size - 1; i++) {
            String max = words.get(0);
            int index = 0;
            for (int j = 1; j < size - i; j++) {
                if (words.get(j).compareTo(max) > 0) { //Find the max of the words that have not been sorted
                    max = words.get(j);
                    index = j;
                }
            }
            String temp = words.get(size - 1 - i); //Swap the max word with the correct position at the end of the arraylist
            words.set(index, temp);
            words.set(size - 1 - i, max);
        }
    }

    /**
     * Checks if a sorted ArrayList of Strings contains a given word
     * @param word The word that we want to know if the list contains
     * @param words The sorted list to be searched
     * @param contain If this is true, we will check if the word is contained anywhere the word to be searched
     *                If it is false, only search if the exact word is found.
     * @return True if the word is found, false if else
     */
    private boolean containsWord(String word, ArrayList<String> words, boolean contain) {
        word = word.toLowerCase();
        int low = 0;
        int high = words.size() - 1;
        int mid = (low + high) / 2;
        while (low <= high) { //Uses binary search because the list is sorted
            if (contain && words.get(mid).contains(word)) { //Is the word inside this word (usually plurals)
                return true;
            }
            else if (word.compareTo(words.get(mid)) < 0) {
                high = mid - 1; //Need to search in the first half of the remaining list
            }
            else if (word.compareTo(words.get(mid)) > 0) {
                low = mid + 1; //Need to search in the second half of the remaining list
            }
            else {
                return true; //We found it
            }
            mid = (low + high) / 2; //Update the searching index
        }
        return false;
    }

    /**
     * Pulls only the uncommon and non-repeated words out of the description of a book
     * @return An ArrayList of the Important Words to describe a book
     */
    private ArrayList<String> findImportantWords() {
        String description = book.getDescription().toLowerCase();
        //This removes all punctuation, makes each word lower case, and it is split into an array at spaces
        String[] wordsInDescription = description.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        ArrayList<String> important = removeCommonWords(wordsInDescription); //Removes common words
        sortWords(important); //Sorts the important words
        return important;
    }

    /**
     * Removes common English words from the description
     * @param description The description of the book, to lower case, with no punctuation, and as an array
     * @return An ArrayList of the important words in the description of the book
     */
    private ArrayList<String> removeCommonWords(String[] description) {
        ArrayList<String> leftWords = new ArrayList<>();
        for (String word : description) {
            if(!containsWord(word, commonWords, false)) { //Only want to remove it if the exact word shows up
                if (!leftWords.contains(word)) { //Only add new words
                    leftWords.add(word);
                }
            }
        }
        return leftWords;
    }

    /**
     * An access method for the Book which this vector refers to
     * @return The book which the information for this vector is derived from
     */
    public Book getBook() {
        return book;
    }

    /**
     * An access method for the important words in the book's description of the BookVector
     * @return An ArrayList containing the important words as defined above
     */
    public ArrayList<String> getImportantWords() {
        return this.importantWords;
    }

    /**
     * This assigns values to different indices of the vector based on characteristics of the book.
     * Each category has a different index in the vector, and then the description is searched for words
     * that I think relate to each category.
     */
    private void buildVector() {
        this.setValue(0, 1); //This is a constant
        this.setValue(1, (book.getPublished() * 0.0001)); //These are adjusted to be small values
        this.setValue(2, (book.getNumRatings() * 0.00001));
        this.setValue(3, (book.getNumPages() * 0.001));
        this.setValue(4, (book.getAverageRating() * 0.1));
        isTopics("christian","clergy", 5);
        String[] christianTopics = {"christian", "clergy", "miracle", "inspiration", "faith", "preacher", "pastor", "jesus", "grace",
                                    "redemption", "reverend", "god", "priest", "religion", "saint", "theology"};
        hasDescription(christianTopics, 5);
        isTopic("adventure",6);
        String[] adventureTopics = {"adventure", "action", "police", "kidnap", "marine", "spy", "espionage", "secret", "treasure",
                                    "agent", "mafia", "enemy"};
        hasDescription(adventureTopics, 6);
        isTopics("romance", "love", 7);
        String[] romanceTopics = {"romance", "love", "partner", "boyfriend", "girlfriend", "wife", "marriage", "husband", "beautiful",
                                    "relationship", "passion", "happy", "feeling"};
        hasDescription(romanceTopics, 7);
        isTopic("fantasy",8);
        String[] fantasyTopics = {"fantasy", "kingdom", "dwarf", "witch", "magic", "prince", "king", "queen", "princess", "spell", "evil",
                                    "quest", "journey", "myth", "trilogy"};
        hasDescription(fantasyTopics, 8);
        isTopic("science fiction", 9);
        String[] scienceFictionTopics = {"science", "scientist", "moon", "robot", "virus", "research", "mission", "clone", "space",
                                        "time", "future", "alien", "teleport", "technology", "planet", "cyborg", "solar system", "nebula"};
        hasDescription(scienceFictionTopics, 9);
        isTopic("essay",10);
        String[] essayTopics = {"essay", "short", "critique", "thought", "idea"};
        hasDescription(essayTopics, 10);
        isTopics("juvenile","children", 11);
        String[] childrenTopics = {"child", "kid", "boy", "girl", "son", "daughter", "school"};
        hasDescription(childrenTopics, 11);
        isTopic("war",12);
        String[] warTopics = {"war", "battle", "army", "soldier", "prisoner", "revolution", "militia", "bomb"};
        hasDescription(warTopics, 12);
        isTopic("history",13);
        String[] historyTopics = {"history", "classic", "year"};
        hasDescription(historyTopics, 13);
        isTopic("biography",14);
        isTopic("nonfiction",15);
        String[] nonfictionTopics = {"true", "nonfiction", "political", "science", "math", "numbers", "history",
                                    "philosophy"};
        hasDescription(nonfictionTopics, 15);
        isTopic("fiction",16);
        String[] fictionTopics = {"novel", "fiction", "literature"};
        hasDescription(fictionTopics, 16);
        isTopics("mystery", "detective",17);
        String[] mysteryTopics = {"mystery", "detect", "clue", "investigation", "criminal", "crime", "attorney"};
        hasDescription(mysteryTopics, 17);
        isTopic("art", 18);
        String[] artTopics = {"art", "paint", "draw", "music", "sing", "instrument", "canvas", "costume"};
        hasDescription(artTopics, 18);
        isTopic("horror", 19);
        String[] horrorTopics = {"ghost", "scary", "scare", "horror", "creepy"};
        hasDescription(horrorTopics, 19);
        this.setValue(20, book.getAverageRating() * 0.01 + Math.random() * 0.01);
        String[] award = {"prize", "award", "laureate", "bestselling", "acclaim"};
        hasDescription(award, 20);
        isTopic("drama",21);
        String[] dramaTopics = {"moving", "emotion", "drama", "serious", "play", "impact"};
        hasDescription(dramaTopics, 21);
    }

    /**
     * Checks if the Categories of a Book matches the categories given
     * @param one The first of the two categories to check
     * @param two The second of the two categories to check
     * @param index The index of the vector that corresponds to this category
     */
    private void isTopics(String one, String two, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(one) || category.toLowerCase().contains(two)) {
                //If the category of the book contains one of these words, the vector will be updated to reflect that
                this.setValue(index, book.getAverageRating() * 0.1 + 0.5); //The value will be greater than 0.5 if the book has this topic
                return;
            }
        }
        //Set the value low, and slightly randomized in order to allow the matrix to invertible if this topic is not found
        this.setValue(index, book.getAverageRating() * 0.01 + Math.random() * 0.01);

    }


    /**
     * Checks if a Category of a Book matches the category given
     * @param topic The category that we wish to determine the presence of
     * @param index The corresponding index of the vector to this category
     */
    private void isTopic(String topic, int index) {
        for (String category : categories) {
            if (category.toLowerCase().contains(topic)) {
                //Value is greater than 0.5 at that index of the array
                this.setValue(index, book.getAverageRating() * 0.1 + 0.5);
                return;
            }
        }
        //Value is small and random
        this.setValue(index, book.getAverageRating() * 0.01 + Math.random() * 0.01);
    }

    /**
     * Checks if the description of a book contains words relating to a specific genre/category
     * @param genre The words that I think are present in the descriptions of books relating to this topic
     * @param index The corresponding index of the vector of this topic
     */
    public void hasDescription(String[] genre, int index) {
        double count = this.getValue(index);
        for (String word : genre) {
            if (containsWord(word, importantWords, true)) {
                //Checks if this word is present in the description of the book
                //Increases the value of the vector at that index slightly
                count = count + book.getAverageRating() * 0.05;
            }
        }
        if (count > 1) {
            //The maximum value is 1
            count = 1;
        }
        this.setValue(index, count); //Increases value of the vector at this index
    }

    /**
     * A toString method for the BookVector Class
     * @return A String representation of a Book Vector
     */
    public String toString() {
        String[] topics = {"Constant", "Year Published", "Ratings", "Pages", "Average Rating", "Christian", "Adventure", "Romance", "Fantasy",
                "Science Fiction", "Essay", "Juvenile", "War", "History", "Biography", "Nonfiction", "Fiction", "Mystery", "Art", "Horror",
                "Award", "Drama"};
        String representation = "";
        for (int i = 0; i < this.getSize(); i++) {
            representation += topics[i] + this.getValue(i) + "\n";
        }
        return representation;
    }
}

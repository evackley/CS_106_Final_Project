import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ViewHistory {
    private Stack<String> historyStack;
    private String filename;

    public ViewHistory(String filename) {
        this.filename = filename;
        historyStack = new Stack<>();
    }

    public void addBook(String bookTitle) {
        historyStack.push(bookTitle);
        saveHistory();
    }

    public void clearHistory() {
        while (!historyStack.isEmpty()) {
            historyStack.pop();
        }
        saveHistory();
    }

    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            String bookTitle = historyStack.peek();
            sb.append(bookTitle).append("\n");
        }
        return sb.toString();
    }

    public void loadHistory() {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return;
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                historyStack.push(line);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveHistory() {
        try {
            FileWriter fw = new FileWriter(filename);
            for (int i = historyStack.size() - 1; i >= 0; i--) {
                String bookTitle = historyStack.peek();
                fw.write(bookTitle + "\n");
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

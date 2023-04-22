import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BookDisplay {
    private JPanel rootPanel;
    private JLabel bookImage;
    private JLabel bookInformation;
    private JButton display;

    public BookDisplay() {
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] files = {"Book_data/Book1.csv"};
                Book test = null;
                try {
                    test = Main.randomBook(Main.loadBooks(files));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(test);

                URL url = null;
                try {
                    url = new URL(test.getThumbnail());
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                Image image = null;
                try {
                    image = ImageIO.read(url);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                bookInformation.setText(test.toString());
                ImageIcon icon = new ImageIcon(image);
                bookImage.setIcon(icon);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("BookDisplay");
        frame.setContentPane(new BookDisplay().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }
}

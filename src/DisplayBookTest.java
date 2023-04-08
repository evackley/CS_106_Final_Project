import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayBookTest {

    private JPanel mainPanel;

    public static void main(String[] args) {
        Image image = null;/* w  ww .  ja  v  a 2 s.c o m*/ //Attribution
        try {
            URL url = new URL("http://books.google.com/books/content?id=KQZCPgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
            image = ImageIO.read(url);
        }
        catch (IOException e) {
        }

// Use a label to display the image
        JFrame frame = new JFrame();

        JLabel lblimage = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(lblimage, BorderLayout.CENTER);
        frame.setSize(300, 400);
        frame.setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(lblimage);
// add more components here
        frame.add(mainPanel);
        frame.setVisible(true);
    }

}

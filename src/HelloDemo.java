import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloDemo extends JFrame {
    private JPanel panelMain;
    private JButton btnClick;
    private JTextField txtName;

    public HelloDemo() {
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(btnClick, "Hello, " + txtName.getText());
            }
        });
    }

    public static void main(String[] args) {
        HelloDemo h = new HelloDemo();
        h.setContentPane(h.panelMain);
        h.setTitle("Hello");
        h.setBounds(600,200,200,200);
        //h.setSize(300,400);
        h.setVisible(true);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

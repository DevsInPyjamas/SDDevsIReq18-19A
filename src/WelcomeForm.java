import javax.swing.*;
import java.awt.*;

public class WelcomeForm {
    private JPanel welcomeWindow;
    private JButton cerrarSesionButton;
    private JButton economicoButton;
    private JButton becasButton;
    private JButton adminButton;
    private JLabel bienvenidoLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ACOES App");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(550, 250));
        frame.setContentPane(new WelcomeForm().welcomeWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    WelcomeForm() {

    }

}

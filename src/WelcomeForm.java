import javax.swing.*;
import java.awt.*;

public class WelcomeForm {
    private JButton cerrarSesionButton;
    private JButton economicoButton;
    private JButton becasButton;
    private JButton adminButton;
    private JLabel acoesappLabel;
    private JButton modificarPerfilPropioButton;
    private JPanel welcomePanel;
    private JLabel bienvenidoLabel;

    WelcomeForm() {
        welcomePanel.setSize(550, 250);
        JFrame frame = new JFrame("ACOES App");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(550, 250));
        frame.setContentPane(welcomePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

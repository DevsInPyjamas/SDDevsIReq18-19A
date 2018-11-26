import javax.swing.*;
import db_management.DBManager;

import java.awt.Dimension;
import java.util.List;


public class ACOESApp{
    private JPanel loginWindow;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private DBManager dbManager = new DBManager("localhost", "ACOES");
    private WelcomeForm welcomeForm;
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("ACOES App");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(550, 250));
        frame.setContentPane(new ACOESApp().loginWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ACOESApp() {
        loginWindow.setSize(550, 250);
        entrarButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Entrar")) {
                List<Object[]> queryTuples = dbManager.
                        select("select * from Usuario where email = '" + textField1.getText() + "';");
                try {
                    if ((queryTuples.get(0))[2].equals(passwordField1.getText())) {
                        new WelcomeForm();
                        frame.dispose();
                    } else {
                        wrongLogInDialog();
                    }

                } catch (Exception exception) {
                    wrongLogInDialog();
                }
            }
        });
    }

    private void wrongLogInDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("La combinación de email y contraseña no es correcta");
        dialog.setBounds(400, 400, 300, 200);
        dialog.setMinimumSize(new Dimension(550, 150));
        dialog.setContentPane(new JLabel("         Si no recuerda su combinación, contacte con uno de sus administradores."));
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setVisible(true);
    }
}

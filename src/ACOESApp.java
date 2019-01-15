import javax.swing.*;
import db_management.DBManager;
import db_management.Usuario;

import java.awt.Dimension;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ACOESApp{
    private JPanel loginWindow;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private DBManager dbManager = new DBManager();
    private static JFrame frame;

    public static void main(String[] args) {
        new ACOESApp();
    }

    ACOESApp() {
        frame = new JFrame("ACOES App");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(550, 250));
        frame.setContentPane(loginWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        loginWindow.setSize(550, 250);
        entrarButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Entrar")) {
                List<Object[]> queryTuples = dbManager.
                        select("select * from Usuario where email = '" + textField1.getText() + "';");
                    try {
                        if ((queryTuples.get(0))[2].equals(passwordField1.getText())) {
                            new WelcomeForm(new Usuario((String) queryTuples.get(0)[0]));
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos no son correctos");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos no son correctos");
                        ex.printStackTrace();
                    }
                }
        });
    }

}

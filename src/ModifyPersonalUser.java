import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ModifyPersonalUser {
    private JPanel modificarUsuarioPersonalPanel;
    private JButton backButton;
    private JLabel modificarUsuarioLabel;
    private JButton modificarButton;
    private JPasswordField contraseñaAntiguaTextField;
    private JPasswordField nuevaContraseñaTextField;
    private JPasswordField repetirContraseñaTextField;
    private Usuario loggedUser;

    ModifyPersonalUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        modificarUsuarioPersonalPanel.setSize(700, 400);
        JFrame frame = new JFrame("Modificar Contraseña");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarUsuarioPersonalPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener(e -> {
            new WelcomeForm(loggedUser);
            frame.dispose();
        });
        modificarButton.addActionListener(e -> {
            if (loggedUser.getPassword().equals(contraseñaAntiguaTextField.getText())) {
                if (nuevaContraseñaTextField.getText().equals(repetirContraseñaTextField.getText())) {
                    loggedUser.setPassword(nuevaContraseñaTextField.getText());
                    loggedUser.save();
                    JOptionPane.showMessageDialog(new JFrame(), "Contraseña cambiada con éxito.");
                    new ACOESApp();
                    frame.dispose();
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Las contraseñas no coinciden.");
            } else
                JOptionPane.showMessageDialog(new JFrame(), "Contraseña antigua incorrecta.");
        });
    }
}

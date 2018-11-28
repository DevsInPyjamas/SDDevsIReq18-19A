import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ChildData {
    private JPanel childDataPanel;
    private JButton backButton;
    private JButton modifyKidButton;
    private JButton generateFichaKidButton;
    private JButton deleteKidButton;
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField fechaNacimientoField;
    private JTextField nombreMadreField;
    private JTextField nombrePadreField;
    private JEditorPane historialPane;
    private JPanel textFieldsPanel;
    private Usuario loggedUser;

    ChildData(Usuario loggedUser) {
        generateFichaKidButton.setVisible(false);
        this.loggedUser = loggedUser;
        childDataPanel.setSize(700, 250);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(childDataPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
    }
}

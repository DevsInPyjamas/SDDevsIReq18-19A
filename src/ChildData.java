import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ChildData {
    private JPanel modificarNinoPanel;
    private JButton modifyKidButton;
    private JButton subirFotoButton;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField nombreMadreTextField;
    private JTextField nombrePadreTextField;
    private JEditorPane historialEditorPane;
    private JButton backButton;
    private Usuario loggedUser;


    ChildData(Usuario loggedUser, int idChild) {
        this.loggedUser = loggedUser;
        modificarNinoPanel.setSize(700, 400);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarNinoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven child = new Joven(Integer.toString(idChild));
        nombreTextField.setText(child.getNombre());
        fechaNacimientoTextField.setText(child.getFechaNacimiento().toString());
        apellidoTextField.setText(child.getApellidos());
        nombreMadreTextField.setText(child.getNombreMadre());
        nombrePadreTextField.setText(child.getNombrePadre());
        historialEditorPane.setText(child.getHistorial());
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        modifyKidButton.addActionListener((e) -> {

        });
    }
}

import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ChildData {
    private JPanel childDataServer;
    private JButton modifyKidButton;
    private JButton subirFotoButton;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField nombreMadreTextField;
    private JTextField nombrePadreTextField;
    private JEditorPane historialEditorPane;
    private JButton backButton;
    private JComboBox modificarProyectoComboBox;
    private JTextField modificarNombre;
    private JTextField modificarApellidos;
    private JTextField modificarFechaNacimiento;
    private JTextField modificarNombreMadre;
    private JTextField modificarNombrePadre;
    private JEditorPane modificarHistorialPane;
    private JTextField cambiarProyecto;
    private JButton generateFichaButton;
    private JButton actualizarButton;
    private Usuario loggedUser;


    ChildData(Usuario loggedUser, String idChild) {
        this.loggedUser = loggedUser;
        generateFichaButton.setVisible(false); // TODO algun dia lo quitaremos!!!!
        displayButtons(false);
        childDataServer.setSize(700, 400);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(childDataServer);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven child = new Joven(idChild);
        nombreTextField.setText(child.getNombre());
        fechaNacimientoTextField.setText(child.getFechaNacimiento());
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
            childDataServer.setSize(1400, 400);
            frame.setMinimumSize(new Dimension(1200, 400));
            displayButtons(true);
        });
    }

    private void displayButtons(boolean siONo){
        //modificarProyectoComboBox.setVisible(siONo);
        modificarNombre.setVisible(siONo);
        modificarApellidos.setVisible(siONo);
        modificarFechaNacimiento.setVisible(siONo);
        modificarNombreMadre.setVisible(siONo);
        modificarNombrePadre.setVisible(siONo);
        modificarHistorialPane.setVisible(siONo);
    }
}

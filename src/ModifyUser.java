import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ModifyUser {
    private JPanel modificarUsuarioPanel;
    private JButton backButton;
    private JButton borrarUsuarioButton;
    private JLabel modificarUsuarioLabel;
    private JLabel emailLabel;
    private JTextField emailField;
    private JPanel permisosPanel;
    private JRadioButton coordinadorGeneralProyectosRadioButton;
    private JRadioButton responsableEconomicoRadioButton;
    private JRadioButton coordinadorProyectoRadioButton;
    private JRadioButton responsableGeneralProyectosRadioButton;
    private JRadioButton becasRadioButton;
    private JRadioButton economicoRadioButton;
    private JButton modificarButton;
    private JRadioButton usuarioRasoRadioButton;
    private JRadioButton gestorEspanaRadioButton;
    private JRadioButton coordinadorAsociaciónEspanaRadioButton;
    private JRadioButton coordinadorACOESEspanaRadioButton;
    private Usuario loggedUser;

    ModifyUser(Usuario loggedUser, String emailUser) {
        this.loggedUser = loggedUser;
        modificarUsuarioPanel.setSize(700, 500);
        JFrame frame = new JFrame("Informacion del usuario");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(modificarUsuarioPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Usuario modUser = new Usuario(emailUser);
        emailField.setText(modUser.getEmail());
        setRadioButtonSelected(modUser);
    }

    private void setRadioButtonSelected(Usuario modUser) {
        if (coordinadorGeneralProyectosRadioButton.getText().equals(modUser.getRol().getNombre())) {
            coordinadorGeneralProyectosRadioButton.setSelected(true);
        } else if (responsableEconomicoRadioButton.getText().equals(modUser.getRol().getNombre())) {
            responsableEconomicoRadioButton.setSelected(true);
        } else if (coordinadorProyectoRadioButton.getText().equals(modUser.getRol().getNombre())) {
            coordinadorProyectoRadioButton.setSelected(true);
        } else if (responsableGeneralProyectosRadioButton.getText().equals(modUser.getRol().getNombre())) {
            responsableGeneralProyectosRadioButton.setSelected(true);
        } else if (becasRadioButton.getText().equals(modUser.getRol().getNombre())) {
            becasRadioButton.setSelected(true);
        } else if (economicoRadioButton.getText().equals(modUser.getRol().getNombre())) {
            economicoRadioButton.setSelected(true);
        } else if (usuarioRasoRadioButton.getText().equals(modUser.getRol().getNombre())) {
            usuarioRasoRadioButton.setSelected(true);
        } else if (gestorEspanaRadioButton.getText().equals(modUser.getRol().getNombre())) {
            gestorEspanaRadioButton.setSelected(true);
        } else if (coordinadorAsociaciónEspanaRadioButton.getText().equals(modUser.getRol().getNombre())) {
            coordinadorAsociaciónEspanaRadioButton.setSelected(true);
        } else if (coordinadorACOESEspanaRadioButton.getText().equals(modUser.getRol().getNombre())) {
            coordinadorACOESEspanaRadioButton.setSelected(true);
        }
    }
}

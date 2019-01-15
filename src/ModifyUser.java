import db_management.*;

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
    private JButton actualizarButton;
    private JTextField usuarioTextField;
    private JTextField nombreTextField;
    private JComboBox proyectoComboBox;
    private JComboBox asociacionComboBox;
    private Usuario loggedUser;
    private boolean modifying = false;
    private DBManager dbManager = new DBManager();

    ModifyUser(Usuario loggedUser, String emailUser) {
        this.loggedUser = loggedUser;
        displayButtons(false);
        emailField.setEditable(false);
        modificarUsuarioPanel.setSize(700, 600);
        JFrame frame = new JFrame("Informacion del usuario");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(modificarUsuarioPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        proyectoComboBox.addItem(null);
        for (Object[] tuple : dbManager.select("select isDeleted, nombre from Proyecto;")) {
            if (!(boolean) tuple[0]) {
                proyectoComboBox.addItem(tuple[1]);
            }
        }
        asociacionComboBox.addItem(null);
        for (Object[] tuple : dbManager.select("select isDeleted, nombre from Asociacion")) {
            if (!(boolean) tuple[0]) {
                asociacionComboBox.addItem(tuple[1]);
            }
        }
        Usuario modUser = new Usuario(emailUser);
        emailField.setText(modUser.getEmail());
        nombreTextField.setText(modUser.getNombre());
        usuarioTextField.setText(modUser.getUsuario());
        proyectoComboBox.setSelectedItem((modUser.getProyecto() != null) ? modUser.getProyecto().getNombre() : null);
        asociacionComboBox.setSelectedItem((modUser.getAsociacion() != null) ? modUser.getAsociacion().getNombre() : null);
        setRadioButtonSelected(modUser);
        backButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                if (modifying) {
                    new ModifyUser(loggedUser, modUser.getEmail());
                    frame.dispose();
                } else {
                    new SearchUser(loggedUser);
                    frame.dispose();
                }
            }
        });
        modificarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar")) {
                modifying = true;
                displayButtons(true);
            }
        });
        actualizarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Actualizar")) {
                this.modifiyingUserDB(modUser);
                JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el usuario...");
                displayButtons(false);
                emailField.setText(modUser.getEmail());
                nombreTextField.setText(modUser.getNombre());
                usuarioTextField.setText(modUser.getUsuario());
                proyectoComboBox.setSelectedItem((modUser.getProyecto() != null) ? modUser.getProyecto().getNombre() : null);
                asociacionComboBox.setSelectedItem((modUser.getAsociacion() != null) ? modUser.getAsociacion().getNombre() : null);
                modifying = false;
            }
        });
        borrarUsuarioButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Borrar Usuario")) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quiere eliminar el usuario?", "Confirmación de Borrado",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_NO_OPTION) {
                    modUser.setIsDeleted(true);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado el usuario de la base de datos");
                    new SearchUser(loggedUser);
                    frame.dispose();
                }
            }
        });

    }

    private void displayButtons(boolean siONo) {
        nombreTextField.setEditable(siONo);
        usuarioTextField.setEditable(siONo);
        proyectoComboBox.setEnabled(siONo);
        asociacionComboBox.setEnabled(siONo);
        actualizarButton.setVisible(siONo);
        coordinadorGeneralProyectosRadioButton.setEnabled(siONo);
        responsableEconomicoRadioButton.setEnabled(siONo);
        coordinadorProyectoRadioButton.setEnabled(siONo);
        responsableGeneralProyectosRadioButton.setEnabled(siONo);
        becasRadioButton.setEnabled(siONo);
        economicoRadioButton.setEnabled(siONo);
        usuarioRasoRadioButton.setEnabled(siONo);
        gestorEspanaRadioButton.setEnabled(siONo);
        coordinadorAsociaciónEspanaRadioButton.setEnabled(siONo);
        coordinadorACOESEspanaRadioButton.setEnabled(siONo);
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

    private void modifiyingUserDB(Usuario modUser) {
        modUser.setNombre(nombreTextField.getText());
        modUser.setUsuario(usuarioTextField.getText());
        if (modificarRol() != -1) {
            new Rol(modificarRol());
        }
        if ((asociacionComboBox.getSelectedItem() != null)) {
            new Asociacion(
                    (int) dbManager.select("select id from Asociacion where nombre like '%" +
                            asociacionComboBox.getSelectedItem() + "%';").get(0)[0]
            );
        }
        if (proyectoComboBox.getSelectedItem() != null) {
            new Proyecto(
                    (int) dbManager.select("select id from Proyecto where nombre like '%" +
                            proyectoComboBox.getSelectedItem() + "%'").get(0)[0]
            );
        }
    }

    private int modificarRol() {
        if (coordinadorGeneralProyectosRadioButton.isSelected()) {
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorGeneralProyectosRadioButton.getText() + "%'").get(0)[0];
        } else if (responsableEconomicoRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    responsableEconomicoRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorProyectoRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorProyectoRadioButton.getText() + "%'").get(0)[0];
        } else if (responsableGeneralProyectosRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    responsableGeneralProyectosRadioButton.getText() + "%'").get(0)[0];
        } else if (becasRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    becasRadioButton.getText() + "%'").get(0)[0];
        } else if (economicoRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    economicoRadioButton.getText() + "%'").get(0)[0];
        } else if (usuarioRasoRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    usuarioRasoRadioButton.getText() + "%'").get(0)[0];
        } else if (gestorEspanaRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    gestorEspanaRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorAsociaciónEspanaRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorACOESEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorAsociaciónEspanaRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorACOESEspanaRadioButton.isSelected()) {
            coordinadorGeneralProyectosRadioButton.setSelected(false);
            responsableEconomicoRadioButton.setSelected(false);
            coordinadorProyectoRadioButton.setSelected(false);
            responsableGeneralProyectosRadioButton.setSelected(false);
            becasRadioButton.setSelected(false);
            economicoRadioButton.setSelected(false);
            usuarioRasoRadioButton.setSelected(false);
            gestorEspanaRadioButton.setSelected(false);
            coordinadorAsociaciónEspanaRadioButton.setSelected(false);
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorACOESEspanaRadioButton.getText() + "%'").get(0)[0];
        }
        return -1;
    }
}

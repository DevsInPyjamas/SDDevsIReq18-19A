import db_management.*;

import javax.swing.*;
import java.awt.Dimension;
import java.util.List;

public class NewUser {
    private JPanel nuevoUserPanel;
    private JButton backButton;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField userField;
    private JComboBox asociacionComboBox;
    private JComboBox proyectoComboBox;
    private JButton anadirButton;
    private JRadioButton coordinadorGeneralRadioButton;
    private JRadioButton responsabeGeneralProyectoRadioButton;
    private JRadioButton coordinadorGeneralDeProyectosRadioButton;
    private JRadioButton responsableEconomicoRadioButton;
    private JRadioButton becasRadioButton;
    private JRadioButton economicoRadioButton;
    private JRadioButton usuarioRasoRadioButton;
    private JRadioButton gestorEspanaRadioButton;
    private JRadioButton coordinadorAsociacionEspanaRadioButton;
    private JRadioButton coordinadorACOESEspanaRadioButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    NewUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevoUserPanel.setSize(700, 600);
        JFrame frame = new JFrame("Nuevo Usuario");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevoUserPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuplesP, queryTuplesA;
        if (!loggedUser.getRol().isAdmin()) {
            queryTuplesP = dbManager.select("select p.isDeleted, p.nombre from Proyecto p inner join Usuario u on " +
                    "u.pertenece_proyecto = p.id where u.email = '" + loggedUser.getEmail() + "';");
            queryTuplesA = dbManager.select("select a.isDeleted, a.nombre from Asociacion a inner join " +
                    "Usuario u on u.pertenece_asociacion = a.id where u.email = '" + loggedUser.getEmail() + "';");
        } else {
            queryTuplesP = dbManager.select("select isDeleted, nombre from proyecto;");
            queryTuplesA = dbManager.select("select isDeleted, nombre from asociacion;");
        }
        proyectoComboBox.addItem(null);
        for (Object[] tuple : queryTuplesP) {
            if (!(boolean) tuple[0]) {
                proyectoComboBox.addItem(tuple[1]);
            }
        }
        asociacionComboBox.addItem(null);
        for (Object[] tuple : queryTuplesA) {
            if (!(boolean) tuple[0]) {
                asociacionComboBox.addItem(tuple[1]);
            }
        }
        backButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atrás")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
        anadirButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Añadir")) {
                if (asociacionComboBox == null || proyectoComboBox == null || emailField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
                } else {
                    Usuario user = new Usuario();
                    user.setEmail(emailField.getText());
                    user.setNombre(nameField.getText());
                    user.setUsuario(userField.getText());
                    user.setRol(new Rol(modificarRol()));
                    if (!user.getRol().isSuperSpanishAdmin() && !user.getRol().isSuperAdmin()) {
                        user.setAsociacion(new Asociacion((Integer) dbManager.select("select id from asociacion " +
                                "where nombre = '" + asociacionComboBox.getSelectedItem().toString() + "';").get(0)[0]));
                        user.setProyecto(new Proyecto((Integer) dbManager.select("select id from proyecto " +
                                "where nombre = '" + proyectoComboBox.getSelectedItem().toString() + "';").get(0)[0]));
                    }
                    user.save();
                    JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                    new AdminArea(loggedUser);
                    frame.dispose();
                }
            }
        });
    }

    private int modificarRol() {
        if (coordinadorGeneralRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorGeneralRadioButton.getText() + "%'").get(0)[0];
        } else if (responsabeGeneralProyectoRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    responsabeGeneralProyectoRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorGeneralDeProyectosRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorGeneralDeProyectosRadioButton.getText() + "%'").get(0)[0];
        } else if (responsableEconomicoRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    responsableEconomicoRadioButton.getText() + "%'").get(0)[0];
        } else if (becasRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    becasRadioButton.getText() + "%'").get(0)[0];
        } else if (economicoRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    economicoRadioButton.getText() + "%'").get(0)[0];
        } else if (usuarioRasoRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    usuarioRasoRadioButton.getText() + "%'").get(0)[0];
        } else if (gestorEspanaRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    gestorEspanaRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorAsociacionEspanaRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorAsociacionEspanaRadioButton.getText() + "%'").get(0)[0];
        } else if (coordinadorACOESEspanaRadioButton.isSelected()) {
            return (int) dbManager.select("select id from Rol where nombre like '%" +
                    coordinadorACOESEspanaRadioButton.getText() + "%'").get(0)[0];
        }
        return -1;
    }
}

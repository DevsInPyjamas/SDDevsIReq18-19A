import db_management.Asociacion;
import db_management.DBManager;
import db_management.Usuario;
import db_management.Socio;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class NewMember {
    private JPanel nuevoMiembroPanel;
    private JButton backButton;
    private JLabel nuevoSocioLabel;
    private JLabel nombreLabel;
    private JTextField nombreField;
    private JLabel apellidosLabel;
    private JTextField apellidosField;
    private JLabel fechaNacimientoLabel;
    private JTextField fechaNacimientoField;
    private JLabel telefonoLabel;
    private JTextField telefonoField;
    private JLabel direccionLabel;
    private JTextField direccionField;
    private JLabel poblacionLabel;
    private JTextField poblacionField;
    private JLabel provinciaLabel;
    private JTextField provinciaField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JButton anadirButton;
    private JLabel asociacionLabel;
    private JComboBox asociacionComboBox;
    private JLabel dniLabel;
    private JTextField dniField;
    private JLabel codigoPostalLabel;
    private JTextField codigopostalField;
    private JLabel mensualidadLabel;
    private JTextField mensualidadField;
    Usuario loggedUser;

    NewMember(Usuario loggedUser){
        this.loggedUser = loggedUser;
        nuevoMiembroPanel.setSize(700,250);
        JFrame frame = new JFrame("Información Miembro");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevoMiembroPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuples;
        DBManager dbManager = new DBManager();
        if(!loggedUser.getRol().isAdmin()) {
            queryTuples = dbManager.select("select a.nombre from Asociacion a inner join " +
                    "Usuario u on u.pertenece_asociacion = a.id where u.email = '" + loggedUser.getEmail() + "';");
        } else {
            queryTuples = dbManager.select("select nombre from Asociacion");
        }
        for(Object[] tuple : queryTuples) {
            asociacionComboBox.addItem(tuple[0]);
        }
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        anadirButton.addActionListener((e) -> {
            if(checkIfThereAreNotBlankFields()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                Socio soc = new Socio();
                soc.setNombre(nombreField.getText());
                soc.setApellidos(apellidosField.getText());
                soc.setDni(dniField.getText());
                soc.setFechaNacimiento((fechaNacimientoField.getText().isEmpty()) ? null : fechaNacimientoField.getText());
                soc.setTelefono(Integer.parseInt(telefonoField.getText()));
                soc.setDireccion(direccionField.getText());
                soc.setPoblacion(poblacionField.getText());
                soc.setProvincia(provinciaField.getText());
                soc.setCodigoPostal(Integer.parseInt(codigopostalField.getText()));
                soc.setMensualidad(Double.parseDouble(mensualidadField.getText()));
                soc.setEmail(emailField.getText());

                int idAsociacion = (int) dbManager.select("select id from Asociacion where nombre like '" +
                        asociacionComboBox.getSelectedItem().toString() + "';").get(0)[0];

                soc.setAsociacion(new Asociacion(idAsociacion));

                soc.save();

                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
    }

    private boolean checkIfThereAreNotBlankFields() {
        return dniField.getText().isEmpty() || nombreField.getText().isEmpty();
    }
}

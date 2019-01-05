import javax.swing.*;

import db_management.Asociacion;
import db_management.DBManager;
import db_management.Usuario;
import db_management.Socio;

import java.awt.*;

public class ModifyMember {
    private JPanel modificarSocioPanel;
    private JButton backButton;
    private JButton borrarSocioButton;
    private JLabel modificarSocioLabel;
    private JLabel nombreLabel;
    private JTextField nombreField;
    private JLabel apellidosLabel;
    private JTextField apellidosField;
    private JLabel fechaNacimientoLabel;
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
    private JButton modificarButton;
    private JTextField fechaNacimientoField;
    private JTextField dniField;
    private JLabel dniLabel;
    private JLabel asociacionLabel;
    private JComboBox asociacionComboBox;
    private JLabel mensualidadLabel;
    private JTextField mensualidadField;
    private JButton actualizarButton;
    private JLabel codigoPostalLabel;
    private JTextField codigoPostalField;
    private Usuario loggedUser;
    private boolean modifying = false;
    private DBManager dbManager = new DBManager();

    ModifyMember(Usuario loggedUser, Integer memberId){
        this.loggedUser = loggedUser;
        displayButtons(false);
        modificarSocioPanel.setSize(700, 400);
        JFrame frame = new JFrame("Información Proyecto");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarSocioPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Socio soc = new Socio(memberId);
        nombreField.setText(soc.getNombre());
        apellidosField.setText(soc.getApellidos());
        dniField.setText(soc.getDni());
        telefonoField.setText(Integer.toString(soc.getTelefono()));
        direccionField.setText(soc.getDireccion());
        poblacionField.setText(soc.getPoblacion());
        provinciaField.setText(soc.getProvincia());
        fechaNacimientoField.setText(soc.getFechaNacimiento());
        emailField.setText(soc.getEmail());

        backButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                if (modifying) {
                    try {
                        new ModifyMember(loggedUser, soc.getId());
                        frame.dispose();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error: " + e1.getMessage());                    }
                } else {
                    new SearchMember();
                    frame.dispose();
                }
            }
        });

        modificarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar Socio")) {
                modifying = true;
                frame.setMinimumSize(new Dimension(700, 400));
                displayButtons(true);
            }
        });

        actualizarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Actualizar")) {
                try {
                    this.modifyMemberDB(soc);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el proyecto...");
                } catch (Exception p) {
                    JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " +
                            p.getMessage());
                }
                displayButtons(false);
                modificarSocioPanel.setSize(700, 400);
                frame.setMinimumSize(new Dimension(700, 400));
                nombreField.setText(soc.getNombre());
                apellidosField.setText(soc.getApellidos());
                dniField.setText(soc.getDni());
                telefonoField.setText(Integer.toString(soc.getTelefono()));
                direccionField.setText(soc.getDireccion());
                poblacionField.setText(soc.getPoblacion());
                provinciaField.setText(soc.getProvincia());
                codigoPostalField.setText(Integer.toString(soc.getCodigoPostal()));
                fechaNacimientoField.setText(soc.getFechaNacimiento());
                emailField.setText(soc.getEmail());

                modifying = false;
            }
        });

        borrarSocioButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Borrar Socio")) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quiere eliminar el socio?", "Confirmación de Borrado",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_NO_OPTION) {
                    soc.setIsDeleted(true);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado el socio de la base de datos");
                    new SearchMember();
                    frame.dispose();
                }
            }
        });
    }

    private void displayButtons(boolean siONo){
        nombreField.setEditable(siONo);
        apellidosField.setEditable(siONo);
        dniField.setEditable(siONo);
        telefonoField.setEditable(siONo);
        direccionField.setEditable(siONo);
        poblacionField.setEditable(siONo);
        provinciaField.setEditable(siONo);
        codigoPostalField.setEditable(siONo);
        fechaNacimientoField.setEditable(siONo);
        emailField.setEditable(siONo);
        asociacionComboBox.setEditable(siONo);
        mensualidadField.setEditable(siONo);
    }

    private void modifyMemberDB(Socio soc){
        soc.setNombre(nombreField.getText());
        soc.setApellidos(apellidosField.getText());
        soc.setDni(dniField.getText());
        soc.setFechaNacimiento(fechaNacimientoField.getText());
        soc.setTelefono(Integer.parseInt(telefonoField.getText()));
        soc.setDireccion(direccionField.getText());
        soc.setPoblacion(poblacionField.getText());
        soc.setProvincia(provinciaField.getText());
        soc.setCodigoPostal(Integer.parseInt(codigoPostalField.getText()));
        soc.setMensualidad(Double.parseDouble(mensualidadField.getText()));
        soc.setEmail(emailField.getText());

        int idAsociacion = (int) dbManager.select("select id from Asociacion where nombre like '" +
                asociacionComboBox.getSelectedItem().toString() + "';").get(0)[0];

        soc.setAsociacion(new Asociacion(idAsociacion));

        soc.save();
    }
}

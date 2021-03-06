import db_management.DBManager;
import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class NewChild {
    private JPanel nuevoNinoPanel;
    private JButton backButton;
    private JButton subirFotoButton;
    private JButton addKidButton;
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField nombreMadreField;
    private JTextField nombrePadreField;
    private JTextField fechaNacimientoFIeld;
    private JEditorPane historialPane;
    private JLabel newKid;
    private JLabel nombreLabel;
    private JLabel apellidosLabel;
    private JLabel fechaNacimientoLabel;
    private JLabel nombreMadreLabel;
    private JLabel nombrePadreLabel;
    private JLabel historialLabel;
    private JLabel anadirProyecoLabel;
    private JComboBox proyectoComboBox;
    private JTextField comunidadTextField;
    private JComboBox generoComboBox;
    private JTextField fechaEntradaTextFIeld;
    private JTextField becaTextField;
    private JTextField notaMediaTextFIeld;
    private JEditorPane observacionesPane;
    private JTextField observacionesTextField;
    Usuario loggedUser;

    NewChild(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevoNinoPanel.setSize(700, 250);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevoNinoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuples;
        DBManager dbManager = new DBManager();
        if(!loggedUser.getRol().isAdmin()) {
            queryTuples = dbManager.select("select p.nombre from Proyecto p inner join " +
                    "Usuario u on u.pertenece_proyecto = p.id and u.email = '" + loggedUser.getEmail() + "';");
        } else {
            queryTuples = dbManager.select("select nombre from Proyecto");
        }
        for(Object[] tuple : queryTuples) {
            proyectoComboBox.addItem(tuple[0]);
        }
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        addKidButton.addActionListener((e) -> {
            // Solution adapted from https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
            if(checkIfThereAreNotBlankFields()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                new Joven(nombreField.getText(), apellidosField.getText(), fechaNacimientoFIeld.getText(),
                        nombreMadreField.getText(), nombrePadreField.getText(), historialPane.getText(),
                        comunidadTextField.getText(), Objects.requireNonNull(generoComboBox.getSelectedItem()).toString(),
                        observacionesPane.getText(), becaTextField.getText(),
                        Float.parseFloat(notaMediaTextFIeld.getText()));

                int idProyecto = (int) dbManager.select("select id from Proyecto where nombre like '" +
                        proyectoComboBox.getSelectedItem() + "';").get(0)[0];
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String entradaToStr = dtf.format(LocalDate.now());
                int idJoven = (int) dbManager.select("select max(id) from Jovenes where nombre like '" +
                        nombreField.getText() + "' and apellidos like '" + apellidosField.getText() + "';").get(0)[0];
                dbManager.execute("insert into Accion(id_proyecto, id_joven, fecha_entrada)" +
                        " values ('" + idProyecto + "', '" + idJoven + "', '" + entradaToStr + "');");

                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
    }

    private boolean checkIfThereAreNotBlankFields() {
        return fechaNacimientoFIeld.getText().isEmpty() ||
                apellidosField.getText().isEmpty() || nombreField.getText().isEmpty();
    }

}

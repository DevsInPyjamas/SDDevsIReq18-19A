import db_management.DBManager;
import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ChildData {
    private JPanel childDataServer;
    private JButton modifyKidButton;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField nombreMadreTextField;
    private JTextField nombrePadreTextField;
    private JEditorPane historialEditorPane;
    private JButton backButton;
    private JButton subirFotoButton;
    private JComboBox cambiarProyectoComboBox;
    private JButton generateFichaButton;
    private JButton actualizarButton;
    private JButton deleteKid;
    private JTextField comunidadTextField;
    private JTextField fechaEntradaTextBox;
    private JTextField fechaSalidaTextBox;
    private JTextField becaTextField;
    private JTextField notaMediaTextField;
    private JEditorPane observacionesPane;
    private JComboBox generoComboBox;
    private Usuario loggedUser;
    private boolean modifying = false;
    private DBManager dbManager = new DBManager();

    ChildData(Usuario loggedUser, String idChild) {
        this.loggedUser = loggedUser;
        List<Object[]> queryTuples;
        if (!loggedUser.getRol().isAdmin()) {
            queryTuples = dbManager.select("select p.nombre from Proyecto p inner join " +
                    "Usuario u on u.pertenece_proyecto = p.id and u.email = '" + loggedUser.getEmail() + "';");
        } else {
            queryTuples = dbManager.select("select nombre from Proyecto");
        }
        for (Object[] tuple : queryTuples) {
            cambiarProyectoComboBox.addItem(tuple[0]);
        }
        generateFichaButton.setVisible(false); // TODO algun dia lo quitaremos!!!!
        displayButtons(false);
        childDataServer.setSize(700, 600);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
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
        comunidadTextField.setText(child.getDatosComunidad());
        generoComboBox.setSelectedItem(child.getGenero());
        fechaEntradaTextBox.setText(child.getFechaNacimiento());
        fechaSalidaTextBox.setText(child.getFechaBaja());
        becaTextField.setText(child.getBeca());
        notaMediaTextField.setText(String.valueOf(child.getNotaMedia()));
        observacionesPane.setText(child.getObservaciones());
        String text = (String) dbManager.select("select p.nombre from Proyecto p, Accion a where a.id_proyecto = p.id " +
                "and a.id_joven = '" + child.getId() + "';").get(0)[0];
        cambiarProyectoComboBox.setSelectedItem(text);
        backButton.addActionListener((e) -> {
            if (e.getActionCommand().equals("Atrás")) {
                if (modifying) {
                    new ChildData(loggedUser, String.valueOf(child.getId()));
                    frame.dispose();
                } else {
                    new SearchChild(loggedUser);
                    frame.dispose();
                }
            }
        });
        modifyKidButton.addActionListener((e) -> {
            modifying = true;
            childDataServer.setSize(750, 700);
            frame.setMinimumSize(new Dimension(750, 700));
            displayButtons(true);
        });
        actualizarButton.addActionListener((e) -> {
            try {
                this.modifyKidDB(child);
                JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el niño...");
            } catch (Exception p) {
                JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " + p.getMessage());
            }
            displayButtons(false);
            childDataServer.setSize(700, 600);
            frame.setMinimumSize(new Dimension(700, 650));
            nombreTextField.setText(child.getNombre());
            fechaNacimientoTextField.setText(child.getFechaNacimiento());
            apellidoTextField.setText(child.getApellidos());
            nombreMadreTextField.setText(child.getNombreMadre());
            nombrePadreTextField.setText(child.getNombrePadre());
            historialEditorPane.setText(child.getHistorial());
            comunidadTextField.setText(child.getDatosComunidad());
            generoComboBox.setSelectedItem(child.getGenero());
            fechaEntradaTextBox.setText(child.getFechaEntrada());
            fechaSalidaTextBox.setText(child.getFechaBaja());
            becaTextField.setText(child.getBeca());
            notaMediaTextField.setText(String.valueOf(child.getNotaMedia()));
            observacionesPane.setText(child.getObservaciones());
            String text1 = (String) dbManager.select("select p.nombre from Proyecto p, Accion a where a.id_proyecto = p.id " +
                    "and a.id_joven = '" + child.getId() + "';").get(0)[0];
            cambiarProyectoComboBox.setSelectedItem(text1);
            modifying = false;
        });
        deleteKid.addActionListener((e) -> {
            /*
             *  Code solution adapted from https://stackoverflow.com/questions/8689122/joptionpane-yes-no-options-confirm-dialog-box-issue
             * */
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que quiere eliminar al niño?", "Confirmación de Borrado",
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                child.setIsDeleted(true);
                JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado al niño de la base de datos");
                new SearchChild(loggedUser);
                frame.dispose();
            }
        });
    }

    private void displayButtons(boolean siONo) {
        nombreTextField.setEditable(siONo);
        apellidoTextField.setEditable(siONo);
        actualizarButton.setVisible(siONo);
        fechaNacimientoTextField.setEditable(siONo);
        nombreMadreTextField.setEditable(siONo);
        nombrePadreTextField.setEditable(siONo);
        historialEditorPane.setEditable(siONo);
        cambiarProyectoComboBox.setEnabled(siONo);
        comunidadTextField.setEditable(siONo);
        generoComboBox.setEnabled(siONo);
        fechaEntradaTextBox.setEditable(siONo);
        fechaSalidaTextBox.setEditable(siONo);
        becaTextField.setEditable(siONo);
        notaMediaTextField.setEditable(siONo);
        observacionesPane.setEditable(siONo);
    }

    private void modifyKidDB(Joven kid) {
        if (!nombreTextField.getText().equals(kid.getNombre())) {
            kid.setNombre(nombreTextField.getText());
        }
        if (!apellidoTextField.getText().equals(kid.getApellidos())) {
            kid.setApellidos(apellidoTextField.getText());
        }
        if (!nombrePadreTextField.getText().equals(kid.getNombrePadre())) {
            kid.setNombrePadre(nombrePadreTextField.getText());
        }
        if (!nombreMadreTextField.getText().equals(kid.getNombreMadre())) {
            kid.setNombreMadre(nombreMadreTextField.getText());
        }
        if (!historialEditorPane.getText().equals(kid.getHistorial())) {
            kid.setHistorial(historialEditorPane.getText());
        }
        if (!comunidadTextField.getText().equals(kid.getDatosComunidad())) {
            kid.setDatosComunidad(comunidadTextField.getText());
        }
        if (!fechaEntradaTextBox.getText().equals(kid.getFechaEntrada())) {
            kid.setFechaEntrada(fechaEntradaTextBox.getText());
        }
        if (!fechaSalidaTextBox.getText().isEmpty()) {
            kid.setFechaBaja(fechaSalidaTextBox.getText());
        }
        if (!becaTextField.getText().equals(kid.getBeca())) {
            kid.setBeca(becaTextField.getText());
        }
        if (Float.parseFloat(notaMediaTextField.getText()) != kid.getNotaMedia()) {
            kid.setNotaMedia(Float.parseFloat(notaMediaTextField.getText()));
        }
        if (!observacionesPane.getText().equals(kid.getObservaciones())) {
            kid.setObservaciones(observacionesPane.getText());
        }
        if (!generoComboBox.getSelectedItem().equals(kid.getGenero())) {
            kid.setGenero(generoComboBox.getSelectedItem().toString());
        }
        /*if (!cambiarProyectoComboBox.getSelectedItem().equals(dbManager.select("select p.nombre from proyecto p inner " +
                "join Accion a on a.id_proyecto = p.id where a.id_joven = " + kid.getId() + ";"))) {*/
            int idProyecto = (int) dbManager.select("select id from Proyecto where nombre like '" +
                    cambiarProyectoComboBox.getSelectedItem() + "';").get(0)[0];
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String entradaToStr = dtf.format(LocalDate.now());
            dbManager.execute("update Accion set id_proyecto = '" + idProyecto + "', fecha_entrada = '" + entradaToStr +
                    "' where id_joven = '" + kid.getId() + "';");
       // }
        /*if(!((String) modificarProyectoComboBox.getItemAt(modificarProyectoComboBox.getSelectedIndex())).isEmpty()) {
           Tendríamos que añadir un deste que modifique el proyecto en el que está el niño
        }*/
    }
}

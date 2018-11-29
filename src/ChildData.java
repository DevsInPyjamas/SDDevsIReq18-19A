import db_management.DBManager;
import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.Dimension;
import java.util.List;

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
    private DBManager dbManager = new DBManager();


    ChildData(Usuario loggedUser, String idChild) {
        this.loggedUser = loggedUser;
        List<Object[]> queryTuples;
        if(!loggedUser.getRol().isAdmin()) {
            queryTuples = dbManager.select("select p.nombre from Proyecto p inner join " +
                    "Usuario u on u.pertenece_proyecto = p.id and u.email = '" + loggedUser.getEmail() + "';");
        } else {
            queryTuples = dbManager.select("select nombre from Proyecto");
        }
        for(Object[] tuple : queryTuples) {
            modificarProyectoComboBox.addItem(tuple[0]);
        }
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
                new SearchChild(loggedUser);
                frame.dispose();
            }
        });
        modifyKidButton.addActionListener((e) -> {
            frame.setMinimumSize(new Dimension(1000, 400));
            displayButtons(true);
        });
        actualizarButton.addActionListener((e) -> {
            try {
                this.modifyKidDB(child);
                JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el niño...");
            } catch (Exception p) {
                JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " + p.getMessage());
            }
        });
    }

    private void displayButtons(boolean siONo){
        modificarProyectoComboBox.setVisible(siONo);
        modificarNombre.setVisible(siONo);
        modificarApellidos.setVisible(siONo);
        actualizarButton.setVisible(siONo);
        modificarFechaNacimiento.setVisible(siONo);
        modificarNombreMadre.setVisible(siONo);
        modificarNombrePadre.setVisible(siONo);
        modificarHistorialPane.setVisible(siONo);
    }

    private void modifyKidDB(Joven kid) {
        if(!modificarNombre.getText().isEmpty()) {
            kid.setNombre(modificarNombre.getText());
        }
        if(!modificarApellidos.getText().isEmpty()) {
            kid.setApellidos(modificarApellidos.getText());
        }
        if(!modificarNombrePadre.getText().isEmpty()) {
            kid.setNombrePadre(modificarNombrePadre.getText());
        }
        if(!modificarNombreMadre.getText().isEmpty()) {
            kid.setNombreMadre(modificarNombreMadre.getText());
        }
        if(!modificarHistorialPane.getText().isEmpty()) {
            kid.setHistorial(modificarHistorialPane.getText());
        }
        /*if(!((String) modificarProyectoComboBox.getItemAt(modificarProyectoComboBox.getSelectedIndex())).isEmpty()) {
           Tendríamos que añadir un deste que modifique el proyecto en el que está el niño
        }*/
    }
 }

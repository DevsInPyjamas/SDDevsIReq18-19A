import db_management.DBManager;
import db_management.Proyecto;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Objects;

public class ModifyProject {
    private JPanel modificarProyectoPanel;
    private JButton atrasButton;
    private JButton borrarProyectoButton;
    private JButton actualizarButton;
    private JTextField nombreTextField;
    private JTextField ubicacionTextField;
    private JTextField coordinadorTextField;
    private JTextField responsableTextFIeld;
    private JTextField modificarNombreTextField;
    private JTextField modificarUbicacionTextField;
    private JTextField modificarCoordinadorTextField;
    private JTextField modificarResponsableTextField;
    private JButton modificarProyectoButton;
    private JTextField tipoProyectoTextField;
    private JComboBox modificarTipoProyectoComboBox;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    ModifyProject(Usuario loggedUser, int idProject) throws Exception {
        this.loggedUser = loggedUser;
        displayButtons(false);
        modificarProyectoPanel.setSize(700, 400);
        JFrame frame = new JFrame("Información Proyecto");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarProyectoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Proyecto proyecto = new Proyecto(idProject);
        nombreTextField.setText(proyecto.getNombre());
        ubicacionTextField.setText(proyecto.getUbicacion());
        coordinadorTextField.setText(proyecto.getCoordinadorAsignado().getEmail());
        responsableTextFIeld.setText(proyecto.getResponsableEconomico().getEmail());
        tipoProyectoTextField.setText(proyecto.getTipoProyecto());
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new SearchProject(loggedUser);
                frame.dispose();
            }
        });
        modificarProyectoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar Proyecto")) {
                frame.setMinimumSize(new Dimension(1000, 400));
                displayButtons(true);
            }
        });
        actualizarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Actualizar")) {
                try {
                    this.modifyProjectDB(proyecto);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el proyecto...");
                } catch (Exception p) {
                    JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " + p.getMessage());
                }
                displayButtons(false);
                modificarProyectoPanel.setSize(700, 400);
                frame.setMinimumSize(new Dimension(700, 400));
                nombreTextField.setText(proyecto.getNombre());
                ubicacionTextField.setText(proyecto.getUbicacion());
                coordinadorTextField.setText(proyecto.getCoordinadorAsignado().getEmail());
                responsableTextFIeld.setText(proyecto.getResponsableEconomico().getEmail());
                tipoProyectoTextField.setText(proyecto.getTipoProyecto());
            }
        });
        borrarProyectoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Borrar Proyecto")){
                int dialogResult = JOptionPane.showConfirmDialog (null,
                        "¿Estás seguro de que quiere eliminar el proyecto?","Confirmación de Borrado",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_NO_OPTION) {
                    dbManager.execute("delete from Accion where id_proyecto = '" + proyecto.getId() + "';");
                    dbManager.execute("delete from Proyecto where id = '" + proyecto.getId() + "';");
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado el proyecto de la base de datos");
                    new SearchProject(loggedUser);
                    frame.dispose();
                }
            }
        });

    }

    private void displayButtons(boolean siONo) {
        modificarNombreTextField.setVisible(siONo);
        modificarUbicacionTextField.setVisible(siONo);
        modificarCoordinadorTextField.setVisible(siONo);
        modificarResponsableTextField.setVisible(siONo);
        modificarTipoProyectoComboBox.setVisible(siONo);
        actualizarButton.setVisible(siONo);
    }

    private void modifyProjectDB(Proyecto proyecto) throws Exception {
        if (!modificarNombreTextField.getText().isEmpty()) {
            proyecto.setNombre(modificarNombreTextField.getText());
        }
        if (!modificarUbicacionTextField.getText().isEmpty()) {
            proyecto.setUbicacion(modificarUbicacionTextField.getText());
        }
        if (!modificarCoordinadorTextField.getText().isEmpty()) {
            proyecto.setCoordinadorAsignado(new Usuario(modificarCoordinadorTextField.getText()));
        }
        if (!modificarResponsableTextField.getText().isEmpty()) {
            proyecto.setResponsableEconomico(new Usuario(modificarResponsableTextField.getText()));
        }
        if(!Objects.requireNonNull(modificarTipoProyectoComboBox.getSelectedItem()).toString().equals("")) {
            proyecto.setTipoProyecto(modificarTipoProyectoComboBox.getSelectedItem().toString());
        }
    }
}

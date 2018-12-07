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
    private JButton modificarProyectoButton;
    private JComboBox tipoProyectoComboBox;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();
    private boolean modifying = false;

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
        tipoProyectoComboBox.setSelectedItem(proyecto.getTipoProyecto());
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                if (modifying) {
                    try {
                        new ModifyProject(loggedUser, proyecto.getId());
                        frame.dispose();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error: " + e1.getMessage());                    }
                } else {
                    new SearchProject(loggedUser);
                    frame.dispose();
                }
            }
        });
        modificarProyectoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar Proyecto")) {
                modifying = true;
                frame.setMinimumSize(new Dimension(700, 400));
                displayButtons(true);
            }
        });
        actualizarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Actualizar")) {
                try {
                    this.modifyProjectDB(proyecto);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente el proyecto...");
                } catch (Exception p) {
                    JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " +
                            p.getMessage());
                }
                displayButtons(false);
                modificarProyectoPanel.setSize(700, 400);
                frame.setMinimumSize(new Dimension(700, 400));
                nombreTextField.setText(proyecto.getNombre());
                ubicacionTextField.setText(proyecto.getUbicacion());
                coordinadorTextField.setText(proyecto.getCoordinadorAsignado().getEmail());
                responsableTextFIeld.setText(proyecto.getResponsableEconomico().getEmail());
                tipoProyectoComboBox.setSelectedItem(proyecto.getTipoProyecto());
                modifying = false;
            }
        });
        borrarProyectoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Borrar Proyecto")) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quiere eliminar el proyecto?", "Confirmación de Borrado",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_NO_OPTION) {
                    proyecto.setIsDeleted(true);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado el proyecto de la base de datos");
                    new SearchProject(loggedUser);
                    frame.dispose();
                }
            }
        });

    }

    private void displayButtons(boolean siONo) {
        nombreTextField.setEditable(siONo);
        ubicacionTextField.setEditable(siONo);
        coordinadorTextField.setEditable(siONo);
        responsableTextFIeld.setEditable(siONo);
        tipoProyectoComboBox.setEnabled(siONo);
        actualizarButton.setVisible(siONo);
    }

    private void modifyProjectDB(Proyecto proyecto) throws Exception {
        if (!nombreTextField.getText().equals(proyecto.getNombre())) {
            proyecto.setNombre(nombreTextField.getText());
        }
        if (!ubicacionTextField.getText().equals(proyecto.getUbicacion())) {
            proyecto.setUbicacion(ubicacionTextField.getText());
        }
        if (!coordinadorTextField.getText().equals(proyecto.getCoordinadorAsignado().getEmail())) {
            proyecto.setCoordinadorAsignado(new Usuario(ubicacionTextField.getText()));
        }
        if (!responsableTextFIeld.getText().equals(proyecto.getResponsableEconomico().getEmail())) {
            proyecto.setResponsableEconomico(new Usuario(ubicacionTextField.getText()));
        }
        if (!Objects.requireNonNull(tipoProyectoComboBox.getSelectedItem()).equals(proyecto.getTipoProyecto())) {
            proyecto.setTipoProyecto(Objects.requireNonNull(tipoProyectoComboBox.getSelectedItem()).toString());
        }
    }
}

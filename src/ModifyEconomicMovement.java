import db_management.DBManager;
import db_management.Transaccion;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ModifyEconomicMovement {
    private JPanel modificarMovEconomico;
    private JButton atrasButton;
    private JButton borrarMovimientoButton;
    private JButton actualizarButton;
    private JTextField emisorTextField;
    private JTextField conceptoTextField;
    private JTextField cantidadTextField;
    private JComboBox tipoGastoBox;
    private JButton modificarMovimientoButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();
    private boolean modifying = false;


    ModifyEconomicMovement(Usuario loggedUser, int idMovEconomic) {
        this.loggedUser = loggedUser;
        displayButtons(false);
        modificarMovEconomico.setSize(700, 400);
        JFrame frame = new JFrame("Información del Movimiento Económico");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarMovEconomico);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        for(Object[] tuple : dbManager.select("select nombre from TipoGasto;")) {
            tipoGastoBox.addItem(tuple[0]);
        }
        Transaccion trans = new Transaccion(idMovEconomic);
        emisorTextField.setText(trans.getEmisor());
        conceptoTextField.setText(trans.getConcepto());
        cantidadTextField.setText(Double.toString(trans.getCantidad()));
        tipoGastoBox.setSelectedItem(trans.getTipoGasto());
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                if (modifying) {
                    try {
                        new ModifyEconomicMovement(loggedUser, trans.getId());
                        frame.dispose();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error: " + e1.getMessage());
                    }
                } else {
                    new SearchEconomicMovement(loggedUser);
                    frame.dispose();
                }
            }
        });

        modificarMovimientoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar Movimento Económico")) {
                modifying = true;
                frame.setMinimumSize(new Dimension(700, 400));
                displayButtons(true);
            }
        });
        actualizarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Actualizar")) {
                try {
                    this.modifyEconomicMovementDB(trans);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha modificado correctamente la transacción...");
                } catch (Exception p) {
                    JOptionPane.showMessageDialog(new JFrame(), "Algo ha fallado al modificar los datos... " +
                            p.getMessage());
                }
                displayButtons(false);
                modificarMovEconomico.setSize(700, 400);
                frame.setMinimumSize(new Dimension(700, 400));
                emisorTextField.setText(trans.getEmisor());
                conceptoTextField.setText(trans.getConcepto());
                cantidadTextField.setText(Double.toString(trans.getCantidad()));
                tipoGastoBox.setSelectedItem(trans.getTipoGasto());
                modifying = false;
            }
        });

        borrarMovimientoButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Borrar Movimiento Económico")) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quiere eliminar la transacción?", "Confirmación de Borrado",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_NO_OPTION) {
                    trans.setDeleted(true);
                    JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado la transacción de la base de datos");
                    new SearchEconomicMovement(loggedUser);
                    frame.dispose();
                }
            }
        });

    }

    private void displayButtons(boolean siONo) {
        emisorTextField.setEditable(siONo);
        conceptoTextField.setEditable(siONo);
        cantidadTextField.setEditable(siONo);
        tipoGastoBox.setEnabled(siONo);
        actualizarButton.setVisible(siONo);
    }

    private void modifyEconomicMovementDB(Transaccion trans) throws Exception {
        if (!emisorTextField.getText().equals(trans.getEmisor())) {
            trans.setEmisor(emisorTextField.getText());
        }
        if (!conceptoTextField.getText().equals(trans.getConcepto())) {
            trans.setConcepto(conceptoTextField.getText());
        }
        if (!cantidadTextField.getText().equals(trans.getCantidad())) {
            trans.setCantidad(Double.valueOf(cantidadTextField.getText()));
        }
        /*if (!Objects.requireNonNull(tipoGastoBox.getSelectedItem()).equals(trans.getTipoGasto())) {
            trans.setTipoProyecto(Objects.requireNonNull(tipoGastoBox.getSelectedItem()).toString());
        }*/
    }

}

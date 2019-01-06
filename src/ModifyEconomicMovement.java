import db_management.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModifyEconomicMovement {
    private JPanel modificarMovEconomico;
    private JButton atrasButton;
    private JButton borrarMovimientoButton;
    private JButton actualizarButton;
    private JTextField emisorTextField;
    private JTextField conceptoTextField;
    private JTextField cantidadTextField;
    private JComboBox tipoGastoBox;
    private JComboBox socioComboBox;
    private JComboBox empresaComboBox;
    private JComboBox proyectoComboBox;
    private JButton modificarMovimientoButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();
    private boolean modifying = false;


    ModifyEconomicMovement(Usuario loggedUser, int idMovEconomic) {
        this.loggedUser = loggedUser;
        displayButtons(false);
        proyectoComboBox.setVisible(false);
        modificarMovEconomico.setSize(700, 400);
        JFrame frame = new JFrame("Información del Movimiento Económico");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setContentPane(modificarMovEconomico);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        if(loggedUser.getRol().isSuperAdmin()) {
            proyectoComboBox.setVisible(true);
            for(Object[] tuple : dbManager.select("select nombre from Proyecto;")) {
                proyectoComboBox.addItem(tuple[0]);
            }
        }
        empresaComboBox.addItem(null);
        for(Object[] tuple : dbManager.select("select nombre from Empresa;")) {
            empresaComboBox.addItem(tuple[0]);
        }
        List<Object[]> queryTuples = (loggedUser.getRol().isSuperAdmin()) ?
                dbManager.select("select isDeleted, concat(nombre, ' ', apellidos)" + " from Socio;") :
                dbManager.select("select isDeleted, concat(nombre, ' ', apellidos)" + " from Socio " +
                "where asociation = '" + loggedUser.getProyecto().getId() + "';");
        socioComboBox.addItem(null);
        for(Object[] tuple : queryTuples) {
            if(!(boolean) tuple[0]) {
                socioComboBox.addItem(tuple[1]);
            }
        }
        for(Object[] tuple : dbManager.select("select nombre from TipoGasto;")) {
            tipoGastoBox.addItem(tuple[0]);
        }
        Transaccion trans = new Transaccion(idMovEconomic);
        emisorTextField.setText(trans.getEmisor());
        if(loggedUser.getRol().isSuperAdmin()) {
            proyectoComboBox.setSelectedItem(trans.getProyecto().getNombre());
        }
        if(trans.getSocio().getId() != 0) {
            socioComboBox.setSelectedItem(trans.getSocio().getNombre() + " " + trans.getSocio().getApellidos());
        } else {
            socioComboBox.setSelectedItem(null);
        }
        if(trans.getEmpresa() != null) {
            empresaComboBox.setSelectedItem(trans.getEmpresa().getNombre());
        } else {
            empresaComboBox.setSelectedItem(null);
        }
        conceptoTextField.setText(trans.getConcepto());
        cantidadTextField.setText(Double.toString(trans.getCantidad()));
        tipoGastoBox.setSelectedItem(trans.getTipoGasto());
        atrasButton.addActionListener(e -> {
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
        });

        modificarMovimientoButton.addActionListener(e -> {
            modifying = true;
            frame.setMinimumSize(new Dimension(700, 400));
            displayButtons(true);
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
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que quiere eliminar la transacción?", "Confirmación de Borrado",
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_NO_OPTION) {
                trans.setDeleted(true);
                trans.save();
                JOptionPane.showMessageDialog(new JFrame(), "Se ha eliminado la transacción de la base de datos");
                new SearchEconomicMovement(loggedUser);
                frame.dispose();
            }
        });

    }

    private void displayButtons(boolean siONo) {
        emisorTextField.setEditable(siONo);
        conceptoTextField.setEditable(siONo);
        cantidadTextField.setEditable(siONo);
        tipoGastoBox.setEnabled(siONo);
        actualizarButton.setVisible(siONo);
        socioComboBox.setEnabled(siONo);
        proyectoComboBox.setEnabled(siONo);
        empresaComboBox.setEnabled(siONo);
    }

    private void modifyEconomicMovementDB(Transaccion trans) {
        int idEmpresa, idSocio, idProyecto, idTipoGasto;
        trans.setCantidad(Double.valueOf(cantidadTextField.getText()));
        trans.setConcepto(conceptoTextField.getText());
        trans.setEmisor(emisorTextField.getText());
        if(socioComboBox.getSelectedItem() != null) {
            idSocio = (int) dbManager.select("select id from Socio where concat(nombre, ' ', apellidos) = '"
                    + this.socioComboBox.getSelectedItem() + "';").get(0)[0];
            trans.setSocio(new Socio(idSocio));
        }
        if(empresaComboBox.getSelectedItem() != null) {
            idEmpresa = (int) dbManager.select("select id from Empresa where nombre = '" +
                    this.empresaComboBox.getSelectedItem() + "';").get(0)[0];
            trans.setEmpresa(new Empresa(idEmpresa));
        }
        idProyecto = (int) dbManager.select("select id from Proyecto where nombre = '" +
                this.proyectoComboBox.getSelectedItem() + "';").get(0)[0];
        idTipoGasto = (int) dbManager.select("select id from TipoGasto where nombre = '" +
                this.tipoGastoBox.getSelectedItem() + "';").get(0)[0];
        trans.setProyecto(new Proyecto(idProyecto));
        trans.setTipoGasto(new TipoGasto(idTipoGasto));
        trans.save();
    }

}

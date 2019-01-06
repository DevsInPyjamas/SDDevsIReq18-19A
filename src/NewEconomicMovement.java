import db_management.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NewEconomicMovement {
    private JPanel nuevaTrancision;
    private JButton backButton;
    private JButton addEconomicButton;
    private JTextField emisorField;
    private JTextField conceptoField;
    private JTextField cantidadField;
    private JComboBox tipoGastoComboBox;
    private JComboBox empresaComboBox;
    private JComboBox socioComboBox;
    private Usuario loggedUser;

    NewEconomicMovement(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevaTrancision.setSize(700, 250);
        JFrame frame = new JFrame("Nueva transacción");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevaTrancision);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuples;
        DBManager dbManager = new DBManager();
        queryTuples = dbManager.select("select nombre from TipoGasto;");
        for(Object[] tuple : queryTuples) {
            tipoGastoComboBox.addItem(tuple[0]);
        }
        queryTuples = dbManager.select("select nombre from Empresa;");
        for(Object[] tuple : queryTuples) {
            empresaComboBox.addItem(tuple[0]);
        }
        queryTuples = dbManager.select("select isDeleted, concat(nombre, ' ', apellidos)" + " from Socio " +
                "where asociation = '" + loggedUser.getProyecto().getId() + "';");
        for(Object[] tuple : queryTuples) {
            if(!(boolean) tuple[0]) {
                socioComboBox.addItem(tuple[1]);
            }
        }
        backButton.addActionListener((e) -> {
            if (e.getActionCommand().equals("Atrás")) {
                new EconomicSection(loggedUser);
                frame.dispose();
            }
        });
        addEconomicButton.addActionListener((e) -> {
            if (everyMandatoryFieldIsFilled()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                Transaccion t = new Transaccion();
                t.setEmisor(emisorField.getText());
                t.setConcepto(conceptoField.getText());
                int tipoGastoID = (int) dbManager.select("select id from TipoGasto where nombre like '" +
                        tipoGastoComboBox.getSelectedItem() + "';").get(0)[0];
                t.setTipoGasto(new TipoGasto(tipoGastoID));
                t.setCantidad(Double.valueOf(cantidadField.getText()));
                t.setProyecto(loggedUser.getProyecto());
                if(socioComboBox.getSelectedItem() != null) {
                    int socioID = (int) dbManager.select("select id from Socio where " +
                            "contat(nombre, ' ', apellidos) = '" + socioComboBox.getSelectedItem() + "';").get(0)[0];
                    t.setSocio(new Socio(socioID));
                }
                if(empresaComboBox.getSelectedItem() != null) {
                    int empresaID = (int) dbManager.select("select id from Empresa where nombre like '" + 
                            empresaComboBox.getSelectedItem() + "';").get(0)[0];
                    t.setEmpresa(new Empresa(empresaID));
                }
                try {
                    t.save();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos no son correctos");
                }
                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new EconomicSection(loggedUser);
                frame.dispose();
            }
        });
    }

    private boolean everyMandatoryFieldIsFilled() {
        return (empresaComboBox.getSelectedItem() != null || socioComboBox.getSelectedItem() != null) 
                && tipoGastoComboBox.getSelectedItem() != null && !emisorField.getText().isEmpty() &&
                !cantidadField.getText().isEmpty();
    }
}
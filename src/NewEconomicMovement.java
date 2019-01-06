import db_management.DBManager;
import db_management.TipoGasto;
import db_management.Usuario;
import db_management.Transaccion;

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
        backButton.addActionListener((e) -> {
            if (e.getActionCommand().equals("Atrás")) {
                new EconomicSection(loggedUser);
                frame.dispose();
            }
        });
        addEconomicButton.addActionListener((e) -> {
            if (checkIfThereAreNotBlankFields()) {
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
                t.save();
                /*
                emisorField.getText(), conceptoField.getText(), cantidadField.getText(),
                        Objects.requireNonNull(tipoGastoComboBox.getSelectedItem()).toString()
                 */
                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new EconomicSection(loggedUser);
                frame.dispose();
            }
        });
    }

    private boolean checkIfThereAreNotBlankFields() {
        return false;
    }
}
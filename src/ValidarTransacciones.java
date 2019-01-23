import db_management.DBManager;
import db_management.Transaccion;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidarTransacciones {
    private Usuario loggedUser;
    private JButton atrasButton;
    private JTable validateTransactionTable;
    private JPanel validateTransactionPanel;
    private DBManager dbManager = new DBManager();

    public ValidarTransacciones(Usuario loggedUser) {
        this.loggedUser= loggedUser;
        validateTransactionPanel.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(validateTransactionPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        validateTransactionTable.setModel(new DefaultTableModel(new Object[]{"id", "Emisor", "Cantidad", "Tipo de Gasto", "Info"}, 6));
        DefaultTableModel model = (DefaultTableModel) validateTransactionTable.getModel();
        validateTransactionTable.setFocusable(false);
        validateTransactionTable.setRowSelectionAllowed(false);
        model.setRowCount(0);
        cargarDatos(model);
        validateTransactionTable.getSelectionModel().addListSelectionListener(e -> {
            if(model.getRowCount() > 0) {
                int idEcMov = (int) validateTransactionTable.getValueAt(validateTransactionTable.getSelectedRow(), 0);
                Transaccion t = new Transaccion(idEcMov);
                t.setValidated(true);
                t.save();
                JOptionPane.showMessageDialog(new JFrame(), "Mensaje validado con éxito");
                model.setRowCount(0);
                cargarDatos(model);
            }
        });
        atrasButton.addActionListener(e -> {
            new EconomicSection(loggedUser);
            frame.dispose();
        });
    }

    private void cargarDatos(DefaultTableModel model) {
        // model.setRowCount(0);
        for(Object[] row : processWhenSearchWithoutValue()) {
            Object[] rowObject = new Object[row.length];
            if(!(boolean) row[0] && !(boolean) row[1]) {
                rowObject[0] = row[2];
                rowObject[1] = row[3];
                rowObject[2] = row[4];
                rowObject[3] = row[5];
                model.addRow(rowObject);
            }
        }
    }

    private java.util.List<Object[]> processWhenSearchWithoutValue() {
        if(loggedUser.getRol().isSuperAdmin()) {
            return dbManager.select("select t.isDeleted, t.isValidated, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id;");
        } else {
            return dbManager.select("select t.isDeleted, t.isValidated, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id and t.proyecto = " + loggedUser.getProyecto().getId() + ";");
        }
    }
}

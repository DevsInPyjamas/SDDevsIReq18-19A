import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchEconomicMovement {
    private JPanel searchEconomicMovement;
    private JButton atrasButton;
    private JTextField searchMovEconimicTextField;
    private JButton searchButton;
    private JTable seachMovEconomicTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchEconomicMovement(Usuario loggedUser){
        this.loggedUser= loggedUser;
        searchEconomicMovement.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchEconomicMovement);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        seachMovEconomicTable.setModel(new DefaultTableModel(new Object[]{"id", "Emisor", "Cantidad", "Tipo de Gasto"}, 6));
        DefaultTableModel model = (DefaultTableModel) seachMovEconomicTable.getModel();
        seachMovEconomicTable.setFocusable(false);
        seachMovEconomicTable.setRowSelectionAllowed(false);
        atrasButton.addActionListener((e)->{
            new EconomicSection(loggedUser);
            frame.dispose();
        });
        searchButton.addActionListener((e)->{
            model.setRowCount(0);
            java.util.List<Object[]> queryTuples;
            if (searchMovEconimicTextField.getText().isEmpty()) {
                queryTuples = processWhenSearchWithoutValue();
            } else {
                queryTuples = normalSearchProcess();
            }
            for(Object[] tuple : queryTuples) {
                if(!(boolean) tuple[0]) {
                    Object[] row = new Object[tuple.length];
                    row[0] = tuple[1];
                    row[1] = tuple[2];
                    row[2] = tuple[3];
                    row[3] = tuple[4];
                    model.addRow(row);
                }
            }
            seachMovEconomicTable.setFocusable(true);
            seachMovEconomicTable.setRowSelectionAllowed(true);
        });
        seachMovEconomicTable.getSelectionModel().addListSelectionListener(e -> {
            try {
                int idEcMov = (int) seachMovEconomicTable.getValueAt(seachMovEconomicTable.getSelectedRow(), 0);
                new ModifyEconomicMovement(loggedUser, idEcMov);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());
            }
        });
    }

    private List<Object[]> normalSearchProcess() {
        String currentValue = this.searchMovEconimicTextField.getText();
        if(loggedUser.getRol().isSuperAdmin()) {
            return dbManager.select("select t.isDeleted, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id where t.emisor like '%" +
                    currentValue + "%' or tg.nombre like '%" + currentValue + "%';");
        } else {
            return dbManager.select("select t.isDeleted, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id where t.emisor like '%" +
                    currentValue + "%' or tg.nombre like '%" + currentValue + "%' and t.proyecto = " + loggedUser.getProyecto().getId() + ";");
        }
    }

    private java.util.List<Object[]> processWhenSearchWithoutValue() {
        if(loggedUser.getRol().isSuperAdmin()) {
            return dbManager.select("select t.isDeleted, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id;");
        } else {
            return dbManager.select("select t.isDeleted, t.id, t.emisor, t.cantidad, tg.nombre from transaccion t " +
                    "inner join TipoGasto TG on t.tipoGasto = TG.id and t.proyecto = " + loggedUser.getProyecto().getId() + ";");
        }
    }
}

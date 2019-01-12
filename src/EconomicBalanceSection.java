import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class EconomicBalanceSection {
    private Usuario loggedUser;
    private JButton atrasButton;
    private JLabel balanceLabel;
    private JTextField beginningDateTextField;
    private JTextField finishDateTextField;
    private JButton searchButton;
    private JTable balanceTable;
    private JPanel balanceSectionPanel;
    private DBManager dbManager = new DBManager();
    private double balance;

    public EconomicBalanceSection(Usuario loggedUser) {
        this.loggedUser= loggedUser;
        balanceSectionPanel.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setContentPane(balanceSectionPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        balanceTable.setModel(new DefaultTableModel(new Object[]{"id", "Beneficiario", "Cantidad", "Tipo de Gasto", "Fecha"}, 6));
        DefaultTableModel model = (DefaultTableModel) balanceTable.getModel();
        balanceTable.setFocusable(false);
        balanceTable.setRowSelectionAllowed(false);
        this.balanceLabel.setText("0");
        atrasButton.addActionListener(e -> {
            new EconomicSection(loggedUser);
            frame.dispose();
        });
        searchButton.addActionListener(e -> {
            model.setRowCount(0);
            String begginningDateValue = (beginningDateTextField.getText().isEmpty()) ? null  :
                    "'" + beginningDateTextField.getText() + "'";
            String finnishDateValue = (finishDateTextField.getText().isEmpty()) ? null :
                    "'" + finishDateTextField.getText() + "'";
            Integer valueOfID = (loggedUser.getProyecto() == null) ? null : loggedUser.getProyecto().getId();
            List<Object[]> query = dbManager.select("exec TransaccionesRango " +  begginningDateValue + ", " +
                    finnishDateValue + ", " + valueOfID);
            for(Object[] tuple : query) {
                if((boolean) tuple[6] && !(boolean) tuple[4]) {
                    Object[] row = new Object[tuple.length];
                    row[0] = tuple[0];
                    BigDecimal a = (BigDecimal) tuple[3];
                    balance += a.doubleValue();
                    row[1] = dbManager.select("exec DatosBeneficiario " + tuple[0]).get(0)[1];
                    row[2] = tuple[3];
                    row[3] = dbManager.select("select nombre from TipoGasto where id = " + tuple[5]).get(0)[0];
                    row[4] = tuple[9];
                    model.addRow(row);
                }
            }
            balanceLabel.setText(Double.toString(balance));
        });
    }
}

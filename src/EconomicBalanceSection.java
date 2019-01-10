import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EconomicBalanceSection {
    private Usuario loggedUser;
    private JButton atrasButton;
    private JLabel balanceLabel;
    private JTextField beginningDateTextField;
    private JTextField finishDateTextField;
    private JButton searchButton;
    private JTable balanceTable;
    private JPanel balanceSectionPanel;

    public EconomicBalanceSection(Usuario loggedUser) {
        this.loggedUser= loggedUser;
        balanceSectionPanel.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setContentPane(balanceSectionPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        balanceTable.setModel(new DefaultTableModel(new Object[]{"id", "Emisor", "Cantidad", "Tipo de Gasto"}, 6));
        DefaultTableModel model = (DefaultTableModel) balanceTable.getModel();
        balanceTable.setFocusable(false);
        balanceTable.setRowSelectionAllowed(false);
    }
}

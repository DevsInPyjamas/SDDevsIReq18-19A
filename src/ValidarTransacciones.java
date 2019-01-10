import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ValidarTransacciones {
    private Usuario loggedUser;
    private JButton atrasButton;
    private JTable validateTransactionTable;
    private JPanel validateTransactionPanel;

    public ValidarTransacciones(Usuario loggedUser) {
        this.loggedUser= loggedUser;
        validateTransactionPanel.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(validateTransactionPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        validateTransactionTable.setModel(new DefaultTableModel(new Object[]{"id", "Emisor", "Cantidad", "Tipo de Gasto"}, 6));
        DefaultTableModel model = (DefaultTableModel) validateTransactionTable.getModel();
        validateTransactionTable.setFocusable(false);
        validateTransactionTable.setRowSelectionAllowed(false);
        atrasButton.addActionListener(e -> {
            new EconomicSection(loggedUser);
            frame.dispose();
        });
    }
}

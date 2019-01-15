import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class TypeOfTransaction {
    private JPanel transactionTypePanel;
    private JButton backButton;
    private JButton newWaste;
    private JButton newDonation;
    private JButton newMensuality;


    public TypeOfTransaction(Usuario loggedUser) {
        transactionTypePanel.setSize(700, 250);
        JFrame frame = new JFrame("Nueva transacción");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(transactionTypePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener((e) -> {
            new EconomicSection(loggedUser);
            frame.dispose();
        });
        newWaste.addActionListener(e -> {
            new NewEconomicMovement(loggedUser, null);
            frame.dispose();
        });
        newDonation.addActionListener(e -> {
            new NewEconomicMovement(loggedUser, true);
            frame.dispose();
        });
        newMensuality.addActionListener(e -> {
            new NewEconomicMovement(loggedUser, false);
            frame.dispose();
        });
    }
}

import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewProject extends JPanel{
    private JPanel nuevoProyectoPanel;
    private JButton backButton;
    private JLabel nuevoProyectoLabel;
    private JLabel nombreLabel;
    private JTextField nombreField;
    private JLabel tipoProyectoLabel;
    private JComboBox tipoProyectoComboBox;
    private JLabel ubicacionLabel;
    private JTextField ubicacionField;
    private JLabel coordinadorLabel;
    private JTextField coordinadorTextField;
    private JLabel economicoLabel;
    private JTextField economicoField;
    private JLabel poblacionLabel;
    private JTextField poblacionField;
    private JLabel departamentoLabel;
    private JTextField departamentoField;
    private JButton anadirButton;
    private Usuario loggedUser;
    private JPanel adminWindow;


    public NewProject(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        adminWindow.setSize(700, 250);
        JFrame frame = new JFrame("Admin Section");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(adminWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Atras")){
                    new AdminArea(loggedUser);
                    frame.dispose();
                }
            }

        });
        anadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Añadir")) {
                    // TODO añadir niño al sistema.
                }
            }
        });
    }
}

import db_management.DBManager;
import db_management.Proyectos;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
    private JButton anadirButton;
    private Usuario loggedUser;
    private JPanel adminWindow;
    private Proyectos proyecto;


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
                    DBManager bd = new DBManager();
                    proyecto.setNombre(nombreField.getText());
                    proyecto.setUbicacion(ubicacionField.getText());
                    String queryGeneral = (String) bd.select("SELECT email FROM Usuario WHERE nombre = '" +
                            coordinadorTextField.getText() + "';").get(0)[0];
                    proyecto.setCoordinadorAsignado(new Usuario(queryGeneral));
                    String queryEconomico = (String) bd.select("SELECT email FROM Usuario WHERE nombre = '" +
                            economicoField.getText() + "';").get(0)[0];
                    proyecto.setResponsableEconomico(new Usuario(queryEconomico));
                    proyecto.setTipoProyecto(Objects.requireNonNull(tipoProyectoComboBox.getSelectedItem()).toString());
                }
            }
        });
    }
}

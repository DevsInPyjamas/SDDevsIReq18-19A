import db_management.DBManager;
import db_management.Proyecto;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class NewProject extends JPanel{
    private JPanel nuevoProyectoPanel;
    private JButton backButton;
    private JTextField nombreField;
    private JComboBox tipoProyectoComboBox;
    private JTextField ubicacionField;
    private JTextField coordinadorTextField;
    private JTextField economicoField;
    private JButton anadirButton;
    private Usuario loggedUser;
    private Proyecto proyecto;


    public NewProject(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        //nuevoProyectoPanel.setSize(250, 500);
        JFrame frame = new JFrame("Admin Section");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setContentPane(nuevoProyectoPanel);
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
                    try {
                        String nombre = nombreField.getText();
                        String ubicacion = ubicacionField.getText();
                        Usuario coord = new Usuario(coordinadorTextField.getText());
                        Usuario resp = new Usuario(economicoField.getText());
                        String tipoProy = Objects.requireNonNull(tipoProyectoComboBox.getSelectedItem()).toString();
                        proyecto = new Proyecto(nombre, ubicacion, coord, resp, tipoProy);
                        putaMadreNene("Se ha añadido el proyecto correctamente");
                    }catch (Exception ex) {
                        putaMadreNene(ex.getMessage());
                    }
                }
            }
        });
    }

    private void putaMadreNene(String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Información");
        dialog.setBounds(400, 400, 300, 200);
        dialog.setMinimumSize(new Dimension(550, 150));
        dialog.setContentPane(new JLabel(message));
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.setVisible(true);
    }
}

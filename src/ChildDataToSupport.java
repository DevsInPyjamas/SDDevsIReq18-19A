import db_management.DBManager;
import db_management.Joven;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class ChildDataToSupport {
    private JPanel childDataToSupportPanel;
    private JButton atrasButton;
    private JTextField nombreTextField;
    private JTextField apellidosTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField proyectoTextField;
    private JTextField becaTextField;
    private JTextField fechaEntradaTextField;
    private JButton apadrinarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    ChildDataToSupport(Usuario loggedUser, String idChild) {
        this.loggedUser = loggedUser;
        childDataToSupportPanel.setSize(700, 600);
        JFrame frame = new JFrame("Información Niño para apadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(childDataToSupportPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven child = new Joven(Integer.parseInt(idChild));
        nombreTextField.setText(child.getNombre());
        fechaNacimientoTextField.setText(child.getFechaNacimiento());
        apellidosTextField.setText(child.getApellidos());
        fechaEntradaTextField.setText(child.getFechaEntrada());
        String text = (child.getCurrentProjectID() == -1) ? "" :
                (String) dbManager.select("select nombre from Proyecto where id = '" + child.getCurrentProjectID()
                        + "';").get(0)[0];
        proyectoTextField.setText(text);
        becaTextField.setText(child.getBeca());
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new SearchChildToSupport(loggedUser);
                frame.dispose();
            }
        });
        apadrinarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Apadrinar")) {
                new SearchSocioToSupporChild(loggedUser, idChild);
                frame.dispose();
            }
        });
    }
}

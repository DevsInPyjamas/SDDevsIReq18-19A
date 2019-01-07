import db_management.ApadrinarJoven;
import db_management.DBManager;
import db_management.Joven;
import db_management.Usuario;
import java.awt.Dimension;
import javax.swing.*;

public class ChildDataWithSupport {
    private JPanel childDataToUncheckSupportPanel;
    private JButton atrasButton;
    private JButton desapadrinarButton;
    private JTextField nombreTextField;
    private JTextField apellidosTextField;
    private JTextField fechaNacimientoTextField;
    private JTextField proyectoTextField;
    private JTextField becaTextField;
    private JTextField fechaEntradaTextField;
    private JTextField nombreApadrinadorTextField;
    private JTextField mensualidadTextField;
    private JButton modificarCuotaButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    ChildDataWithSupport(Usuario loggedUser, String idChild) {
        this.loggedUser = loggedUser;
        childDataToUncheckSupportPanel.setSize(700, 600);
        JFrame frame = new JFrame("Información Niño para desapadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(childDataToUncheckSupportPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven child = new Joven(Integer.parseInt(idChild));
        int apadrinarJovenId = (int) dbManager.select("select a.id from apadrinarjoven a " +
                "inner join jovenes j on j.id = a.joven_id where j.id = '" + child.getId() + "';").get(0)[0];
        ApadrinarJoven apadrinarJoven = new ApadrinarJoven(apadrinarJovenId);
        nombreTextField.setText(child.getNombre());
        fechaNacimientoTextField.setText(child.getFechaNacimiento());
        apellidosTextField.setText(child.getApellidos());
        fechaEntradaTextField.setText(child.getFechaEntrada());
        String text = (child.getCurrentProjectID() == -1) ? "" :
                (String) dbManager.select("select nombre from Proyecto where id = '" + child.getCurrentProjectID()
                        + "';").get(0)[0];
        proyectoTextField.setText(text);
        becaTextField.setText(child.getBeca());
        Object[] query = dbManager.select("select concat(s.nombre, ' ', s.apellidos), a.cuota, s.id from socio s inner join apadrinarjoven a " +
                "on a.apadrinador_id = s.id where a.joven_id = " + idChild + ";").get(0);
        nombreApadrinadorTextField.setText((String) query[0]);
        mensualidadTextField.setText(query[1] + " €");
        String idSocio = String.valueOf(query[2]);
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")) {
                new SupportedChildList(loggedUser);
                frame.dispose();
            }
        });
        desapadrinarButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Desapadrinar")) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que quieres terminar este apadrinamiento?",
                        "Confirmación de Desapadrinamiento", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    apadrinarJoven.finApadrinamiento();
                    apadrinarJoven.save();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Se ha terminado el apadrinamiento correctamente");
                    new SupportedChildList(loggedUser);
                    frame.dispose();
                }
            }
        });
        modificarCuotaButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar Cuota")) {
                new ModifySupporFee(loggedUser, idChild, idSocio);
                frame.dispose();
            }
        });
    }
}

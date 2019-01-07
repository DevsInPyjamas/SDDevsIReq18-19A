import db_management.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifySupporFee {
    private JPanel modifySupportFeePanel;
    private JButton atrasButton;
    private JTextField cuotaTextField;
    private JButton modificarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    ModifySupporFee(Usuario loggedUser, String idChild, String idSocio) {
        this.loggedUser = loggedUser;
        modifySupportFeePanel.setSize(700,250);
        JFrame frame = new JFrame("Mensualidad");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(modifySupportFeePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven joven = new Joven(Integer.parseInt(idChild));
        Socio socio = new Socio(Integer.parseInt(idSocio));
        int idApadrinarJoven = (int) dbManager.select("select id from apadrinarjoven where apadrinador_id = '" +
                socio.getId() + "' and joven_id = '" + joven.getId() + "';").get(0)[0];
        ApadrinarJoven apadrinarJoven = new ApadrinarJoven(idApadrinarJoven);
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")){
                new ChildDataToSupport(loggedUser, idChild);
                frame.dispose();
            }
        });
        modificarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Modificar")) {
                apadrinarJoven.setCuota(Double.parseDouble(cuotaTextField.getText()));
                apadrinarJoven.save();
                JOptionPane.showMessageDialog(new JFrame(), "Cuota modificada correctamente");
                new UncheckChildAsSupported(loggedUser);
                frame.dispose();
            }
        });
    }
}

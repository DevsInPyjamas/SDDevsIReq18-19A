import db_management.ApadrinarJoven;
import db_management.Joven;
import db_management.Socio;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SupportChildFee {
    private JPanel supportChildFeePanel;
    private JButton atrasButton;
    private JTextField cuotaTextField;
    private JButton apadrinarButton;
    private Usuario loggedUser;

    SupportChildFee(Usuario loggedUser, String idChild, String idSocio) {
        this.loggedUser = loggedUser;
        supportChildFeePanel.setSize(700,250);
        JFrame frame = new JFrame("Mensualidad");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(supportChildFeePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Joven joven = new Joven(Integer.parseInt(idChild));
        Socio socio = new Socio(Integer.parseInt(idSocio));
        ApadrinarJoven apadrinarJoven = new ApadrinarJoven();
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")){
                new SearchSocioToSupporChild(loggedUser, idChild);
                frame.dispose();
            }
        });
        apadrinarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Apadrinar")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                apadrinarJoven.setJoven(joven);
                apadrinarJoven.setApadrinador(socio);
                apadrinarJoven.setFecha_inicio(dtf.format(LocalDate.now()));
                apadrinarJoven.setCuota(Double.parseDouble(cuotaTextField.getText()));
                apadrinarJoven.save();
                JOptionPane.showMessageDialog(new JFrame(), "Se ha completado el apadrinamiento");
                new SupportChild(loggedUser);
                frame.dispose();
            }
        });
    }
}

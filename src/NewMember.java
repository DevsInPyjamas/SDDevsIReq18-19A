import javax.swing.*;

public class NewMember {
    private JPanel nuevoMiembroPanel;
    private JButton backButton;
    private JLabel nuevoSocioLabel;
    private JLabel nombreLabel;
    private JTextField nombreField;
    private JLabel apellidosLabel;
    private JTextField apellidosField;
    private JLabel fechaNacimientoLabel;
    private JTextField fechaNacimientoField;
    private JLabel telefonoLabel;
    private JTextField telefonoField;
    private JLabel direccionLabel;
    private JTextField direccionField;
    private JLabel poblacionLabel;
    private JTextField poblacionField;
    private JLabel provinciaLabel;
    private JTextField provinciaField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JButton anadirButton;
    Usuario loggedUser;

    NewMember(Usuario loggedUser){
        this.loggedUser = loggedUser;
        nuevoMiembroPanel.setSize(700, 250);
        JFrame frame = new JFrame("Información Miembro");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevoNinoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuples;
        DBManager dbManager = new DBManager();

        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });

        anadirButton.addActionListener((e) -> {
            // Solution adapted from https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
            if(checkIfThereAreNotBlankFields()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                new Socio(nombreField.getText(), apellidosField.getText(), fechaNacimientoFIeld.getText(),
                        telefonoField.getText(), direccionField.getText(), poblacionField.getText(),
                        provinciaField.getText(), emailField.getText());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String entradaToStr = dtf.format(LocalDate.now());
                int idJoven = (int) dbManager.select("select max(id) from Jovenes where nombre like '" +
                        nombreField.getText() + "' and apellidos like '" + apellidosField.getText() + "';").get(0)[0];
                dbManager.execute("insert into Accion(id_proyecto, id_joven, fecha_entrada)" +
                        " values ('" + idProyecto + "', '" + idJoven + "', '" + entradaToStr + "');");

                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
    }
}

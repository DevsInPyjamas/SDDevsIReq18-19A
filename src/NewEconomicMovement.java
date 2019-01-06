import db_management.DBManager;
import db_management.Usuario;
import db_management.Transaccion;

public class NewEconomicMovement {
    private JPanel nuevaTrancision;
    private JButton backButton;
    private JButton addEconomicButton;
    private JTextField emisorField;
    private JTextField conceptoField;
    private JTextField cantidadField;
    private JComboBox tipoGastoField;
    private JLabel tipoGastoField;
    private JLabel cantidadField;
    private JLabel conceptoField;
    private JLabel emisorField;
    Usuario loggedUser;

    NewEconomicMovement(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevaTrancision.setSize(700, 250);
        JFrame frame = new JFramne("Nueva transacción");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevaTrancision);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<Object[]> queryTuples;
        DBManager dbManager = new DBManager();
        if (!loggedUser.getRol().isAdmin()) {
            queryTuples = dbManager.select("select p.nombre from Proyecto p inner join " +
                    "Usuario u on u.pertenece_proyecto = p.id and u.email = '" + loggedUser.getEmail() + "';");

        } else {
            queryTuples = dbManager.select("select nombre from Proyecto");
        }
        backButton.addActionListener((e) -> {
            if (e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });

        addEconomicButton.addActionListener((e) -> {
            if (checkIfThereAreNotBlankFields()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                new Transaccion(emisorField.getText(), conceptoField.getText(), cantidadField.getText(),
                        Objects.requireNonNull(tipoGastoField.getSelectedItem()).toString());

                int tipoGasto = (int) dbManager.select("select id from TipoGasto where nombre like '" +
                        tipoGastoField.getSelectedItem() + "';").get(0)[0];
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                //String entradaToStr = dtf.format(LocalDate.now());
                JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos son correctos");
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
    }
    private boolean checkIfThereAreNotBlankFields() {
        return fechaNacimientoFIeld.getText().isEmpty() ||
                apellidosField.getText().isEmpty() || nombreField.getText().isEmpty();
    }
}
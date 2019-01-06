import db_management.DBManager;
import db_management.Usuario;

public class SearchEconomicMovement {
    private JPanel searchEconomicMovement;
    private JButton atrasButton;
    private JTextField searchMovEconimicTextField;
    private JButton searchButton;
    private JTable seachMovEconomicTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchEconomicMovement(Usuario loggedUser){
        this.loggedUser= loggedUser;
        searchEconomicMovement.setSize(700, 500);
        JFrame frame= new JFrame("Buscar Movimentos Economicos");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchEconomicMovement);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        seachMovEconomicTable.setModel(new DefaultTableModel(new Object[]{"id", "Emisor", "Cantidad", "Tipo de Gasto"}, 6));
        DefaultTableModel model = (DefaultTableModel) seachMovEconomicTable.getModel();
        atrásButton.addActionListener((e)->{
            if(e.getActionCommand().equals("Atrás")){
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });

        buscarButton.addActionListener((e)->{
            if(e.getActionCommand().equals("Buscar")){
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if (searchMovEconimicTextField.getText().isEmpty()) {
                    queryTuples = processWhenSearchWithoutValue();
                } else {
                    queryTuples = normalSearchProcess();
                }
                for(Object[] tuple : queryTuples) {
                    if(!(boolean) tuple[0]) {
                        Object[] row = new Object[tuple.length];
                        row[0] = tuple[1];
                        row[1] = tuple[2];
                        row[2] = tuple[3];
                        row[3] = tuple[3];
                        model.addRow(row);
                    }
                }
            }
        });

        seachMovEconomicTable.getSelectionModel().addListSelectionListener(e -> {
            try {
                int idEcMov = (int) seachMovEconomicTable.getValueAt(seachMovEconomicTable.getSelectedRow(), 0);
                new ModifyEconomicMovement(loggedUser, idProject);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());
            }
        });
    }

    private List<Object[]> normalSearchProcess() {
        return dbManager.select("select isDeleted, id, emisor, concepto, cantidad, tipoGasto from transaccion " +
                "where emisor like '" + searchMovEconimicTextField.getText() + "';");
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        return dbManager.select("select isDeleted, id, emisor, concepto, cantidad, tipoGasto from transaccion");
    }


}

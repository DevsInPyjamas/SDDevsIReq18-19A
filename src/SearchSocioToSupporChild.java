import db_management.DBManager;
import db_management.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.util.List;

public class SearchSocioToSupporChild {
    private JPanel searchSocioToSupportChildPanel;
    private JButton atrasButton;
    private JTable socioToSupportChild;
    private JTextField buscarSocioTextField;
    private JButton buscarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchSocioToSupporChild(Usuario loggedUser, String idChild){
        this.loggedUser = loggedUser;
        searchSocioToSupportChildPanel.setSize(700, 250);
        JFrame frame = new JFrame("Busqueda de socio para apadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchSocioToSupportChildPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        socioToSupportChild.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Asociacion"},
                5));
        DefaultTableModel model = (DefaultTableModel) socioToSupportChild.getModel();
        socioToSupportChild.setFocusable(false);
        socioToSupportChild.setRowSelectionAllowed(false);
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new ChildDataToSupport(loggedUser, idChild);
                frame.dispose();
            }
        });
        buscarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Buscar")) {
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if (buscarSocioTextField.getText().isEmpty()) {
                    queryTuples = processWhenSearchWithoutValue();
                } else {
                    queryTuples = normalSearchProcess();
                }

                for (Object[] tuple : queryTuples) {
                    if(!(boolean) tuple[0]) {
                        Object[] row = new Object[tuple.length];
                        row[0] = tuple[1];
                        row[1] = tuple[2];
                        row[2] = tuple[3];
                        model.addRow(row);
                    }
                }
            }
            socioToSupportChild.setFocusable(true);
            socioToSupportChild.setRowSelectionAllowed(true);
        });
        socioToSupportChild.getSelectionModel().addListSelectionListener(e -> {
            String idSocio = socioToSupportChild.getValueAt(socioToSupportChild.getSelectedRow(), 0).toString();
            new SupportChildFee(loggedUser, idChild, idSocio);
            frame.dispose();
        });

    }

    private List<Object[]> processWhenSearchWithoutValue() {
        List<Object[]> queryTuples;

        queryTuples = dbManager.select("select s.isDeleted, s.id, s.nombre, a.nombre from socio s " +
                "left outer join asociacion a on a.id = s.asociacion");

        return queryTuples;
    }

    private List<Object[]> normalSearchProcess() {
        List<Object[]> queryTuples;

        queryTuples = dbManager.select("select s.isDeleted, s.id, s.nombre, a.nombre from socio s " +
                "left outer join asociacion a on a.id = s.asociacion where s.nombre like '%" +
                buscarSocioTextField.getText() + "%';");

        return queryTuples;
    }
}

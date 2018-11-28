import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchChild {
    private JButton backButton;
    private JPanel searchChildPanel;
    private JTextField searchChildTextField;
    private JTable searchChildTable;
    private JButton buscarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchChild(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        searchChildPanel.setSize(700, 250);
        JFrame frame = new JFrame("Buscar niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchChildPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        searchChildTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "Fecha Nacimiento"}, 5));
        DefaultTableModel model = (DefaultTableModel) searchChildTable.getModel();
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        buscarButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Buscar")) {
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if(searchChildTextField.getText().isEmpty()) {
                   queryTuples = processWhenSearchWithoutValue();
                } else {
                    queryTuples = normalSearchProcess();
                }
                for(Object[] tuple : queryTuples) {
                    model.addRow(tuple);
                }
            }
        });
        searchChildTable.getSelectionModel().addListSelectionListener(e -> {
            System.out.println(searchChildTable.getValueAt(searchChildTable.getSelectedRow(), 0).toString());
        });
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        List<Object[]> query = dbManager.
                select("select pertenece_proyecto from Usuario where email = '" +
                        loggedUser.getEmail() + "';");
        int id_proyecto;
        List<Object[]> queryTuples;
        if (query.get(0)[0] != null) {
             id_proyecto = (int) query.get(0)[0];
             queryTuples = dbManager.select("select j.id, j.nombre, j.apellidos, j.fechaNacimiento " +
                    "from proyecto p left outer join Accion A on " +
                    "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where P.id = '" + id_proyecto + "';");
        } else {
            queryTuples = dbManager.select("select j.id, j.nombre, j.apellidos, j.fechaNacimiento " +
                    "from proyecto p left outer join Accion A on " +
                    "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven;");
        }
        return queryTuples;
    }

    private List<Object[]> normalSearchProcess() {
        int id_proyecto = (int) dbManager.
                select("select pertenece_proyecto from Usuario where email = '" +
                        loggedUser.getEmail() + "';").get(0)[0];
        List<Object[]> queryTuples = dbManager.select("select j.id, j.nombre, j.apellidos, j.fechaNacimiento " +
                "from proyecto p left outer join Accion A on " +
                "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where P.id = '" + id_proyecto + "' " +
                "and J.nombre like '%" + searchChildTextField.getText() + "%';");
        return queryTuples;
    }
}

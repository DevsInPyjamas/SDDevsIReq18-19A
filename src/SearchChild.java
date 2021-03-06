import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
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
        searchChildPanel.setSize(700, 500);
        JFrame frame = new JFrame("Buscar niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setContentPane(searchChildPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        searchChildTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "Proyecto"}, 10));
        DefaultTableModel model = (DefaultTableModel) searchChildTable.getModel();
        searchChildTable.setFocusable(false);
        searchChildTable.setRowSelectionAllowed(false);
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
                    if(!(boolean) tuple[0]) {
                        Object[] row = new Object[tuple.length];
                        row[0] = tuple[1];
                        row[1] = tuple[2];
                        row[2] = tuple[3];
                        row[3] = (!isDeletedThatProject((String) tuple[4])) ? tuple[4] : "";
                        model.addRow(row);
                    }
                }
            }
            searchChildTable.setFocusable(true);
            searchChildTable.setRowSelectionAllowed(true);
        });
        searchChildTable.getSelectionModel().addListSelectionListener(e -> {
            String idChild = searchChildTable.getValueAt(searchChildTable.getSelectedRow(), 0).toString();
            new ChildData(loggedUser, idChild);
            frame.dispose();
        });
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        List<Object[]> query = dbManager.
                select("select pertenece_proyecto from Usuario where email = '" +
                        loggedUser.getEmail() + "';");
        int idProyecto;
        List<Object[]> queryTuples;
        if (!loggedUser.getRol().isSuperAdmin()) {
             idProyecto = (int) query.get(0)[0];
             queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre " +
                    "from proyecto p left outer join Accion A on " +
                    "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where P.id = '" + idProyecto + "';");
        } else {
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre " +
                    "from proyecto p left outer join Accion A on " +
                    "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where p.isDeleted = 0;");
        }
        return queryTuples;
    }

    private List<Object[]> normalSearchProcess() {
        List<Object[]> query = dbManager.
                select("select pertenece_proyecto from Usuario where email = '" +
                        loggedUser.getEmail() + "';");
        List<Object[]> queryTuples;
        int idProyecto;
        if (query.get(0)[0] != null) {
            idProyecto = (int) query.get(0)[0];
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre " +
                    "from proyecto p left outer join Accion A on " +
                    "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where P.id = '" + idProyecto + "' " +
                    "and concat(J.nombre, ' ',J.apellidos) like '%" + searchChildTextField.getText() + "%';");
        } else {
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre " +
                            "from proyecto p left outer join Accion A on " +
                            "A.id_proyecto = P.id left outer join Jovenes J on J.id = A.id_joven where " +
                    "concat(J.nombre, ' ' ,J.apellidos) like '%" + searchChildTextField.getText() + "%';");
        }
        return queryTuples;
    }

    private boolean isDeletedThatProject(String projectName) {
        Object[] query = dbManager.select("select isDeleted from Proyecto where nombre like '" + projectName + "';").get(0);
        return (boolean) query[0];
    }
}

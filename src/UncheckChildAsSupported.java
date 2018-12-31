import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UncheckChildAsSupported {
    private JPanel ucheckChildAsSupported;
    private JButton atrasButton;
    private JTextField searchChildTextField;
    private JTable childrenList;
    private JButton buscarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    UncheckChildAsSupported(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        ucheckChildAsSupported.setSize(700, 250);
        JFrame frame = new JFrame("Lista de niños para desapadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(ucheckChildAsSupported);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        childrenList.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "Proyecto", "Apadrinador"},
                                            5));
        DefaultTableModel model = (DefaultTableModel) childrenList.getModel();
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new SupportChild(loggedUser);
                frame.dispose();
            }
        });
        buscarButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Buscar")) {
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if (searchChildTextField.getText().isEmpty()) {
                    queryTuples = processWhenSearchWithoutValue();
                } else {
                    queryTuples = normalSearchProcess();
                }
                for (Object[] tuple : queryTuples) {
                    if (!(boolean) tuple[0]) {
                        Object[] row = new Object[tuple.length];
                        row[0] = tuple[1];
                        row[1] = tuple[2];
                        row[2] = tuple[3];
                        row[3] = (!isDeletedThatProject((String) tuple[4])) ? tuple[4] : "";
                        row[4] = tuple[5];
                        model.addRow(row);
                    }
                }
            }
        });
        childrenList.getSelectionModel().addListSelectionListener(e -> {
            String idChild = childrenList.getValueAt(childrenList.getSelectedRow(), 0).toString();
            new ChildDataToUncheckSupport(loggedUser, idChild);
            frame.dispose();
        });
    }

    private List<Object[]> normalSearchProcess() {
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        List<Object[]> queryTuples;
        int idProyecto;
        if (query.get(0)[0] != null) {
            idProyecto = (int) query.get(0)[0];
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre, s.nombre " +
                    "from proyecto p left outer join Accion a on a.id_proyecto = p.id left outer join " +
                    "jovenes j on j.id = a.id_joven inner join apadrinarjoven ap on ap.joven_id = j.id " +
                    "inner join Socio s on s.id = ap.apadrinador_id where p.id = '" + idProyecto + "' and " +
                    "concat(j.nombre, ' ', j.apellidos) like '%" + searchChildTextField.getText() + "%' " +
                    "and ap.fecha_fin is null;");
        } else {
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre, s.nombre " +
                    "from proyecto p left outer join Accion a on a.id_proyecto = p.id left outer join " +
                    "jovenes j on j.id = a.id_joven inner join apadrinarjoven ap on ap.joven_id = j.id "+
                    "inner join Socio s on s.id = ap.apadrinador_id where p.isDeleted = 0 and " +
                    "concat(j.nombre, ' ', j.apellidos) like '%" + searchChildTextField.getText() + "%' " +
                    "and ap.fecha_fin is null;");
        }
        return queryTuples;
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        List<Object[]> queryTuples;
        int idProyecto;
        if (query.get(0)[0] != null) {
            idProyecto = (int) query.get(0)[0];
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre, s.nombre " +
                    "from proyecto p left outer join Accion a on a.id_proyecto = p.id left outer join " +
                    "jovenes j on j.id = a.id_joven inner join apadrinarjoven ap on ap.joven_id = j.id " +
                    "inner join Socio s on s.id = ap.apadrinador_id where p.id = '" + idProyecto + "' " +
                    "and ap.fecha_fin is null;");
        } else {
            queryTuples = dbManager.select("select j.isDeleted, j.id, j.nombre, j.apellidos, p.nombre, s.nombre " +
                    "from proyecto p left outer join Accion a on a.id_proyecto = p.id left outer join " +
                    "jovenes j on j.id = a.id_joven inner join apadrinarjoven ap on ap.joven_id = j.id "+
                    "inner join Socio s on s.id = ap.apadrinador_id where p.isDeleted = 0 " +
                    "and ap.fecha_fin is null;");
        }
        return queryTuples;
    }

    private boolean isDeletedThatProject(String projectName) {
        Object[] query = dbManager.select("select isDeleted from Proyecto where nombre like '" + projectName + "';").get(0);
        return (boolean) query[0];
    }
}
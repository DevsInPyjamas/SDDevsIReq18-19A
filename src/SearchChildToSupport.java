import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchChildToSupport {
    private JPanel searchChildToSupport;
    private JButton atrasButton;
    private JButton buscarButton;
    private JTable childrenList;
    private JTextField searchChildTextField;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchChildToSupport(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        searchChildToSupport.setSize(700,250);
        JFrame frame = new JFrame("Lista de niños para apadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchChildToSupport);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        childrenList.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "Proyecto"}, 10));
        DefaultTableModel model = (DefaultTableModel) childrenList.getModel();
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")){
                new SupportChild(loggedUser);
                frame.dispose();
            }
        });
        buscarButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Buscar")) {
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if(searchChildTextField.getText().isEmpty()) {
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
                        row[3] = (!isDeletedThatProject((String) tuple[4])) ? tuple[4] : "";
                        model.addRow(row);
                    }
                }
            }
        });
        childrenList.getSelectionModel().addListSelectionListener(e -> {
            //TODO Preguntar a aleks
        });
    }

    private List<Object[]> normalSearchProcess() {
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        List<Object[]> queryTuples;
        int idProyecto;
        if(query.get(0)[0] != null) {
            idProyecto = (int) query.get(0)[0];
            queryTuples = dbManager.select("");
        } else {
            queryTuples = dbManager.select("");
        }
        return queryTuples;
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        List<Object[]> queryTuples;
        int idProyecto;
        if(query.get(0)[0] != null) {
            idProyecto = (int) query.get(0)[0];
            queryTuples = dbManager.select("");
        } else {
            queryTuples = dbManager.select("");
        }
        return queryTuples;
    }

    private boolean isDeletedThatProject(String projectName) {
        Object[] query = dbManager.select("select isDeleted from Proyecto where nombre like '" + projectName + "';").get(0);
        return (boolean) query[0];
    }
}

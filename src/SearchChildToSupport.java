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
            // Hola :)
            // He modificado algunas cosas de la búsqueda el problema es que a la hora de buscar
        });
    }

    private List<Object[]> normalSearchProcess() {
        //TODO No sé si ese es el campo de búsqueda pero mete entre las comillas simples del exec NoApadrinados
        // los datos a buscar, ejemplo, si quiero buscar a pedro amador hago lo siguiente:
        // exec NoApadrinados 'pedro amador', entonces, lo que se coja de la barra de búsqueda, es lo que hay
        // que meter dentro del parametro de exec
        return dbManager.select("exec NoApadrinados '" + this.searchChildTextField.getText() + "';");
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        //TODO el procedimiento está programado para que cuando reciba un null devuelva a todos los chavales
        return dbManager.select("exec NoApadrinados null;");
    }

    private boolean isDeletedThatProject(String projectName) {
        Object[] query = dbManager.select("select isDeleted from Proyecto where nombre like '" + projectName + "';").get(0);
        return (boolean) query[0];
    }
}

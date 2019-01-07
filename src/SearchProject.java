import db_management.DBManager;
import db_management.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchProject {
    private JPanel searchProjectPanel;
    private JButton atrásButton;
    private JTextField searchProjectTextField;
    private JButton buscarButton;
    private JTable searchProjectTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchProject(Usuario loggedUser){
        this.loggedUser= loggedUser;
        searchProjectPanel.setSize(700, 500);
        JFrame frame= new JFrame("Buscar proyecto");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchProjectPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        searchProjectTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Coordinador", "Responsable"}, 5));
        DefaultTableModel model = (DefaultTableModel) searchProjectTable.getModel();
        searchProjectTable.setFocusable(false);
        searchProjectTable.setRowSelectionAllowed(false);
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
                if (searchProjectTextField.getText().isEmpty()) {
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
            searchProjectTable.setFocusable(true);
            searchProjectTable.setRowSelectionAllowed(true);
        });
        searchProjectTable.getSelectionModel().addListSelectionListener(e -> {
            try {
                int idProject = (int) searchProjectTable.getValueAt(searchProjectTable.getSelectedRow(), 0);
                new ModifyProject(loggedUser, idProject);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());
            }
        });
    }

    private List<Object[]> normalSearchProcess() {
        return dbManager.select("select isDeleted, id, nombre, project_coordinator, project_responsable from proyecto " +
                "where nombre like '" + searchProjectTextField.getText() + "';");
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        return dbManager.select("select isDeleted, id, nombre, project_coordinator, project_responsable from proyecto");
    }


}

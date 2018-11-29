import db_management.DBManager;
import db_management.Usuario;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchProject {
    private JPanel searchProjectPanel;
    private JButton atrásButton;
    private JTextField searchProjectTextField;
    private JTable seachProjectTable;
    private JButton buscarButton;
    private JTable searchProjectTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchProject(Usuario loggedUser){
        this.loggedUser= loggedUser;
        JFrame frame= new JFrame("Buscar proyecto");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchProjectPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        seachProjectTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Ubicacion", "Coordinador",
                "Responsable"}, 5));
        DefaultTableModel model = (DefaultTableModel) seachProjectTable.getModel();
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
                    model.addRow(tuple);
                }
            }
        });
        seachProjectTable.getSelectionModel().addListSelectionListener(e -> {
            String idProject = seachProjectTable.getValueAt(seachProjectTable.getSelectedRow(), 0).toString();
            new ModifyProject(); //TODO Pasar loggedUser y idProject.
        });
    }

    private List<Object[]> normalSearchProcess() {
        //TODO
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        int idProyecto;
        List<Object[]> queryTuples = null;

        if(query.get(0)[0] != null) {

        } else {

        }

        return queryTuples;
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        //TODO
        List<Object[]> query = dbManager.select("select pertenece_proyecto from Usuario where email = '" +
                loggedUser.getEmail() + "';");
        int idProyecto;
        List<Object[]> queryTuples = null;

        if(query.get(0)[0] != null) {

        } else {

        }

        return queryTuples;    }


}

import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;

public class SearchMember {
    private JButton atrasButton;
    private JTextField searchMemberTextField;
    private JPanel searchMemberPanel;
    private JButton buscarButton;
    private JTable searchMemberTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    public SearchMember(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        searchMemberPanel.setSize(700,250);
        JFrame frame = new JFrame("Información Miembro");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(searchMemberPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        searchMemberTable.setFocusable(false);
        searchMemberTable.setRowSelectionAllowed(false);
        searchMemberTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "DNI", "Asociacion"}, 6));
        DefaultTableModel model = (DefaultTableModel) searchMemberTable.getModel();
        atrasButton.addActionListener((e)->{
            new GrantManagement(loggedUser);
            frame.dispose();
        });
        buscarButton.addActionListener(e -> {
            model.setRowCount(0);
            java.util.List<Object[]> queryTuples;
            if (searchMemberTextField.getText().isEmpty()) {
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
                    row[3] = tuple[4];
                    model.addRow(row);
                }
            }
            searchMemberTable.setFocusable(true);
            searchMemberTable.setRowSelectionAllowed(true);
        });
        searchMemberTable.getSelectionModel().addListSelectionListener(e -> {
            try {
                int idSocio = (int) searchMemberTable.getValueAt(searchMemberTable.getSelectedRow(), 0);
                new ModifyMember(loggedUser, idSocio);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());
            }
        });
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        //TODO hacer que si el notas es super admin de españa tenga acceso a todos
        // los usuarios
        int loggedUserAssociationID = loggedUser.getAsociacion().getId();
        if(loggedUser.getRol().isSuperSpanishAdmin()) {
            return dbManager.select("select s.isDeleted, s.id, concat(s.nombre, ' ', s.apellidos), s.dni, a.nombre from" +
                    " socio s inner join asociacion a on s.asociacion = a.id;");
        } else {
            return dbManager.select("select s.isDeleted, s.id, concat(s.nombre, ' ', s.apellidos), s.dni, a.nombre from" +
                    " socio s inner join asociacion a on s.asociacion = a.id where s.asociacion = '"
                    + loggedUserAssociationID + "';");
        }
    }

    private List<Object[]> normalSearchProcess() {
        //TODO hacer que si el notas es super admin de españa tenga acceso a todos
        // los usuarios
        String searchInput = searchMemberTextField.getText();
        int loggedUserAssociationID = loggedUser.getAsociacion().getId();
        if(loggedUser.getRol().isSuperSpanishAdmin()) {
            return dbManager.select("select s.isDeleted, s.id, concat(s.nombre, ' ', s.apellidos), s.dni, a.nombre from" +
                    " socio s inner join asociacion a on s.asociacion = a.id where"
                     + " (concat(s.nombre, ' ', s.apellidos) like '" +
                    searchInput + "' or s.dni like '" + searchInput + "');");
        } else {
            return dbManager.select("select s.isDeleted, s.id, concat(s.nombre, ' ', s.apellidos), s.dni, a.nombre from" +
                    " socio s inner join asociacion a on s.asociacion = a.id where s.asociacion = '"
                    + loggedUserAssociationID + "' and (concat(s.nombre, ' ', s.apellidos) like '" +
                    searchInput + "' or s.dni like '" + searchInput + "');");
        }
    }
}

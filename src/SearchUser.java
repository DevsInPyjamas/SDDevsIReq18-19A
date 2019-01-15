import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchUser {
    private JPanel buscarUserPanel;
    private JButton backButton;
    private JLabel busquedaUsuarioLabel;
    private JTextField buscadorField;
    private JButton buscadorButton;
    private JTable searchUserTable;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    SearchUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        buscarUserPanel.setSize(800, 500);
        JFrame frame = new JFrame("Lista de Usuarios");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setContentPane(buscarUserPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        searchUserTable.setModel(new DefaultTableModel(new Object[]{"email", "Usuario", "Nombre", "Rol"},
                5));
        DefaultTableModel model = (DefaultTableModel) searchUserTable.getModel();
        searchUserTable.setFocusable(false);
        searchUserTable.setRowSelectionAllowed(false);
        backButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
        buscadorButton.addActionListener(e -> {
           if (e.getActionCommand().equals("Buscar")) {
               model.setRowCount(0);
               List<Object[]> queryTuples;
               if (buscadorField.getText().isEmpty()) {
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
                        String nombreRol = (String) dbManager.select("select nombre from Rol where id = " +
                                tuple[4]).get(0)[0];
                        row[3] = nombreRol;
                        model.addRow(row);
                    }
               }
           }
           searchUserTable.setFocusable(true);
           searchUserTable.setRowSelectionAllowed(true);
        });
        searchUserTable.getSelectionModel().addListSelectionListener(e -> {
            try {
                String emailUsuario = (String) searchUserTable.getValueAt(searchUserTable.getSelectedRow(), 0);
                new ModifyUser(loggedUser, emailUsuario);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());
            }
        });
    }

    private List<Object[]> normalSearchProcess() {
        return dbManager.select("select isDeleted, email, usuario, nombre, rol_id from usuario where email like '%" +
                buscadorField.getText() + "%';");
    }

    private List<Object[]> processWhenSearchWithoutValue() {
        return dbManager.select("select isDeleted, email, usuario, nombre, rol_id from usuario");
    }
}

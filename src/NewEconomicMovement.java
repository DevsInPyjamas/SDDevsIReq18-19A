import db_management.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewEconomicMovement {
    private JPanel nuevaTrancision;
    private JButton backButton;
    private JButton addEconomicButton;
    private JTextField emisorField;
    private JTextField conceptoField;
    private JTextField cantidadField;
    private JComboBox tipoGastoComboBox;
    private JComboBox beneficiarioComboBox;
    private JComboBox tipoBeneficiarioComboBox;
    private JComboBox comboBoxProyecto;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    NewEconomicMovement(Usuario loggedUser, Boolean typeOfTransaction) {
        this.loggedUser = loggedUser;
        comboBoxProyecto.setVisible(false);
        nuevaTrancision.setSize(700, 250);
        JFrame frame = new JFrame("Nueva transacción");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 600));
        frame.setContentPane(nuevaTrancision);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        initializeFields(loggedUser, typeOfTransaction);
        backButton.addActionListener((e) -> {
            new TypeOfTransaction(loggedUser);
            frame.dispose();
        });
        tipoBeneficiarioComboBox.addActionListener(e -> {
            if(tipoBeneficiarioComboBox.getSelectedItem() != null) {
                beneficiarioComboBox.removeAllItems();
                fillTheBeneficiario(loggedUser, (String) tipoBeneficiarioComboBox.getSelectedItem());
            }
        });
        addEconomicButton.addActionListener((e) -> {
            if (!everyMandatoryFieldIsFilled()) {
                JOptionPane.showMessageDialog(new JFrame(), "Hay campos obligatorios en blanco");
            } else {
                Transaccion t = buildTransaction(loggedUser, typeOfTransaction, (String) tipoBeneficiarioComboBox.getSelectedItem());
                try {
                    t.save();
                    JOptionPane.showMessageDialog(new JFrame(), "Se han guardado los datos");
                    new EconomicSection(loggedUser);
                    frame.dispose();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Los datos introducidos no son correctos");
                }
            }
        });
    }

    private Transaccion buildTransaction(Usuario loggedUser, Boolean whatKindOfTransaction, String table) {
        Transaccion t = new Transaccion();
        t.setEmisor(emisorField.getText());
        t.setConcepto(conceptoField.getText());
        int tipoGastoID = (int) dbManager.select("select id from TipoGasto where nombre like '" +
                tipoGastoComboBox.getSelectedItem() + "';").get(0)[0];
        t.setTipoGasto(new TipoGasto(tipoGastoID));
        t.setCantidad(Double.valueOf(cantidadField.getText()));
        if(!loggedUser.getRol().isSuperAdmin() && !loggedUser.getRol().spanishBoy()) {
            t.setProyecto(loggedUser.getProyecto());
        } else {
            int proyectoID = (int) dbManager.select("select id from proyecto where nombre like '" +
                    this.comboBoxProyecto.getSelectedItem() + "';").get(0)[0];
            t.setProyecto(new Proyecto(proyectoID));
        }
        if(tipoBeneficiarioComboBox.getSelectedItem() != null) {
            String query = (table.equals("Jovenes")) ? "select id from Jovenes where concat(nombre, ' ', apellidos) like " +
                    " '" + this.beneficiarioComboBox.getSelectedItem() + "';" : "select id from Colaborador where nombre like '" +
                    this.beneficiarioComboBox.getSelectedItem() + "';";
            Integer beneficiarioID  = (Integer) dbManager.select(query).get(0)[0]; //= (int) dbManager.select("exec DatosBeneficiario " + t.getId()).get(0)[0];
            t.setBeneficiario(beneficiarioID);
            t.setTablaBeneficiario(table);
        }
        t.setWhatKindOfTransactionIs(whatKindOfTransaction);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        t.setFecha(dtf.format(now));
        return t;
    }

    private void fillTheBeneficiario(Usuario loggedUser, String selectedItem) {
        String query = buildQueryString(loggedUser, selectedItem);
        List<Object[]> queryTuples2 = dbManager.select(query);
        beneficiarioComboBox.addItem(null);
        for(Object[] tuple : queryTuples2) {
            beneficiarioComboBox.addItem(tuple[0]);
        }
    }

    private String buildQueryString(Usuario loggedUser, String selectedItem) {
        String queryString = "", select = "";
        if (selectedItem.equals("Jovenes")) {
            select = "select concat(nombre, ' ', apellidos) from " + selectedItem;
            if (!loggedUser.getRol().isSuperAdmin()) {
                queryString += " inner join inner join Accion A on Jovenes.id = A.id_joven where a.id_proyecto = " +
                        loggedUser.getProyecto().getId();
            }
        } else if (selectedItem.equals("Colaborador") || selectedItem.equals("Empresa")) {
            select  = "select nombre from " + selectedItem;
            if (!loggedUser.getRol().isSuperAdmin()) {
                queryString += " where pertenece_proyecto = " + loggedUser.getProyecto().getId();
            }
        }
        queryString += ";";
        return select + queryString;
    }

    private void initializeFields(Usuario loggedUser, Boolean typeOfTransaction) {
        List<Object[]> queryTuples;
        comboBoxProyecto.setVisible(true);
        tipoBeneficiarioComboBox.addItem(null);
        if(typeOfTransaction == null)
            tipoBeneficiarioComboBox.addItem("Empresa");
        tipoBeneficiarioComboBox.addItem("Colaborador");
        tipoBeneficiarioComboBox.addItem("Jovenes");
        if(loggedUser.getRol().isSuperAdmin() || loggedUser.getRol().spanishBoy()) {
            comboBoxProyecto.setVisible(true);
            queryTuples = dbManager.select("select nombre from Proyecto;");
            for(Object[] tuple : queryTuples) {
                comboBoxProyecto.addItem(tuple[0]);
            }
        } else {
            comboBoxProyecto.addItem(loggedUser.getProyecto().getNombre());
        }
        comboBoxProyecto.setEditable(false);
        queryTuples = dbManager.select("select nombre from TipoGasto;");
        for(Object[] tuple : queryTuples) {
            tipoGastoComboBox.addItem(tuple[0]);
        }
        if(loggedUser.getRol().spanishBoy()) {
            if (!loggedUser.getRol().isSuperAdmin() && !loggedUser.getRol().isSuperSpanishAdmin()) {
                queryTuples = dbManager.select("select isDeleted, concat(nombre, ' ', apellidos)" + " from Socio " +
                        "where asociacion = '" + loggedUser.getAsociacion().getId() + "';");
            } else {
                queryTuples = dbManager.select("select isDeleted, concat(nombre, ' ', apellidos)" + " from Socio;");
            }
        } else {
            queryTuples = dbManager.select("select * from Socio where nombre like 'Jesucristosuperestrellalol12345';");
        }
        tipoBeneficiarioComboBox.addItem(null);
        for(Object[] tuple : queryTuples) {
            if(!(boolean) tuple[0]) {
                tipoBeneficiarioComboBox.addItem(tuple[1]);
            }
        }
    }

    private boolean everyMandatoryFieldIsFilled() {
        return (beneficiarioComboBox.getSelectedItem() != null || tipoBeneficiarioComboBox.getSelectedItem() != null)
                && tipoGastoComboBox.getSelectedItem() != null && !emisorField.getText().isEmpty() &&
                !cantidadField.getText().isEmpty();
    }
}
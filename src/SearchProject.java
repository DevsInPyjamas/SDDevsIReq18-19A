import javax.swing.*;

public class SearchProject {
    private JPanel searchProjectPanel;
    private JButton atrásButton;
    private JTextField searchProjectTextField;
    private JTable seachProjectTable;
    private JButton buscarButton;
    private Usuario loggedUser;
    private DBManager dbManager = new DBManager();

    seachProject(Usuario loggedUser){
        this.loggedUser= loggedUser;
        JFrame frame= new JFrame("Buscar proyecto");
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchProjectPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        seachProjectTable.setModel(new DefaultTableModel(new Object[]{"id", "Nombre", "Apellidos", "Fecha Nacimiento"}));
        DefaultTableModel model = (DefaultTableModel) seachProjectTable.getModel();
        atrásButton.addActionListener((e)->{
            if(e.getActionCommand().equals("Atrás")){
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        buscarButton.addActionListener((e)->){
            if(e.getActionCommand().equals("Buscar")){
                model.setRowCount(0);
                List<Object[]> queryTuples;
                if(searchProjectTextField.getText().isEmpty()){
                    queryTuples=processWhenSearchWithoutValue();
                }else{
                    queryTuples= normalSearchProcess();
                }
                for(Object[] tuple : queryTuples){
                    model.addRow(tuple);
                }
            }
        });
    }



}

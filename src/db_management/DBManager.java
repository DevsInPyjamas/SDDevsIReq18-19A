package db_management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.*;

public class DBManager {
    private SQLServerDataSource dataSource;
    private Connection connection;

    public DBManager(String server, String databaseName) {
        try {
            dataSource = new SQLServerDataSource();
            dataSource.setUser("alkesst");
            dataSource.setPassword("1234");
            dataSource.setServerName(server);
            dataSource.setPortNumber(1433);
            dataSource.setDatabaseName(databaseName);
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new Error("Error al Conectar con la base de datos." + ex.getMessage());
        }
    }

    protected void finalize() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            throw new Error("Error al Cerrar la Conexión." + ex.getMessage());
        }
    }

    public List<Object[]> select(String sel) {
        ResultSet rset;
        List<Object[]> lista = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            rset = stmt.executeQuery(sel);
            ResultSetMetaData meta = rset.getMetaData();
            int numCol = meta.getColumnCount();
            while (rset.next()) {
                Object[] tupla = new Object[numCol];
                for (int i = 0; i < numCol; ++i) {
                    tupla[i] = rset.getObject(i + 1);
                }
                lista.add(tupla);
            }
            rset.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new Error("Error en el SELECT: " + sel + ". " + ex.getMessage());
        }

        return lista;
    }

    public void execute(String ins) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(ins);
            stmt.close();
        } catch (SQLException ex) {
            throw new Error("Error en el INSERT: " + ins + ". " + ex.getMessage());
        }
    }


}

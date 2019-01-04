package db_management;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class TipoGasto {
    private String nombre;
    private int id;

    public TipoGasto() {
    }

    public TipoGasto(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from TipoGasto where id = '" + id +"';");
        if(!query.isEmpty()) {
            this.nombre = (String) query.get(0)[1];
            this.id = (int) query.get(0)[0];
        } else {
            throw new NoSuchElementException("No existe el tipo de gasto con id: " + id);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if(id != 0) {
            dbManager.execute("Update TipoGasto set nombre = '" + nombre + "' where id = '"
            + this.id + "';");
        } else {
            dbManager.execute("insert into TipoGasto(nombre) values ('" + nombre + "')");
            this.id = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
    }

    @Override
    public String toString() {
        return "TipoGasto{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return this.id;
    }
}

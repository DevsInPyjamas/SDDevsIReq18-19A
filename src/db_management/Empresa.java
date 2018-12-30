package db_management;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class Empresa {
    private int id;
    private String nombre;

    public Empresa() {
    }

    public Empresa(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from Empresa where id = '" + id +"';");
        if(!query.isEmpty()) {
            this.id = id;
            this.nombre = (String) query.get(0)[0];
        } else {
            throw new NoSuchElementException("No existe la Empresa con id: " + id);
        }
    }

    public int getId() {
        return id;
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
            dbManager.execute("update Empresa set nombre = '" + this.nombre + "' where id = " +  this.id + ";");
        } else {
            dbManager.execute("insert into Empresa(nombre) values ('" + this.nombre + "');");
            this.id = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

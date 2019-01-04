package db_management;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class Empresa {
    private int id;
    private String nombre;
    private String nif;
    private String direccion;

    public Empresa() {
    }

    public Empresa(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from Empresa where id = '" + id +"';");
        if(!query.isEmpty()) {
            this.id = id;
            this.nombre = (String) query.get(0)[0];
            this.nif = (String) query.get(0)[2];
            this.direccion = (String) query.get(0)[3];
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

    public String getNif() {
        return nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if(id != 0) {
            dbManager.execute("update Empresa set nombre = '" + this.nombre + "'" +
                    ", direccion = '" + this.direccion + "', nif = '" + this.nif + "' where id = " +  this.id + ";");
        } else {
            dbManager.execute("insert into Empresa(nombre, direccion, nif) values ('" + this.nombre + "'," +
                    "'" + this.direccion + "', '" + this.nif + "');");
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

package db_management;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class Asociacion {
    private int id;
    private String nombre;
    private String localizacion;
    private boolean isDeleted;

    public Asociacion() {
    }

    public Asociacion(int idAsociacion) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from Asociacion where id = '" + idAsociacion + "';");
        if(!query.isEmpty()) {
            Object[] row = query.get(0);
            this.id = idAsociacion;
            this.nombre = (String) row[1];
            this.localizacion = (String) row[2];
            this.isDeleted = (boolean) row[3];
        } else {
            throw new NoSuchElementException("No existe asociación con id: " + idAsociacion);
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public int getId() {
        return this.id;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if(this.id != 0) {
            int isDeletedToInt = (this.isDeleted) ? 1 : 0;
            dbManager.execute("update Asociacion set " +
                    "nombre = '" + this.nombre + "'," +
                    "localizacion = '" + this.localizacion + "'," +
                    "isDeleted = " + isDeletedToInt + " where id = " + this.id + ";");
        } else {
            dbManager.execute("insert into Asociacion(nombre, localizacion) values ('" + this.nombre + "','"
                    + this.localizacion + "');");
            this.id  = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
    }

    @Override
    public String toString() {
        return "Asociacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

package db_management;

import java.util.List;
import java.util.NoSuchElementException;

public class TipoGasto {
    private String nombre;

    public TipoGasto() {
    }

    public TipoGasto(String nombre) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from TipoGasto where nombre = '" + nombre +"';");
        if(!query.isEmpty()) {
            this.nombre = (String) query.get(0)[0];
        } else {
            throw new NoSuchElementException("No existe el tipo de gasto con id: " + nombre);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoGasto{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}

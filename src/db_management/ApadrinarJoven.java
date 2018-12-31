package db_management;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

public class ApadrinarJoven {
    private int id;
    private int apadrinador_id;
    private int joven_id;
    private double cuota;
    private String fecha_inicio;
    private String fecha_fin;

    public ApadrinarJoven() {
    }

    public ApadrinarJoven(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from apadrinarjoven " +
                "where id = '" + id + "';");
        if (!query.isEmpty()) {
            Object[] row = query.get(0);
            this.id = id;
            this.apadrinador_id = (Integer) row[1];
            this.joven_id = (Integer) row[2];
            this.cuota = Double.parseDouble(row[3].toString());
            this.fecha_inicio = row[4].toString();
            if(row[5] != null) {
                this.fecha_fin = row[5].toString();
            } else {
                this.fecha_fin = null;
            }
        } else {
            throw new NoSuchElementException("No existe el apadrinamiento.");
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApadrinador_id() {
        return apadrinador_id;
    }

    public void setApadrinador_id(int apadrinador_id) {
        this.apadrinador_id = apadrinador_id;
    }

    public int getJoven_id() {
        return joven_id;
    }

    public void setJoven_id(int joven_id) {
        this.joven_id = joven_id;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void finApadrinamiento() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.setFecha_fin(dtf.format(LocalDate.now()));
    }

    public void save() {
        
    }

    @Override
    public String toString() {
        return "ApadrinarJoven{" +
                "id=" + id +
                ", apadrinador_id=" + apadrinador_id +
                ", joven_id=" + joven_id +
                ", cuota=" + cuota +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                '}';
    }
}

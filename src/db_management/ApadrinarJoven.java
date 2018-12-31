package db_management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

public class ApadrinarJoven {
    private int id;
    private int apadrinador_id;
    private Socio apadrinador;
    private int joven_id;
    private Joven joven;
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
            this.fecha_fin = (String) row[5];
        } else {
            throw new NoSuchElementException("No existe el apadrinamiento con id: " + id);
        }
    }

    public void setApadrinador(Socio apadrinador) {
        this.apadrinador = apadrinador;
        this.apadrinador_id = apadrinador.getId();
    }

    public void setJoven(Joven joven) {
        this.joven = joven;
        this.joven_id = joven.getId();
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

    public int getId() {
        return id;
    }

    public Socio getApadrinador() {
        apadrinador = new Socio(this.apadrinador_id);
        return apadrinador;
    }

    public Joven getJoven() {
        joven = new Joven(this.joven_id);
        return joven;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void finApadrinamiento() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.setFecha_fin(dtf.format(LocalDate.now()));
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if(this.id != 0) {
            dbManager.execute("Update ApadrinarJoven set " +
                    " apadrinador_id = " + this.apadrinador_id +
                    ", joven_id = " + this.joven_id + ", cuota = " + this.cuota + ", fecha_fin  = '" + this.fecha_fin +
                    "' where id = " + this.id + ";");
        } else {
            dbManager.execute("Insert into ApadrinarJoven(apadrinador_id, joven_id, cuota, fecha_inicio)" +
                    "values (" + this.apadrinador_id + ", " + this.joven_id + ", " + this.cuota + ", '" +
                    this.fecha_inicio + "');");
            this.id  = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
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

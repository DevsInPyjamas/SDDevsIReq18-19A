package db_management;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Joven {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";

    private int id;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String nombreMadre;
    private String nombrePadre;
    private String historial;
    private String datosComunidad;
    private String genero;
    private String observaciones;
    private String fechaEntrada;
    private String fechaBaja;
    private String beca;
    private float notaMedia;
    private boolean isDeleted;
    private int currentProjectID;

    public Joven(int id){
        DBManager db = new DBManager();

        Object[] tuples = db.select("SELECT * FROM Jovenes WHERE id = '" + id + "';").get(0);
        this.id = (int) tuples[0];
        this.nombre = (String)tuples[1];
        this.nombreMadre = (String)tuples[2];
        this.nombrePadre = (String)tuples[3];
        this.apellidos = (String)tuples[4];
        this.historial = (String)tuples[5];
        this.datosComunidad = (String)tuples[6];
        this.genero = (String)tuples[7];
        this.observaciones = (String) tuples[8];
        this.fechaEntrada = tuples[9].toString();
        this.beca = (String)tuples[11];
        this.fechaNacimiento =  tuples[12].toString();
        this.isDeleted = (boolean) tuples[13];
        this.notaMedia = (float) tuples[14];
        List<Object[]> queryTuples = db.select("select p.id from Accion a inner join Proyecto p on p.id = a.id_proyecto" +
                " inner join Jovenes j on j.id = a.id_joven where fecha_salida is null and j.id = '" + this.id + "';");
        this.currentProjectID = (!queryTuples.isEmpty()) ? (int) queryTuples.get(0)[0] : -1;
    }

    public Joven(String nombre, String apellidos, String fechaNacimiento,
                 String nombreMadre, String nombrePadre, String historial, String datosComunidad,
                 String genero, String observaciones, String beca, float notaMedia)  {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        String entradaToStr = dtf.format(localDate);

        DBManager db = new DBManager();
        db.execute("INSERT INTO Jovenes (nombre, apellidos, fechaNacimiento, nombreMadre, nombrePadre, historial," +
                "datosComunidad, genero, observaciones, fechaEntrada, beca, notaMedia) VALUES('" + nombre + "','" + apellidos + "','"
                + fechaNacimiento + "','" + nombreMadre + "','" + nombrePadre + "','" + historial + "','"
                + datosComunidad + "','" + genero + "','" + observaciones + "','" + entradaToStr + "','"
                + beca + "', '" + notaMedia + "');");
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.nombreMadre = nombreMadre;
        this.nombrePadre = nombrePadre;
        this.historial = historial;
        this.datosComunidad = datosComunidad;
        this.genero = genero;
        this.observaciones = observaciones;
        this.fechaEntrada = entradaToStr;
        this.beca = beca;
        this.notaMedia = notaMedia;
    }

    public void setId(int id) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET id = '" + id + "' WHERE id = '" + this.id + "';");
        this.id = id;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET nombre = '" + nombre + "' WHERE id = '" + this.id + "';");
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET apellidos = '" + apellidos + "' WHERE id = '" + this.id + "';");
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nacimientoToStr = format.format(fechaNacimiento);

        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET FechaNacimiento = '" + nacimientoToStr + "' WHERE id = '" + this.id + "';");
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNombreMadre(String nombreMadre) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET nombreMadre = '" + nombreMadre + "' WHERE id = '" + this.id + "';");
        this.nombreMadre = nombreMadre;
    }

    public void setNombrePadre(String nombrePadre) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET nombrePadre = '" + nombrePadre + "' WHERE id = '" + this.id + "';");
        this.nombrePadre = nombrePadre;
    }

    public void setHistorial(String historial) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET historial = '" + historial + "' WHERE id = '" + this.id + "';");
        this.historial = historial;
    }

    public void setDatosComunidad(String datosComunidad) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET datosComunidad = '" + datosComunidad + "' WHERE id = '" + this.id + "';");
        this.datosComunidad = datosComunidad;
    }

    public void setGenero(String genero) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET genero = '" + genero + "' WHERE id = '" + this.id + "';");
        this.genero = genero;
    }

    public void setObservaciones(String observaciones) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET observaciones = '" + observaciones + "' WHERE id = '" + this.id + "';");
        this.observaciones = observaciones;
    }

    public void setFechaEntrada(String fechaEntrada) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET fechaEntrada = '" + fechaEntrada + "' WHERE id = '" + this.id + "';");
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaBaja(String fechaBaja) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET fechaBaja = '" + fechaBaja + "' WHERE id = '" + this.id + "';");
        this.fechaBaja = fechaBaja;
    }


    public void setBeca(String beca) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET beca = '" + beca + "' WHERE id = '" + this.id + "';");
        this.beca = beca;
    }

    public void setNotaMedia(float notaMedia) {
        DBManager db = new DBManager();
        db.execute("UPDATE Jovenes SET notaMedia = '" + notaMedia + "' WHERE id = '" + this.id + "';");
        this.notaMedia = notaMedia;
    }

    public void setIsDeleted(boolean newValue) {
        DBManager db = new DBManager();
        int value = (newValue)? 1 : 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);
        db.execute("UPDATE Jovenes SET isDeleted = '" + value + "' WHERE id = '" + this.id + "';");
        db.execute("UPDATE Jovenes set fechaBaja = '" + today + "' where id = '" + this.id + "';");
        db.execute("UPDATE Accion set fecha_salida = '" + today + "' where id_joven ='" + this.id + "';");
        this.currentProjectID = -1;
        this.isDeleted = newValue;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public String getHistorial() {
        return historial;
    }

    public String getDatosComunidad() {
        return datosComunidad;
    }

    public String getGenero() {
        return genero;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public String getBeca() {
        return beca;
    }

    public float getNotaMedia() { return notaMedia; }

    public boolean getIsDeleted() { return isDeleted; }

    public int getCurrentProjectID() {
        return this.currentProjectID;
    }

}

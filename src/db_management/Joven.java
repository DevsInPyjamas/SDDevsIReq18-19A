package db_management;

import java.text.SimpleDateFormat;
import java.util.*;

public class Joven {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";

    private String dni;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String nombreMadre;
    private String nombrePadre;
    private String historial;
    private String datosComunidad;
    private String genero;
    private String observaciones;
    private Date fechaEntrada;
    private Date fechaBaja;
    private String centro;
    private String beca;

    public Joven(String dni){
        DBManager db = new DBManager(BD_SERVER,BD_NAME);

        Object[] tuples = db.select("SELECT * FROM Jovenes WHERE DNI = '" + dni + "';").get(0);
        this.dni = (String)tuples[0];
        this.nombre = (String)tuples[1];
        this.apellidos = (String)tuples[2];
        this.fechaNacimiento = (Date)tuples[3];
        this.nombreMadre = (String)tuples[4];
        this.nombrePadre = (String)tuples[5];
        this.historial = (String)tuples[6];
        this.datosComunidad = (String)tuples[7];
        this.genero = (String)tuples[8];
        this.observaciones = (String)tuples[9];
        this.fechaEntrada = (Date)tuples[10];
        this.fechaBaja = (Date)tuples[11];
        this.centro = (String)tuples[12];
        this.beca = (String)tuples[13];
    }

    public Joven(String dni, String nombre, String apellidos, Date fechaNacimiento,
                 String nombreMadre, String nombrePadre, String historial, String datosComunidad,
                 String genero, String observaciones, Date fechaEntrada, Date fechaBaja,
                 String centro, String beca){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nacimientoToStr = format.format(fechaNacimiento);
        String entradaToStr = format.format(fechaEntrada);
        String bajaToStr = format.format(fechaBaja);

        DBManager db = new DBManager(BD_SERVER,BD_NAME);
        db.execute("INSERT INTO Jovenes VALUES('" + dni + "','" + nombre + "','" + apellidos + "','"
                + nacimientoToStr + "','" + nombreMadre + "','" + nombrePadre + "','" + historial + "','"
                + datosComunidad + "','" + genero + "','" + observaciones + "','" + entradaToStr + "','"
                + bajaToStr + "','" + centro + "','" + beca + ");");
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.nombreMadre = nombreMadre;
        this.nombrePadre = nombrePadre;
        this.historial = historial;
        this.datosComunidad = datosComunidad;
        this.genero = genero;
        this.observaciones = observaciones;
        this.fechaEntrada = fechaEntrada;
        this.fechaBaja = fechaBaja;
        this.centro = centro;
        this.beca = beca;
    }

    public void setDni(String dni) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET dni = '" + dni + "' WHERE dni = '" + this.dni + "';");
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET nombre = '" + nombre + "' WHERE dni = '" + this.dni + "';");
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET apellidos = '" + apellidos + "' WHERE dni = '" + this.dni + "';");
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nacimientoToStr = format.format(fechaNacimiento);

        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET FechaNacimiento = '" + nacimientoToStr + "' WHERE dni = '" + this.dni + "';");
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNombreMadre(String nombreMadre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET nombreMadre = '" + nombreMadre + "' WHERE dni = '" + this.dni + "';");
        this.nombreMadre = nombreMadre;
    }

    public void setNombrePadre(String nombrePadre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET nombrePadre = '" + nombrePadre + "' WHERE dni = '" + this.dni + "';");
        this.nombrePadre = nombrePadre;
    }

    public void setHistorial(String historial) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET historial = '" + historial + "' WHERE dni = '" + this.dni + "';");
        this.historial = historial;
    }

    public void setDatosComunidad(String datosComunidad) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET datosComunidad = '" + datosComunidad + "' WHERE dni = '" + this.dni + "';");
        this.datosComunidad = datosComunidad;
    }

    public void setGenero(String genero) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET genero = '" + genero + "' WHERE dni = '" + this.dni + "';");
        this.genero = genero;
    }

    public void setObservaciones(String observaciones) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET observaciones = '" + observaciones + "' WHERE dni = '" + this.dni + "';");
        this.observaciones = observaciones;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String entradaToStr = format.format(fechaEntrada);
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET fechaEntrada = '" + entradaToStr + "' WHERE dni = '" + this.dni + "';");
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaBaja(Date fechaBaja) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String bajaToStr = format.format(fechaBaja);
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET fechaBaja = '" + bajaToStr + "' WHERE dni = '" + this.dni + "';");
        this.fechaBaja = fechaBaja;
    }

    public void setCentro(String centro) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET centro = '" + centro + "' WHERE dni = '" + this.dni + "';");
        this.centro = centro;
    }

    public void setBeca(String beca) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Joven SET beca = '" + beca + "' WHERE dni = '" + this.dni + "';");
        this.beca = beca;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Date getFechaNacimiento() {
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

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public String getCentro() {
        return centro;
    }

    public String getBeca() {
        return beca;
    }
}

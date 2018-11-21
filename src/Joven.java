import java.text.SimpleDateFormat;
import java.util.*;

public class Joven {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "";

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

        Object[] tuples = db.exec("SELECT * FROM Jovenes WHERE DNI = '" + dni + "';").get(0);
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
        db.exec("INSERT INTO Jovenes VALUES('" + dni + "','" + nombre + "','" + apellidos + "','"
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
}

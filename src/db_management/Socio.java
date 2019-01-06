package db_management;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;

public class Socio {
    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String fechaNacimiento;
    private Integer telefono;
    private String direccion;
    private Integer codigoPostal;
    private String provincia;
    private String poblacion;
    private double mensualidad;
    private String email;
    private boolean isDeleted;
    private int idAsociacion;
    private Asociacion asociacion;

    public Socio() {
    }

    public Socio(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from Socio where id ='" + id + "';");
        if(!query.isEmpty()) {
            Object[] row = query.get(0);
            this.id = id;
            this.nombre = (String) row[1];
            this.dni = (String) row[2];
            this.telefono = (Integer) row[3];
            this.direccion = (String) row[4];
            this.codigoPostal = (Integer) row[4+1];
            this.provincia = (String) row[5+1];
            this.poblacion = (String) row[6+1];
            this.mensualidad = Double.parseDouble(row[7+1].toString());
            this.apellidos = (String) row[8+1];
            this.fechaNacimiento = row[9+1].toString();
            this.email = (String)row[10+1];
            this.isDeleted = (boolean) row[11+1];
        } else {
            throw new NoSuchElementException("No existe Socio con id: " + id);
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getFechaNacimiento() { return fechaNacimiento; }

    public int getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public double getMensualidad() {
        return mensualidad;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getApellidos() { return apellidos; }

    public String getEmail() { return email; }

    public Asociacion getAsociacion() {
        this.asociacion = new Asociacion(this.idAsociacion);
        return asociacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        if (fechaNacimiento != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String nacimientoToStr = format.format(fechaNacimiento);
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public void setMensualidad(double mensualidad) {
        this.mensualidad = mensualidad;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
        this.idAsociacion = asociacion.getId();
    }

    //TODO añadir apellidos, email, fecha nacimiento
    public void save() {
        DBManager dbManager = new DBManager();
        if(id != 0) {
            int isDeletedToInt = (isDeleted) ? 1 : 0;
            dbManager.execute("update Socio set " +
                    "nombre =  '" + this.nombre +"', dni ='" +  this.dni + "', telefono = " + this.telefono + "," +
                    "direccion =  '" + this.direccion + "', codigo_postal = " + this.codigoPostal + ", provincia = " +
                    "'" + this.provincia + "', poblacion = '" + this.poblacion + "', mensualidad = " + this.mensualidad +
                    ", asociacion = " + this.idAsociacion + ", isDeleted = " + isDeletedToInt + ";");
        } else {
            dbManager.execute("insert into Socio(nombre, dni, telefono," +
                    "direccion, codigo_postal, provincia, poblacion, mensualidad, asociacion) " +
                    "values ('" + this.nombre +"', '" +  this.dni + "'," + this.telefono + ", '" + this.direccion + "'," +
                    "" + this.codigoPostal + ", '" + this.provincia + "','" + this.poblacion + "'," + this.mensualidad +
                    "," + this.idAsociacion + ");");
            // @@IDENTITY returns the id from the last inserted row
            this.id = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
    }

    @Override
    public String toString() {
        return "Socio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", telefono=" + telefono +
                ", direccion='" + direccion + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", provincia='" + provincia + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", mensualidad=" + mensualidad +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", idAsociacion=" + idAsociacion +
                ", asociacion=" + asociacion +
                '}';
    }
}
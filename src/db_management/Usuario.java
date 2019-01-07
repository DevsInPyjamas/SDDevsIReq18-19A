package db_management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private String usuario;
    private String password;
    private String nombre;
    private String email;
    private Rol role;
    private Integer idProyecto;
    private Proyecto proyecto;
    private Integer idAsociacion;
    private Asociacion asociacion;

    public List<Usuario> selectAllUsers() throws Exception {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select email from Usuario");
        List<Usuario> list = new ArrayList<>();
        for (Object[] obj : query) {
            list.add(new Usuario((String) obj[0]));
        }
        return list;
    }

    public Usuario(String email) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);

        Object[] tuples = db.select("SELECT * FROM Usuario WHERE email = '" + email + "';").get(0);
        this.email = (String) tuples[0];
        this.usuario = (String) tuples[1];
        this.password = (String) tuples[2];
        this.nombre = (String) tuples[3];
        this.idProyecto = (Integer) tuples[5];
        Object[] queryTuple = db.select("select rol_id from Usuario where email = '"  + this.email + "';").get(0);
        role = new Rol((int) queryTuple[0]);
        this.idAsociacion = (Integer) tuples[6];
    }

    public Usuario(String usuario, String password, String nombre, String email) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);

        db.execute("INSERT INTO Usuario(email, usuario, password, nombre) VALUES('" + email +
                "', '" + usuario + "', '" + password + "', '" + nombre + "');");
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Usuario SET usuario = '" + usuario + "' WHERE email = '" + this.email + "';");
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return role;
    }

    public Asociacion getAsociacion() {
        asociacion = new Asociacion(this.idAsociacion);
        return asociacion;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
        this.idAsociacion = asociacion.getId();
    }

    public void setPassword(String password) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Usuario SET password = '" + password + "' WHERE email = '" + this.email + "';");
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Usuario SET nombre = '" + nombre + "' WHERE email = '" + this.email + "';");
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Usuario SET email = '" + email + "' WHERE email = '" + this.email + "';");
        this.email = email;
    }

    public Rol getRole() {
        return role;
    }

    public Proyecto getProyecto() {
        if(this.idProyecto != null) {
            proyecto = new Proyecto(this.idProyecto);
        }
        return proyecto;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public void setProyecto(Proyecto proyecto) {
        this.idProyecto = proyecto.getId();
        this.proyecto = proyecto;
    }

    public int perteneceAsociacion() {
        return proyecto.getAsociacion().getId();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", idProyecto=" + idProyecto +
                ", proyecto=" + proyecto +
                '}';
    }
}

package db_management;

import java.util.List;
import java.util.NoSuchElementException;

public class Usuario {
    private String usuario;
    private String password;
    private String nombre;
    private String email;
    private boolean isDeleted;
    private Integer idRol;
    private Rol role;
    private Integer idProyecto;
    private Proyecto proyecto;
    private Integer idAsociacion;
    private Asociacion asociacion;

    public Usuario() {
        this.email = "";
    }

    public Usuario(String email) {
        DBManager dbManager = new DBManager();

        List<Object[]> query = dbManager.select("select * from Usuario where email = '" + email + "';");
        if (!query.isEmpty()) {
            Object[] tuple = query.get(0);
            this.email = (String) tuple[0];
            this.usuario = (String) tuple[1];
            this.password = (String) tuple[2];
            this.nombre = (String) tuple[3];
            this.isDeleted = (boolean) tuple[4];
            this.idRol = (Integer) tuple[5];
            this.role = new Rol(idRol);
            this.idProyecto = (Integer) tuple[6];
            this.idAsociacion = (Integer) tuple[7];
        } else {
            throw new NoSuchElementException("No existe el usuario con el email: " + email);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Rol getRol() {
        return role;
    }

    public void setRol(Rol role) {
        this.idRol = role.getId();
        this.role = role;
    }

    public Proyecto getProyecto() {
        if(this.proyecto == null && idProyecto != null) {
            proyecto = new Proyecto(idProyecto);
        }
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.idProyecto = proyecto.getId();
        this.proyecto = proyecto;
    }

    public Asociacion getAsociacion() {
        if(this.asociacion == null && idAsociacion != null) {
            asociacion = new Asociacion(idAsociacion);
        }
        return asociacion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if (!existeUsuario()) {
            int isDeletedAsInt = (this.isDeleted) ? 1 : 0;
            dbManager.execute("update Usuario set " +
                    "usuario = '" + this.usuario + "', " +
                    "password = '" + this.password + "', " +
                    "nombre = '" + this.nombre + "', " +
                    "isDeleted = " + isDeletedAsInt + ", " +
                    "rol_id = " + this.idRol + ", " +
                    "pertenece_proyecto = " + this.idProyecto + ", " +
                    "pertenece_asociacion = " + this.idAsociacion + " " +
                    "where email = '" + this.email + "';");
        } else {
            dbManager.execute("Insert into Usuario(email, usuario, password, nombre, rol_id, " +
                    "pertenece_proyecto, pertenece_asociacion) values(" +
                    "'" + this.email + "', '" + this.usuario + "', '" + this.password + "', '" + this.nombre + "', " +
                    this.idRol + ", " + this.idProyecto + ", " +
                    this.idAsociacion + ");");
        }
    }

    private boolean existeUsuario() {
        DBManager dbManager = new DBManager();
        return dbManager.select("select * from usuario where email = '" + this.email + "';").isEmpty();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", idRol=" + idRol +
                ", role=" + role +
                ", idProyecto=" + idProyecto +
                ", proyecto=" + proyecto +
                ", idAsociacion=" + idAsociacion +
                ", asociacion=" + asociacion +
                '}';
    }
}

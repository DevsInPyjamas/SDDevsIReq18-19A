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
            this.idProyecto = (tuple[6] == null) ? -1 : (Integer) tuple[6];
            this.idAsociacion = (tuple[7] == null) ? -1 : (Integer) tuple[7];
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

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if (this.email != null) {
            int isDeletedAsInt = (this.isDeleted) ? 1 : 0;
            dbManager.execute("update Usuario set " +
                    "usuario = '" + this.usuario + "', " +
                    "password = '" + this.password + "', " +
                    "nombre = '" + this.nombre + "', " +
                    "isDeleted = " + isDeletedAsInt + "', " +
                    "rol_id = " + this.role.getId() + "', " +
                    "pertenece_proyecto = " + this.proyecto.getId() + "', " +
                    "pertenece_asociacion = " + this.asociacion.getId() + "', " +
                    "where email = '" + this.email + "';");
        } else {
            dbManager.execute("Insert into Usuario(email, usuario, password, nombre, isDeleted, rol_id, " +
                    "pertenece_proyecto, pertenece_asociacion values(" +
                    "'" + this.email + "', '" + this.usuario + "', '" + this.password + "', '" + this.nombre + "', " +
                    this.isDeleted + ", " + this.role.getId() + ", " + this.proyecto.getId() + ", " +
                    this.asociacion.getId() + ");");
        }
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

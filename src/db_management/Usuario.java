package db_management;

public class Usuario {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private String usuario;
    private String password;
    private String nombre;
    private String email;

    public Usuario(String email) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);

        Object[] tuples = db.select("SELECT * FROM Usuario WHERE email = '" + email + "';").get(0);
        this.email = (String) tuples[0];
        this.usuario = (String) tuples[1];
        this.password = (String) tuples[2];
        this.nombre = (String) tuples[3];
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
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        Object[] queryTuple = db.select("select rol_id from Usuario where email = '"  + this.email + "';").get(0);
        return new Rol((int) queryTuple[0]);
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
}

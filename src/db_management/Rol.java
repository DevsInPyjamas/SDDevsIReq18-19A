package db_management;

public class Rol {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private int id;
    private String nombre;

    public Rol(int id) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        Object[] tuples = db.select("SELECT * FROM Rol WHERE id = '" + id + "';").get(0);
        this.id = (int) tuples[0];
        this.nombre = (String) tuples[1];
    }

    public Rol(int id, String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("INSERT INTO Rol(id, nombre) values ('" + id + "', '" + nombre + "');");
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Rol SET id = '" + id + "' WHERE id = '" + this.id + "';");
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Rol SET nombre = '" + nombre + "' WHERE id = '" + this.id + "';");
        this.nombre = nombre;
    }

    public boolean isAdmin() {
        /* TODO Por favor no lo dejéis así gracias.*/
        return this.nombre.contains("Coordinador");
    }
}

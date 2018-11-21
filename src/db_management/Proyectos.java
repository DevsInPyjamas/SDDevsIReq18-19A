package db_management;

public class Proyectos {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private int id;
    private String nombre;
    private String ubicacion;

    public Proyectos(int id) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        Object[] tuples = db.select("SELECT * FROM Proyectos WHERE id = '" + id + "';").get(0);
        this.id = (int) tuples[0];
        this.nombre = (String) tuples[1];
        this.ubicacion = (String) tuples[2];
    }

    public Proyectos(int id, String nombre, String ubicacion) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("INSERT INTO Proyectos(id, nombre, ubicacion) values ('" + id + "', '" +
                nombre + "', '" + ubicacion + ");");
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET id = '" + id + "' WHERE id = '" + this.id + "';");
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET nombre = '" + nombre + "' WHERE id = '" + this.id + "';");
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET ubicacion = '" + ubicacion + "' WHERE id = '" + this.id + "';");
        this.ubicacion = ubicacion;
    }
}

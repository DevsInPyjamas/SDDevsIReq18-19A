package db_management;

public class Proyectos {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private int id;
    private String nombre;
    private String ubicacion;
    private Usuario coordinadorAsignado;
    private Usuario responsableEconomico;
    private String tipoProyecto;

    public Proyectos(int id) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        Object[] tuples = db.select("SELECT * FROM Proyectos WHERE id = '" + id + "';").get(0);
        this.id = (int) tuples[0];
        this.nombre = (String) tuples[1];
        this.ubicacion = (String) tuples[2];
        this.tipoProyecto = (String) tuples[3];
        String queryGeneral = (String) db.select("SELECT email FROM Usuario WHERE nombre = '" +
                tuples[4] + "';").get(0)[0];
        coordinadorAsignado = new Usuario(queryGeneral);
        String queryEconomico = (String) db.select("SELECT email FROM Usuario WHERE nombre = '" +
                tuples[5] + "';").get(0)[0];
        responsableEconomico = new Usuario(queryEconomico);
    }

    public Proyectos(int id, String nombre, String ubicacion, Usuario coordinadorAsignado, Usuario responsableEconomico,
                     String tipoProyecto) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("INSERT INTO Proyectos values ('" + id + "', '" +
                nombre + "', '" + ubicacion + "', '" + tipoProyecto + "', '" + coordinadorAsignado.getNombre() + "', '"
                + responsableEconomico.getNombre() + "');");
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.coordinadorAsignado = coordinadorAsignado;
        this.responsableEconomico = responsableEconomico;
        this.tipoProyecto = tipoProyecto;
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

    public Usuario getCoordinadorAsignado() {
        return coordinadorAsignado;
    }

    public void setCoordinadorAsignado(Usuario coordinadorAsignado) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET project_coordinator = '" + responsableEconomico.getNombre() +
                "' WHERE id = '" + this.id + "';");
        this.coordinadorAsignado = coordinadorAsignado;
    }

    public Usuario getResponsableEconomico() {
        return responsableEconomico;
    }

    public void setResponsableEconomico(Usuario responsableEconomico) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET project_responsable = '" + responsableEconomico.getNombre() +
                "' WHERE id = '" + this.id + "';");
        this.responsableEconomico = responsableEconomico;

    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyectos SET tipoProyecto = '" + tipoProyecto +
                "' WHERE id = '" + this.id + "';");
        this.tipoProyecto = tipoProyecto;
    }
}

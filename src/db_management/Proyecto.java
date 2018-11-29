package db_management;

public class Proyecto {
    private static String BD_SERVER = "localhost";
    private static String BD_NAME = "ACOES";
    private int id;
    private String nombre;
    private String ubicacion;
    private Usuario coordinadorAsignado;
    private Usuario responsableEconomico;
    private String tipoProyecto;

    public Proyecto(int id) throws Exception {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        Object[] tuples = db.select("SELECT * FROM Proyecto WHERE id = '" + id + "';").get(0);
        this.id = (int) tuples[0];
        this.nombre = (String) tuples[1];
        this.ubicacion = (String) tuples[2];
        this.tipoProyecto = (String) tuples[3];
        coordinadorAsignado = new Usuario((String) tuples[4]);
        responsableEconomico = new Usuario((String) tuples[5]);
    }

    public Proyecto(String nombre, String ubicacion, Usuario coordinadorAsignado, Usuario responsableEconomico,
                    String tipoProyecto) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("INSERT INTO Proyecto(nombre, ubicacion, tipoProyecto, project_coordinator," +
                " project_responsable, general_project_coordinator, general_project_responsable) values('" +
                nombre + "', '" + ubicacion + "', '" + tipoProyecto + "', '" + coordinadorAsignado.getEmail() + "', '"
                + responsableEconomico.getEmail() + "', 'a@a.com' , 'b@b.com');");
        //this.id = id;
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
        db.execute("UPDATE Proyecto SET id = '" + id + "' WHERE id = '" + this.id + "';");
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyecto SET nombre = '" + nombre + "' WHERE id = '" + this.id + "';");
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyecto SET ubicacion = '" + ubicacion + "' WHERE id = '" + this.id + "';");
        this.ubicacion = ubicacion;
    }

    public Usuario getCoordinadorAsignado() {
        return coordinadorAsignado;
    }

    public void setCoordinadorAsignado(Usuario coordinadorAsignado) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyecto SET project_coordinator = '" + responsableEconomico.getNombre() +
                "' WHERE id = '" + this.id + "';");
        this.coordinadorAsignado = coordinadorAsignado;
    }

    public Usuario getResponsableEconomico() {
        return responsableEconomico;
    }

    public void setResponsableEconomico(Usuario responsableEconomico) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyecto SET project_responsable = '" + responsableEconomico.getNombre() +
                "' WHERE id = '" + this.id + "';");
        this.responsableEconomico = responsableEconomico;

    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        DBManager db = new DBManager(BD_SERVER, BD_NAME);
        db.execute("UPDATE Proyecto SET tipoProyecto = '" + tipoProyecto +
                "' WHERE id = '" + this.id + "';");
        this.tipoProyecto = tipoProyecto;
    }
}

package db_management;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class Transaccion {
    private int id;
    private String emisor;
    private String concepto;
    private double cantidad;
    private boolean isDeleted;
    private Proyecto proyecto;
    private int idProyecto;
    private TipoGasto tipoGasto;
    private boolean isValidated;
    private int beneficiario;
    private String tablaBeneficiario;
    private String fecha;

    public Transaccion() {
    }

    public Transaccion(int id) {
        DBManager dbManager = new DBManager();
        List<Object[]> query = dbManager.select("select * from Transaccion where id = '" + id + "';");
        if(!query.isEmpty()) {
            Object[] row = query.get(0);
            this.id = id;
            this.emisor = (String) row[1];
            this.concepto = (String) row[2];
            this.cantidad = Double.parseDouble(row[3].toString());
            this.isDeleted = (boolean) row[4];
            this.tipoGasto = new TipoGasto((int) row[5]);
            this.isValidated = (boolean) row[6];
            this.beneficiario = (int) row[7];
            this.tablaBeneficiario = (String) row[8];
            this.fecha = row[9].toString();
            this.idProyecto = (int) row[10];
        } else {
            throw new NoSuchElementException("No existe Transacción con la id: " + id);
        }
    }

    public int getId() {
        return id;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Proyecto getProyecto() {
        this.proyecto = new Proyecto(this.idProyecto);
        return proyecto;
    }


    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        this.idProyecto = proyecto.getId();
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public int getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(int beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getTablaBeneficiario() {
        return tablaBeneficiario;
    }

    public void setTablaBeneficiario(String tablaBeneficiario) {
        this.tablaBeneficiario = tablaBeneficiario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void save() {
        DBManager dbManager = new DBManager();
        if (this.id != 0) {
            int isDeletedAsInt = (this.isDeleted) ? 1 : 0;
            int isValidatedAsInt = (this.isValidated) ?  1 : 0;
            dbManager.execute("update Transaccion set " +
                    "emisor = '" + this.emisor + "', " +
                    "concepto = '" + this.concepto + "', " +
                    "cantidad = " + this.cantidad + ", " +
                    "isDeleted = " + isDeletedAsInt + ", " +
                    "proyecto = " + this.idProyecto + "," +
                    "tipoGasto = " + this.tipoGasto.getId() + ", "+
                    "isValidated = " + isValidatedAsInt + ", " +
                    "beneficiario = " + this.beneficiario + ", " +
                    "tablaBeneficiario = '" + this.tablaBeneficiario + "', " +
                    "fecha= '" + this.fecha + "' " +
                    "where id = " + this.id + ";");
        }
        else {
            dbManager.execute("Insert into Transaccion(" +
                    "emisor, concepto, cantidad, isDeleted, proyecto, tipoGasto, isValidated, beneficiario, " +
                    "tablaBeneficiario, fecha) values(" +
                    "'" + this.emisor + "', '" + this.concepto + "', " +
                    "" + this.cantidad + ", '" + this.isDeleted + "'," + this.idProyecto + "" +
                    ",'" + this.tipoGasto.getId() + "', " + this.isValidated + ", " +
                    this.beneficiario + ", '" + this.tablaBeneficiario + "', '" + this.fecha
                    + "');");
            this.id = Integer.parseInt(((BigDecimal) dbManager.select("select @@IDENTITY;").get(0)[0]).toBigInteger()
                    .toString());
        }
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", emisor='" + emisor + '\'' +
                ", concepto='" + concepto + '\'' +
                ", cantidad=" + cantidad +
                ", isDeleted=" + isDeleted +
                ", proyecto=" + proyecto +
                ", idProyecto=" + idProyecto +
                ", tipoGasto=" + tipoGasto +
                ", isValidated=" + isValidated +
                ", beneficiario=" + beneficiario +
                ", tablaBeneficiario='" + tablaBeneficiario + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}

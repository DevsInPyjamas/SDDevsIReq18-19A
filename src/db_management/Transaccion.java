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
    private Socio socio;
    private int id_socio;
    private TipoGasto tipoGasto;
    private int id_empresa;
    private Empresa empresa;

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
            this.tipoGasto = new TipoGasto((String) row[5]);
            this.idProyecto = (int) row[6];
            this.id_empresa = (int) row[7];
            this.id_socio = (int) row[8];
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

    public void setSocio(Socio socio) {
        this.socio = socio;
        this.id_socio = socio.getId();
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        this.id_empresa = empresa.getId();
    }

    public void save() {
        DBManager dbManager = new DBManager();
        Object isIdEmpresaNull = (this.id_empresa == 0) ? null : this.id_empresa;
        Object isIdSocioNull = (this.id_socio == 0) ? null : this.id_socio;
        if (this.id != 0) {
            dbManager.execute("update Transaccion set " +
                    "emisor = '" + this.emisor + "', " +
                    "concepto = '" + this.concepto + ", " +
                    "cantidad = '" + this.cantidad + ", " +
                    "isDeleted = '" + this.isDeleted + ", " +
                    "proyecto = '" + this.idProyecto + "," +
                    "id_empresa = " + isIdEmpresaNull + "," +
                    "tipoGasto = '" + this.tipoGasto.getNombre() + "', "+
                    "socio_donante = " + isIdSocioNull + ", " +
                    "where id = '" + this.id + "';");
        }
        else {
            dbManager.execute("Insert into Transaccion(" +
                    "emisor, concepto, cantidad, isDeleted, proyecto, tipoGasto, id_empresa, socio_donante) values(" +
                    "'" + this.emisor + "', '" + this.concepto + "', " +
                    "" + this.cantidad + ", '" + this.isDeleted + "'," + this.idProyecto + "" +
                    ",'" + this.tipoGasto.getNombre() + "', " + isIdEmpresaNull + ", " + isIdSocioNull + ");");
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
                ", socio=" + socio +
                ", tipoGasto='" + tipoGasto + '\'' +
                ", empresa=" + empresa +
                '}';
    }
}

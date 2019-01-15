import db_management.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Transaccion t = new Transaccion(10003);
        System.out.println(t);
        t.setWhatKindOfTransactionIs(null);
        t.save();
        System.out.println(t);
        Transaccion t2 = new Transaccion();
        t2.setEmisor("a");
        t2.setCantidad(20.0);
        t2.setTipoGasto(new TipoGasto(35001));
        t2.setBeneficiario(5002);
        t2.setTablaBeneficiario("Jovenes");
        t2.setFecha("2000-01-10");
        t2.setProyecto(new Proyecto(3));
        t2.setValidated(true);
        t2.setWhatKindOfTransactionIs(true);
        t2.save();
        System.out.println(t2);
        t2.save();
        System.out.println(t2);
    }
}

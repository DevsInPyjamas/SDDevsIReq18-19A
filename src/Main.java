import db_management.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApadrinarJoven ap = new ApadrinarJoven();
        ap.setApadrinador(new Socio(15000));
        ap.setJoven(new Joven(5020));
        ap.setFecha_inicio("31-01-2018");
        ap.setCuota(200.0);
        ap.save();
        System.out.println(ap);
        ApadrinarJoven ap2 = new ApadrinarJoven(ap.getId());
        System.out.println(ap2);
        ap2.setFecha_fin("01-01-2019");
        ap2.save();
        System.out.println(ap2);
    }
}

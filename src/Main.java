import db_management.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TipoGasto tipoGasto = new TipoGasto();
        tipoGasto.setNombre("Pajas");
        tipoGasto.save();
        System.out.println(tipoGasto);
        TipoGasto t2 = new TipoGasto(tipoGasto.getId());
        t2.setNombre("Handjobs");
        t2.save();
        System.out.println(t2);
    }
}

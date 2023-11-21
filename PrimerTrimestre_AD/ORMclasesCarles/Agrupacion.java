package centro;

import java.util.ArrayList;
import java.util.List;

public abstract class Agrupacion {
    private ArrayList<Mantenible> elementos = new ArrayList<>();

    public List<Mantenible> elementos() {
        return elementos;
    }

    @Override
    public String toString() {
        String cadena = "";
        int i = 0;
        for (Mantenible elemento : elementos) {
            if (i > 0) {
                cadena += ",";
            } else {
                cadena = "\"" + this.getClass().getName() + "\":[";
            }
            cadena += elemento.toString();
            i++;
        }
        cadena += "]";
        return cadena;
    }
}
package centro;

import utilidades.App;
import utilidades.Lector;

public abstract class Mantenimiento<T extends Mantenible> extends Agrupacion {
    private int tabulaciones = 0;

    public void tabulaciones(int tabulaciones) {
        this.tabulaciones = tabulaciones;
    }

    private int tabulaciones() {
        return tabulaciones;
    }

    public abstract int Alta(T nuevo, Agrupacion[] dependencias, Integer id);

    public abstract int Alta();

    public abstract int Baja(T abandono, Agrupacion[] dependencias);

    public abstract int Baja();

    public abstract int Modificacion(T modificado);

    public abstract int Modificacion();

    public abstract int Listado(Agrupacion[] dependencias);

    public abstract int Listado(int tabs);

    public abstract int Exporta(String nombreArchivo);

    public abstract int Importa(String nombreArchivo);

    public int Menu() {
        int option = -1;
        Lector in = new Lector(System.in);
        while ((option < 0)) {
            System.out.println("(0)- Salir");
            System.out.println("(1)- Alta de " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            System.out.println("(2)- Baja de " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            System.out.println("(3)- Modificacion de " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            System.out.println("(4)- Listado de " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            System.out.println("(5)- Exportar " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            System.out.println("(6)- Importar " + App.ANSI_GREEN + getClass().getName() + App.ANSI_WHITE);
            option = in.leerEntero(0, 6);
        }
        return option;
    }

    public void Haz(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                Alta();
                break;
            case 2:
                Baja();
                break;
            case 3:
                Modificacion();
                break;
            case 4:
                Listado(tabulaciones());
                break;
            case 5:
                Exporta(getClass().getName());
                break;
            case 6:
                Importa(getClass().getName());
                break;
            default:
                System.out.println(App.ANSI_CYAN + "Hay que elegit una de las opciones (numero entre parentesis)"
                        + App.ANSI_WHITE);
        }
    }

    public void Gestion() {
        int opcion = -1;
        do {
            opcion = Menu();
            Haz(opcion);
        } while (opcion != 0);
    }
}
package utilidades;

import persistencia.ORM;

public class App {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //Variable que define el BDD a usar, leido por la clase ORM
    private static int opcionDeConexion;
    public static int getOpcion() {
        return opcionDeConexion;
    }

    private static int menu() {
        int option = -1;
        Lector in = new Lector(System.in);
        System.out.println("\n(0)- Salir");
        System.out.println("(1)- MySQL");
        System.out.println("(2)- PostgreSQL");
        option = in.leerEntero(0, 2);
        in = null;
        System.gc();
        return option;
    }

    private static void haz(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                opcionDeConexion = 1;
                orm();
                break;
            case 2:
                opcionDeConexion = 2;
                orm();
                break;
            default:
                System.out.println(App.ANSI_CYAN + "Hay que elegit una de las opciones (numero entre parentesis)"
                        + App.ANSI_WHITE);
        }
    }

    private static int orm() {
        int retorno = 0;
        ORM orm = new ORM();
        orm.haz();
        return retorno;
    }

    public static void main(String[] args) {
        int opcion = -1;
        opcion = menu();
        haz(opcion);
    }
}
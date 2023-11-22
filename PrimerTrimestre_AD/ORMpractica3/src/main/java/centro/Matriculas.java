package centro;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.query.Query;
import persistencia.ORM;
import utilidades.Lector;

public class Matriculas {
    
    private short[] preguntarMatricula() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Modificar notas-");
        short nia = -1;
        while (nia < 0) {
            System.out.print("Introduzca NIA del alumno: ");
            nia = sc.nextShort();
            if (nia < 0) {
                System.out.println("No valido, introduzca un valor entero.");
            }
        }
        short id = -1;
        while (id < 0) {
            System.out.print("Introduzca ID del módulo: ");
            id = sc.nextShort();
            if (id < 0) {
                System.out.println("No valido, introduzca un valor entero.");
            }
        }
        short[] niaId = {nia, id};
        return niaId;
    }

    public int modificarNotas() {
        short[] niaId = preguntarMatricula();

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Matricula WHERE ID_Alumno = :nia AND ID_Modulo = :id");
        query.setParameter("nia", niaId[0]);
        query.setParameter("id", niaId[1]);

        Matricula aModificar = (Matricula) query.uniqueResult();

        if (aModificar != null) {
            aModificar.modificarNotas();
            session.getTransaction().commit();
            System.out.println("Se ha dado de ha modificado las notas.");
        } else {
            System.out.println("No existe una matricula así.");
        }

        session.close();
        return 0;
    }

    public int evaluarModulo() {
        short[] niaId = preguntarMatricula();

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Matricula WHERE ID_Alumno = :nia AND ID_Modulo = :id");
        query.setParameter("nia", niaId[0]);
        query.setParameter("id", niaId[1]);

        Matricula aModificar = (Matricula) query.uniqueResult();

        if (aModificar != null) {
            String calificacion="";
            switch (menuCalificar()) {
                case 1 ->
                    calificacion = "Suspendido";
                case 2 ->
                    calificacion = "Bien";
                case 3 ->
                    calificacion = "Notable";
                case 4 ->
                    calificacion = "Excelente";
                default -> {
                    System.out.println("No se introdujo un valor valido");
                    return 0;
                }
            }
            aModificar.evaluarModulo(calificacion);
            session.getTransaction().commit();
            System.out.println("Se ha dado de ha modificado la evaluación.");
        } else {
            System.out.println("No existe una matricula así.");
        }

        session.close();
        return 0;
    }
    
    public int mostrar(){
        return 0;
    }

    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Calificar              |");
        System.out.println("|2| Modificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|" + "-".repeat(26) + "|");
        int opcion = sc.leerEntero(0,3);
        return opcion;
    }

    private int menuCalificar() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("Calificaciones:");
        System.out.println("1- Suspendido");
        System.out.println("2- Bien");
        System.out.println("3- Notable");
        System.out.println("4-Excelente");
        int opcion = sc.leerEntero(1,4);
        return opcion;
    }
}

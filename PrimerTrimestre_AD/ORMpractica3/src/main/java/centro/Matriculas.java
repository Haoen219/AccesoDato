package centro;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.query.Query;
import persistencia.ORM;
import utilidades.Lector;

public class Matriculas {
    
    private int[] preguntarMatricula() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Modificar notas-");
        int nia = -1;
        while (nia < 0) {
            System.out.print("Introduzca ID del alumno: ");
            nia = sc.nextInt();
            if (nia < 0) {
                System.out.println("No valido, introduzca un valor entero.");
            }
        }
        int id = -1;
        while (id < 0) {
            System.out.print("Introduzca ID del módulo: ");
            id = sc.nextInt();
            if (id < 0) {
                System.out.println("No valido, introduzca un valor entero.");
            }
        }
        int[] niaId = {nia, id};
        return niaId;
    }

    public int modificarNotas() {
        int[] niaId = preguntarMatricula();

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        //Buscar Alumno
        Query query = session.createQuery("FROM Alumno WHERE id = :nia");
        query.setParameter("nia", niaId[0]);
        Alumno alumno = (Alumno)query.uniqueResult();
        //Buscar Modulo
        Query query2 = session.createQuery("FROM Modulo WHERE id = :id");
        query2.setParameter("id", niaId[1]);
        Modulo modulo = (Modulo)query2.uniqueResult();

        Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu AND modulo = :modu").setParameter("alu", alumno).setParameter("modu", modulo);
        Matricula aModificar = (Matricula) queryMatri.uniqueResult();

        if (aModificar != null) {
            aModificar.getNotas().modificarNota();

            session.update(aModificar);
            session.getTransaction().commit();
            System.out.println("Se ha modificado las notas.");
        } else {
            System.out.println("No existe una matricula así.");
        }

        session.close();
        return 0;
    }

    public int evaluarModulo() {
        int[] niaId = preguntarMatricula();

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        //Buscar Alumno
        Query query = session.createQuery("FROM Alumno WHERE id = :nia");
        query.setParameter("nia", niaId[0]);
        Alumno alumno = (Alumno)query.uniqueResult();
        //Buscar Modulo
        Query query2 = session.createQuery("FROM Modulo WHERE id = :id");
        query2.setParameter("id", niaId[1]);
        Modulo modulo = (Modulo)query2.uniqueResult();

        Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu AND modulo = :modu").setParameter("alu", alumno).setParameter("modu", modulo);
        Matricula aModificar = (Matricula) queryMatri.uniqueResult();

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
            }
            aModificar.setCalificacion(calificacion);
            session.update(aModificar);
            session.getTransaction().commit();
            System.out.println("Se ha dado de ha modificado la evaluación.");
        } else {
            System.out.println("No existe una matricula así.");
        }

        session.close();
        return 0;
    }
    
    public void mostrar(){
        Lector sc= new Lector(System.in);
        System.out.println("\n-Boletín-");
        System.out.print("Introduzca ID del alumno: ");
        int nia=sc.leerEntero(0,999);

        /*
        try (Session session = new ORM().conexion().getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Alumno WHERE id = :nia").setParameter("nia", nia);
            Alumno alumno = (Alumno)query.uniqueResult();
    
            if(alumno!=null){
                System.out.println(alumno.getNombre());
    
                Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu").setParameter("alu", alumno);
                List<Matricula> matriculas = queryMatri.getResultList();
    
                if(!matriculas.isEmpty()){
                    System.out.println("Matriculas:");
                    for(Matricula matricula : matriculas){
                        matricula.imprimir();
                    }
                    System.out.println("");
                }else{
                    System.out.println("-Sin matriculas-");
                }
            }else{
                System.out.println("--No existe el alumno");
            }
        }
        */

        Session session = new ORM().conexion().getSessionFactory().openSession();
        
        Query query = session.createQuery("FROM Alumno WHERE id = :nia").setParameter("nia", nia);
        Alumno alumno = (Alumno)query.uniqueResult();

        if(alumno!=null){
            System.out.println(alumno.getNombre());

            Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu").setParameter("alu", alumno);
            List<Matricula> matriculas = queryMatri.getResultList();

            //Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu").setParameter("alu", alumno, );
            //List<Matricula> matriculas = queryMatri.getResultList();

            if(!matriculas.isEmpty()){
                System.out.println("Matriculas:");
                for(Matricula matricula : matriculas){
                    matricula.imprimir();
                }
                System.out.println("");
            }else{
                System.out.println("-Sin matriculas-");
            }
        }else{
            System.out.println("--No existe el alumno");
        }
        session.close();
    }

    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Modificar              |");
        System.out.println("|2| Calificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|" + "-".repeat(26) + "|");
        int opcion = sc.leerEntero(0,3);
        return opcion;
    }

    private int menuCalificar() {
        Lector sc = new Lector(System.in);
        int opcion = -1;
        System.out.println("");
        System.out.println("Calificaciones:");
        System.out.println("1- Suspendido");
        System.out.println("2- Bien");
        System.out.println("3- Notable");
        System.out.println("4- Excelente");
        while(opcion <1 || opcion>4){
            opcion = sc.leerEntero(1,4);
        }
        return opcion;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;
import utilidades.Lector;

/**
 *
 * @author haoen
 */
public class Modulos implements BDDAlumnosModulos {

//MODULO
    @Override
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de alta módulo-");
        System.out.print("Introduzca el nombre del módulo: ");
        String nombre = sc.leer();

        Modulo modulo = new Modulo(nombre);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(modulo);

        session.getTransaction().commit();
        System.out.println("Se ha dado de alta al módulo " + nombre);
        session.close();
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de baja módulo-");
        System.out.print("Introduzca ID del módulo: ");
        int id = sc.leerEntero(0, 15);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query2 = session.createQuery("FROM Modulo WHERE ID = :id");
        query2.setParameter("id", id);
        Modulo deBaja = (Modulo)query2.uniqueResult();

        //Modulo deBaja = (Modulo)session.get(Modulo.class, id);

        if (deBaja != null) {
            /*
            for (Matricula matricula : deBaja.getMatriculados()) {
                Notas notas = matricula.getNotas();
                session.delete(notas);
                session.delete(matricula);
            }
            */
            session.delete(deBaja);

            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al módulo " + deBaja.getNombre());
        } else {
            System.out.println("No existe modulo con ese ID");
        }

        session.close();
        return 0;
    }

//MATRICULA
    public int matricularAlumno() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.leerEntero(0, 999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Alumno WHERE ID = :nia");
        query.setParameter("nia", nia);
        Alumno aMatricular = (Alumno)query.uniqueResult();

        if (aMatricular != null) {
            System.out.print("Introduzca ID del modulo: ");
            int id = sc.leerEntero(0, 999);

            Query query2 = session.createQuery("FROM Modulo WHERE ID = :id");
            query2.setParameter("id", id);
            Modulo moduloMatri = (Modulo)query2.uniqueResult();

            if (moduloMatri != null) {
                aMatricular.matricularModulo(id);
                //Matricula matricula=new Matricula(nia, id);
                session.update(aMatricular);
                session.getTransaction().commit();
                System.out.println("Se ha dado de baja al módulo " + aMatricular.getNombre());
            } else {
                System.out.println("No existe Modulo con ese ID");
            }
        }else{
            System.out.println("El alumno no existe.");
        }

        session.close();
        return 0;
    }

    //MENU
    @Override
    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener M?dulos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|4| Matricular Alumno      |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.leerEntero(0, 4);
        return opcion;
    }

    //IMPRIMIR
    public void listar() {
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Modulo", Modulo.class);
        List<Modulo> modulos = query.getResultList();

        if(modulos!=null){
            for(Modulo x : modulos){
                x.imprimir();
            }
        }else{
            System.out.println("Lista de modulos vacio.");
        }
        session.close();
    }
//        System.out.println("\n-Listar modulos-");
//        try {
//            this.lector = new Scanner(this.baseDeModulos);
//            this.lector.nextLine();
//
//            while (this.lector.hasNextLine()) {
//                transformarModulo(this.lector.nextLine());
//                if (this.moduloComodin != null) {
//                    this.moduloComodin.imprimir();
//                }
//            }
//        } catch (InputMismatchException ex) {
//            System.out.println("--Error listando, el elemento no era del tipo esperado");
//        } catch (NoSuchElementException ex) {
//            System.out.println("--Error listando, no hay más líneas en el archivo");
//        } catch (Exception ex) {
//            System.out.println("--Error listando, error inesperado\n" + ex);
//        } finally {
//            this.lector.close();
//            this.moduloComodin = null;
//        }
//        System.out.println("--Fin de la lista");
//    }

    //GETTER
//    public Modulo getModulo(int id) {
//        transformarModulo(buscarModulo(id));
//        try {
//            return this.moduloComodin;
//        } finally {
//            this.moduloComodin = null;
//        }
//    }
}

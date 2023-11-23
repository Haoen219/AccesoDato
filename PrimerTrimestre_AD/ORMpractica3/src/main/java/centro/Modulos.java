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
        System.out.println("Se ha dado de alta al módulo " + nombre+ " ID:"+modulo.getId());
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
        
        Query query2 = session.createQuery("FROM Modulo WHERE id = :id");
        query2.setParameter("id", id);
        Modulo deBaja = (Modulo)query2.uniqueResult();

        if (deBaja != null) {
            Query queryMatri = session.createQuery("FROM Matricula  WHERE  modulo = :modu").setParameter("modu", deBaja);
            List<Matricula> matriculaExistente = queryMatri.getResultList();
            
            for (Matricula matricula : matriculaExistente) {
                Notas notas = matricula.getNotas();
                session.delete(notas);
                session.delete(matricula);
            }
            
            session.delete(deBaja);

            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al módulo " + deBaja.getNombre());
        } else {
            System.out.println("--No existe modulo con ese ID");
        }

        session.close();
        return 0;
    }

//MATRICULA
    public int matricularAlumno() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca ID del alumno: ");
        int nia = sc.leerEntero(0, 999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Alumno WHERE id = :nia").setParameter("nia", nia);
        Alumno aMatricular = (Alumno)query.uniqueResult();

        if (aMatricular != null) {
            System.out.print("Introduzca ID del modulo: ");
            int id = sc.leerEntero(0, 999);

            Query query2 = session.createQuery("FROM Modulo WHERE id = :id").setParameter("id", id);
            Modulo moduloMatri = (Modulo)query2.uniqueResult();

            if(moduloMatri != null){
                //quey para comprobar que no exista una matricula de este alumno y modulo
                Query queryMatri = session.createQuery("FROM Matricula m WHERE m.alumno = :alu AND m.modulo = :modu").setParameter("alu", aMatricular).setParameter("modu", moduloMatri);
                List<Matricula> matriculaExistente = queryMatri.getResultList();

                if (matriculaExistente.isEmpty()) {
                    //notas
                    Notas notas = new Notas();
                    notas.instanciarNotas();
                    session.save(notas);

                    //matricula
                    Matricula matricula=new Matricula();
                    matricula.setAlumno(aMatricular);
                    matricula.setModulo(moduloMatri);
                    matricula.setNotas(notas);
                    session.save(matricula);

                    session.getTransaction().commit();
                    System.out.println("Se ha dado de matriculado al alumno " + aMatricular.getNombre()+ " en el módulo "+moduloMatri.getNombre());
                } else {
                    System.out.println("Ya existe una Matricula de este modulo para el alumno.");
                }
            }else{
                System.out.println("--No existe Modulo con ese ID");
            }
            
        }else{
            System.out.println("--El alumno no existe");
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
        System.out.println("\n-Listar modulos-");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Modulo", Modulo.class);
        List<Modulo> modulos = query.getResultList();

        if(!modulos.isEmpty()){
            for(Modulo x : modulos){
                x.imprimir();
            }
            System.out.println("--Fin de la lista--");
        }else{
            System.out.println("Lista de modulos vacio");
        }
        session.close();
    }
}

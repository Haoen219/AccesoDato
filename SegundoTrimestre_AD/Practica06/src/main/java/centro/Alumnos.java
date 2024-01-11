/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;
import utilidades.Lector;

/**
 *
 * @author haoen
 */
public class Alumnos implements BDDAlumnosModulos {

    public Alumnos() {
    }

    @Override
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");
        ArrayList<Alumno> alumnos = new ArrayList<>();

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        while (seguir) {
            System.out.print("Introduzca el nombre del alumno: ");
            String nombre = sc.leer();

            if (nombre.equalsIgnoreCase("0")) {
                seguir = false;
            } else {
                Alumno alumno = new Alumno(nombre);
                alumnos.add(alumno);
                session.save(alumno);
                System.out.println("\t" + nombre + " añadido a la lista de espera.");
            }
        }

        session.getTransaction().commit();
        session.close();
        if (alumnos.size() > 0) {
            System.out.println("\nSe ha dado de alta a:");
            for (Alumno alu : alumnos) {
                System.out.println("ID:" + alu.getId() + "\t" + alu.getNombre());
            }
        }
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc = new Lector(System.in);
        ArrayList<Alumno> alumnos = new ArrayList<>();
        boolean seguir = true;
        System.out.println("\n-Dar de baja alumno-  (volver con [0])");

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        while (seguir) {
            System.out.print("Introduzca ID del alumno: ");
            int nia = sc.leerEntero(0, 999);
            if (nia == 0) {
                seguir = false;
            } else {
                Query query = session.createQuery("FROM Alumno WHERE id = :nia");
                query.setParameter("nia", nia);
                Alumno deBaja = (Alumno) query.uniqueResult();

                if (deBaja != null) {
                    Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu").setParameter("alu",
                            deBaja);
                    List<Matricula> matriculaExistente = queryMatri.getResultList();

                    for (Matricula matricula : matriculaExistente) {
                        session.delete(matricula);
                    }

                    session.delete(deBaja);
                    alumnos.add(deBaja);
                    System.out.println("\t" + deBaja.getNombre() + " añadido a la lista de espera.");
                } else {
                    System.out.println("--No existe este alumno");
                }
            }
        }
        session.getTransaction().commit();
        session.close();
        if (alumnos.size() > 0) {
            System.out.println("\nSe ha dado de baja a:");
            for (Alumno alu : alumnos) {
                System.out.println("ID:" + alu.getId() + "\t" + alu.getNombre());
            }
        }
        return 0;
    }

    // MENU
    @Override
    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Alumnos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.leerEntero(0, 3);
        return opcion;
    }

    // IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar Alumnos-");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Alumno", Alumno.class);
        List<Alumno> alumnos = query.getResultList();

        if (!alumnos.isEmpty()) {
            for (Alumno x : alumnos) {
                x.imprimir();
            }
            System.out.println("--Fin de la lista--");
        } else {
            System.out.println("Lista de alumno vacio");
        }
        session.close();
    }
}

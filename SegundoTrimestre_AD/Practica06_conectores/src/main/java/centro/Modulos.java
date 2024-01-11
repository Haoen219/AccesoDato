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
public class Modulos implements BDDAlumnosModulos {

    // MODULO
    @Override
    public int darDeAlta() {
        boolean seguir = true;
        ArrayList<Modulo> modulos = new ArrayList<>();
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Modulo", Modulo.class);
        List<Modulo> lista = query.getResultList();
        int id;
        if (!lista.isEmpty()) {
            id = lista.get(lista.size() - 1).getId() + 1;
        } else {
            id = 0;
        }

        while (seguir) {
            System.out.print("Introduzca el nombre del módulo: ");
            String nombre = sc.leer();

            if (nombre.equalsIgnoreCase("0")) {
                seguir = false;
            } else {
                Modulo modulo = new Modulo(nombre);
                modulo.setId(id);
                session.save(modulo);
                modulos.add(modulo);
                System.out.println("\t" + nombre + " añadido a la lista de espera.");
            }
        }
        session.getTransaction().commit();
        session.close();
        if (modulos.size() > 0) {
            System.out.println("\nSe ha dado de alta a:");
            for (Modulo modu : modulos) {
                System.out.println("ID:" + modu.getId() + "\t" + modu.getNombre());
            }
        }
        return 0;
    }

    @Override
    public int darDeBaja() {
        boolean seguir = true;
        ArrayList<Modulo> modulos = new ArrayList<>();
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de baja módulo-  (volver con [0])");

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        while (seguir) {
            System.out.print("Introduzca ID del módulo: ");
            int id = sc.leerEntero(0, 15);

            if (id == 0) {
                seguir = false;
            } else {
                Query query2 = session.createQuery("FROM Modulo WHERE id = :id");
                query2.setParameter("id", id);
                Modulo deBaja = (Modulo) query2.uniqueResult();

                if (deBaja != null) {
                    Query queryMatri = session.createQuery("FROM Matricula  WHERE  modulo = :modu").setParameter("modu",
                            deBaja);
                    List<Matricula> matriculaExistente = queryMatri.getResultList();

                    for (Matricula matricula : matriculaExistente) {
                        session.delete(matricula);
                    }

                    session.delete(deBaja);
                    modulos.add(deBaja);
                    System.out.println("\t" + deBaja.getNombre() + " añadido a la lista de espera.");
                } else {
                    System.out.println("--No existe modulo con ese ID");
                }
            }
        }
        session.getTransaction().commit();
        session.close();
        if (modulos.size() > 0) {
            System.out.println("\nSe ha dado de baja a:");
            for (Modulo modu : modulos) {
                System.out.println("ID:" + modu.getId() + "\t" + modu.getNombre());
            }
        }
        return 0;
    }

    // MATRICULA
    public int matricularAlumno() {
        boolean seguir = true;
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-  (volver con [0])");

        Session session = new ORM().conexion().getSessionFactory().openSession();
        while (seguir) {
            System.out.print("Introduzca ID del alumno: ");
            int nia = sc.leerEntero(0, 999);

            if (nia == 0) {
                seguir = false;
            } else {
                Query query = session.createQuery("FROM Alumno WHERE id = :nia").setParameter("nia", nia);
                Alumno aMatricular = (Alumno) query.uniqueResult();

                if (aMatricular != null) {
                    System.out.print("Introduzca ID del modulo: ");
                    int id = sc.leerEntero(0, 999);

                    if (id == 0) {
                        seguir = false;
                    } else {
                        Query query2 = session.createQuery("FROM Modulo WHERE id = :id").setParameter("id", id);
                        Modulo moduloMatri = (Modulo) query2.uniqueResult();

                        Query queryId = session.createQuery("FROM Matricula", Matricula.class);
                        List<Matricula> lista = queryId.getResultList();
                        int idMatri;
                        if (!lista.isEmpty()) {
                            idMatri = lista.get(lista.size() - 1).getId() + 1;
                        } else {
                            idMatri = 1;
                        }

                        Query queryId2 = session.createQuery("FROM Notas", Notas.class);
                        List<Notas> lista2 = queryId2.getResultList();
                        int idNotas;
                        if (!lista2.isEmpty()) {
                            idNotas = lista2.get(lista2.size() - 1).getId() + 1;
                        } else {
                            idNotas = 0;
                        }

                        if (moduloMatri != null) {
                            // quey para comprobar que no exista una matricula de este alumno y modulo
                            Query queryMatri = session
                                    .createQuery("FROM Matricula m WHERE m.alumno = :alu AND m.modulo = :modu")
                                    .setParameter("alu", aMatricular).setParameter("modu", moduloMatri);
                            List<Matricula> matriculaExistente = queryMatri.getResultList();

                            if (matriculaExistente.isEmpty()) {
                                session.beginTransaction();
                                // notas
                                Notas notas = new Notas();
                                notas.instanciarNotas();
                                notas.setId(idNotas);
                                session.save(notas);

                                // matricula
                                Matricula matricula = new Matricula();
                                matricula.setId(idMatri);
                                matricula.setNotas(notas);
                                matricula.setAlumno(aMatricular);
                                matricula.setModulo(moduloMatri);

                                session.save(matricula);
                                session.getTransaction().commit();
                                System.out.println("Se creado matricula de " + aMatricular.getNombre()
                                        + " en el módulo " + moduloMatri.getNombre());
                            } else {
                                System.out.println("Ya existe una Matricula de este modulo para el alumno.");
                            }
                        } else {
                            System.out.println("--No existe Modulo con ese ID");
                        }
                    }
                } else {
                    System.out.println("--El alumno no existe");
                }
            }
        }
        session.close();
        System.out.println("\n+Se han guardado las matriculas creadas.");
        return 0;
    }

    // MENU
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

    // IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar modulos-");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Modulo", Modulo.class);
        List<Modulo> modulos = query.getResultList();

        if (!modulos.isEmpty()) {
            for (Modulo x : modulos) {
                x.imprimir();
            }
            System.out.println("--Fin de la lista--");
        } else {
            System.out.println("Lista de modulos vacio");
        }
        session.close();
    }
}

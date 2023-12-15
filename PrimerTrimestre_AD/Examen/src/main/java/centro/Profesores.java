/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;
import utilidades.Lector;

/**
 *
 * @author haoen
 */
public class Profesores implements BDDAlumnosModulos {

    private static File profesores = new File("profesores.txt");

    public Profesores() {
    }

    @Override
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de alta profesor-");
        System.out.print("Introduzca el nombre del profesor: ");
        String nombre = sc.leer();

        Profesor profesor = new Profesor(nombre);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(profesor);

        session.getTransaction().commit();
        session.close();
        System.out.println("Se ha dado de alta al alumno " + nombre + " ID:" + profesor.getId());
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Dar de baja profesor-");
        System.out.print("Introduzca ID del profesor: ");
        int nia = sc.leerEntero(0, 999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Profesor WHERE id = :nia");
        query.setParameter("nia", nia);
        Profesor deBaja = (Profesor) query.uniqueResult();

        if (deBaja != null) {
            Query queryMatri = session.createQuery("FROM Matricula WHERE profesor = :alu").setParameter("alu", deBaja);
            List<Matricula> matriculaExistente = queryMatri.getResultList();

            for (Matricula matricula : matriculaExistente) {
                Notas notas = matricula.getNotas();
                session.delete(notas);
                session.delete(matricula);
            }

            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al profesor " + deBaja.getNombre());
        } else {
            System.out.println("--No existe este profesor");
        }

        session.close();
        return 0;
    }

    public int modificar() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Modificar profesor-");
        System.out.print("Introduzca ID del profesor: ");
        int nia = sc.leerEntero(0, 999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Profesor WHERE id = :nia");
        query.setParameter("nia", nia);
        Profesor deBaja = (Profesor) query.uniqueResult();

        if (deBaja != null) {
            System.out.print("Introduzca el nombre: ");
            String nombre = sc.leer();

            deBaja.setNombre(nombre);

            session.update(deBaja);
            session.getTransaction().commit();
            System.out.println("Se cambiado de nombre a " + deBaja.getNombre());
        } else {
            System.out.println("--No existe este alumno");
        }

        session.close();
        return 0;
    }

    public int matricularModulo() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular Módulo-");
        System.out.print("Introduzca ID del módulo: ");
        int nia = sc.leerEntero(0, 999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Modulo WHERE id = :nia").setParameter("nia", nia);
        Modulo aMatricular = (Modulo) query.uniqueResult();

        if (aMatricular != null) {
            System.out.print("Introduzca ID del profesor: ");
            int id = sc.leerEntero(0, 999);

            Query query2 = session.createQuery("FROM Profesor WHERE id = :id").setParameter("id", id);
            Profesor profesor = (Profesor) query2.uniqueResult();

            if (profesor != null) {
                aMatricular.setProfesor(profesor);
                session.update(aMatricular);

                session.getTransaction().commit();
                System.out.println("Se ha dado de matriculado al modulo " + aMatricular.getNombre());
            } else {
                System.out.println("--El profesor no existe");
            }
        } else {
            System.out.println("--No existe modulo con ese ID");
        }
        session.close();
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
        System.out.println("|3| Modificacion           |");
        System.out.println("|4| Matricular módulo      |");
        System.out.println("|5| Importar profesores    |");
        System.out.println("|6| Exportar profesores    |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.leerEntero(0, 6);
        return opcion;
    }

    /*
    // IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar Profesores-");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Profesor", Alumno.class);
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
    */

    public void importar(){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        if(!this.profesores.exists()){
            System.out.println("No existe el fichero de profesores, creandolo...");
            try{
                this.profesores.createNewFile();
            }catch(Exception ex){
                System.out.println("No se ha podido crear el fichero de profesores!");
                System.out.println(ex);
            }
            return;
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.profesores));
            String line;
            while ((line = reader.readLine()) != null) {
                //ID#NOMBRE
                String[] datos = line.split("#");
                Profesor profesor=new Profesor(datos[1]);
                session.saveOrUpdate(profesor);
            }
            reader.close();
            session.getTransaction().commit();
        }catch(Exception ex){
            System.out.println("No se ha podido crear el fichero de profesores!");
            System.out.println(ex);
        }
        session.close();
    }

    public void exportar() {
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        if(!this.profesores.exists()){
            System.out.println("No existe el fichero de profesores, creandolo...");
            try{
                this.profesores.createNewFile();
            }catch(Exception ex){
                System.out.println("No se ha podido crear el fichero de profesores!");
                System.out.println(ex);
            }
            return;
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.profesores));
            String line;

            Query queryMatri = session.createQuery("FROM Profesor");
            List<Profesor> profesores = queryMatri.getResultList();

            for(Profesor profe : profesores){
                writer.write(profe.toLine());
                writer.newLine();
            }

            writer.close();
            session.getTransaction().commit();
        }catch(Exception ex){
            System.out.println("No se ha podido crear el fichero de profesores!");
            System.out.println(ex);
        }
        session.close();
        System.out.println("Se ha exportado al fichero profesores.txt");
    }
}

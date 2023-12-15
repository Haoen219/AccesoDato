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
public class Alumnos implements BDDAlumnosModulos {

    public Alumnos() {}
    
    @Override
    public int darDeAlta() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.leer();
        
        Alumno alumno = new Alumno(nombre);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(alumno);

        session.getTransaction().commit();
        session.close();
        System.out.println("Se ha dado de alta al alumno "+ nombre+ " ID:"+alumno.getId());
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca ID del alumno: ");
        int nia=sc.leerEntero(0,999);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Alumno WHERE id = :nia");
        query.setParameter("nia", nia);
        Alumno deBaja = (Alumno)query.uniqueResult();

        if(deBaja!=null){
            Query queryMatri = session.createQuery("FROM Matricula WHERE alumno = :alu").setParameter("alu", deBaja);
            List<Matricula> matriculaExistente = queryMatri.getResultList();
            
            for (Matricula matricula : matriculaExistente) {
                Notas notas = matricula.getNotas();
                session.delete(notas);
                session.delete(matricula);
            }

            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al alumno "+ deBaja.getNombre());
        }else{
            System.out.println("--No existe este alumno");
        }
        
        session.close();
        return 0;
    }
    
    //MENU
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
        int opcion = sc.leerEntero(0,3);
        return opcion;
    }

    //IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar Alumnos-");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Alumno", Alumno.class);
        List<Alumno> alumnos = query.getResultList();

        if(!alumnos.isEmpty()){
            for(Alumno x : alumnos){
                x.imprimir();
            }
            System.out.println("--Fin de la lista--");
        }else{
            System.out.println("Lista de alumno vacio");
        }
        session.close();
    }
}

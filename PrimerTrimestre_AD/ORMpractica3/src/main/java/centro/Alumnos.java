/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.List;
import java.util.Scanner;

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
        /*
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,15);
        */
        
        Alumno alumno = new Alumno(nombre);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(alumno);

        session.getTransaction().commit();
        session.close();
        System.out.println("Se ha dado de alta al alumno "+ nombre);
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc= new Lector(System.in);
        //Scanner sc = new Scanner(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,999);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = "FROM Alumno WHERE alumno_id = :nia";
        Query query = session.createQuery(hql);
        query.setParameter("nia", nia);
        
        Alumno deBaja = (Alumno)query.uniqueResult();
        

        //Alumno deBaja = (Alumno) session.get(Alumno.class, nia);

        if(deBaja!=null){
            for(Matricula matricula : deBaja.getMatriculas()){
                Notas notas = matricula.getNotas();
                session.delete(notas);
                session.delete(matricula);
            }
            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al alumno "+ deBaja.getNombre());
        }else{
            System.out.println("No existe este alumno.");
        }
        
        session.close();
        return 0;
    }
    
    /*
    //ACTUALIZAR
    public int actualizar(short nia, short ID){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Matricula WHERE alumno_id = :nia AND ID_Modulo = :id");
        query.setParameter("nia", nia);
        query.setParameter("id", ID);

        Matricula aModificar = (Matricula) query.uniqueResult();
        
        if(aModificar!=null){
            session.delete(aModificar);
            session.getTransaction().commit();
        }
        session.close();
        return 0;
    }
    */
    
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
//    @Override
    public void listar() {}
//        System.out.println("\n-Listar alumnos-");
//        try {
//            this.lector = new Scanner(this.baseDeAlumnos);
//            this.lector.nextLine();
//
//            while (this.lector.hasNextLine()) {
//                transformarAlumno(this.lector.nextLine());
//                this.alumnoComodin.imprimir();
//                this.alumnoComodin = null;
//            }
//        } catch (InputMismatchException ex) {
//            System.out.println("--Error listando alumnos, el elemento no era del tipo esperado");
//        } catch (NoSuchElementException ex) {
//            System.out.println("--Error listando alumnos, no hay más líneas en el archivo");
//        } catch (Exception ex) {
//            System.out.println("--Error listando alumnos, error inesperado\n" + ex);
//        } finally {
//            this.lector.close();
//            this.alumnoComodin = null;
//        }
//        System.out.println("--Fin de la lista");
//    }

//    public void imprimirBoletin() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("\n-Imprimir Bolet?n-");
//        System.out.print("Introduzca NIA del alumno: ");
//        int nia = sc.nextInt();
//        if (buscarAlumno(nia) != null) {
//            transformarAlumno(buscarAlumno(nia));
//            if (this.alumnoComodin.comprobarMatricula()) {
//                this.alumnoComodin.imprimirBoletin();
//            } else {
//                System.out.println("--Este alumno no tiene Matricula a?n");
//            }
//            this.alumnoComodin = null;
//        } else {
//            System.out.println("--No existe ese alumno en la base");
//        }
//    }
}

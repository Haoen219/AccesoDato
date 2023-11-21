package centro;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import persistencia.ORM;
import utilidades.Lector;


/**
 *
 * @author haoen
 */
@Entity
@Table(name="Modulo")
public class Modulo {
    @Id
    @Column(name = "ID")
    private short ID;
    @Column(name="Nombre")
    private String nombre;
    
    @ManyToOne
    Set<Alumno> matriculados;
    
    

    public Modulo(){}
    public Modulo(String nombre){
        this.nombre=nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    public int getIDENTIFICADOR() {
        return this.ID;
    }

    public int darDeAlta() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de alta módulo-");
        System.out.print("Introduzca el nombre del módulo: ");
        String nombre=sc.leer();
        
        Modulo modulo = new Modulo(nombre);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(modulo);

        session.getTransaction().commit();
        System.out.println("Se ha dado de alta al módulo "+ nombre);
        session.close();
        return 0;
    }

    public int darDeBaja() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de baja módulo-");
        System.out.print("Introduzca ID del módulo: ");
        int id=sc.leerEntero(0,15);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Modulo deBaja = session.get(Modulo.class, (short)id);

        if(deBaja!=null){
            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al módulo "+ deBaja.nombre);
        }else{
            System.out.println("No existe modulo con ese ID");
        }
        
        session.close();
        return 0;
    }

    public int listar(){
        //POR HACER
        return 0;
    }

    public int matricularAlumno(){
        Lector sc= new Lector(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,15);

        System.out.print("Introduzca ID del modulo: ");
        int id=sc.leerEntero(0,15);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Alumno aMatricular = session.get(Alumno.class, (short)nia);
        Modulo moduloMatri = session.get(Modulo.class, (short)id);

        if(aMatricular!=null){
            session.update(aMatricular);
            session.getTransaction().commit();
            aMatricular.actualizar((short)nia, this.ID);
            System.out.println("Se ha dado de baja al módulo "+ aMatricular.getNombre());
        }else{
            System.out.println("No existe Alumno con ese ID");
        }

        session.close();
        return 0;
    }

    public int menu() {
        Lector sc= new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Modulos-----|");
        System.out.println("|0| Volver al menú previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|4| Matricular Alumno      |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCIÓN: ");
        int opcion=sc.leerEntero(0,5);
        return opcion;
    }
}
package centro;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name="Alumno")
public class Alumno{
    @Id
    @Column(name="ID")
    private short ID;
    @Column(name="Nombre")
    private String nombre;
    @ManyToOne
    @JoinColumn(name="Matriculas")
    private Set<Matricula> matriculas;

    public Alumno(){}
    public Alumno(String nombre){
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

    public int darDeBaja() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,15);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Alumno deBaja = session.get(Alumno.class, (short)nia);

        if(deBaja!=null){
            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al alumno "+ deBaja.nombre);
        }
        
        session.close();
        return 0;
    }

    public int matricularModulo(){

        return 0;
    }

    public int desmatricularModulo(){

        return 0;
    }

    public int listar(){
        //POR HACER
        return 0;
    }

    public int actualizar(short nia, int ID){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Alumno aCambiar = session.get(Alumno.class, (short)nia);

        if(aCambiar!=null){
            for (Matricula x : aCambiar.matriculas){
                if(x.idModulo==ID){
                    
                }
            }

            session.update(aCambiar);
            session.getTransaction().commit();
            //System.out.println("Se ha dado de baja al alumno "+ deBaja.nombre);
        }
        
        session.close();
        return 0;
    }

    public int menu() {
        Lector sc= new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Alumnos-----|");
        System.out.println("|0| Volver al menú previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCIÓN: ");
        int opcion=sc.leerEntero(0,5);
        return opcion;
    }
}

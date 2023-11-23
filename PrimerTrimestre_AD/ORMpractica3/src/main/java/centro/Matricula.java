/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;


/**
 *
 * @author haoen
 */
@Entity
@Table(name="Matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_id")
    private int id;
    @OneToOne(targetEntity = Alumno.class)
    @JoinColumn(name ="alumno_id", nullable =false)
    private Alumno alumno;
    @OneToOne(targetEntity = Modulo.class)
    @JoinColumn(name ="modulo_id", nullable =false)
    private Modulo modulo;
    @OneToOne(targetEntity = Notas.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name="notas_id")
    private Notas notas;
    @Column(name="Calificación")
    private String calificacion;

    public Matricula(){}
    
    /*
    public Matricula(int idAlu, int idModulo){
        this.idAlumno=idAlu;
        this.idModulo=idModulo;
        
        this.notas = new Notas(true);
    }
    */
    
    public int modificarNotas(){
        //POR IMPLEMENTAR
        return 0;
    }

    public int getId(){
        return this.id;
    }
    
    public Notas getNotas(){
        return this.notas;
    }

    public Alumno getAlumno(){
        return this.alumno;
    }

    public Modulo getModulo(){
        return this.modulo;
    }

    public void setAlumno(int id){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Alumno WHERE ID = :nia", Alumno.class).setParameter("nia", id);
        Alumno alumno = (Alumno)query.uniqueResult();

        if(alumno!=null){
            this.alumno=alumno;
        }
        session.close();
    }
    public void setModulo(int id){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Modulo WHERE ID = :id", Modulo.class).setParameter("id", id);
        Modulo modulos = (Modulo)query.uniqueResult();

        if(modulos!=null){
            
        }
        session.close();
    }
    
//    public int matricularModulo(short id){
//        double[] comodin={0,0,0};
//        this.idModulo=id;
//        return 0;
//    }
    
    public int evaluarModulo(String calificaion){
        this.calificacion=calificacion;
        return 0;
    }
    
//    //IMPRIMIR NOTAS
//    public void imprimirModulos(){
//        for(int id: this.modulosMatriculados.keySet()){
//            Modulo comodin=BaseDeDatos.modulos.getModulo(id);
//            System.out.printf("\tID: %-8d %-20s ",comodin.getIDENTIFICADOR(),comodin.getNombre());
//            
//            System.out.print("Notas: [ ");
//            if(this.modulosMatriculados.containsKey(id)){
//                for (int i = 0; i < this.modulosMatriculados.get(id).length; i++) {
//                    System.out.printf("-%02.2f- ",this.modulosMatriculados.get(id)[i]);
//                }
//            }
//            System.out.println("]  Calificaci?n: "+this.calificaciones.get(id));
//        }
//    }
}

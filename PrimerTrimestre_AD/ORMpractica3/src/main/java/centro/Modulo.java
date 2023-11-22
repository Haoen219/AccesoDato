/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    

    public int matricularAlumno(){
        //POR IMPLEMENTAR
        
        
//        Lector sc= new Lector(System.in);
//        System.out.println("\n-Matricular alumno-");
//        System.out.print("Introduzca NIA del alumno: ");
//        int nia=sc.leerEntero(0,15);
//
//        System.out.print("Introduzca ID del modulo: ");
//        int id=sc.leerEntero(0,15);
//
//        Session session = new ORM().conexion().getSessionFactory().openSession();
//        session.beginTransaction();
//
//        Alumno aMatricular = session.get(Alumno.class, (short)nia);
//        Modulo moduloMatri = session.get(Modulo.class, (short)id);
//
//        if(aMatricular!=null){
//            session.update(aMatricular);
//            session.getTransaction().commit();
//            aMatricular.actualizar((short)nia, this.ID);
//            System.out.println("Se ha dado de baja al módulo "+ aMatricular.getNombre());
//        }else{
//            System.out.println("No existe Alumno con ese ID");
//        }
//
//        session.close();
        return 0;
    }
    
    public int listar(){
        //POR HACER
        return 0;
    }
    
    //IMPRIMIR
    public void imprimir() {
        //POR HACER
    }
}

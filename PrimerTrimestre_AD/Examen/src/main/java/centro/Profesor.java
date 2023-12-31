/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;



/**
 *
 * @author haoen
 */
@Entity
@Table(name="Profesor")
public class Profesor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profesor_id")
    private int id;
    @Column(name="profesor_nombre")
    private String nombre;

    public Profesor(){}
    public Profesor(String nombre){
        this.nombre=nombre;
    }
    
    //GETTER
    public String getNombre() {
        return this.nombre;
    }
    public int getId() {
        return this.id;
    }

    public String toLine(){
        return this.id+"#"+this.nombre;
    }
    
    //IMPRIMIR
    public void imprimir() {
        System.out.printf("ID: %-8d Nombre: %-30s",this.getId(), this.getNombre());

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Matricula WHERE profesor = :nia");
        query.setParameter("nia", this);
        List<Matricula> matriculas = query.getResultList();

        if(!matriculas.isEmpty()){
            System.out.printf(" Modulos: %-2d\n",matriculas.size());
        }else{
            System.out.println(" -Sin matricula-");
        }

        session.close();
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

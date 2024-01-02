/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.query.Query;

import persistencia.ORM;



/**
 *
 * @author haoen
 */
@Entity
@Table(name="Alumno")
public class Alumno{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alumno_id")
    private int id;
    @Column(name="alumno_nombre")
    private String nombre;

    /*
    @Column(name= "matricula_id")
    @OneToMany(targetEntity = Matricula.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="matricula_id", nullable = true)
    private Set<Matricula> matriculas;
    */

    public Alumno(){}
    public Alumno(String nombre){
        this.nombre=nombre;
    }
    
    //GETTER
    public String getNombre() {
        return this.nombre;
    }
    public int getId() {
        return this.id;
    }

    public void addMatricula(Matricula matricula) {
        //this.matriculas.add(matricula);
    }
    
    //IMPRIMIR
    public void imprimir() {
        System.out.printf("ID: %-8d Nombre: %-30s",this.getId(), this.getNombre());

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Matricula WHERE alumno = :nia");
        query.setParameter("nia", this);
        List<Matricula> matriculas = query.getResultList();

        if(!matriculas.isEmpty()){
            System.out.printf(" Modulos: %-2d\n",matriculas.size());
        }else{
            System.out.println(" -Sin matricula-");
        }

        session.close();
    }
}

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
@Table(name="Modulo")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modulo_id")
    private int id;
    @Column(name="modulo_nombre")
    private String nombre;

    /*
    @Column(name= "matricula_id")
    @OneToMany(targetEntity = Matricula.class, cascade = CascadeType.ALL)
    @JoinColumn(name ="matricula_id", nullable = true)
    private Set<Matricula> matriculas;
    */

    public Modulo(){}
    public Modulo(String nombre){
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
        System.out.printf("\tID: %-8d %-20s ", this.getId(), this.getNombre());

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Matricula WHERE modulo = :modu");
        query.setParameter("modu", this);
        List<Matricula> matriculas = query.getResultList();

        if(!matriculas.isEmpty()){
            System.out.printf(" Alumnos: %-2d\n",matriculas.size());
        }else{
            System.out.println(" -Sin matriculados-");
        }

        session.close();
    }
}

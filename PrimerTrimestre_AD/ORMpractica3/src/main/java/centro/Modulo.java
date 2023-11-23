/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private int ID;
    @Column(name="modulo_nombre")
    private String nombre;
    
    @ManyToOne(targetEntity = Matricula.class, cascade = CascadeType.ALL)
    @JoinColumn(name="matricula_id")
    private Set<Matricula> matriculados;
    
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
    public Set<Matricula> getMatriculados() {
        return this.matriculados;
    }
    
    public int listar(){
        //POR HACER
        return 0;
    }
    
    //IMPRIMIR
    public void imprimir() {
        System.out.printf("\tID: %-8d %-20s Alumnos: ", this.getIDENTIFICADOR(), this.getNombre());
        System.out.println((this.matriculados!=null)?this.matriculados.size():"-Sin matriculados-");
    }
}

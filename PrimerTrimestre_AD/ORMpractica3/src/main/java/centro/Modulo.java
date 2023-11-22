/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "ID")
    private short ID;
    @Column(name="Nombre")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name="Matriculas")
    Set<Matricula> matriculados;
    
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
    
    public int listar(){
        //POR HACER
        return 0;
    }
    
    //IMPRIMIR
    public void imprimir() {
        //POR HACER
    }
}

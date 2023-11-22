/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public Set<Matricula> getMatriculas(){
        return this.matriculas;
    }


    public int actualizar(short id){
        return 0;
    }

    
    public boolean comprobarMatricula(){ //
        if(this.matriculas.size()==0){
            return false;
        }
        return true;
    }


    //hay que modificar para que seleccione la matricula correspondiente
//    public boolean comprobarModulo(int id){
//        /*return this.matricula.comprobarModulo(id);*/
//    }
    
    public int eliminarModulo(short id){
        
        
        /*if(this.matricula.eliminarModulo(id)==0){
            return 0;
        }*/
        return -1;
    }
    public int modificarNota(short id, int posicion, double nota){
        /*if(this.matricula.modificarNota(id, posicion, nota)==0){
            return 0;
        }*/
        return -1;
    }
    public int evaluarModulo(short id, String calificacion){
        /*if(this.matricula.evaluarModulo(id, calificacion)==0){
            return 0;
        }*/
        return -1;
    }
    
    
    //MODULO
    //instanciar nuevo matricula en "matriculas" con el ID_MODULO de id
    public int matricularModulo(short id){
        if(this.matriculas.add(new Matricula(this.ID, id))){
            return 0;
        }
        return -1;
    }
    
//    //IMPRIMIR
//    public void imprimir() {
//        System.out.printf("NIA: %-8d Nombre: %-30s",this.getIDENTIFICADOR(), this.getNombre());
//        if(comprobarMatricula()){
//            System.out.printf(" Modulos: %-2d\n",this.matricula.getNumeroModulos());
//        }else{
//            System.out.println(" -Sin matricula-");
//        }
//    }
//    public void imprimirBoletin() {
//        imprimir();
//        imprimirModulos();
//    }
//    public void imprimirModulos() {
//        System.out.println("Matricula: ");
//        this.matricula.imprimirModulos();
//    }
}

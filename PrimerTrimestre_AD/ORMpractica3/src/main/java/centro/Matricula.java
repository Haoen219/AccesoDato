/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author haoen
 */
@Entity
@Table(name="Matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private short ID;
    @OneToMany
    @JoinColumn(name ="ID_Alumno", nullable =false)
    short idAlumno;
    @OneToMany
    @JoinColumn(name ="ID_Modulo", nullable =false)
    short idModulo;
    @OneToOne
    @JoinColumn(name="ID_Notas")
    Notas notas;
    @Column(name="Calificación")
    private String calificacion;

    public Matricula(){}
    
    public Matricula(short idAlu, short idModulo){
        this.idAlumno=idAlu;
        this.idModulo=idModulo;
        
        this.notas = new Notas(true);
    }
    
    public int modificarNotas(){
        //POR IMPLEMENTAR
        return 0;
    }
    
    public int crearNotas(){
        //POR IMPLEMENTAR
        return 0;
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

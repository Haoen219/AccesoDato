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
    @Column(name = "matricula_id")
    private int id;
    @OneToOne(targetEntity = Alumno.class)
    @JoinColumn(name ="alumno_id")
    private Alumno alumno;
    @OneToOne(targetEntity = Modulo.class)
    @JoinColumn(name ="modulo_id")
    private Modulo modulo;
    @OneToOne(targetEntity = Notas.class)
    @JoinColumn(name="notas_id")
    private Notas notas;
    @Column(name="Calificación")
    private String calificacion;

    public Matricula(){}
    
    public int modificarNotas(){
        //POR IMPLEMENTAR
        return 0;
    }

    //GETTERS
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
    public String getCalificacion(){
        return this.calificacion;
    }

    //SETTER
    public void setAlumno(Alumno alumno){
        this.alumno=alumno;
    }
    public void setModulo(Modulo modulo){
        this.modulo=modulo;
    }
    public void setNotas(Notas notas){
        this.notas=notas;
    }
    public void setCalificacion(String calificacion){
        this.calificacion=calificacion;
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

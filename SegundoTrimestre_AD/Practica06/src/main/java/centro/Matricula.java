/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(targetEntity = Alumno.class)
    @JoinColumn(name ="alumno_id", nullable = false)
    private Alumno alumno;
    @ManyToOne(targetEntity = Modulo.class)
    @JoinColumn(name ="modulo_id", nullable = false)
    private Modulo modulo;

    @OneToOne(targetEntity = Notas.class, cascade = CascadeType.ALL)
    @JoinColumn(name="notas_id", nullable = false)
    private Notas notas;
    @Column(name="calificacion")
    private String calificacion;

    public Matricula(){}

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

    
    //IMPRIMIR NOTAS
    public void imprimir(){
        System.out.printf("ID: %-8d Modulo: %-30s  ",this.id, this.modulo.getNombre());
        System.out.printf("Notas: %2d | %2d | %2d    Calificacion: %s\n", this.notas.getNota1(),this.notas.getNota2(),this.notas.getNota3(), this.calificacion);
    }
}

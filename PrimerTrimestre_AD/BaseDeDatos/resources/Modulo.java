/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.TreeSet;

import jakarta.persistence.ManyToOne;

/**
 *
 * @author haoen
 */
public class Modulo extends BaseAlumnoModulo {
    @Id
    @GeneratedValue
    final private int IDENTIFICADOR;

    @Column(name="Nombre")
    private String nombre;

    @ManyToOne(targetEntity = Matricula.class, fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="Alumno_id", referencedColumnName = "Alumnos")
    private Alumno matriculado;
    
    public String getNombre() {
        return nombre;
    }
    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }
}

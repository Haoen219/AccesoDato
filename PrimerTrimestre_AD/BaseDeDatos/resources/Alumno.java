/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;

import jakarta.persistence.Column;

/**
 *
 * @author haoen
 */
@Entity
@Table(name="Alumno")
public class Alumno{
    @Id
    @GeneratedValue
    final private int IDENTIFICADOR;

    @Column(name="Nombre")
    private String nombre;

    @ManyToOne(targetEntity = Matricula.class, fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="Matricula_id", referencedColumnName = "Matriculas")
    private Matricula matricula;
    
    public String getNombre() {
        return nombre;
    }
    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }
}

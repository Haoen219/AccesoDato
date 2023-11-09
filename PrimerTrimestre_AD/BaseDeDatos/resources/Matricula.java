/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.TreeMap;

import jakarta.persistence.Id;

/**
 *
 * @author haoen
 */
@Entity
@Table(name="Matricula")
public class Matricula {
    @Id
    @Column(name = "ID Alumno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="ID Módulo")
    private int idModulo;
    @Column(name="Módulo")
    private String nombre;
    @Column(name="Notas")
    private double[] notas = {0, 0, 0};
    @Column(name="Calificación")
    private String calificacion;
}

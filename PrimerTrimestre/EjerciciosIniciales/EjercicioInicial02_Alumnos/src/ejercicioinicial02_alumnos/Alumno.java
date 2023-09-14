/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicioinicial02_alumnos;

import java.util.ArrayList;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Alumno {
    private String nombre;
    private ArrayList<Double> notas= new ArrayList<>();
    private int aprobados;
    
    public Alumno (String nombre, double... notas){
        this.nombre=nombre;
        for(double a:notas){
            this.notas.add(a);
            if(a>=5){
                this.aprobados++;
            }
        }
    }
}

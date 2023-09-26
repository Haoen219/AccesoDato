/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.ArrayList;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Modulo {
    private String nombre;
    final private int ID;
    private ArrayList<Alumno> alumnos= new ArrayList();
    
    public Modulo(String nombre, int ID){
        this.nombre=nombre;
        this.ID=ID;
    }
    
    public void imprimirLista(){
        System.out.printf("ID: %-8d Nombre: %-30s", this.ID, this.nombre);
        System.out.println("Alumnos matriculados: ");
        for (Alumno comodin : alumnos) {
            System.out.println("");
        }
    }
    
    public void imprimirModulo(){
        //imprimir modulo individual de un alumno con sus notas.
    }
}

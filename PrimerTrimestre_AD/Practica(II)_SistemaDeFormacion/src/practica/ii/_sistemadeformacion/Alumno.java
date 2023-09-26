/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Alumno {
    private String nombre;
    final private int NIA;
    private ArrayList<Modulo> matricula= new ArrayList<>();
    
    public Alumno(String nombre, int NIA){
        this.nombre=nombre;
        this.NIA=NIA;
    }
    
    public void imprimirLista(){
        System.out.printf("NIA: %-8d Nombre: %-30s", this.NIA, this.nombre);
        for (Modulo comodin : matricula) {
            System.out.println("Matricula: ");
            //comodin.imprimirAlumno;
        }
    }
    
    public void imprimirAlumno(){
        System.out.printf("NIA: %-8d Nombre: %-30s", this.NIA, this.nombre);
        for (Modulo comodin : matricula) {
            System.out.println("Matricula: ");
            //comodin.imprimirAlumno;
        }
    }
}

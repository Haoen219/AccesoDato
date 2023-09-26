/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.Map;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Alumno {
    private String nombre;
    final private int NIA;
    private Map matricula;
    
    public Alumno(String nombre, int NIA){
        this.nombre=nombre;
        this.NIA=NIA;
    }
    
    public void imprimir(){
        System.out.printf("NIA: %-8d Nombre: %-30s", this.NIA, this.nombre);
    }
}

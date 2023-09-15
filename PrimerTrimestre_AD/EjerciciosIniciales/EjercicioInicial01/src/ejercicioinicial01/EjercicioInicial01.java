/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicioinicial01;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class EjercicioInicial01 {
    public static void imprimir(List<Persona> personas){
        for (int i = 0; i < personas.size(); i++) {
            Persona comodin= personas.get(i);
            System.out.println((i+1)+". "+comodin.getNombre()+" - Altura: "+comodin.getAltura()+" - Edad:"+comodin.getEdad());
        }
        System.out.println("");
    }
    
    public static void main(String[] args) {
        Persona p1= new Persona("Mario", 167, 22);
        Persona p2= new Persona("Pepe", 173, 52);
        Persona p3= new Persona("Manuel", 158, 27);
        Persona p4= new Persona("David", 164, 25);
        Persona p5= new Persona("Alberto", 184, 80);
        
        List<Persona> personas = new LinkedList<>(); 
        personas.add(p1);
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);
        personas.add(p5);
        
        System.out.println("Personas sin ordenar:");
        imprimir(personas);
        
        Collections.sort(personas, new ComparadorAlturaPersona());
        System.out.println("Personas ordenadas por altura:");
        imprimir(personas);
        
        Collections.sort(personas, new ComparadorEdadPersona());
        System.out.println("Personas ordenadas por edad:");
        imprimir(personas);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicioinicial02_alumnos;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class EjercicioInicial02_Alumnos {
    
    public static void imprimir(List<Alumno> alumnos){
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno comodin= alumnos.get(i);
            System.out.println((i+1)+". "+comodin.getNombre()+" - Total:"+comodin.getNotas().size()+" - Aprobados:"+comodin.getAprobados()+" - Porcentaje: "+comodin.getPorcentajeAprobado());
        }
        System.out.println("");
    }
    
    public static void main(String[] args) {
        Alumno a1= new Alumno("Mario", 4, 2, 2.3, 5);
        Alumno a2= new Alumno("Pepe", 9, 2, 7, 4, 6, 3.3);
        Alumno a3= new Alumno("Manuel", 8, 7, 7, 8,0, 3);
        Alumno a4= new Alumno("David", 4, 8, 9, 2, 1, 8, 8, 9);
        Alumno a5= new Alumno("Alberto", 1, 0, 1);
        
        List<Alumno> alumnos = new LinkedList<>(); 
        alumnos.add(a1);
        alumnos.add(a2);
        alumnos.add(a3);
        alumnos.add(a4);
        alumnos.add(a5);
        
        System.out.println("Lista sin ordenar:");
        imprimir(alumnos);

        Collections.sort(alumnos);
        System.out.println("Lista ordenada:");
        imprimir(alumnos);
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Modulo {
    private String nombre;
    final private int ID;
    private Map<Integer, Alumno> alumnos= new HashMap();
    private Map<Alumno, Integer> alumnosNota= new HashMap();
    
    public Modulo(String nombre, int ID){
        this.nombre=nombre;
        this.ID=ID;
    }
    
    public void imprimirLista(){
        System.out.printf("ID: %-8d Nombre: %-30s", this.getID(), this.getNombre());
        System.out.println("Alumnos matriculados: ");
        for (int comodin : alumnos.keySet()) {
            System.out.println("");
        }
    }
    
    public int matricularAlumno(Alumno alumno, int nota){
        if(this.alumnos.put(alumno.getNIA(), alumno)==null && this.alumnosNota.put(alumno, nota)==null){
            return 0;
        }
        return -1;
    }
    
    public void imprimirNotas(int nia){
        System.out.printf("\t-%-5d %-10s Nota: %-2d"+this.ID, this.nombre, this.alumnosNota.get(this.alumnos.get(nia)));
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }
}

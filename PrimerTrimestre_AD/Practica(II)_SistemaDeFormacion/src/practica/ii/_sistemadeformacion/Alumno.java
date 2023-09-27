/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Alumno {
    private String nombre;
    final private int NIA;
    private Map<Integer, Modulo> modulos= new TreeMap();
    
    public Alumno(String nombre, int NIA){
        this.nombre=nombre;
        this.NIA=NIA;
    }
    
    public int matricularModulo(Modulo modulo){
        if(this.modulos.put(modulo.getID(), modulo)==null){
            return 0;
        }
        return -1;
    }
    
    public void imprimirAlumno(){
        System.out.printf("NIA: %-8d Nombre: %-30s\n", this.getNIA(), this.getNombre());
    }
    
    public void imprimirMatricula(){
        System.out.printf("NIA: %-8d Nombre: %-30s\n", this.getNIA(), this.getNombre());
        System.out.println("Matricula: ");
        for (int entry : modulos.keySet()) {
            System.out.printf("\tID: %-8d %-20s Nota: %2d\n", modulos.get(entry).getID(),
                    modulos.get(entry).getNombre(), modulos.get(entry).getNotaAlumno(NIA));
            System.out.println("\tID: "+modulos.get(entry).getID()+" "+modulos.get(entry).getNombre()+" Nota:");
            modulos.get(entry).getID();
        }
        
//        for (Modulo comodin : matricula) {
//            System.out.println("Matricula: ");
//            comodin.imprimirNotas(this.NIA);
//        }
    }
    
    
    public String getNombre() {
        return nombre;
    }
    public int getNIA() {
        return NIA;
    }
}

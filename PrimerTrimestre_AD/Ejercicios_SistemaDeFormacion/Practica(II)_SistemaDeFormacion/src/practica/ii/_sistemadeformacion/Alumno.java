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
    private Map<Integer, String> calificacion= new TreeMap();
    
    //Builder
    public Alumno(String nombre, int NIA){
        this.nombre=nombre;
        this.NIA=NIA;
    }
    
    //Modulo
    public int matricularModulo(Modulo modulo){
        if(this.modulos.put(modulo.getID(), modulo)==null && this.calificacion.put(modulo.getID(), null)==null){
            return 0;
        }
        return -1;
    }
    public int desmatricularModulo(Modulo modulo){
        if(this.modulos.remove(modulo.getID())==null && this.calificacion.remove(modulo.getID())==null){
            return 0;
        }
        return -1;
    }
    public int calificarModulo(Modulo modulo, String calificacion){
        if(this.calificacion.put(modulo.getID(), calificacion)==null){
            return 0;
        }
        return -1;
    }
    
    //Imprimir
    public void imprimirAlumno(){
        System.out.printf("NIA: %-8d Nombre: %-30s Modulos: %-2d\n",
                this.NIA, this.nombre,this.modulos.entrySet().size());
    }
    public void imprimirMatricula(){
        imprimirAlumno();
        System.out.println("Matricula: ");
        for (int entry : modulos.keySet()) {
            System.out.printf("\tID: %-8d %-20s Nota: %2d |Calificaci?n: %-10s\n", modulos.get(entry).getID(),
                    modulos.get(entry).getNombre(), modulos.get(entry).getNotaAlumno(NIA),
                    this.calificacion.get(this.modulos.get(entry)));
        }
    }
    
    //Getter
    public String getNombre() {
        return nombre;
    }
    public int getNIA() {
        return NIA;
    }
}

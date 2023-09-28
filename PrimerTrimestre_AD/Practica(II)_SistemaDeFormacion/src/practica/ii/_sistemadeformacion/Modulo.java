/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Modulo{
    private String nombre;
    final private int ID;
    private Map<Integer, Alumno> alumnos= new TreeMap();
    private Map<Alumno, Integer> alumnosNota= new HashMap();
    
    //Builder
    public Modulo(String nombre, int ID){
        this.nombre=nombre;
        this.ID=ID;
    }
    
    //Alumno
    public int matricularAlumno(Alumno alumno){
        if(this.alumnos.put(alumno.getNIA(), alumno)==null && this.alumnosNota.put(alumno, null)==null){
            return 0;
        }
        return -1;
    }
    public int desmatricularAlumno(Alumno alumno){
        if(this.alumnos.remove(alumno.getNIA())==null && this.alumnosNota.remove( alumno)==null){
            return 0;
        }
        return -1;
    }
    public int modificarAlumno(Alumno alumno, int nota){
        if(this.alumnos.put(alumno.getNIA(), alumno)==null && this.alumnosNota.put(alumno, nota)==null){
            return 0;
        }
        return -1;
    }
    
    //Imprimir
    public void imprimirModulo(){
        System.out.printf("\tID: %-8d %-20s Alumnos: ",
                this.ID, this.nombre);
        if(this.alumnos.isEmpty()){
            System.out.println("0");
        }else{
            System.out.println(this.alumnos.size());
        }
    }
    public void imprimirLista(){
        System.out.printf("ID: %-8d Nombre: %-20s", this.getID(), this.getNombre());
        if(this.alumnos.isEmpty()){
            System.out.println("-No hay alumnos matriculados-");
        }else{
            System.out.println("Alumnos matriculados: ");
            for (int comodin : alumnos.keySet()) {
                this.alumnos.get(comodin).imprimirAlumno();
            }
        }
    }
    
    public void imprimirNotas(int nia){
        System.out.printf("\t-%-5d %-20s Nota: %-2d"+this.ID, this.nombre, this.alumnosNota.get(this.alumnos.get(nia)));
    }

    //Getter
    public int getNotaAlumno(int nia) {
        return this.alumnosNota.get(this.alumnos.get(nia));
    }
    public String getNombre() {
        return nombre;
    }
    public int getID() {
        return ID;
    }
}

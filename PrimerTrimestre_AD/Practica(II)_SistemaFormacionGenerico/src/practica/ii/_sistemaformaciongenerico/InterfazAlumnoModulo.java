/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public interface InterfazAlumnoModulo <T, V> {
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
        System.out.printf("\tNIA: %-8d Nombre: %-30s\n", this.getNIA(), this.getNombre());
        System.out.println("Matricula: ");
        for (int entry : modulos.keySet()) {
            System.out.printf("\tID: %-8d %-20s Nota: %2d |Calificaci?n: %-10s\n", modulos.get(entry).getID(),
                    modulos.get(entry).getNombre(), modulos.get(entry).getNotaAlumno(NIA),
                    this.calificacion.get(this.modulos.get(entry)));
        }
    }
    
    
    
    
    //Alumno
    public int matricularAlumno(Alumno alumno){
        if(this.alumnos.put(alumno.getNIA(), alumno)==null && this.alumnosNota.put(alumno.getNIA(), null)==null){
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
        if(this.alumnos.put(alumno.getNIA(), alumno)==null && this.alumnosNota.put(alumno.getNIA(), nota)==null){
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
}

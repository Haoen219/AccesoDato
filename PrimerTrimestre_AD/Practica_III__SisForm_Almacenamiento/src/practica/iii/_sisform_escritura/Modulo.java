/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.iii._sisform_escritura;

import java.util.TreeSet;

/**
 *
 * @author haoen
 */
public class Modulo extends BaseAlumnoModulo {
    private TreeSet<Integer> alumnosMatriculados= new TreeSet<>();
    
    //Builder
    public Modulo(String nombre, int ID){
        super(nombre, ID);
    }
    
    //ALUMNO
    public int matricularAlumno(int nia){
        if(this.alumnosMatriculados.add(nia)){          //devuelve true si funciona
            return 0;
        }
        return -1;
    }
    public int anularMatriculaAlumno(int nia){
        if(this.alumnosMatriculados.remove(nia)){
            return 0;
        }
        return -1;
    }
    
    //GUARDAR EN FICHERO
    public String formatoFichero(){
        //ID_NOMBRE_NIA-NIA-NIA-NIA...
        String modulo= String.format("%-4 %-20s ", this.getIDENTIFICADOR(), this.getNombre());
        for(int nia:this.alumnosMatriculados){
            modulo+=nia+"-";
        }
        return modulo;
    }

    //IMPRIMIR
    public void imprimir() {                                                    //NO IMPRIMIA LOS ALUMNOS MATRICULADOS SINO LOS QUE HABIAN EN LA BASE DE DATOS
        System.out.printf("\tID: %-8d %-20s Alumnos: %d", this.getIDENTIFICADOR(), this.getNombre(),this.alumnosMatriculados.size());
    }
}

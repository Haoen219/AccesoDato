/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

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

    //IMPRIMIR
    @Override
    public void imprimir() {
        System.out.printf("\tID: %-8d %-20s Alumnos: ", this.getIDENTIFICADOR(), this.getNombre());
        if(this.alumnosMatriculados.isEmpty()){
            System.out.println("0");
        }else{
            System.out.println(BaseDeDatos.alumnos.alumnos.size());
        }
    }
}

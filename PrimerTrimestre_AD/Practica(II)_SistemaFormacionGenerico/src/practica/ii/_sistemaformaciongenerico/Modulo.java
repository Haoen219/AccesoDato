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
    private TreeSet<Integer> alumnos= new TreeSet<>();
    
    //Builder
    public Modulo(String nombre, int ID){
        super(nombre, ID);
    }
    
    //ALUMNO
    public int matricularAlumno(int nia){
        if(this.alumnos.add(nia)){          //devuelve true si funciona
            return 0;
        }
        return -1;
    }
    public int anularMatriculaAlumno(int nia){
        if(this.alumnos.remove(nia)){
            return 0;
        }                                           //anularMatriculaAlumno y comprobarAlumno funcionan
        return -1;                                  //para poder eliminar alumnos
    }
    public int comprobarAlumnos(int nia){
        if(this.alumnos.contains(nia)){
            if(anularMatriculaAlumno(nia)==0){
                return 0;
            }
        }
        return -1;
    }

    //IMPRIMIR
    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author haoen
 */
public class BaseDeDatos {
    private Map<Integer, Alumno> alumnos= new TreeMap();
    private Map<Integer, Modulo> modulos= new TreeMap();
    
    public BaseDeDatos(){}
    
    //FUNCIONES
    public int menu(){
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----------MEN?-----------|");
        System.out.println("|0|-Salir del programa     |");
        System.out.println("|1|-Mantener Alumnos       |");
        System.out.println("|2|-Mantener M?dulos       |");
        System.out.println("|3|-Evaluar                |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    //ALTA
    public int altaAlumno(){
        Alumno comodin=Alumno.darDeAlta();
        if(this.alumnos.put(comodin.getIDENTIFICADOR(), comodin)==null){
            return 0;
        }
        return -1;
    }
    public int altaModulo(){
        Modulo comodin=Modulo.darDeAlta();
        if(this.modulos.put(comodin.getIDENTIFICADOR(), comodin)==null){
            return 0;
        }
        return -1;
    }
    //BAJA
    public int bajaAlumno(){
        int nia=Alumno.darDeBaja();
        if(this.alumnos.remove(nia)!=null){
            actualizarAlumno(nia);
            return 0;
        }
        return -1;  
    }
    public int bajaModulo(){
        int id=Modulo.darDeBaja();
        if(this.modulos.remove(id)!=null){
            actualizarModulo(id);
            return 0;
        }
        return -1; 
    }
    //ACTUALIZAR
    private int actualizarAlumno(int nia){
        return 0;    
    }
    private int actualizarModulo(int id){
        return 0;
    }
    //GETTER
}

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
    static Alumnos alumnos= new Alumnos();
    static Modulos modulos= new Modulos();
    
    public BaseDeDatos(){}
    
    //MENU
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
    
    //BAJA
    public int bajaAlumno(){
        int nia=alumnos.darDeBaja();
        if(nia==0){
            this.alumnos.actualizar(nia);
            return 0;
        }
        return -1;  
    }
    public int bajaModulo(){
        int id=modulos.darDeBaja();
        if(id==0){
            this.modulos.actualizar(id);
            return 0;
        }
        return -1; 
    }
    
    //MATRICULAR ALUMNO
    public int matricularAlumno(){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        System.out.print("Introduzca ID del m?dulo: ");
        int id=sc.nextInt();
        
        if(this.modulos.matricularAlumno(id, nia)==0 && this.alumnos.matricularModulo(id, nia)==0){
            return 0;
        }
        return -1;
    }
}

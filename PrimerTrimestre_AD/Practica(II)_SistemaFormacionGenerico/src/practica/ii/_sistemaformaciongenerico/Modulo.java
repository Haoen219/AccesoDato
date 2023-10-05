/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author haoen
 */
public class Modulo extends AlumnoModulo {
    private TreeSet<Integer> alumnos= new TreeSet<>();
    
    //Builder
    public Modulo(String nombre, int ID){
        super(nombre, ID);
    }
    
    //MODULO
    public Modulo darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta modulo-");
        System.out.print("Introduzca el nombre del modulo: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        
        return (new Modulo(nombre, nia));
    }
    public int darDeBaja(int identificador) {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de baja m?dulo-");
        System.out.print("Introduzca ID del m?dulo: ");
        
        return sc.nextInt();
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
        }
        return -1;
    }
    private int comprobarAlumnos(int nia){
        if(this.alumnos.contains(nia)){
            if(anularMatriculaAlumno(nia)==0){
                return 0;
            }
        }
        return -1;
    }

    //IMPRIMIR
    @Override
    public int imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public int imprimirDetallado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int menu() {
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----Mantener M?dulos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|4| Matricular Alumno      |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
}

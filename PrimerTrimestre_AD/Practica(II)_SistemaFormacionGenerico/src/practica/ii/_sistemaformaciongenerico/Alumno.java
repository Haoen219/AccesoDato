/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Scanner;

/**
 *
 * @author haoen
 */
public class Alumno extends AlumnoModulo{
    private Matricula matricula;
    
    //Builder
    public Alumno(String nombre, int NIA){
        super(nombre, NIA);
    }

    //ALUMNO
    public static Alumno darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        
        return (new Alumno(nombre, nia));
    }
    public static int darDeBaja() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        
        return sc.nextInt();
    }
    
    //MODULO
    
//    public int crearMatricula(){
//        Matricula matricula= new Matricula(this.NIA);
//    }
    
    
    //IMPRIMIR
    public int imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public int imprimirDetallado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public static int menu() {
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----Mantener M?dulos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
}

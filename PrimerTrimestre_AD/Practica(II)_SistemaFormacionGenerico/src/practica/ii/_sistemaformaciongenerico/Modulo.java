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
public class Modulo implements InterfazAlumnoModulo {
    private String nombre;
    final private int ID;
    
    //Builder
    public Modulo(String nombre, int ID){
        this.nombre=nombre;
        this.ID=ID;
    }

    @Override
    public int darDeAlta(int identificador, Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public int darDeBaja(int identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    @Override
    public int imprimirMenu() {
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

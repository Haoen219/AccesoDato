/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class PracticaII_SistemaDeFormacion {
    //MENUS
    public static int menu1(){
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----------MEN?-----------|");
        System.out.println("|    |0|-Salir del prog.   |");
        System.out.println("|A|-Mantener Alumnos-------|");
        System.out.println("|    |1| Dar de alta       |");
        System.out.println("|    |2| Dar de baja       |");
        System.out.println("|    |3| Listar            |");
        System.out.println("|B|-Mantener M?dulos-------|");
        System.out.println("|    |4| Dar de alta       |");
        System.out.println("|    |5| Dar de baja       |");
        System.out.println("|    |6| Listar            |");
        System.out.println("|    |7| Matricular Alumno |");
        System.out.println("|C|-Evaluar----------------|");
        System.out.println("|    |8| Qualificar        |");
        System.out.println("|    |9| Modificar         |");
        System.out.println("|   |10| Extraer bolet?n   |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    //ALUMNOS
    public static void darAltaAlumno(TreeMap alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca la NIA del alumno: ");
        int nia=sc.nextInt();
        if(alumnos.put(nia,new Alumno(nombre,nia))==null){
            System.out.println("----Se ha dado de alta al alumno "+nombre);
        }else{
            System.out.println("####Error, ya existe ese nia en la base de datos.");
        }
    }
    public static void darBajaAlumno(TreeMap alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.print("Introduzca el NIA del alumno: ");
        int nia=sc.nextInt();
        if(alumnos.remove(nia)!=null){
            System.out.println("----Se ha dado de baja al nia "+nia);
        }else{
            System.out.println("####Error, no se ha encontrado al alumno.");
        }
    }
    public static void listarAlumno(TreeMap<Integer, Alumno> alumnos){
        System.out.println("--Lista de Alumnos: ");
        for (int comodin : alumnos.keySet()) {
            alumnos.get(comodin).imprimir();
        }
        System.out.println("\n--Fin de la lista");
    }
    
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        TreeMap alumnos= new TreeMap();
        ArrayList<Modulo> modulos= new ArrayList<>();
        int opcion=1;
        int opcion2=1;
        
        System.out.println("--BIENVENIDO AL SISTEMA DE FORMACI?N--");
        do{
            opcion=menu1();
            switch(opcion){
                case 1:
                    System.out.println("\n-Dar de alta alumno-");
                    darAltaAlumno(alumnos);
                    break;
                case 2:
                    System.out.println("\n-Dar de baja alumno-");
                    darBajaAlumno(alumnos);
                    break;
                case 3:
                    System.out.println("\n-Listar alumnos-");
                    listarAlumno(alumnos);
                    break;
                case 4:
                    break;
                case 5:
                     break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 0:
                    break;
                default:
                    break;
           }
        }while(opcion>0);
        
        
        
    }
    
}

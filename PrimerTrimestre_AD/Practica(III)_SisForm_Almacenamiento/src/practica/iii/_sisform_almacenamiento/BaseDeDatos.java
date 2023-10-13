/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.iii._sisform_almacenamiento;

//import java.io.File;
//import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author haoen
 */
public class BaseDeDatos {
    static Alumnos alumnos= new Alumnos();
    static Modulos modulos= new Modulos();
    
//    File baseDeAlumnos= new File("Alumnos.txt");
//    File baseDeMatriculas= new File("Matriculas.txt");
//    File baseDeModulos= new File("Modulos.txt");
//    
//    PrintWriter escritor= null;
//    Scanner lector=null;
    
    public BaseDeDatos(){
        importarBase();
    }
    
    //FICHERO
    public int guardarBase(){
        if(this.alumnos.guardarBase()==0 && this.modulos.guardarBase()==0){
            System.out.println("++Se ha guardado la base de Datos");
            return 0;
        }
        System.out.println("--No se ha guardado la base de Datos");
        return -1;
    }
    public int importarBase(){
        if( this.modulos.importarBase()==0 && this.alumnos.importarBase()==0){
            System.out.println("++Se ha importado la base de Datos");
            return 0;
        }
        System.out.println("--No se ha importado la base de Datos");
        return -1;
    }
    
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
}

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
public class Alumnos implements BDDAlumnosModulos{
    Map<Integer, Alumno> alumnos= new TreeMap();
    
    public Alumnos(){}
    
    //ALUMNO
    @Override
    public int darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        
        if(this.alumnos.put(nia, new Alumno(nombre, nia))==null){
            return 0;
        }
        return -1;
    }
    @Override
    public int darDeBaja() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        
        if(this.alumnos.containsKey(nia)){
            this.alumnos.remove(nia);
            return 0;
        }
        return -1;
    }
    
    //MATRICULA
    public int matricularModulo(int id, int nia){
        if(this.alumnos.get(nia).matricularModulo(id)==0){
            return 0;
        }
        return -1;
    }
    public int modificarNota(){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Modificar notas-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        this.alumnos.get(nia).imprimirModulos();
        
        System.out.print("--Introduzca ID del m?dulo a modificar: ");
        int id=sc.nextInt();
        System.out.print("--La nota a modificar [1, 2, 3]: ");
        int posicion=sc.nextInt();
        System.out.print("Introduzca la nueva nota: ");
        double nota=sc.nextDouble();
        
        if(this.alumnos.get(nia).modificarNota(id, posicion, nota)==0){
            return 0;
        }
        return -1;
    }
    public int evaluarModulo(){
        Scanner sc= new Scanner(System.in);
        String calificacion="";
        System.out.println("\n-Modificar notas-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        this.alumnos.get(nia).imprimirModulos();
        System.out.print("--Introduzca ID del m?dulo a modificar: ");
        int id=sc.nextInt();
        
        switch(menuCalificar()){
                case 0 -> System.out.println("Acci?n cancelada, volviendo...");
                case 1 -> calificacion="Suspendido";
                case 2 -> calificacion="Bien";
                case 3 -> calificacion="Notable";
                case 4 -> calificacion="Excelente";
                default -> System.out.println("No se introdujo un valor valido");
        }
        if(this.alumnos.get(nia).evaluarModulo(id, calificacion)==0){
            return 0;
        }
        return -1;
    }
    
    //ACTUALIZAR
    public int actualizar(int id){
        for (int comodin : this.alumnos.keySet()) {
            if(this.alumnos.get(comodin).comprobarModulos(id)==-1){
                System.out.println("*Ha ocurrido un error, no se ha podido eliminar el modulo con ID"+
                        id+" de la matricula del alumno "+ this.alumnos.get(id).getNombre());
            }
        }
        return 0;
    }
    
    //MENU
    @Override
    public int menu() {
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Alumnos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    public int menuEvaluar() {
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Calificar              |");
        System.out.println("|2| Modificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    private int menuCalificar(){
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        System.out.println("|-------Calificaci?n-------|");
        System.out.println("|0|-Cancelar y volver      |");
        System.out.println("|1|-Suspendido             |");
        System.out.println("|2|-Bien                   |");
        System.out.println("|3|-Notable                |");
        System.out.println("|3|-Excelente              |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    
//    //GETTER
//    public Alumno getAlumno(int nia){
//        return this.alumnos.get(nia);
//    }
}
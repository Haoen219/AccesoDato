/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.Map;
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
        System.out.println("|0|-Salir del programa     |");
        System.out.println("|1|-Mantener Alumnos-------|");
        System.out.println("|2|-Mantener M?dulos-------|");
        System.out.println("|3|-Evaluar----------------|");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    public static int menu2(int seleccion){
        Scanner sc= new Scanner(System.in);
        System.out.println("");
        switch(seleccion){
            case 1 -> System.out.println("|-----Mantener Alumnos-----|");
            case 2 -> System.out.println("|-----Mantener M?dulos-----|");
            case 3 -> System.out.println("|---------Evaluar----------|");
        }
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.println("|0| Volver al menu previo  |");
        if(seleccion==1 || seleccion==2){
            System.out.println("|1| Dar de alta            |");
            System.out.println("|2| Dar de baja            |");
            System.out.println("|3| Listar                 |");
        }else{
            System.out.println("|1| Qualificar             |");
            System.out.println("|2| Modificar              |");
            System.out.println("|3| Extraer bolet?n        |");
        }
        if(seleccion==2){
            System.out.println("|4| Matricular Alumno      |");
        }
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    
    
    
    
//    //Funciones genericas
//    public void listar(TreeMap<Integer, AlumnosYModulos> elemento){
//        System.out.println("\n-Listar "+elemento.get(1).getClass()+"-");
//        System.out.println("--Lista de "+elemento.get(1).getClass()+": ");
//        for (int entry : elemento.keySet()) {
//            elemento.get(entry).imprimirLista();
//        }
//        System.out.println("\n--Fin de la lista");
//    }
    
    public static int pedirAlumno(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Introduzca el NIA del alumno: ");
        return sc.nextInt();
    }
    public static int pedirModulo(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Introduzca el ID del m?dulo: ");
        return sc.nextInt();
    }
    //ALUMNOS
    public static void darAltaAlumno(TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        int nia=pedirAlumno();
        if(alumnos.put(nia,new Alumno(nombre,nia))==null){
            System.out.println("----Se ha dado de alta al alumno "+nombre);
        }else{
            System.out.println("####Error, ya existe ese nia en la base de datos");
        }
    }
    public static void darBajaAlumno(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Dar de baja alumno-");
        int nia=pedirAlumno();
        if(alumnos.remove(nia)!=null){
            System.out.println("----Se ha dado de baja al nia "+nia);
        }else{
            System.out.println("####Error, no se ha encontrado al alumno");
        }
    }
    public static void listarAlumno(TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Listar alumnos-");
        System.out.println("--Lista de Alumnos: ");
        for (int comodin : alumnos.keySet()) {
            alumnos.get(comodin).imprimirAlumno();
        }
        System.out.println("\n--Fin de la lista");
    }
    //MODULOS
    public static void darAltaModulo(TreeMap<Integer, Modulo> modulos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta modulo-");
        System.out.print("Introduzca el nombre del m?dulo: ");
        String nombre=sc.nextLine();
        int id=0;
        if(modulos.isEmpty()){
            id=1;
        }else{
            id=modulos.lastKey()+1;
        }
//        System.out.println(id);
        if(modulos.put(id,new Modulo(nombre,id))==null){
            System.out.println("----Se ha dado de alta al m?dulo "+nombre);
        }else{
            System.out.println("####Error, ya existe ese ID en la base de datos");
        }
    }
    public static void darBajaModulo(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Dar de baja modulo-");
        int id=pedirModulo();
        if(modulos.remove(id)!=null){
            System.out.println("----Se ha dado de baja al m?dulo con ID "+id);
        }else{
            System.out.println("####Error, no se ha encontrado al m?dulo");
        }
    }
    public static void listarModulo(TreeMap<Integer, Modulo> modulos){
        System.out.println("\n-Listar modulos-");
        System.out.println("--Lista de Alumnos: ");
        for (int comodin : modulos.keySet()) {
            modulos.get(comodin).imprimirLista();
        }
        System.out.println("\n--Fin de la lista");
    }
    public static void matricularAlumno(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Matricular alumno-");
        int id=pedirModulo();
        int nia=pedirAlumno();
        if(modulos.get(id).matricularAlumno(alumnos.get(nia))==0){
            System.out.println("----Se ha matriculado el alumno "+
                    alumnos.get(nia).getNombre()+" al m?dulo de "+
                    modulos.get(id).getNombre());
        }else{
            System.out.println("####Error, no se ha podido matricular");
        }
    }
    //EVALUAR
    public static void cualificar(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Cualificar notas alumno-");
        int nia=pedirAlumno();
        alumnos.get(nia).imprimirAlumno();
        int id=pedirModulo();
        System.out.println("Nota: ");
        int nota=sc.nextInt();
        if(modulos.get(id).cualificarAlumno(alumnos.get(nia), nota)==0){
            System.out.println("----Se ha cualificado a "+alumnos.get(nia).getNombre()+" en "+modulos.get(id).getNombre());
        }else{
            System.out.println("####Error, no se ha podido procesar");
        }
    }
    public static void modificar(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Modificar notas alumno-");
        int nia=pedirAlumno();
        alumnos.get(nia).imprimirAlumno();
        int id=pedirModulo();
        System.out.println("Nota: ");
        int nota=sc.nextInt();
        if(modulos.get(id).cualificarAlumno(alumnos.get(nia), nota)==0){
            System.out.println("----Se ha cualificado a "+alumnos.get(nia).getNombre()+" en "+modulos.get(id).getNombre());
        }else{
            System.out.println("####Error, no se ha podido procesar");
        }
    }
    public static void imprimirBoletin(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Imprimir bolet?n-");
        int nia=pedirAlumno();
//        alumnos.get(nia).imprimirAlumno();
    }
    
    
    
    
    public static void main(String[] args) {
        TreeMap<Integer, Alumno> alumnos= new TreeMap();
        TreeMap<Integer, Modulo> modulos= new TreeMap();
        int opcion=0;
        
        System.out.println("--BIENVENIDO AL SISTEMA DE FORMACI?N--");
        do{
            opcion=menu1();
            switch(opcion){
                case 0 -> System.out.println("\n***Saliendo del programa...");
                case 1 -> {
                    //Mantener Alumno
                    switch(menu2(opcion)){
                        case 0 -> System.out.println("Volviendo al menu previo...");
                        case 1 -> darAltaAlumno(alumnos);
                        case 2 -> darBajaAlumno(modulos, alumnos);
                        case 3 -> listarAlumno(alumnos);
                        default -> System.out.println("Opci?n no valida, volviendo...");
                    }
                }
                case 2 -> {
                    //Mantener Alumno
                    switch(menu2(opcion)){
                        case 0 -> System.out.println("Volviendo al menu previo...");
                        case 1 -> darAltaModulo(modulos);
                        case 2 -> darBajaModulo(modulos, alumnos);
                        case 3 -> listarModulo(modulos);
                        case 4 -> matricularAlumno(modulos, alumnos);
                        default -> System.out.println("Opci?n no valida, volviendo...");
                    }
                }
                case 3 -> {
                    //Evaluar
                    switch(menu2(opcion)){
                        case 0 -> System.out.println("Volviendo al menu previo...");
                        case 1 -> cualificar(modulos, alumnos);
                        case 2 -> modificar(modulos, alumnos);
                        case 3 -> imprimirBoletin(modulos, alumnos);
                        default -> System.out.println("Opci?n no valida, volviendo...");
                    }
                }
                default -> System.out.println("Opci?n no valida, reintente...");
            }
        }while(opcion>0);
        
        
        
    }
    
}

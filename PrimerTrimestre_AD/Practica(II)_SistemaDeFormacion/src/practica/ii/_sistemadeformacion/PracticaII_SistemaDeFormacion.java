/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.ii._sistemadeformacion;

import java.util.InputMismatchException;
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
        System.out.println("|1|-Mantener Alumnos       |");
        System.out.println("|2|-Mantener M?dulos       |");
        System.out.println("|3|-Evaluar                |");
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
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    public static int menuCalificacion(){
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
    
    
    
//    //Funciones genericas
//    public void listar(TreeMap<Integer, AlumnosYModulos> elemento){
//        System.out.println("\n-Listar "+elemento.get(1).getClass()+"-");
//        System.out.println("--Lista de "+elemento.get(1).getClass()+": ");
//        for (int entry : elemento.keySet()) {
//            elemento.get(entry).imprimirLista();
//        }
//        System.out.println("\n--Fin de la lista");
//    }
    
    public static int pedirAlumno(TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        int nia=0;
        do{
            System.out.println("Introduzca el NIA del alumno: ");
            nia=sc.nextInt();
            if(!alumnos.containsKey(nia)) System.out.println("El NIA indicado no existe");
        }while(!alumnos.containsKey(nia));
        return nia;
    }
    public static int pedirModulo(TreeMap<Integer, Modulo> modulos){
        Scanner sc= new Scanner(System.in);
        int id=0;
        do{
            System.out.println("Introduzca el ID del m?dulo: ");
            id=sc.nextInt();
            if(!modulos.containsKey(id)) System.out.println("El ID indicado no existe");
        }while(!modulos.containsKey(id));
        return id;
    }
    
    
    
    
    //ALUMNOS
    public static void darAltaAlumno(TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        if(alumnos.put(nia,new Alumno(nombre,nia))==null){
            System.out.println("----Se ha dado de alta al alumno "+nombre+" con NIA "+nia);
        }else{
            System.out.println("####Error, ya existe ese NIA en la base de datos");
        }
    }
    public static void darBajaAlumno(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Dar de baja alumno-");
        int nia=pedirAlumno(alumnos);
        if(alumnos.remove(nia)!=null){
            System.out.println("----Se ha dado de baja al NIA "+nia);
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
        System.out.println("--Fin de la lista");
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
        if(modulos.put(id,new Modulo(nombre,id))==null){
            System.out.println("----Se ha dado de alta al m?dulo "+nombre+" con ID "+id);
        }else{
            System.out.println("####Error, ya existe ese ID en la base de datos");
        }
    }
    public static void darBajaModulo(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Dar de baja modulo-");
        int id=pedirModulo(modulos);
        if(modulos.remove(id)!=null){
            System.out.println("----Se ha dado de baja al m?dulo con ID "+id);
        }else{
            System.out.println("##Error, no se ha podido proceder");
        }
    }
    public static void listarModulo(TreeMap<Integer, Modulo> modulos){
        System.out.println("\n-Listar modulos-");
        for (int comodin : modulos.keySet()) {
            modulos.get(comodin).imprimirModulo();
        }
        System.out.println("--Fin de la lista");
    }
    public static void matricularAlumno(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Matricular alumno-");
        int id=pedirModulo(modulos);
        int nia=pedirAlumno(alumnos);
        if(modulos.get(id).matricularAlumno(alumnos.get(nia))==0 && alumnos.get(nia).matricularModulo(modulos.get(id))==0){
            System.out.println("----Se ha matriculado el alumno "+
                alumnos.get(nia).getNombre()+" al m?dulo de "+
                modulos.get(id).getNombre());
        }else{
            System.out.println("##Ya existe ese alumno en el m?dulo");
        }
    }
    
    
    
    
    //EVALUAR
    public static void calificar(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Cualificar notas alumno-");
        int opcion=1, id=0, nia=0;
        do{
            id=pedirModulo(modulos);
            nia=pedirAlumno(alumnos);
            
            String calificacion="";
            opcion=menuCalificacion();
            switch(opcion){
                case 0 -> System.out.println("Volviendo...");
                case 1 -> calificacion="Suspendido";
                case 2 -> calificacion="Bien";
                case 3 -> calificacion="Notable";
                case 4 -> calificacion="Excelente";
                default -> System.out.println("No se introdujo un valor valido");
            }
            if(alumnos.get(nia).calificarModulo(modulos.get(id), calificacion)==0){
                System.out.println("----Se ha cualificado a "+alumnos.get(nia).getNombre()+" en "+modulos.get(id).getNombre());
            }else{
                System.out.println("####Error, no se ha podido procesar");
            } 
        }while(opcion>0);
        
    }
    public static void modificar(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Modificar notas alumno-");
        int id=pedirModulo(modulos);
        int nia=pedirAlumno(alumnos);
        
        System.out.println("Nota: ");
        int nota=sc.nextInt();
        
        if(modulos.get(id).modificarAlumno(alumnos.get(nia), nota)==0){
            System.out.println("----Se ha cualificado a "+alumnos.get(nia).getNombre()+" en "+modulos.get(id).getNombre());
        }else{
            System.out.println("####Error, no se ha podido procesar");
        }
    }
    public static void imprimirBoletin(TreeMap<Integer, Modulo> modulos, TreeMap<Integer, Alumno> alumnos){
        System.out.println("\n-Imprimir bolet?n-");
        int nia=pedirAlumno(alumnos);
        alumnos.get(nia).imprimirMatricula();
    }
    
    
    
    //Main
    public static void main(String[] args) {
        TreeMap<Integer, Alumno> alumnos= new TreeMap();
        TreeMap<Integer, Modulo> modulos= new TreeMap();
        int opcion=1, opcion2=1;
        System.out.println("--BIENVENIDO AL SISTEMA DE FORMACI?N--");
        do{
            try{
                opcion=menu1();
                switch(opcion){
                    case 0 -> System.out.println("\n***Saliendo del programa...");
                    case 1 -> {
                        //Mantener Alumno
                        do{
                            try{
                                opcion2=menu2(opcion);
                                switch(opcion2){
                                    case 0 -> System.out.println("Volviendo al menu previo...");
                                    case 1 -> darAltaAlumno(alumnos);
                                    case 2 -> {
                                        if(!alumnos.isEmpty()){
                                            darBajaAlumno(modulos, alumnos);
                                        }else System.out.println("--Su lista de alumnos esta vacio");
                                    }
                                    case 3 -> {
                                        if(!alumnos.isEmpty()){
                                            listarAlumno(alumnos);
                                        }else System.out.println("--Su lista de alumnos esta vacio");
                                    }
                                    default -> System.out.println("Opci?n no valida");
                                }
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    case 2 -> {
                        //Mantener Modulo
                        do{
                            try{
                                opcion2=menu2(opcion);
                                switch(opcion2){
                                    case 0 -> System.out.println("Volviendo al menu previo...");
                                    case 1 -> darAltaModulo(modulos);
                                    case 2 -> {
                                        if(!modulos.isEmpty()){
                                            darBajaModulo(modulos, alumnos);
                                        }else System.out.println("--Su lista de modulos esta vacio");
                                    }
                                    case 3 -> {
                                        if(!modulos.isEmpty()){
                                            listarModulo(modulos);
                                        }else System.out.println("--Su lista de modulos esta vacio");
                                    }
                                    case 4 -> {
                                        if(!modulos.isEmpty()){
                                            matricularAlumno(modulos, alumnos);
                                        }else System.out.println("--Su lista de modulos esta vacio");
                                    }
                                    default -> System.out.println("Opci?n no valida");
                                }
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    case 3 -> {
                        //Evaluar
                        do{
                            try{
                                opcion2=menu2(opcion);
                                if(!alumnos.isEmpty()){
                                    switch(opcion2){
                                        case 0 -> System.out.println("Volviendo al menu previo...");
                                        case 1 -> calificar(modulos, alumnos);
                                        case 2 -> modificar(modulos, alumnos);
                                        case 3 -> imprimirBoletin(modulos, alumnos);
                                        default -> System.out.println("Opci?n no valida");
                                    }
                                }else{
                                    System.out.println("--Su lista de alumnos esta vacio, no hay nada que evaluar");
                                }
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    default -> System.out.println("Opci?n no valida, reintente...");
                }
            }catch(InputMismatchException ex){
                System.out.println("###ERROR: ha introducido un valor que no es entero.\n");
            }catch(Exception ex){
                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
            }
        }while(opcion>0);
    }
}
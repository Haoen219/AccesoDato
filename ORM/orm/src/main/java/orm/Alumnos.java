/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author haoen
 */
public class Alumnos implements BDDAlumnosModulos{
    TreeMap<Integer, Alumno> alumnos= new TreeMap();
    public Alumnos(){}
    
    //ALUMNO
    public boolean comprobar(int nia){
        if(this.alumnos.containsKey(nia))return true;
        else System.out.println("--NIA no existe en la base de datos");
        return false;
    }
    
    @Override
    public int darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        
        if(this.alumnos.containsKey(nia)){
            System.out.println("--Ya existe un alumno con ese NIA");
        }else{
            if(this.alumnos.put(nia, new Alumno(nombre, nia))==null){
                System.out.println("++Se ha dado de alta al alumno (NIA:"+nia+")");
                return 0;
            }
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
            if(BaseDeDatos.modulos.actualizar(nia)==0){
                System.out.println("++Se ha dado de baja al alumno (NIA:"+nia+")");
                return 0;
            }else{
                System.out.println("--No se ha podido dar de baja al alumno (NIA:"+nia+")");
            }
        }else{
            System.out.println("--No se ha encontrado ese NIA en la base de datos");
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
        if(BaseDeDatos.alumnos.comprobar(nia)){
            if(this.alumnos.get(nia).comprobarMatricula()){
                this.alumnos.get(nia).imprimirModulos();
                System.out.print("Introduzca ID del m?dulo a modificar: ");
                int id=sc.nextInt();
                if(this.alumnos.get(nia).comprobarModulo(id)){
                    System.out.print("La nota a modificar [1, 2, 3]: ");
                    int posicion=sc.nextInt();
                    if(posicion>0 && posicion<4){
                        System.out.print("Introduzca la nueva nota: ");
                        double nota=sc.nextDouble();
                        if(this.alumnos.get(nia).modificarNota(id, posicion, nota)==0){
                            return 0;
                        } 
                    }else{
                        System.out.println("--Se ha introducido una posici?n no v?lida");
                    }
                }
            }else{
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
        return -1;
    }
    public int evaluarModulo(){
        Scanner sc= new Scanner(System.in);
        String calificacion="";
        System.out.println("\n-Evaluar modulos-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        if(comprobar(nia)){
            if(this.alumnos.get(nia).comprobarMatricula()){
                this.alumnos.get(nia).imprimirModulos();
                System.out.print("Introduzca ID del m?dulo a modificar: ");
                int id=sc.nextInt();
                if(this.alumnos.get(nia).comprobarModulo(id)){
                    switch(menuCalificar()){
                        case 1 -> calificacion="Suspendido";
                        case 2 -> calificacion="Bien";
                        case 3 -> calificacion="Notable";
                        case 4 -> calificacion="Excelente";
                        default -> {
                            System.out.println("No se introdujo un valor valido");
                            return 0;
                        }
                    }
                    if(this.alumnos.get(nia).evaluarModulo(id, calificacion)==0){
                        System.out.println("++Se ha evaluado el modulo");
                        return 0;
                    } else{
                        System.out.println("--No se ha podido evaluar");
                    } 
                }
            }else{
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
        return -1;
    }
    
    //ACTUALIZAR
    public int actualizar(int id){
        for (int comodin : this.alumnos.keySet()) {
            if(this.alumnos.get(comodin).comprobarMatricula()){
                if(this.alumnos.get(comodin).eliminarModulos(id)==-1){
                    System.out.println("*Ha ocurrido un error, no se ha podido eliminar el modulo con ID "+
                            id+" de la matricula del alumno con NIA "+ this.alumnos.get(comodin).getNombre());
                }
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
        System.out.println("Calificaciones:");
        System.out.println("1- Suspendido");
        System.out.println("2- Bien");
        System.out.println("3- Notable");
        System.out.println("4-Excelente");
        System.out.print("OPCI?N: ");
        int opcion=sc.nextInt();
        return opcion;
    }
    
    //IMPRIMIR
    @Override
    public void listar(){
        System.out.println("\n-Listar alumnos-");
        for(int nia:this.alumnos.keySet()){
            this.alumnos.get(nia).imprimir();
        }
        System.out.println("--Fin de la lista");
    }
    public void imprimirBoletin(){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Imprimir Bolet?n-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.nextInt();
        if(comprobar(nia)){
            if(this.alumnos.get(nia).comprobarMatricula()){
                this.alumnos.get(nia).imprimirBoletin();
            }else{
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
    }
}
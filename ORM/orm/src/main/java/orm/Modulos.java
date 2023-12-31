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
public class Modulos implements BDDAlumnosModulos{
    TreeMap<Integer, Modulo> modulos= new TreeMap();
    
    public Modulos(){}
    
    //MODULO
    @Override
    public boolean comprobar(int id){
        if(this.modulos.containsKey(id))return true;
        else System.out.println("--ID no existe en la base de datos");
        return false;
    }
    @Override
    public int darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta m?dulo-");
        System.out.print("Introduzca el nombre del m?dulo: ");
        String nombre=sc.nextLine();
        int id=0;
        
        if(this.modulos.isEmpty())id=1;         //da el ID automaticamente y asigna ID=1
        else id=this.modulos.lastKey()+1;       //si la lista esta vacio
        
        if(this.modulos.containsKey(id)){
            System.out.println("--Ya existe un modulo con ese ID");
        }else{
            if(this.modulos.put(id, new Modulo(nombre, id))==null){
                System.out.println("++Se ha dado de alta al m?dulo (ID:"+id+")");
                return 0;
            }
        }
        return -1;
    }
    @Override
    public int darDeBaja() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de baja m?dulo-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id=sc.nextInt();
        
        if(comprobar(id)){
            this.modulos.remove(id);
            if(BaseDeDatos.alumnos.actualizar(id)==0){
                System.out.println("++Se ha dado de baja al m?dulo (ID:"+id+")");
                return 0;
            }
        }
        return -1;
    }
    
    //MATRICULA
    public int matricularAlumno(){
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id=sc.nextInt();
        if(comprobar(id)){
            System.out.print("Introduzca NIA del alumno: ");
            int nia=sc.nextInt();
            if(BaseDeDatos.alumnos.comprobar(nia)){
                if(this.modulos.get(id).matricularAlumno(nia)==0 && BaseDeDatos.alumnos.matricularModulo(id, nia)==0){
                    System.out.println("++Se ha matriculado el alumno (NIA:"+nia+")");
                    return 0;
                }else{
                    System.out.println("--No se ha podido matricular al alumno (NIA:"+nia+")");
                }
            }
        }
        return -1;
    }
    
    //ACTUALIZAR
    @Override
    public int actualizar(int nia){
        for (int comodin : this.modulos.keySet()) {
            if(this.modulos.get(comodin).anularMatriculaAlumno(nia)==-1){
                System.out.println("*Ha ocurrido un error, no se ha podido eliminar el alumno con nia "+nia
                        +" del m?dulo "+this.modulos.get(comodin).getIDENTIFICADOR());
            }
        }
        return 0;
    }

    //MENU
    @Override
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
    
    //IMPRIMIR
    public void listar(){
        System.out.println("\n-Listar modulos-");
        for(int nia:this.modulos.keySet()){
            this.modulos.get(nia).imprimir();
        }
        System.out.println("--Fin de la lista");
    }
    
    //GETTER
    public Modulo getModulo(int id){
        return this.modulos.get(id);
    }
}

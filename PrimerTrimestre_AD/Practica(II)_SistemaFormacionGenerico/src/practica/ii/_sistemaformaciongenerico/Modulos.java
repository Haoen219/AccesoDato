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
public class Modulos implements BDDAlumnosModulos{
    Map<Integer, Modulo> modulos= new TreeMap();
    
    public Modulos(){}
    
    //MODULO
    @Override
    public int darDeAlta() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de alta m?dulo-");
        System.out.print("Introduzca el nombre del m?dulo: ");
        String nombre=sc.nextLine();
        System.out.print("Introduzca ID del m?dulo: ");
        int id=sc.nextInt();
        
        if(this.modulos.put(id, new Modulo(nombre, id))==null){
            return 0;
        }
        return -1;
    }
    @Override
    public int darDeBaja() {
        Scanner sc= new Scanner(System.in);
        System.out.println("\n-Dar de baja m?dulo-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id=sc.nextInt();
        
        if(this.modulos.containsKey(id)){
            this.modulos.remove(id);
            return 0;
        }
        return -1;
    }
    
    //MATRICULA
    public int matricularAlumno(int id, int nia){
        if(this.modulos.get(id).matricularAlumno(nia)==0){
            return 0;
        }
        return -1;
    }
    
    //ACTUALIZAR
    @Override
    public int actualizar(int nia){
        for (int comodin : this.modulos.keySet()) {
            if(this.modulos.get(comodin).comprobarAlumnos(nia)==-1){
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
    
    //GETTER
    public Modulo getModulo(int id){
        return this.modulos.get(id);
    }
}

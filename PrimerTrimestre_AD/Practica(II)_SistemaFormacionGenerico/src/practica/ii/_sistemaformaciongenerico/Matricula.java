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
public class Matricula {
    private Map<Integer, Double[]> modulos= new TreeMap();
    private Map<Integer, String> calificaciones= new TreeMap();
    
    public Matricula(){}
    
    public int matricularModulo(int id){
        if(this.modulos.put(id, new Double[3])==null){       //los modulos reci?n matriculados no tiene notas
            return 0;
        }
        return -1;
    }
    
    public int anularMatriculaModulo(int id){
        if(this.modulos.remove(id)==null && this.calificaciones.remove(id)==null){
            return 0;
        }
        return -1;
    }
    
    public int modificarNota(int id, int posicion, double nota){
        Double[] comodin= this.modulos.get(id);
        comodin[posicion-1]=nota;                   //[posicion-1] porque le pedi al usuario 1, 2, o 3.
        if(this.modulos.put(id, comodin)!=null){
            return 0;
        }
        return -1;
    }
    
    public int evaluarModulo(int id, String calificaion){
        if(this.calificaciones.put(id, calificaion)!=null){
            return 0;
        }
        return -1;
    }
    
    //sirve para eliminar modulos dados de baja
    public int comprobarModulos(int id){
        for(int comodin:this.modulos.keySet()){
            if(comodin==id){
                this.modulos.remove(id);
                return 0;
            }
        }
        return -1;
    }
    
    //IMPRIMIR NOTAS
    public void imprimirModulos(){
        for(int id: this.modulos.keySet()){
            Modulo comodin=BaseDeDatos.modulos.getModulo(id);
            System.out.printf("\tID: %-8d %-20s  Notas: [ ",comodin.getIDENTIFICADOR(),comodin.getNombre());
            for (int i = 0; i < this.modulos.size(); i++) {
                System.out.printf("-%2.2f"+this.modulos.get(i)+"- ");
            }
            System.out.println("]  Calificaci?n: "+this.calificaciones.get(id));
        }
    }
    
    //GETTER
    public int getNumeroModulos(){
        return this.modulos.size();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author haoen
 */
public class Matricula {
    private TreeMap<Integer, Double[]> modulosMatriculados= new TreeMap();
    private TreeMap<Integer, String> calificaciones= new TreeMap();
    final private String CALIFICACIONDEFECTO="Sin calificar";
    
    public Matricula(){}
    
    public int matricularModulo(int id){           //los modulos reci?n matriculados no tiene notas
        if(this.modulosMatriculados.put(id, new Double[3])==null && this.calificaciones.put(id, this.CALIFICACIONDEFECTO)==null){
            return 0;
        }
        return -1;
    }
    
    public int eliminarModulo(int id){
        if(this.modulosMatriculados.remove(id)==null && this.calificaciones.remove(id)==null){
            return 0;
        }
        return -1;
    }
    
    public int modificarNota(int id, int posicion, double nota){
        Double[] comodin= this.modulosMatriculados.get(id);
        comodin[posicion-1]=nota;                   //[posicion-1] porque le pedi al usuario 1, 2, o 3.
        if(this.modulosMatriculados.put(id, comodin)!=null){
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
    
    public boolean comprobarModulo(int id){
        if (this.modulosMatriculados.containsKey(id)){
            return true;
        }else{
            System.out.println("--Modulo no existe en la matricula");
        }
        return false;
    }
    
    //IMPRIMIR NOTAS
    public void imprimirModulos(){
        for(int id: this.modulosMatriculados.keySet()){
            Modulo comodin=BaseDeDatos.modulos.getModulo(id);
            System.out.printf("\tID: %-8d %-20s  ",comodin.getIDENTIFICADOR(),comodin.getNombre());
            
            System.out.print("Notas: [ ");
            if(this.modulosMatriculados.containsKey(id)){
                for (int i = 0; i < this.modulosMatriculados.get(id).length; i++) {
                    if(this.modulosMatriculados.get(id)[i]==null){
                        System.out.printf("-nulo- ");
                    }else{
                        System.out.printf("-%-2.2f- ",this.modulosMatriculados.get(id)[i]);
                    }
                }
            }
            System.out.println("]  Calificaci?n: "+this.calificaciones.get(id));
        }
    }
    
    //GETTER
    public int getNumeroModulos(){
        return this.modulosMatriculados.size();
    }
}

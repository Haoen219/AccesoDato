/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.iv._sisform_fichero;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author haoen
 */
public class Matricula {
    private TreeMap<Integer, double[]> modulosMatriculados= new TreeMap();
    private TreeMap<Integer, String> calificaciones= new TreeMap();
    final private String CALIFICACIONDEFECTO="Sin-calificar";
    
    public Matricula(){}
    
    public int matricularModulo(int id){           //los modulos reci?n matriculados tienen 0 de nota
        double[] comodin={0,0,0};
        if(this.modulosMatriculados.put(id, comodin)==null && this.calificaciones.put(id, this.CALIFICACIONDEFECTO)==null){
            
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
        double[] comodin= this.modulosMatriculados.get(id);
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
            System.out.printf("\tID: %-8d %-20s ",comodin.getIDENTIFICADOR(),comodin.getNombre());
            
            System.out.print("Notas: [ ");
            if(this.modulosMatriculados.containsKey(id)){
                for (int i = 0; i < this.modulosMatriculados.get(id).length; i++) {
                    System.out.printf("-%02.2f- ",this.modulosMatriculados.get(id)[i]);
                }
            }
            System.out.println("]  Calificaci?n: "+this.calificaciones.get(id));
        }
    }
    
    //GUARDAR EN FICHERO
    public String formatoFichero(){
        //ID_NOTA-NOTA-NOTA-_CALIFICACION ID_NOTA-NOTA-NOTA-_CALIFICACION ...
        String matricula="";
        for(int id:this.modulosMatriculados.keySet()){
            matricula+=id+"_";
            
            for(int i=0; i<this.modulosMatriculados.get(id).length; i++){
                matricula+=this.modulosMatriculados.get(id)[i]+"-";
            }
            matricula+="_"+this.calificaciones.get(id)+" ";
        }
        return matricula;
    }
    
    //GETTER
    public int getNumeroModulos(){
        return this.modulosMatriculados.size();
    }
}

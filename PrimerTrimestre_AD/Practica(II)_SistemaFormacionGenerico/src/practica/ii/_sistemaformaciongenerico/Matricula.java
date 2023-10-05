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
    private Map<Integer, Integer[]> modulos= new TreeMap();
    private Map<Integer, String> calificaciones= new TreeMap();
    
    public Matricula(){}
    
    public int matricularModulo(int id){
        if(this.modulos.put(id, null)==null){
            return 0;
        }
        return -1;
    }
    
    public int anularMatriculaModulo(){
        
        return 0;
    }
    
    public int modificarNotas(){
        
        return 0;
    }
    
    public int evaluar(){
        
        return 0;
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
}

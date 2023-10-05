/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public abstract class AlumnoModulo{
    private String nombre;
    final private int IDENTIFICADOR;
    
    AlumnoModulo(String nombre, int identificador){
        this.nombre=nombre;
        this.IDENTIFICADOR=identificador;
    }
    
    Object darDeAlta(){
        return null;
    }
    int darDeBaja(){
        return 0;
    }
    int imprimir(){
        return 0;
    }
    
    int imprimirDetallado(){
        return 0;
    }
    int menu(){
        return 0;
    }
    
    //GETTER
    public String getNombre() {
        return nombre;
    }
    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }
    
}

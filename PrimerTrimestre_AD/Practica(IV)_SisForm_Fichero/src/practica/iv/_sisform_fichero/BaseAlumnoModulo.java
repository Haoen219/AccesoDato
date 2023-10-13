/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package practica.iv._sisform_fichero;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public abstract class BaseAlumnoModulo{
    private String nombre;
    final private int IDENTIFICADOR;
    
    BaseAlumnoModulo(String nombre, int identificador){
        this.nombre=nombre;
        this.IDENTIFICADOR=identificador;
    }
    
    //GETTER
    public String getNombre() {
        return nombre;
    }
    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }
    
}

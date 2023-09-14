/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicioinicial01;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Persona implements Comparable<Persona>{
    private String nombre;
    private int altura;
    private int edad;
    
    public Persona (String nombre, int altura, int edad){
        this.nombre=nombre;
        this.altura=altura;
        this.edad=edad;
    }
    
    @Override
    public int compareTo(Persona o) {
        int resultado=0;
        if (this.getAltura()<o.getAltura()) {
            resultado=6;
        }else if (this.getAltura()>o.getAltura()) {
            resultado=-6; 
        }else resultado=2;
        
        if (this.getEdad()<o.getEdad()) {
            resultado*=2; 
        }else if (this.getEdad()>o.getEdad()) {
            resultado/=2;
        }else resultado-=2;
        
        return resultado;
    }
    /**
     * Altura--
     * Si es m?s alto es positivo.
     * 
     * Edad----
     * Si tiene m?s edad es 3/-3/1.
     * Si tiene menos es 12/-12/4.
     * 
     * Si es igual es 0.
     */
    
    /**
     * - -    6*2= 12
     * - =    6-2= 4
     * - +    6/2= 3
     * 
     * = -    2*2= 4
     * = =    2-2= 0
     * = +    2/2= 1
     * 
     * + -   -6*2= -12
     * + =   -6-2= -8
     * + +   -6/2= -3
     */
    
    public String getNombre() {
        return nombre;
    }
    public int getAltura() {
        return altura;
    }
    public int getEdad() {
        return edad;
    }
    
}

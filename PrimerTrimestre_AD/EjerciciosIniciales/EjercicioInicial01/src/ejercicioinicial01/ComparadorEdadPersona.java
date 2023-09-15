/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicioinicial01;

import java.util.Comparator;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class ComparadorEdadPersona implements Comparator<Persona> {
    @Override
    public int compare(Persona p1, Persona p2) {
        int comparacion= p1.compareTo(p2);
        int[] valorMayor= new int[]{1,3,-3};
        
        if (comparacion==valorMayor[0] || comparacion==valorMayor[1] || comparacion==valorMayor[2]){
            return -1;
        }else if (comparacion==0){
            return 0;
        }else{
            return 1;
        }
    }
//    public int compare(Persona p1, Persona p2) {
//        if (p1.getEdad()<p2.getEdad()){
//            return -1;
//        }else if (p1.getEdad()==p2.getEdad()){
//            return 0;
//        }else{
//            return 1;
//        }
//    }
}

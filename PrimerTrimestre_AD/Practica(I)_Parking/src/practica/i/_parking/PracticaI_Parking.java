/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.i._parking;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class PracticaI_Parking {
    static Parking parking;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca la cantidad de plazas: ");
        int plazas= sc.nextInt();
        System.out.print("Introduzca la cantidad de coches: ");
        int numeroCoches= sc.nextInt();
        System.out.println("");
        
        MaquinaEntrada cochesCirculando= new MaquinaEntrada();
        
        //instanciar parking
        parking= new Parking(plazas, numeroCoches);
        ArrayList<Coche> coches= new ArrayList<>();
        //instanciar coches
        for(int i=0; i<numeroCoches; i++){
            coches.add(new Coche((i+1)));
            coches.get(i).start();
        }
    }
}

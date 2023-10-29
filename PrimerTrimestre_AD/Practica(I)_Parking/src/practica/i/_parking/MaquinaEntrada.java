/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.i._parking;


/**
 *
 * @author haoen
 */
public class MaquinaEntrada{
    private int random;
    final static int MINIMO = 1000;
    final static int MAXIMO = 4000;
    
    public void aparcarCoche(int id) {
        PracticaI_Parking.parking.aparcarCoche(id);
    }

    public void salirCoche(int id) {
        PracticaI_Parking.parking.salirCoche(id);
    }
}

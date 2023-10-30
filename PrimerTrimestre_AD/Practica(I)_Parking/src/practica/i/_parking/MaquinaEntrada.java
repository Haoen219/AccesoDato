/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.i._parking;

/**
 *
 * @author haoen
 */
public class MaquinaEntrada {
    public boolean entrarCoche(int id) {
        return PracticaI_Parking.parking.aparcarCoche(id);
    }

    public boolean salirCoche(int id) {
        return PracticaI_Parking.parking.desaparcarCoche(id);
    }
}

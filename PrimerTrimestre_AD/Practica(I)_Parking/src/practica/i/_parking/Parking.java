/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.i._parking;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Parking {

    private int plazas;
    private int plazasLibres;
    private int numeroCoches;
    private int[] parking;

    public Parking(int plazas, int coches) {
        this.plazas = plazas;
        this.plazasLibres = plazas;
        this.numeroCoches = coches;
        this.parking = new int[plazas];
        instanciarParking();
    }

    private void instanciarParking() {
        for (int i = 0; i < this.plazas; i++) {
            this.parking[i] = 0;
        }
    }

    public boolean aparcarCoche(int coche) {
        //busca el primer sitio libre
        int libre = -1;
        for (int i = 0; i < this.plazas; i++) {
            if (this.parking[i] == 0) {
                libre = i;
                break;
            }
        }
        if (libre > -1) {
            this.parking[libre] = coche;
            this.plazasLibres--;
            System.out.println("ENTRADA: El coche " + coche + " aparca en la plaza " + libre);
            mostrarParking();
            return true;
        }
        return false;
    }

    public boolean desaparcarCoche(int coche) {
        boolean salir = false;
        for (int i = 0; i < this.plazas; i++) {
            if (this.parking[i] == coche) {
                this.parking[i] = 0;
                salir = true;
                break;
            }
        }
        this.plazasLibres++;
        if (salir) {
            System.out.println("SALIDA: El coche " + coche + " sale del parking");
            mostrarParking();
        } else {
            //no deeria de imprimirse
            System.out.println("#No se ha podido salir del parking!");
        }
        return salir;
    }

    public void mostrarParking() {
        System.out.println("Plazas libres: " + this.plazasLibres);
        System.out.print("Parking: ");
        for (int i = 0; i < this.plazas; i++) {
            System.out.print("[" + this.parking[i] + "]");
        }
        System.out.println("\n");
    }
}

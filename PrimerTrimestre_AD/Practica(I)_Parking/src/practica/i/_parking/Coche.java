/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.i._parking;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Coche extends Thread {
    private int random;
    final static int MINIMO = 1000;
    final static int MAXIMO = 4000;

    final private int ID;
    private static MaquinaEntrada maquina = new MaquinaEntrada();
    //private int plaza;

    public Coche(int id) {
        this.ID = id;
    }

    @Override
    public void run() {
        esperarRandom();
        while (true) {
            synchronized (maquina) {
                this.maquina.aparcarCoche(this.ID);
            }
            esperarRandom();
            synchronized (maquina) {
                this.maquina.salirCoche(this.ID);
            }
            esperarRandom();
        }
    }

    private void esperarRandom() {
        random = (int) (Math.random() * (this.MAXIMO + this.MINIMO));
        try {
            sleep(random);
        } catch (InterruptedException ex) {
            System.out.println("Se ha interrimpido la espera en coche " + this.ID);
        }
    }
//    private synchronized void esperarHasta() {
//        try {
//            wait();
//        } catch (InterruptedException ex) {
//            System.out.println("Se ha interrimpido la espera en coche " + this.ID);
//        }
//    }

    //GETTER SETTER
    public int getID() {
        return this.ID;
    }
}

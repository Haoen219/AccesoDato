/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

/**
 *
 * @author haoen
 */
public interface BDDAlumnosModulos{
    int darDeAlta();
    int darDeBaja();
    boolean comprobar(int identificador);
    int menu();
    int actualizar(int identificador);
    void listar();
}

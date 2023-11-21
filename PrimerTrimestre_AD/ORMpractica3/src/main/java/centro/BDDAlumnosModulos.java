/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package practica.iii._sisform_almacenamiento;

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
    int guardarBase();
    int importarBase();
    void listar();
}

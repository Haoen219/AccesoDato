/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.Scanner;

/**
 *
 * @author haoen
 */
public class Alumno extends BaseAlumnoModulo{
    private Matricula matricula;
    
    //Builder
    public Alumno(String nombre, int NIA){
        super(nombre, NIA);
    }
    
    //MATRICULA
    public int comprobarModulos(int id){                    //sirve para eliminar un modulo de la matricula
        if(this.matricula.comprobarModulos(id)==0){
            return 0;
        }
        return -1;
    }
    public int modificarNota(int id, int posicion, double nota){
        if(this.matricula.modificarNota(id, posicion, nota)==0){
            return 0;
        }
        return -1;
    }
    public int evaluarModulo(int id, String calificacion){
        if(this.matricula.evaluarModulo(id, calificacion)==0){
            return 0;
        }
        return -1;
    }
    
    
    //MODULO
    public int matricularModulo(int id){
        if (this.matricula==null){
            this.matricula= new Matricula();        //instancia una matricula si no tiene, haciendo que
        }                                           //un alumno no tenga matricula hasta no tener un modulo
        if(this.matricula.matricularModulo(id)==0){
            return 0;
        }
        return -1;
    }
    
    
    //IMPRIMIR
    @Override
    public void imprimir() {
        System.out.printf("NIA: %-8d Nombre: %-30s Modulos: %-2d\n",
                this.getIDENTIFICADOR(), this.getNombre(),this.matricula.getNumeroModulos());
    }
    public void imprimirDetallado() {
        imprimir();
        imprimirModulos();
    }
    public void imprimirModulos() {
        System.out.println("Matricula: ");
        this.matricula.imprimirModulos();
    }
}

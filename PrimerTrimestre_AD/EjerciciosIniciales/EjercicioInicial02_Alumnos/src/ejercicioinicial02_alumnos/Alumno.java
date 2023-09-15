/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicioinicial02_alumnos;

import java.util.ArrayList;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Alumno implements Comparable<Alumno> {
    private String nombre;
    private ArrayList<Double> notas= new ArrayList<>();
    private int aprobados;
    private float porcentajeAprobado;
    
    
    public Alumno (String nombre, double... notas){
        this.nombre=nombre;
        for(double a:notas){
            this.notas.add(a);
            if(a>=5){
                this.aprobados++;
            }
        }
        this.porcentajeAprobado=(this.aprobados*100)/this.notas.size();
        System.out.println(this.porcentajeAprobado);
    }
    
    @Override
    public int compareTo(Alumno o) {
        int resultado=0;
        if (this.getPorcentajeAprobado()<o.getPorcentajeAprobado()) {
            resultado=-1;
        }else if (this.getPorcentajeAprobado()>o.getPorcentajeAprobado()) {
            resultado=1; 
        }else resultado=0;
        
        if(resultado==0){
            if (this.getAprobados()<o.getAprobados()) {
                resultado=-1; 
            }else if (this.getAprobados()>o.getAprobados()) {
                resultado=1;
            }else resultado=0;
        }
        return resultado;
    }

    public ArrayList<Double> getNotas() {
        return notas;
    }
    public int getAprobados() {
        return aprobados;
    }
    public double getPorcentajeAprobado() {
        return porcentajeAprobado;
    }
    public String getNombre() {
        return nombre;
    }
}

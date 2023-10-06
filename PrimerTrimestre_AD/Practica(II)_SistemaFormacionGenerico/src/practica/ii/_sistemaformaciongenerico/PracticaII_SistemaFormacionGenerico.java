/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.ii._sistemaformaciongenerico;

import java.util.InputMismatchException;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class PracticaII_SistemaFormacionGenerico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BaseDeDatos baseDeDatos= new BaseDeDatos();
        int opcion=1, opcion2=1;
        System.out.println("--BIENVENIDO AL SISTEMA DE FORMACI?N--");
        do{
            try{
                opcion=baseDeDatos.menu();
                switch(opcion){
                    case 0 -> System.out.println("\n***Saliendo del programa...");
                    case 1 -> {
                        //Mantener Alumno
                        do{
                            try{
                                opcion2=BaseDeDatos.alumnos.menu();
                                switch(opcion2){
                                    
                                }
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    case 2 -> {
                        //Mantener Modulo
                        do{
                            try{
                                opcion2=BaseDeDatos.modulos.menu();
                                switch(opcion2){
                                    
                                }
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    case 3 -> {
                        //Evaluar
                        do{
                            try{
                                opcion2=BaseDeDatos.alumnos.menuEvaluar();
                                
                                
                            }catch(InputMismatchException ex){
                                System.out.println("###ERROR: ha introducido un valor que no es entero.");
                            }catch(Exception ex){
                                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
                            }
                        }while(opcion2>0);
                    }
                    default -> System.out.println("Opci?n no valida, reintente...");
                }
            }catch(InputMismatchException ex){
                System.out.println("###ERROR: ha introducido un valor que no es entero.\n");
            }catch(Exception ex){
                System.out.println("###ERROR: ha ocurrido un error inesperado.\n"+ex.getLocalizedMessage());
            }
        }while(opcion>0);
    }
    
}

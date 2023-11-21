package persistencia;

import java.util.InputMismatchException;

import org.hibernate.Session;

import centro.Alumno;
import centro.Matricula;
import centro.Modulo;
import centro.Notas;

import utilidades.App;
import utilidades.Lector;


public class ORM 
{
    /* 
    private static Alumno alumnos;
    private static Modulo modulos;
    private static Matricula matriculas;
    private static Notas notas;
    */
    
    private static final Conexion conexion=new Conexion();
    
    //MENU DE PROGRAMA
    private int menu()
    {
        int option=-1;
        Lector in = new Lector(System.in);
        while ((option<0))
        {
            System.out.println("");
            System.out.println("|-----------MENÚ-----------|");
            System.out.println("|0|-Salir del programa     |");
            System.out.println("|1|-Mantener Alumnos       |");
            System.out.println("|2|-Mantener Módulos       |");
            System.out.println("|3|-Mantener Matricula     |");
            System.out.println("|"+"-".repeat(26)+"|");
            System.out.print("OPCIÓN: ");
            option=in.leerEntero(0,5);
        }
        return option;        
    }

    private void realizarOpcion(int choice){
        switch(choice){
            case 0:break;
            case 1:menuAlumnos();break;            
            case 2:menuModulos();break;
            case 3:menuMatriculas();break;            
            default: System.out.println(App.ANSI_CYAN+"Hay que elegit una de las opciones (numero entre parentesis)"+App.ANSI_WHITE);
        }
    }

    private static int menuAlumnos(){
        int opcion=0;
        Alumno alumnoComodin=new Alumno();
        do{
            try{
                opcion=alumnoComodin.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> alumnoComodin.darDeAlta();
                    case 2 -> alumnoComodin.darDeBaja();
                    case 3 -> alumnoComodin.listar();
                    default -> System.out.println("--Opción no valida");
                }
            }catch(InputMismatchException ex){
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            }catch(Exception ex){
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n"+ex);
            }
        }while(opcion>0);
        return 0;
    }            
    private static int menuModulos(){
        int opcion=0;
        Modulo moduloComodin=new Modulo();
        do{
            try{
                opcion=moduloComodin.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> moduloComodin.darDeAlta();
                    case 2 -> moduloComodin.darDeBaja();
                    case 3 -> moduloComodin.listar();
                    case 4 -> moduloComodin.matricularAlumno();
                    default -> System.out.println("--Opción no valida");
                }
            }catch(InputMismatchException ex){
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            }catch(Exception ex){
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n"+ex);
            }
        }while(opcion>0);
        return 0;
    }
    private static int menuMatriculas(){
        int opcion=0;
        Matricula matriculaComodin=new Matricula();
        do{
            try{
                opcion=matriculaComodin.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> matriculaComodin.modificarNota();
                    case 2 -> matriculaComodin.calificar();
                    case 3 -> matriculaComodin.mostrar();
                    default -> System.out.println("--Opción no valida");
                }
            }catch(InputMismatchException ex){
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            }catch(Exception ex){
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n"+ex);
            }
        }while(opcion>0);
        return 0;
    }
    
    public void haz()
    {
        int opcion;
        Session sesion = conexion.getSessionFactory().openSession();
        String resultado = (String) sesion.createNativeQuery("SELECT VERSION()").getSingleResult();
        System.out.println("La versión que estás usando es: " +resultado);

        do
        {
            opcion=menu();
            realizarOpcion(opcion);
        }
        while (opcion!=0);
    }
    /**public static Alumnos alumnos()
    {
        return alumnos;
    }
    public static Modulos modulos()
    {
        return modulos;
    }
    public static Matriculas matriculas()
    {
        return matriculas;
    }
    */
    public Conexion conexion()
    {
        return conexion;
    }
}
package persistencia;

import java.util.InputMismatchException;

import org.hibernate.Session;

import centro.Alumnos;
import centro.Matriculas;
import centro.Modulos;
//import centro.Notas;

import utilidades.App;
import utilidades.Lector;


public class ORM 
{
    
    private static Alumnos alumnos;
    private static Modulos modulos;
    private static Matriculas matriculas;
    //private static Notas notas;
    
    
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
        //Alumnos gestorAlumnos=new Alumnos();
        do{
            try{
                opcion=alumnos.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> alumnos.darDeAlta();
                    case 2 -> alumnos.darDeBaja();
                    case 3 -> alumnos.listar();
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
        //Modulos gestorModulos=new Modulo();
        do{
            try{
                opcion=modulos.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> modulos.darDeAlta();
                    case 2 -> modulos.darDeBaja();
                    case 3 -> modulos.listar();
                    case 4 -> modulos.matricularAlumno();
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
        //Matriculas gestorMatriculas=new Matricula();
        do{
            try{
                opcion=matriculas.menu();
                switch(opcion){
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> matriculas.modificarNotas();
                    case 2 -> matriculas.evaluarModulo();
                    case 3 -> matriculas.mostrar();
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
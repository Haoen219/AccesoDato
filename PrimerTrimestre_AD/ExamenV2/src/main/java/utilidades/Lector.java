package utilidades;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Lector 
{
    private Scanner lector;
    public Lector(InputStream readable)
    {
        lector =new Scanner(readable);
    }
    public String leer()
    {
        return leer(false);
    }
    public String leer(boolean permitidaVacia)
    {
        int retorno=-1;
        String cadena="";
        while ((retorno<0) || (!permitidaVacia && (retorno==0)))
        {
            try
            {
                cadena=lector.nextLine();
                retorno=cadena.length();
            }
            catch (InputMismatchException ime)
            {
                System.err.println(App.ANSI_RED + "Error en la lectura" + App.ANSI_WHITE);
                lector.next();
                retorno=-1;
            }
        }
        return cadena;
    }
    public int leerEntero(boolean signed)
    {
        int retorno=-1;
        boolean salir=false;
        while (!salir)
        {
            try
            {
                retorno=lector.nextInt();
                salir=true;
                if (retorno<0 && !signed)
                {
                    System.err.println(App.ANSI_RED + "se esperaba un numero positivo" + App.ANSI_WHITE);
                    salir=false;
                }
            }
            catch (NumberFormatException | InputMismatchException e)
            {
                System.err.println(App.ANSI_RED + "se esperaba un numero" + App.ANSI_WHITE);
                salir=false;
                lector.next();
            }
            
        }
        return retorno;
    }
    public int leerEntero(int min,int max)
    {
        int retorno=-1;
        while (retorno<0)
        {
            try
            {
                retorno=lector.nextInt();
                if ((retorno<min) || (retorno>max))
                {
                    System.err.println(App.ANSI_RED + "se esperaba un numero entre "+min+" y "+max + App.ANSI_WHITE);
                }
            }
            catch (NumberFormatException | InputMismatchException e)
            {
                System.err.println(App.ANSI_RED + "se esperaba un numero" + App.ANSI_WHITE);
                retorno=-1;
                lector.next();
            }
        }
        return retorno;
    }
    public TextoValor obtener()
    {
        return obtener(false);
    }
    public TextoValor obtener(boolean esReal)
    {
        boolean bien =false;
        int entero=0;
        float real=0.0f;
        String cadena;
        cadena=lector.nextLine();
        while (!bien)
        {
            bien=true;
            try
            {
                if (!esReal)
                {
                    entero=Integer.parseInt(cadena);
                }
                else
                {
                    real=Float.parseFloat(cadena);
                }
            }
            catch (NumberFormatException | InputMismatchException e)
            {
                System.err.println(App.ANSI_RED + "se esperaba un numero" + App.ANSI_WHITE);
                bien=false;
                entero=0;
                cadena=lector.nextLine();
            }   
        }
        if (!esReal)
        {
            return new TextoValor(entero,cadena);
        }
        else
        {
            return new TextoValor(real,cadena);
        }
    }
    public TextoValor obtener(String mensaje,String tipo)
    {
        boolean bien =false,esReal=false,esEntero=false;
        int entero=0;
        float real=0.0f;
        String cadena;
        System.out.print(mensaje);
        TextoValor textovalor=new TextoValor(0,"");
        cadena=lector.nextLine();
        while (!bien)
        {
            bien=true;
            try
            {
                if (tipo.equals("%d"))
                {
                    entero=Integer.parseInt(cadena);
                    esEntero=true;
                }
                if (tipo.equals("%f"))
                {
                    real=Float.parseFloat(cadena);
                    esReal=true;
                }
            }
            catch (NumberFormatException | InputMismatchException e)
            {
                System.err.println(App.ANSI_RED + "se esperaba un numero" + App.ANSI_WHITE);
                bien=false;
                entero=0;
                cadena=lector.nextLine();
            }   
        }
        if (esEntero)
        {
            textovalor= new TextoValor(entero,cadena);
        }
        if (esReal)
        {
            textovalor= new TextoValor(real,cadena);
        }
        if ((!esEntero) && (!esReal))
        {
            textovalor= new TextoValor(0,cadena);
        }
        return textovalor;
    }
}
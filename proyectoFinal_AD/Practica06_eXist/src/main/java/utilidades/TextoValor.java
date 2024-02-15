package utilidades;

public class TextoValor
{
    private int entero=0;
    private String cadena="";
    private float real=0.0f;
    public TextoValor(int e,String s)
    {
        entero=e;
        cadena=s;
    }
    public TextoValor(float r,String s)
    {
        real=r;
        cadena=s;
    }
    public int entero()
    {
        return entero;
    }
    public String cadena()
    {
        return cadena;
    }
    public float real()
    {
        return real;
    }
}

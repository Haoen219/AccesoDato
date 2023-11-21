package centro;

public class Modulo extends Mantenible {
    private long id;
    private byte codigo;
    private String nombre;
    private String abreviatura;

    public Modulo(byte codigo, String nombre, String abreviatura) {
        // A implementar
    }

    public long id() {
        return id;
    }

    public byte codigo() {
        return codigo;
    }

    public String nombre() {
        return nombre;
    }

    public String abreviatura() {
        return abreviatura;
    }

    public void nombre(String nombre) {
        this.nombre = nombre;
    }

    public void abrevitura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}

package centro;

public class Modulos extends Mantenimiento<Modulo> {
    public Modulos() {
        // A implementar
    }

    @Override
    public int Alta() {
        return 0;// A implementar
    }

    @Override
    public int Baja() {
        return 0;// A implementar
    }

    @Override
    public int Modificacion() {
        return 0;// A implementar
    }

    @Override
    public int Importa(String nombreArchivo) {
        return 0;// A implementar
    }

    @Override
    public int Exporta(String nombreArchivo) {
        return 0;// A implementar
    }

    @Override
    public int Alta(Modulo nuevo, Agrupacion[] dependencias, Integer id) {
        return 0;// A implementar
    }

    @Override
    public int Baja(Modulo abandono, Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Modificacion(Modulo modificado) {
        return 0;// A implementar
    }

    @Override
    public int Listado(Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Listado(int tabs) {
        return 0;// A implementar
    }
}
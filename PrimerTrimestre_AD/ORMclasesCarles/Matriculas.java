package centro;

public class Matriculas extends Mantenimiento<Matricula> {
    public Matriculas() {
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
    public int Alta(Matricula nuevo, Agrupacion[] dependencias, Integer id) {
        return 0;// A implementar
    }

    @Override
    public int Baja(Matricula abandono, Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Modificacion(Matricula modificado) {
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
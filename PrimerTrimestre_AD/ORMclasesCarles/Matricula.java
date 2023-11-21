package centro;

public class Matricula extends Mantenible {

    private Notas notas;
    private long id;
    private long alumno;
    private long modulo;

    public Matricula(long alumno, long modulo) {
        // A implementar
    }

    public long id() {
        return id;
    }

    public Notas notas() {
        return notas;
    }

    public void notas(Notas notas) {
        this.notas = notas;
    }

    public long alumno() {
        return alumno;
    }

    public long modulo() {
        return modulo;
    }
}

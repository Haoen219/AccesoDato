package centro;

public class Nota extends Mantenible {
    private long matricula;
    private byte evaluacion;
    private float calificacion;

    public Nota() {
        // A implementar
    }

    public Nota(long matricula, int evaluacion, float nota) {
        // A implementar
    }

    public long matricula() {
        return matricula;
    }

    public byte evaluacion() {
        return evaluacion;
    }

    public float calificacion() {
        return calificacion;
    }

    public void calificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}
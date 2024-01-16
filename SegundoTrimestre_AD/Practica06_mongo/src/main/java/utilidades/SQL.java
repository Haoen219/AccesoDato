package utilidades;

public class SQL {
    //tablas
    public final static String alumno_tabla = "alumno";
    public final static String alumno_id = "alumno_nia";
    public final static String alumno_nombre = "alumno_nombre";

    public final static String modulo_tabla = "modulo";
    public final static String modulo_id = "modulo_id";
    public final static String modulo_nombre = "modulo_nombre";

    public final static String notas_tabla = "notas";
    public final static String notas_id = "notas_id";
    public final static String notas_nota1 = "nota1";
    public final static String notas_nota2 = "nota2";
    public final static String notas_nota3 = "nota3";

    public final static String matricula_tabla = "matricula";
    public final static String matricula_id = "matricula_id";
    public final static String matricula_alumno_id = "almuno_id";
    public final static String matricula_modulo_id = "modulo_id";
    public final static String matricula_notas_id = "notas_id";
    public final static String matricula_calificacion = "calificacion";

    // BUSCAR
    public static String todoAlumno = "SELECT * FROM alumno";
    public static String todoModulo = "SELECT * FROM modulo";
    public static String todoMatricula = "SELECT * FROM matricula";
    public static String todoNotas = "SELECT * FROM notas";

    public static String buscarAlumnoID(String id) {
        return "SELECT * FROM alumno WHERE alumno_nia = '" + id + "'";
    }

    public static String buscarModuloID(String id) {
        return "SELECT * FROM modulo WHERE modulo_id = '" + id + "'";
    }

    public static String buscarMatriculaID(String id) {
        return "SELECT * FROM matricula WHERE matricula_id = '" + id + "'";
    }

    public static String buscaNotaID(String id) {
        return "SELECT * FROM notas WHERE notas_id = '" + id + "'";
    }

    public static String buscarMatriculaAluID(String id) {
        return "SELECT * FROM matricula WHERE alumno_nia = '" + id + "'";
    }

    public static String buscarMatriculaModID(String id) {
        return "SELECT * FROM matricula WHERE modulo_id = '" + id + "'";
    }

    public static String buscarMatriculaDobleID(String nia, String id) {
        return "SELECT * FROM matricula WHERE modulo_id = '" + id + "' AND alumno_nia = '" + nia + "'";
    }

    // INSERTAR
    public static String insertarAlumno(String id, String nombre) {
        return "INSERT INTO alumno (alumno_nia, alumno_nombre) VALUES ('" + id + "', '" + nombre + "')";
    }

    public static String insertarModulo(String id, String nombre) {
        return "INSERT INTO modulo (modulo_id, modulo_nombre) VALUES ('" + id + "', '" + nombre + "')";
    }

    public static String insertarNotas(String id, int nota1, int nota2, int nota3) {
        return "INSERT INTO notas (notas_id, nota1, nota2, nota3) VALUES ('" + id + "', " + nota1 + ", " + nota2
                + ", " + nota3 + ")";
    }

    public static String insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        return "INSERT INTO matricula (matricula_id, alumno_nia, modulo_id, notas_id, calificacion) VALUES ('"
                + id + "', '" + alumno + "', '" + modulo + "', '" + nota + "', '" + calificacion + "')";
    }

    // BORRAR
    public static String borrarAlumno(String id) {
        return "DELETE FROM alumno WHERE alumno_nia = '" + id + "'";
    }

    public static String borrarModulo(String id) {
        return "DELETE FROM modulo WHERE modulo_id = '" + id + "'";
    }

    public static String borrarNotas(String id) {
        return "DELETE FROM notas WHERE notas_id = '" + id + "'";
    }

    public static String borrarMatricula(String id) {
        return "DELETE FROM matricula WHERE matricula_id = '" + id + "'";
    }

    // ACTUALIZAR
    public static String actualizarNota(String id, int nota1, int nota2, int nota3) {
        return "UPDATE notas SET nota1 = " + nota1 + ", nota2 = " + nota2 + ", nota3 =" + nota3 + " WHERE notas_id = '"
                + id + "'";
    }

    public static String actualizarMatricula(String id, String calificacion) {
        return "UPDATE matricula SET calificacion = '" + calificacion + "' WHERE matricula_id = '" + id + "'";
    }
}

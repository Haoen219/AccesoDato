package utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;

import persistencia.ORM;

public class SQL {
    // tablas
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


    // CREATE
    public static boolean insertarAlumno(String id, String nombre) {
        try {
            return ORM.getConnection().createStatement().execute("INSERT INTO alumno (alumno_nia, alumno_nombre) VALUES ('" + id + "', '" + nombre + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarModulo(String id, String nombre) {
        try {
            return ORM.getConnection().createStatement().execute("INSERT INTO modulo (modulo_id, modulo_nombre) VALUES ('" + id + "', '" + nombre + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarNotas(String id, int nota1, int nota2, int nota3) {
        try {
            return ORM.getConnection().createStatement().execute("INSERT INTO notas (notas_id, nota1, nota2, nota3) VALUES ('" + id + "', " + nota1 + ", " + nota2
                + ", " + nota3 + ")");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        try {
            return ORM.getConnection().createStatement().execute("INSERT INTO matricula (matricula_id, alumno_nia, modulo_id, notas_id, calificacion) VALUES ('"
                + id + "', '" + alumno + "', '" + modulo + "', '" + nota + "', '" + calificacion + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    //READ
    public static ResultSet todoAlumno() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM alumno");
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de alumno\n" + ex);
        }
        return null;
    }

    public static ResultSet todoModulo() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM modulo");
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de modulo\n" + ex);
        }
        return null;
    }

    public static ResultSet todoMatricula() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM matricula");
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de matricula\n" + ex);
        }
        return null;
    }

    public static ResultSet todoNotas() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM notas");
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de notas\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarAlumnoID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM alumno WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarModuloID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM modulo WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM matricula WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscaNotaID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM notas WHERE notas_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaAluID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM matricula WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaModID(String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM matricula WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaDobleID(String nia, String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM matricula WHERE modulo_id = '" + id + "' AND alumno_nia = '" + nia + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    // UPDATE
    public static boolean actualizarNota(String id, int nota1, int nota2, int nota3) {
        try {
            ORM.getConnection().createStatement().execute("UPDATE notas SET nota1 = " + nota1 + ", nota2 = " + nota2 + ", nota3 =" + nota3 + " WHERE notas_id = '"
                + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String calificacion) {
        try {
            ORM.getConnection().createStatement().execute("UPDATE matricula SET calificacion = '" + calificacion + "' WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    // DESTROY
    public static boolean borrarAlumno(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM alumno WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean borrarModulo(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM modulo WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean borrarNotas(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM notas WHERE notas_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean borrarMatricula(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM matricula WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }
}

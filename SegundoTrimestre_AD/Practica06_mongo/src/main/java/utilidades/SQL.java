package utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

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
            return ORM.getConnection().createStatement()
                    .execute("INSERT INTO alumno (alumno_nia, alumno_nombre) VALUES ('" + id + "', '" + nombre + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarModulo(String id, String nombre) {
        try {
            return ORM.getConnection().createStatement()
                    .execute("INSERT INTO modulo (modulo_id, modulo_nombre) VALUES ('" + id + "', '" + nombre + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarNotas(String id, int nota1, int nota2, int nota3) {
        try {
            return ORM.getConnection().createStatement()
                    .execute("INSERT INTO notas (notas_id, nota1, nota2, nota3) VALUES ('" + id + "', " + nota1 + ", "
                            + nota2
                            + ", " + nota3 + ")");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    public static boolean insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        try {
            return ORM.getConnection().createStatement().execute(
                    "INSERT INTO matricula (matricula_id, alumno_nia, modulo_id, notas_id, calificacion) VALUES ('"
                            + id + "', '" + alumno + "', '" + modulo + "', '" + nota + "', '" + calificacion + "')");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return false;
    }

    // READ
    public static ResultSet todoAlumno() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM alumno ORDER BY " + alumno_id);
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de alumno\n" + ex);
        }
        return null;
    }

    public static ResultSet todoModulo() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM modulo ORDER BY " + modulo_id);
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de modulo\n" + ex);
        }
        return null;
    }

    public static ResultSet todoMatricula() {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM matricula ORDER BY " + matricula_id);
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de matricula\n" + ex);
        }
        return null;
    }

    public static ResultSet todoNotas() {
        try {
            return ORM.getConnection().createStatement().executeQuery("SELECT * FROM notas ORDER BY " + notas_id);
        } catch (SQLException ex) {
            System.out.println("Error recuperando lista de notas\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarAlumnoID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM alumno WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarModuloID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM modulo WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM matricula WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscaNotaID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM notas WHERE notas_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaAluID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM matricula WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaModID(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .executeQuery("SELECT * FROM matricula WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    public static ResultSet buscarMatriculaDobleID(String nia, String id) {
        try {
            return ORM.getConnection().createStatement().executeQuery(
                    "SELECT * FROM matricula WHERE modulo_id = '" + id + "' AND alumno_nia = '" + nia + "'");
        } catch (SQLException ex) {
            System.out.println("Error buscando alumno con ese NIA\n" + ex);
        }
        return null;
    }

    // UPDATE
    public static boolean actualizarNota(String id, int nota1, int nota2, int nota3) {
        try {
            ORM.getConnection().createStatement()
                    .execute("UPDATE notas SET nota1 = " + nota1 + ", nota2 = " + nota2 + ", nota3 =" + nota3
                            + " WHERE notas_id = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando notas\n" + ex);
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String calificacion) {
        try {
            ORM.getConnection().createStatement().execute(
                    "UPDATE matricula SET calificacion = '" + calificacion + "' WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando matricula\n" + ex);
        }
        return false;
    }

    // DELETE
    public static boolean borrarAlumno(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM alumno WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando alumno NIA:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarModulo(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM modulo WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando modulo ID:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarNotas(String id) {
        try {
            return ORM.getConnection().createStatement().execute("DELETE FROM notas WHERE notas_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando notas ID:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarMatricula(String id) {
        try {
            return ORM.getConnection().createStatement()
                    .execute("DELETE FROM matricula WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando matricula ID:" + id + "\n" + ex);
        }
        return false;
    }

    // MONGO
    // CREATE
    public static boolean insertarAlumnoMongo(String nia, String nombre) {
        try {
            Document alumno = new Document("_id", new ObjectId());
            alumno.append(alumno_id, nia)
                    .append(alumno_nombre, nombre);
            return todoAlumnoMongo().insertOne(alumno).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error insertando alumno en la tabla\n" + ex);
        }
        return false;
    }

    public static boolean insertarModuloMongo(String id, String nombre) {
        try {
            Document modulo = new Document("_id", new ObjectId());
            modulo.append(modulo_id, id)
                    .append(modulo_nombre, nombre);
            return todoModuloMongo().insertOne(modulo).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error insertando modulo en la tabla\n" + ex);
        }
        return false;
    }

    public static boolean insertarNotasMongo(String id, int nota1, int nota2, int nota3) {
        try {
            Document notas = new Document("_id", new ObjectId());
            notas.append(SQL.notas_id, id)
                    .append(notas_nota1, nota1)
                    .append(notas_nota2, nota2)
                    .append(notas_nota3, nota3);
            return todoNotasMongo().insertOne(notas).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error insertando notas en la tabla\n" + ex);
        }
        return false;
    }

    public static boolean insertarMatriculaMongo(String id, String alumno, String modulo, String nota,
            String calificacion) {
        try {
            Document matricula = new Document("_id", new ObjectId());
            matricula.append(matricula_id, id)
                    .append(matricula_alumno_id, alumno)
                    .append(matricula_modulo_id, modulo)
                    .append(matricula_notas_id, nota)
                    .append(matricula_calificacion, calificacion);
            return todoMatriculaMongo().insertOne(matricula).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error insertando matricula en la tabla\n" + ex);
        }
        return false;
    }

    // READ
    public static MongoCollection<Document> todoAlumnoMongo() {
        try {
            return ORM.getMongoDatabase().getCollection(alumno_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de alumnos\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoModuloMongo() {
        try {
            return ORM.getMongoDatabase().getCollection(modulo_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de modulos\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoNotasMongo() {
        try {
            return ORM.getMongoDatabase().getCollection(notas_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de notas\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoMatriculaMongo() {
        try {
            return ORM.getMongoDatabase().getCollection(matricula_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de matriculas\n" + ex);
        }
        return null;
    }

    public static Document buscarAlumnoIDMongo(String nia) {
        try {
            return todoAlumnoMongo().find(new Document(alumno_id, nia)).first();
        } catch (MongoException ex) {
            System.out.println("Error recuperando alumno con ese ID\n" + ex);
        }
        return null;
    }

    public static Document buscarModuloIDMongo(String id) {
        try {
            return todoModuloMongo().find(new Document(modulo_id, id)).first();
        } catch (MongoException ex) {
            System.out.println("Error recuperando modulo con ese ID\n" + ex);
        }
        return null;
    }

    public static Document buscarNotasIDMongo(String id) {
        try {
            return todoNotasMongo().find(new Document(notas_id, id)).first();
        } catch (MongoException ex) {
            System.out.println("Error recuperando notas con ese ID\n" + ex);
        }
        return null;
    }

    public static Document buscarMatriculaIDMongo(String id) {
        try {
            return todoMatriculaMongo().find(new Document(matricula_id, id)).first();
        } catch (MongoException ex) {
            System.out.println("Error recuperando matricula con ese ID\n" + ex);
        }
        return null;
    }

    public static MongoCursor<Document> buscarMatriculaAluIDMongo(String nia) {
        try {
            return todoMatriculaMongo().find(new Document(matricula_alumno_id, nia)).iterator();
        } catch (MongoException ex) {
            System.out.println("Error buscando alumno con NIA:" + nia + "\n" + ex);
        }
        return null;
    }

    public static MongoCursor<Document> buscarMatriculaModIDMongo(String id) {
        try {
            return todoMatriculaMongo().find(new Document(matricula_modulo_id, id)).iterator();
        } catch (MongoException ex) {
            System.out.println("Error buscando matricula con ID_modulo:" + id + "\n" + ex);
        }
        return null;
    }

    public static Document buscarMatriculaDobleIDMongo(String nia, String id) {
        try {
            return todoMatriculaMongo().find(new Document(matricula_alumno_id, nia).append(matricula_modulo_id, id))
                    .first();
        } catch (MongoException ex) {
            System.out.println("Error buscando matricula con NIA_alumno:" + nia + " y ID_modulo:" + id + "\n" + ex);
        }
        return null;
    }

    // UPDATE
    public static boolean actualizarNotaMongo(String id, int nota1, int nota2, int nota3) {
        try {
            Document notas = new Document();
            notas.append("$set", new Document(notas_id, id)
                    .append(notas_nota1, nota1)
                    .append(notas_nota2, nota2)
                    .append(notas_nota3, nota3));
            return todoNotasMongo().updateOne(buscarNotasIDMongo(id), notas).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error actualizando Notas ID:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean actualizarMatriculaMongo(String id, String calificacion) {
        try {
            Document matricula = new Document();
            matricula.append("$set", new Document(matricula_id, id).append(matricula_calificacion, calificacion));
            return todoMatriculaMongo().updateOne(buscarMatriculaIDMongo(id), matricula).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error actualizando Matricula Id:" + id + "\n" + ex);
        }
        return false;
    }

    // DELETE
    public static boolean borrarAlumnoMongo(String id) {
        try {
            return todoAlumnoMongo().deleteOne(new Document(alumno_id, id)).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error borrando alumno NIA:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarModuloMongo(String id) {
        try {
            return todoModuloMongo().deleteOne(new Document(modulo_id, id)).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error borrando modulo ID:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarNotasMongo(String id) {
        try {
            return todoNotasMongo().deleteOne(new Document(notas_id, id)).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error borrando notas ID:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean borrarMatriculaMongo(String id) {
        try {
            return todoMatriculaMongo().deleteOne(new Document(matricula_id, id)).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error borrando matricula ID:" + id + "\n" + ex);
        }
        return false;
    }
}

package utilidades;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import persistencia.Gestor;

public class CRUD {
    // Tablas
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
            notas.append(CRUD.notas_id, id)
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
            return Gestor.getMongoDatabase().getCollection(alumno_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de alumnos\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoModuloMongo() {
        try {
            return Gestor.getMongoDatabase().getCollection(modulo_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de modulos\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoNotasMongo() {
        try {
            return Gestor.getMongoDatabase().getCollection(notas_tabla);
        } catch (MongoException ex) {
            System.out.println("Error recuperando lista de notas\n" + ex);
        }
        return null;
    }

    public static MongoCollection<Document> todoMatriculaMongo() {
        try {
            return Gestor.getMongoDatabase().getCollection(matricula_tabla);
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
    public static boolean actualizarAlumnoMongo(String id, String nombre) {
        try {
            Document alumno = new Document();
            alumno.append("$set", new Document(alumno_id, id).append(alumno_nombre, nombre));
            return todoAlumnoMongo().updateOne(buscarAlumnoIDMongo(id), alumno).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error actualizando alumno nia:" + id + "\n" + ex);
        }
        return false;
    }

    public static boolean actualizarModuloMongo(String id, String nombre) {
        try {
            Document modulo = new Document();
            modulo.append("$set", new Document(modulo_id, id).append(modulo_nombre, nombre));
            return todoMatriculaMongo().updateOne(buscarModuloIDMongo(id), modulo).wasAcknowledged();
        } catch (MongoException ex) {
            System.out.println("Error actualizando modulo ID:" + id + "\n" + ex);
        }
        return false;
    }
    
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

    public static boolean actualizarMatriculaMongo(String id, String alu_id, String modu_id, String nota_id,
            String calificacion) {
        try {
            Document matricula = new Document();
            matricula.append("$set", new Document(matricula_id, id)
                    .append(matricula_alumno_id, alu_id)
                    .append(matricula_modulo_id, modu_id)
                    .append(matricula_notas_id, nota_id)
                    .append(matricula_calificacion, calificacion));
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

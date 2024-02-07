package utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import persistencia.ORM;

public class CRUD_EXIST {
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

    /*
     * String query = "tuConsultaXPath";
     * XPathQueryService service = (XPathQueryService)
     * col.getService("XPathQueryService", "1.0");
     * service.setProperty("indent", "yes");
     * 
     * ResourceSet result = service.query(query);
     * ResourceIterator iter = result.getIterator();
     * 
     * while (iter.hasMoreResources()) {
     * Resource resource = iter.nextResource();
     * String documentContent = (String) resource.getContent();
     * // Trabajar con el contenido del documento
     * }
     * 
     * // Cerrar la conexión
     * col.close();
     * 
     * 
     * 
     * 
     * private static void createCollection() {
     * try {
     * Collection parent = DatabaseManager.getCollection(URI + "/db", USERNAME,
     * PASSWORD);
     * CollectionManagementService mgtService = (CollectionManagementService)
     * parent.getService("CollectionManagementService", "1.0");
     * mgtService.createCollection(COLLECTION);
     * System.out.println("La colección " + COLLECTION + " ha sido creada.");
     * } catch (XMLDBException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * private static void createDocument(Collection collection, String
     * documentName, String content) {
     * try {
     * XMLResource resource = (XMLResource) collection.createResource(documentName,
     * "XMLResource");
     * resource.setContent(content);
     * collection.storeResource(resource);
     * System.out.println("El documento " + documentName + " ha sido creado.");
     * } catch (XMLDBException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * private static void updateDocument(Collection collection, String
     * documentName, String content) {
     * try {
     * XMLResource resource = (XMLResource) collection.getResource(documentName);
     * resource.setContent(content);
     * collection.storeResource(resource);
     * System.out.println("El documento " + documentName + " ha sido actualizado.");
     * } catch (XMLDBException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * private static void retrieveDocument(Collection collection, String
     * documentName) {
     * try {
     * XMLResource resource = (XMLResource) collection.getResource(documentName);
     * System.out.println("Contenido del documento " + documentName + ":");
     * System.out.println(resource.getContent());
     * } catch (XMLDBException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * private static void deleteDocument(Collection collection, String
     * documentName) {
     * try {
     * collection.removeResource(collection.getResource(documentName));
     * System.out.println("El documento " + documentName + " ha sido eliminado.");
     * } catch (XMLDBException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * String collectionPath = "/path/to/collection"; // Ruta de la colección que
     * contiene el documento
     * String documentName = "nombre-del-documento.xml"; // Nombre del documento
     * existente
     * XMLResource resource = (XMLResource) col.getResource(collectionPath + "/" +
     * documentName);
     * Document xmlDocument = (Document) resource.getContentAsDOM();
     * Crear un nuevo elemento y agregarlo al documento:
     * 
     * DocumentBuilder builder =
     * DocumentBuilderFactory.newInstance().newDocumentBuilder();
     * Element nuevaLinea = builder.createElement("linea"); // Nuevo elemento
     * nuevaLinea.setTextContent("Contenido de la nueva línea");
     * 
     * // Agregar el nuevo elemento al documento
     * Element nodoRaiz = xmlDocument.getDocumentElement();
     * nodoRaiz.appendChild(nuevaLinea);
     */

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
    public static boolean actualizarAlumno(String id, String nombre) {
        try {
            ORM.getConnection().createStatement()
                    .execute("UPDATE alumno SET alumno_nombre = " + nombre + " WHERE alumno_nia = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando alumno\n" + ex);
        }
        return false;
    }

    public static boolean actualizarModulo(String id, String nombre) {
        try {
            ORM.getConnection().createStatement()
                    .execute("UPDATE modulo SET modulo_nombre = " + nombre + " WHERE modulo_id = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando modulo\n" + ex);
        }
        return false;
    }

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

    public static boolean actualizarMatricula(String id, String alu_id, String modu_id, String nota_id,
            String calificacion) {
        try {
            ORM.getConnection().createStatement().execute(
                    "UPDATE matricula SET calificacion = '" + calificacion + "', alumno_id = '" + alu_id
                            + "', modulo_id = '" + modu_id + "', notas_id = '" + notas_id + "'  WHERE matricula_id = '"
                            + id + "'");
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
}

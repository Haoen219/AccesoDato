package utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.exist.collections.Collection;
import org.w3c.dom.Document;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import persistencia.Gestor;

public class CRUD_EXIST {
    // tablas
    public final static String alumno_tabla = "alumno";
    public final static String alumno_raiz = "alumnos";
    public final static String alumno_id = "alumno_nia";
    public final static String alumno_nombre = "alumno_nombre";

    public final static String modulo_tabla = "modulo";
    public final static String modulo_raiz = "modulos";
    public final static String modulo_id = "modulo_id";
    public final static String modulo_nombre = "modulo_nombre";

    public final static String notas_tabla = "nota";
    public final static String notas_raiz = "notas";
    public final static String notas_id = "notas_id";
    public final static String notas_nota1 = "nota1";
    public final static String notas_nota2 = "nota2";
    public final static String notas_nota3 = "nota3";

    public final static String matricula_tabla = "matricula";
    public final static String matricula_raiz = "matriculas";
    public final static String matricula_id = "matricula_id";
    public final static String matricula_alumno_id = "almuno_nia";
    public final static String matricula_modulo_id = "modulo_id";
    public final static String matricula_notas_id = "notas_id";
    public final static String matricula_calificacion = "calificacion";

    // CREATE
    /*
     * public static boolean insertarAlumno(String id, String nombre) {
     * String xpathQuery = "/" + alumno_tabla;
     * try {
     * XPathQueryService xpathService = (XPathQueryService)
     * Gestor.getExistCollection()
     * .getService("XPathQueryService", "1.0");
     * ResourceSet result = xpathService.query(xpathQuery);
     * Resource resource = result.getResource(0);
     * 
     * if (resource != null && resource instanceof EXistResource) {
     * String contenidoXML = ((EXistResource) resource).getContent().toString();
     * contenidoXML = contenidoXML.replace("</" + alumno_raiz + ">",
     * "<" + alumno_tabla +
     * "><" + alumno_id + ">" + id + "</" + alumno_id +
     * "><" + alumno_nombre + ">" + nombre + "</" + alumno_nombre +
     * "></" + alumno_tabla +
     * "></" + alumno_raiz + ">");
     * ((EXistResource) resource).setContent(contenidoXML);
     * Gestor.getExistCollection().storeResource((EXistResource) resource);
     * return true;
     * } else {
     * System.out.println("Recurso XML no encontrado.");
     * }
     * } catch (XMLDBException e) {
     * System.out.println("Error insertando Alumno");
     * e.printStackTrace();
     * }
     * return false;
     * }
     */
    public static boolean insertarAlumno(String id, String nombre) {
        String xpathQuery = "/" + alumno_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);

            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                Element nuevoAlumno = doc.createElement(alumno_tabla);

                Element idElement = doc.createElement(alumno_id);
                idElement.setTextContent(id);
                Element nombreElement = doc.createElement(alumno_nombre);
                nombreElement.setTextContent(nombre);

                nuevoAlumno.appendChild(idElement);
                nuevoAlumno.appendChild(nombreElement);

                // Obtener el nodo raíz y añadir el nuevo nodo
                Element raiz = doc.getDocumentElement();
                raiz.appendChild(nuevoAlumno);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertarModulo(String id, String nombre) {
        String xpathQuery = "/" + modulo_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);

            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                Element nuevoModulo = doc.createElement(modulo_tabla);

                Element idElement = doc.createElement(modulo_id);
                idElement.setTextContent(id);
                Element nombreElement = doc.createElement(modulo_nombre);
                nombreElement.setTextContent(nombre);

                nuevoModulo.appendChild(idElement);
                nuevoModulo.appendChild(nombreElement);

                // Obtener el nodo raíz y añadir el nuevo nodo
                Element raiz = doc.getDocumentElement();
                raiz.appendChild(nuevoModulo);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertarNotas(String id, int nota1, int nota2, int nota3) {
        String xpathQuery = "/" + notas_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);

            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                Element nuevoNotas = doc.createElement(notas_tabla);

                Element idElement = doc.createElement(notas_id);
                idElement.setTextContent(id);
                Element nota1Element = doc.createElement(notas_nota1);
                nota1Element.setTextContent(Integer.toString(nota1));
                Element nota2Element = doc.createElement(notas_nota2);
                nota2Element.setTextContent(Integer.toString(nota2));
                Element nota3Element = doc.createElement(notas_nota3);
                nota3Element.setTextContent(Integer.toString(nota3));

                nuevoNotas.appendChild(idElement);
                nuevoNotas.appendChild(nota1Element);
                nuevoNotas.appendChild(nota2Element);
                nuevoNotas.appendChild(nota3Element);

                // Obtener el nodo raíz y añadir el nuevo nodo
                Element raiz = doc.getDocumentElement();
                raiz.appendChild(nuevoNotas);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        String xpathQuery = "/" + matricula_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);

            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                Element nuevoMatricula = doc.createElement(notas_tabla);

                Element idElement = doc.createElement(matricula_id);
                idElement.setTextContent(id);
                Element alumnoElement = doc.createElement(matricula_alumno_id);
                alumnoElement.setTextContent(alumno);
                Element moduloElement = doc.createElement(matricula_modulo_id);
                moduloElement.setTextContent(modulo);
                Element notasElement = doc.createElement(matricula_notas_id);
                notasElement.setTextContent(nota);
                Element calificacionElement = doc.createElement(matricula_calificacion);
                calificacionElement.setTextContent(calificacion);

                nuevoMatricula.appendChild(idElement);
                nuevoMatricula.appendChild(alumnoElement);
                nuevoMatricula.appendChild(moduloElement);
                nuevoMatricula.appendChild(notasElement);
                nuevoMatricula.appendChild(calificacionElement);

                // Obtener el nodo raíz y añadir el nuevo nodo
                Element raiz = doc.getDocumentElement();
                raiz.appendChild(nuevoMatricula);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public static ResourceSet recuperarLista(String lista_elemento) {
        String xpathQuery = "/" + lista_elemento;
        ResourceSet result = null;
        try {
            XPathQueryService xpathService = (XPathQueryService) Gestor.getExistCollection()
                    .getService("XPathQueryService", "1.0");
            result = xpathService.query(xpathQuery);
        } catch (XMLDBException e) {
            System.out.println("Error recuperando lista de " + lista_elemento);
            e.printStackTrace();
        }
        return result;
    }

    public static XMLResource buscarAlumnoID(String id) {
        String xquery = "for $elem in collection('" + alumno_tabla + "')//" + alumno_tabla + "[" + alumno_id + "='" + id
                + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Alumno");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscarModuloID(String id) {
        String xquery = "for $elem in collection('" + modulo_tabla + "')//" + modulo_tabla + "[" + modulo_id + "='" + id
                + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Modulo");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscarMatriculaID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "[" + matricula_id
                + "='" + id + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscaNotaID(String id) {
        String xquery = "for $elem in collection('" + notas_tabla + "')//" + notas_tabla + "[" + notas_id + "='" + id
                + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Notas");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscarMatriculaAluID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_alumno_id + "='" + id + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscarMatriculaModID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_modulo_id + "='" + id + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return resource;
    }

    public static XMLResource buscarMatriculaDobleID(String nia, String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_alumno_id + "='" + nia + "' and " + matricula_modulo_id + "='" + id + "'] return $elem";
        XMLResource resource = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);

            ResourceIterator iterator = result.getIterator();
            while (iterator.hasMoreResources()) {
                resource = (XMLResource) iterator.nextResource();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return resource;
    }

    // UPDATE
    // String xpathQuery = "/" + alumno_tabla;try
    // {
    // XPathQueryService xpathService = (XPathQueryService)
    // Gestor.getExistCollection()
    // .getService("XPathQueryService", "1.0");
    // ResourceSet result = xpathService.query(xpathQuery);
    // Resource resource = result.getResource(0);
    // if (resource != null && resource instanceof EXistResource) {
    // String contenidoXML = ((EXistResource) resource).getContent().toString();
    // contenidoXML = contenidoXML.replace(buscarAlumnoID(id).toString(),
    // "<" + alumno_tabla +
    // "><" + alumno_id + ">" + id + "</" + alumno_id +
    // "><" + alumno_nombre + ">" + nombre + "</" + alumno_nombre +
    // "></" + alumno_tabla +
    // "></" + alumno_raiz + ">");
    // ((EXistResource) resource).setContent(contenidoXML);
    // Gestor.getExistCollection().storeResource((EXistResource) resource);
    // return true;
    // } else {
    // System.out.println("Recurso XML no encontrado.");
    // }
    // }catch(
    // XMLDBException e)
    // {
    // System.out.println("Error insertando Alumno");
    // e.printStackTrace();
    // }return false;

    public static boolean actualizarAlumno(String id, String nombre) {
        String xpathQuery = "/" + alumno_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);
            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                NodeList alumnos = doc.getElementsByTagName(alumno_tabla);
                Element alumno;

                for (int i = 0; i < alumnos.getLength(); i++) {
                    alumno = (Element) alumnos.item(i);
                    Element idElement = (Element) alumno.getElementsByTagName(alumno_id).item(0);

                    if (idElement.getTextContent().equals(id)) {
                        Element nombreElement = (Element) alumno.getElementsByTagName(alumno_nombre).item(0);
                        nombreElement.setTextContent(nombre);

                        // Actualizar el recurso en la colección
                        Gestor.getExistCollection().storeResource(resource);
                        return true;
                    }
                }
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarModulo(String id, String nombre) {
        try {
            Gestor.getConnection().createStatement()
                    .execute("UPDATE modulo SET modulo_nombre = " + nombre + " WHERE modulo_id = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando modulon" + ex);
        }
        return false;
    }

    public static boolean actualizarNota(String id, int nota1, int nota2, int nota3) {
        try {
            Gestor.getConnection().createStatement()
                    .execute("UPDATE notas SET nota1 = " + nota1 + ", nota2 = " + nota2 + ", nota3 =" + nota3
                            + " WHERE notas_id = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando notasn" + ex);
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String calificacion) {
        try {
            Gestor.getConnection().createStatement().execute(
                    "UPDATE matricula SET calificacion = '" + calificacion + "' WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando matriculan" + ex);
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String alu_id, String modu_id, String nota_id,
            String calificacion) {
        try {
            Gestor.getConnection().createStatement().execute(
                    "UPDATE matricula SET calificacion = '" + calificacion + "', alumno_id = '" + alu_id
                            + "', modulo_id = '" + modu_id + "', notas_id = '" + notas_id + "'  WHERE matricula_id = '"
                            + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error actualizando matriculan" + ex);
        }
        return false;
    }

    // DELETE
    public static boolean borrarAlumno(String id) {
        try {
            return Gestor.getConnection().createStatement()
                    .execute("DELETE FROM alumno WHERE alumno_nia = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando alumno NIA:" + id + "n" + ex);
        }
        return false;
    }

    public static boolean borrarModulo(String id) {
        try {
            return Gestor.getConnection().createStatement()
                    .execute("DELETE FROM modulo WHERE modulo_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando modulo ID:" + id + "n" + ex);
        }
        return false;
    }

    public static boolean borrarNotas(String id) {
        try {
            return Gestor.getConnection().createStatement().execute("DELETE FROM notas WHERE notas_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando notas ID:" + id + "n" + ex);
        }
        return false;
    }

    public static boolean borrarMatricula(String id) {
        try {
            return Gestor.getConnection().createStatement()
                    .execute("DELETE FROM matricula WHERE matricula_id = '" + id + "'");
        } catch (SQLException ex) {
            System.out.println("Error borrando matricula ID:" + id + "n" + ex);
        }
        return false;
    }
}

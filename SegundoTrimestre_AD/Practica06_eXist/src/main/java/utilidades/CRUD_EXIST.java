package utilidades;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
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

    public static Element buscarAlumnoID(String id) {
        String xquery = "for $elem in collection('" + alumno_tabla + "')//" + alumno_tabla + "[" + alumno_id + "='" + id
                + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            XMLResource resource = (XMLResource) result.getResource(0);
            elemento = (Element) resource.getContentAsDOM();
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Alumno");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarModuloID(String id) {
        String xquery = "for $elem in collection('" + modulo_tabla + "')//" + modulo_tabla + "[" + modulo_id + "='" + id
                + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            XMLResource resource = (XMLResource) result.getResource(0);
            elemento = (Element) resource.getContentAsDOM();
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Modulo");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarNotaID(String id) {
        String xquery = "for $elem in collection('" + notas_tabla + "')//" + notas_tabla + "[" + notas_id + "='" + id
                + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            XMLResource resource = (XMLResource) result.getResource(0);
            elemento = (Element) resource.getContentAsDOM();
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Notas");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarMatriculaID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "[" + matricula_id
                + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            XMLResource resource = (XMLResource) result.getResource(0);
            elemento = (Element) resource.getContentAsDOM();
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return elemento;
    }

    public static ResourceSet buscarMatriculaAluID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_alumno_id + "='" + id + "'] return $elem";
        ResourceSet matriculas = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            matriculas = queryService.query(xquery);
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return matriculas;
    }

    public static ResourceSet buscarMatriculaModID(String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_modulo_id + "='" + id + "'] return $elem";
        ResourceSet matriculas = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            matriculas = queryService.query(xquery);
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return matriculas;
    }

    public static Element buscarMatriculaDobleID(String nia, String id) {
        String xquery = "for $elem in collection('" + matricula_tabla + "')//" + matricula_tabla + "["
                + matricula_alumno_id + "='" + nia + "' and " + matricula_modulo_id + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            XMLResource resource = (XMLResource) result.getResource(0);
            elemento = (Element) resource.getContentAsDOM();
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return elemento;
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
                Element alumno = buscarAlumnoID(id);
                Element nombreElement = (Element) alumno.getElementsByTagName(alumno_nombre).item(0);
                nombreElement.setTextContent(nombre);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarModulo(String id, String nombre) {
        String xpathQuery = "/" + modulo_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);
            if (resource != null) {
                Element modulo = buscarModuloID(id);
                Element nombreElement = (Element) modulo.getElementsByTagName(modulo_nombre).item(0);
                nombreElement.setTextContent(nombre);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Modulo");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarNota(String id, int nota1, int nota2, int nota3) {
        String xpathQuery = "/" + notas_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);
            if (resource != null) {
                Element nota = buscarNotaID(id);
                Element nota1Element = (Element) nota.getElementsByTagName(notas_nota1).item(0);
                nota1Element.setTextContent(Integer.toString(nota1));
                Element nota2Element = (Element) nota.getElementsByTagName(notas_nota2).item(0);
                nota2Element.setTextContent(Integer.toString(nota2));
                Element nota3Element = (Element) nota.getElementsByTagName(notas_nota3).item(0);
                nota3Element.setTextContent(Integer.toString(nota3));

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Notas");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String calificacion) {
        String xpathQuery = "/" + notas_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);
            if (resource != null) {
                Element matricula = buscarMatriculaID(id);
                Element calificacioElement = (Element) matricula.getElementsByTagName(matricula_calificacion)
                        .item(0);
                calificacioElement.setTextContent(calificacion);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Matricula (calificacion)");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String alu_id, String modu_id, String nota_id,
            String calificacion) {
        String xpathQuery = "/" + notas_tabla;
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(xpathQuery);
            if (resource != null) {
                Element matricula = buscarMatriculaID(id);
                Element alumnoElement = (Element) matricula.getElementsByTagName(matricula_alumno_id).item(0);
                alumnoElement.setTextContent(alu_id);
                Element moduloElement = (Element) matricula.getElementsByTagName(matricula_modulo_id).item(0);
                moduloElement.setTextContent(modu_id);
                Element notasElement = (Element) matricula.getElementsByTagName(matricula_notas_id).item(0);
                notasElement.setTextContent(nota_id);
                Element calificacioElement = (Element) matricula.getElementsByTagName(matricula_calificacion)
                        .item(0);
                calificacioElement.setTextContent(calificacion);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Matricula");
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public static boolean borrarAlumno(String id) {
        String xquery = "update delete collection('" + alumno_tabla + "')//" + alumno_tabla + "[" + alumno_id + "='"
                + id + "']";

        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            queryService.query(xquery);
            System.out.println("El alumno con ID " + id + " ha sido borrado exitosamente");
            return true;
        } catch (XMLDBException e) {
            System.out.println("Error borrando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean borrarModulo(String id) {
        String xquery = "update delete collection('" + modulo_tabla + "')//" + modulo_tabla + "[" + modulo_id + "='"
                + id + "']";

        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            queryService.query(xquery);
            System.out.println("El modulo con ID " + id + " ha sido borrado exitosamente");
            return true;
        } catch (XMLDBException e) {
            System.out.println("Error borrando Modulo");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean borrarNotas(String id) {
        String xquery = "update delete collection('" + notas_tabla + "')//" + notas_tabla + "[" + notas_id + "='" + id
                + "']";

        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            queryService.query(xquery);
            System.out.println("Las notas con ID " + id + " ha sido borrado exitosamente");
            return true;
        } catch (XMLDBException e) {
            System.out.println("Error borrando Notas");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean borrarMatricula(String id) {
        String xquery = "update delete collection('" + matricula_tabla + "')//" + matricula_tabla + "[" + matricula_id
                + "='" + id + "']";

        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            queryService.query(xquery);
            System.out.println("La matricula con ID " + id + " ha sido borrado exitosamente");
            return true;
        } catch (XMLDBException e) {
            System.out.println("Error borrando Matricula");
            e.printStackTrace();
        }
        return false;
    }
}

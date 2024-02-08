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
    public static boolean insertarAlumno2(String id, String nombre) {
        String resourceID = alumno_tabla + ".xml";
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(resourceID);
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
                resource.setContentAsDOM(doc);

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

    public static boolean insertarAlumno(String id, String nombre) {
        String resourceID = alumno_tabla + ".xml";
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(resourceID);
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
                resource.setContentAsDOM(doc);

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
        String resourceID = modulo_tabla + ".xml";
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(resourceID);

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
                resource.setContentAsDOM(doc);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Modulo");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertarNotas(String id, int nota1, int nota2, int nota3) {
        String resourceID = notas_tabla + ".xml";
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(resourceID);

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
                resource.setContentAsDOM(doc);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Notas");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        String resourceID = matricula_tabla + ".xml";
        try {
            XMLResource resource = (XMLResource) Gestor.getExistCollection().getResource(resourceID);

            if (resource != null) {
                Document doc = (Document) resource.getContentAsDOM();

                Element nuevoMatricula = doc.createElement(matricula_tabla);

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
                resource.setContentAsDOM(doc);

                // Actualizar el recurso en la colección
                Gestor.getExistCollection().storeResource(resource);
                return true;
            } else {
                System.out.println("Recurso XML no encontrado.");
            }
        } catch (XMLDBException e) {
            System.out.println("Error insertando Matricula");
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public static ResourceSet recuperarLista(String lista_elemento) {
        String xpathQuery = "for $elem in //" + lista_elemento +" return $elem";
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
        String xquery = "for $elem in //" + alumno_tabla + "[" + alumno_id + "=" + id + "] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            if (result.getSize() > 0) {
                XMLResource resource = (XMLResource) result.getResource(0);
                elemento = (Element) resource.getContentAsDOM();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Alumno");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error recuperando Alumno");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarModuloID(String id) {
        String xquery = "for $elem in //" + modulo_tabla + "[" + modulo_id + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            if (result.getSize() > 0) {
                XMLResource resource = (XMLResource) result.getResource(0);
                elemento = (Element) resource.getContentAsDOM();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Modulo");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarNotaID(String id) {
        String xquery = "for $elem in //" + notas_tabla + "[" + notas_id + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            if (result.getSize() > 0) {
                XMLResource resource = (XMLResource) result.getResource(0);
                elemento = (Element) resource.getContentAsDOM();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Notas");
            e.printStackTrace();
        }
        return elemento;
    }

    public static Element buscarMatriculaID(String id) {
        String xquery = "for $elem in //" + matricula_tabla + "[" + matricula_id + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            if (result.getSize() > 0) {
                XMLResource resource = (XMLResource) result.getResource(0);
                elemento = (Element) resource.getContentAsDOM();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return elemento;
    }

    public static ResourceSet buscarMatriculaAluID(String id) {
        String xquery = "for $elem in //" + matricula_tabla + "[" + matricula_alumno_id + "='" + id + "'] return $elem";
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
        String xquery = "for $elem in //" + matricula_tabla + "[" + matricula_modulo_id + "='" + id + "'] return $elem";
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
        String xquery = "for $elem in //" + matricula_tabla + "[" + matricula_alumno_id + "='" + nia + "' and "
                + matricula_modulo_id + "='" + id + "'] return $elem";
        Element elemento = null;
        try {
            XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
            ResourceSet result = queryService.query(xquery);
            if (result.getSize() > 0) {
                XMLResource resource = (XMLResource) result.getResource(0);
                elemento = (Element) resource.getContentAsDOM();
            }
        } catch (XMLDBException e) {
            System.out.println("Error recuperando Matricula");
            e.printStackTrace();
        }
        return elemento;
    }

    // UPDATE
    public static boolean actualizarAlumno(String id, String nombre) {
        String xquery = "update replace //" + alumno_tabla + "[" + alumno_id + " = " + id + "] with " +
                        "<"+alumno_tabla+">"+
                        "<"+alumno_id+">"+id+"</"+alumno_id+">"+
                        "<"+alumno_nombre+">"+nombre+"</"+alumno_nombre+">"+
                        "</"+alumno_tabla+">";
        Element alumno = null;
        try {
            alumno = buscarAlumnoID(id);
            if (alumno != null) {
                XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
                queryService.query(xquery);
                return true;
            } else {
                System.out.println("No existe este alumno");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Alumno");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarModulo(String id, String nombre) {
        String xquery = "update replace //" + modulo_tabla + "[" + modulo_id + " = " + id + "] with " +
                        "<"+modulo_tabla+">"+
                        "<"+modulo_id+">"+id+"</"+modulo_id+">"+
                        "<"+modulo_nombre+">"+nombre+"</"+modulo_nombre+">"+
                        "</"+modulo_tabla+">";
        Element modulo = null;
        try {
            modulo = buscarModuloID(id);
            if (modulo != null) {
                XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
                queryService.query(xquery);
                return true;
            } else {
                System.out.println("No existe este modulo");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Modulo");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarNota(String id, int nota1, int nota2, int nota3) {
        String xquery = "update replace //" + notas_tabla + "[" + notas_id + " = " + id + "] with " +
                        "<"+notas_tabla+">"+
                        "<"+notas_id+">"+id+"</"+notas_id+">"+
                        "<"+notas_nota1+">"+nota1+"</"+notas_nota1+">"+
                        "<"+notas_nota2+">"+nota2+"</"+notas_nota2+">"+
                        "<"+notas_nota3+">"+nota3+"</"+notas_nota3+">"+
                        "</"+notas_tabla+">";
        Element nota = null;
        try {
            nota = buscarNotaID(id);
            if (nota != null) {
                XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
                queryService.query(xquery);
                return true;
            } else {
                System.out.println("No existe esta Nota");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Notas");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String calificacion) {
        String xquery = "update replace //" + matricula_tabla +"/"+matricula_calificacion+ "[../" + matricula_id + " = " + id + "] with " +
                        "<"+matricula_calificacion+">"+calificacion+"</"+matricula_calificacion+">";
        Element matricula = null;
        try {
            matricula = buscarMatriculaID(id);
            if (matricula != null) {
                XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
                queryService.query(xquery);
                return true;
            } else {
                System.out.println("No existe esta Matricula");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Matricula");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarMatricula(String id, String alu_id, String modu_id, String nota_id, String calificacion) {
        String xquery = "update replace //" + matricula_tabla + "[" + matricula_id + " = " + id + "] with " +
                        "<"+matricula_tabla+">"+
                        "<"+matricula_id+">"+id+"</"+matricula_id+">"+
                        "<"+matricula_alumno_id+">"+alu_id+"</"+matricula_alumno_id+">"+
                        "<"+matricula_modulo_id+">"+modu_id+"</"+matricula_modulo_id+">"+
                        "<"+matricula_notas_id+">"+nota_id+"</"+matricula_notas_id+">"+
                        "<"+matricula_calificacion+">"+calificacion+"</"+matricula_calificacion+">"+
                        "</"+matricula_tabla+">";
        Element matricula = null;
        try {
            matricula = buscarMatriculaID(id);
            if (matricula != null) {
                XQueryService queryService = (XQueryService) Gestor.getExistCollection().getService("XQueryService", "1.0");
                queryService.query(xquery);
                return true;
            } else {
                System.out.println("No existe esta Matricula");
            }
        } catch (XMLDBException e) {
            System.out.println("Error actualizando Matricula");
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public static boolean borrarAlumno(String id) {
        String xquery = "update delete //" + alumno_tabla + "[" + alumno_id + "='" + id + "']";
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
        String xquery = "update delete //" + modulo_tabla + "[" + modulo_id + "='" + id + "']";
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
        String xquery = "update delete //" + notas_tabla + "[" + notas_id + "='" + id + "']";
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
        String xquery = "update delete //" + matricula_tabla + "[" + matricula_id + "='" + id + "']";
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

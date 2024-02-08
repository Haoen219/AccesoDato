/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import utilidades.Lector;
import utilidades.CRUD;
import utilidades.CRUD_EXIST;

/**
 *
 * @author haoen
 */
public class Alumnos {

    // MENU
    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Alumnos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.leerEntero(0, 3);
        return opcion;
    }

    // SQL
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();

            ResultSet queryAlu = CRUD.buscarAlumnoID(nia);
            try {
                if (nia.equals("0")) {
                    seguir = false;
                } else if (queryAlu.next()) {
                    System.out.println("-Ya existe un alumno con ese NIA");
                } else {
                    System.out.print("Introduzca el nombre del alumno: ");
                    String nombre = sc.leer();

                    if (nombre.equalsIgnoreCase("0")) {
                        seguir = false;
                    } else {
                        if (CRUD.insertarAlumno(nia, nombre)) {
                            System.out.println("\tNIA:" + nia + " " + nombre + " dado de alta.\n");
                        } else {
                            System.out.println("\tNo se ha podido dar de alta.\n");
                        }
                    }
                }
                queryAlu.close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando ResultSet.\n" + ex);
            }
        }

        System.out.println("Volviendo...");
        return 0;
    }

    public int darDeBaja() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();
            if (nia.equals("0")) {
                seguir = false;
            } else {
                ResultSet queryAlu = CRUD.buscarAlumnoID(nia);

                try {
                    if (!queryAlu.next()) {
                        System.out.println("-No existe un alumno con ese NIA");
                    } else {
                        try {
                            String nombre = queryAlu.getString(CRUD.alumno_nombre);
                            ResultSet queryMat = CRUD.buscarMatriculaAluID(nia);

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString(CRUD.matricula_id);
                                String idNotas = queryMat.getString(CRUD.notas_id);
                                if (!CRUD.borrarMatricula(idMatri))
                                    System.out.println("\nNo se pudó borrar matricula ID:" + idMatri);
                                if (!CRUD.borrarNotas(idNotas))
                                    System.out.println("\nNo se pudó borrar notas ID:" + idNotas);
                            }
                            if (CRUD.borrarAlumno(nia)) {
                                System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                            } else {
                                System.out.println("\tNo se pudó borrar el alumno.");
                            }
                            queryMat.close();
                        } catch (SQLException ex) {
                            System.out.println("Error cerrando ResultSet matriculas.\n" + ex);
                        }
                    }
                    queryAlu.close();
                } catch (SQLException ex) {
                    System.out.println("Error cerrando ResultSet alumno.\n" + ex);
                }
            }
        }
        return 0;
    }

    public void listar() {
        System.out.print("\n-Listar Alumnos-");
        try {
            ResultSet alumnos = CRUD.todoAlumno();

            if (alumnos.next()) {
                alumnos = CRUD.todoAlumno();
                while (alumnos.next()) {
                    int numMatri = 0;
                    String nia = alumnos.getString(CRUD.alumno_id);
                    String nombre = alumnos.getString(CRUD.alumno_nombre);
                    try {
                        ResultSet matriculas = CRUD.buscarMatriculaAluID(nia);
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (SQLException ex) {
                        System.out.println("Error cerrando ResultSet matriculas del alumno ID:" + nia + "\n" + ex);
                    }
                    System.out.printf("\nNIA:%-10s %-30s ", nia, nombre);
                    if (numMatri > 0) {
                        System.out.print("Matriculas: " + numMatri);
                    } else {
                        System.out.print("-Sin Matriculas-");
                    }
                }
                System.out.println("\n--Fin de la lista--");
            } else {
                System.out.println("\nLista de alumno vacio");
            }
            alumnos.close();
        } catch (SQLException ex) {
            System.out.println("Error cerrando ResultSet alumnos\n" + ex);
        }
    }

    // EXIST
    public int darDeAltaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();

            Document alumno = CRUD.buscarAlumnoIDMongo(nia);

            if (nia.equals("0")) {
                seguir = false;
            } else if (alumno != null) {
                System.out.println("-Ya existe un alumno con ese NIA");
            } else {
                System.out.print("Introduzca el nombre del alumno: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (CRUD.insertarAlumnoMongo(nia, nombre)) {
                        System.out.println("\tNIA:" + nia + " " + nombre + " dado de alta\n");
                    } else {
                        System.out.println("No se ha podido insertar el alumno");
                    }
                }
            }
        }
        System.out.println("Volviendo...");
        return 0;
    }

    public int darDeBajaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();
            if (nia.equals("0")) {
                seguir = false;
            } else {
                Document alumno = CRUD.buscarAlumnoIDMongo(nia);

                if (alumno == null) {
                    System.out.println("-No existe un alumno con ese NIA");
                } else {
                    try {
                        String nombre = alumno.getString(CRUD.alumno_nombre);
                        MongoCursor<Document> matricula = CRUD.buscarMatriculaAluIDMongo(nia);
                        // Borrar notas de cada matricula
                        while (matricula.hasNext()) {
                            Document matri = matricula.next();
                            String idMatri = matri.getString(CRUD.matricula_id);
                            String idNotas = matri.getString(CRUD.notas_id);

                            if (!CRUD.borrarNotasMongo(idNotas))
                                System.out.println("No se pudo borrar Notas ID:" + idNotas);
                            if (!CRUD.borrarMatriculaMongo(idMatri))
                                System.out.println("No se pudo borrar Matricula ID:" + idMatri);
                        }
                        matricula.close();
                        if (CRUD.borrarAlumnoMongo(nia)) {
                            System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                        } else {
                            System.out.println("NO se pudo borrar el alumno");
                        }
                    } catch (Exception ex) {
                        System.out.println("Error cerrando MongoCursor de las matriculas.\n" + ex);
                    }
                }
            }
        }
        return 0;
    }

    public void listarMongo() {
        System.out.println("\n-Listar Alumnos-");
        try {
            MongoCollection<Document> listaAlumnos = CRUD.todoAlumnoMongo();
            MongoCursor<Document> alumnos = listaAlumnos.find().iterator();

            if (alumnos.hasNext()) {
                while (alumnos.hasNext()) {
                    Document alumno = alumnos.next();

                    String nia = alumno.getString(CRUD.alumno_id);
                    String nombre = alumno.getString(CRUD.alumno_nombre);
                    Long numMatri = CRUD.todoMatriculaMongo().countDocuments(new Document(CRUD.matricula_alumno_id, nia));

                    System.out.printf("NIA:%-10s %-30s ", nia, nombre);
                    if (numMatri > 0) {
                        System.out.println("Matriculas: " + numMatri);
                    } else {
                        System.out.println("-Sin Matriculas-");
                    }
                }
                System.out.println("--Fin de la lista--");
            } else {
                System.out.println("Lista de alumno vacio");
            }
            alumnos.close();
        } catch (Exception ex) {
            System.out.println("Error de SQL al retirar todos los alumnos\n" + ex);
        }
    }

    // EXIST
    public int darDeAltaExist() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();

            Element alumno = CRUD_EXIST.buscarAlumnoID(nia);

            if (nia.equals("0")) {
                seguir = false;
            } else if (alumno != null) {
                System.out.println("-Ya existe un alumno con ese NIA");
            } else {
                System.out.print("Introduzca el nombre del alumno: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (CRUD_EXIST.insertarAlumno(nia, nombre)) {
                        System.out.println("\tNIA:" + nia + " " + nombre + " dado de alta\n");
                    } else {
                        System.out.println("No se ha podido insertar el alumno");
                    }
                }
            }
        }
        System.out.println("Volviendo...");
        return 0;
    }

    public int darDeBajaExist() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();
            if (nia.equals("0")) {
                seguir = false;
            } else {
                Element alumno = CRUD_EXIST.buscarAlumnoID(nia);

                if (alumno == null) {
                    System.out.println("-No existe un alumno con ese NIA");
                } else {
                    try {
                        String nombre = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0).getTextContent();
                        ResourceSet matriculas = CRUD_EXIST.buscarMatriculaAluID(nia);
                        ResourceIterator iterator = matriculas.getIterator();

                        while (iterator.hasMoreResources()) {
                            XMLResource resource = (XMLResource) iterator.nextResource();
                            Element matricula = (Element) resource.getContentAsDOM();
                            String idMatri = matricula.getElementsByTagName(CRUD_EXIST.matricula_id).item(0)
                                    .getTextContent();
                            String idNotas = matricula.getElementsByTagName(CRUD_EXIST.matricula_notas_id).item(0)
                                    .getTextContent();

                            if (!CRUD_EXIST.borrarMatricula(idMatri))
                                System.out.println("No se pudo borrar Notas ID:" + idNotas);
                            if (!CRUD_EXIST.borrarNotas(idNotas))
                                System.out.println("No se pudo borrar Matricula ID:" + idMatri);
                        }
                        
                        if (CRUD_EXIST.borrarAlumno(nia)) {
                            System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                        } else {
                            System.out.println("NO se pudo borrar el alumno");
                        }
                    } catch (Exception ex) {
                        System.out.println("Error cerrando MongoCursor de las matriculas.\n" + ex);
                    }
                }
            }
        }
        return 0;
    }

    public void listarExist() {
        System.out.println("\n-Listar Alumnos-");
        try {
            ResourceSet alumnos = CRUD_EXIST.recuperarLista(CRUD_EXIST.alumno_tabla);
            ResourceIterator iterator = alumnos.getIterator();

            if (alumnos.getSize() == 0){
                while (iterator.hasMoreResources()) {
                XMLResource resource = (XMLResource) iterator.nextResource();
                Element modulo = (Element) resource.getContentAsDOM();

                String id = modulo.getElementsByTagName(CRUD_EXIST.alumno_id).item(0).getTextContent();
                String nombre = modulo.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0).getTextContent();
                Long numMatri = CRUD_EXIST.buscarMatriculaAluID(id).getSize();

                System.out.printf("ID:%-10s %-30s ", id, nombre);
                if (numMatri > 0) {
                    System.out.println("Matriculas: " + numMatri);
                } else {
                    System.out.println("-Sin Matriculas-");
                }
            }
            }else{
                System.out.println("Lista de modulos vacio");
            }
        } catch (Exception ex) {
            System.out.println("Error al imprimir los alumnos\n" + ex);
        }
    }
}

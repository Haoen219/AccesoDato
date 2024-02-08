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
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;

import utilidades.Lector;
import utilidades.CRUD;
import utilidades.CRUD_EXIST;

/**
 *
 * @author haoen
 */
public class Modulos {

    // MENU
    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|-----Mantener M?dulos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|4| Matricular Alumno      |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.leerEntero(0, 4);
        return opcion;
    }

    // SQL
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");

        while (seguir) {
            try {
                ResultSet queryMod = CRUD.todoModulo();
                int id = 0;
                if (queryMod.last()) {
                    try {
                        id = Integer.parseInt(queryMod.getString(CRUD.modulo_id));
                    } catch (SQLException ex) {
                        System.out.println("Error retirando lista de modulos para asignar último ID\n" + ex);
                    }
                }
                queryMod.close();

                System.out.print("Introduzca el nombre del modulo: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (CRUD.insertarModulo(Integer.toString(id + 1), nombre)) {
                        System.out.println("\tID:" + Integer.toString(id + 1) + " " + nombre + " dado de alta.");
                    } else {
                        System.out.println("\tNo se ha podido dar de alta.\n");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error cerrando ResultSet\n" + ex);
            }
        }
        System.out.println("Volviendo...");
        return 0;
    }

    public int darDeBaja() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja módulo-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca ID del módulo: ");
            String id = sc.leer();
            if (id.equals("0")) {
                seguir = false;
            } else {
                try {
                    ResultSet queryMod = CRUD.buscarModuloID(id);

                    if (!queryMod.next()) {
                        System.out.println("-No existe un módulo con ese ID");
                    } else {
                        try {
                            String nombre = queryMod.getString(CRUD.modulo_nombre);
                            ResultSet queryMat = CRUD.buscarMatriculaModID(id);

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString(CRUD.matricula_id);
                                String idNotas = queryMat.getString(CRUD.notas_id);
                                if (!CRUD.borrarMatricula(idMatri))
                                    System.out.println("No se ha borrar matricula ID:" + idMatri);
                                if (CRUD.borrarNotas(idNotas))
                                    System.out.println("No se ha borrar notas ID:" + idNotas);
                            }
                            if (CRUD.borrarModulo(id)) {
                                System.out.println("\tID: " + id + " " + nombre + " dado de baja.");
                            } else {
                                System.out.println("\tNo se pudó borrar el modulo.");
                            }
                            queryMat.close();
                        } catch (SQLException ex) {
                            System.out.println("Error cerrando ResultSet matriculas.\n" + ex);
                        }
                    }
                    queryMod.close();
                } catch (SQLException ex) {
                    System.out.println("Error cerrando ResultSet modulo.\n" + ex);
                }
            }
        }
        return 0;
    }

    public int matricularAlumno() {
        boolean seguir = true;
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca NIA del alumno: ");
            String nia = sc.leer();

            if (nia.equals("0")) {
                seguir = false;
            } else {
                try {
                    ResultSet alumno = CRUD.buscarAlumnoID(nia);

                    if (alumno.next()) {
                        String nombre = alumno.getString("alumno_nombre");
                        String id = "";
                        System.out.print("Introduzca ID del módulo: ");
                        id = sc.leer();

                        if (id.equals("0")) {
                            seguir = false;
                        } else {
                            try {
                                ResultSet modulo = CRUD.buscarModuloID(id);

                                if (modulo.next()) {
                                    String moduloNombre = modulo.getString(CRUD.modulo_nombre);
                                    String calificacion = "Sin calificar";

                                    int matriID = 0;
                                    int notasID = 0;
                                    ResultSet matricula = CRUD.todoMatricula();
                                    ResultSet notas = CRUD.todoNotas();

                                    if (matricula.last()) {
                                        matriID = matricula.getInt(CRUD.matricula_id);
                                    }
                                    if (notas.last()) {
                                        notasID = notas.getInt(CRUD.notas_id);
                                    }

                                    if (CRUD.insertarNotas(Integer.toString(notasID + 1), 0, 0, 0)) {
                                        if (CRUD.insertarMatricula(Integer.toString(matriID + 1), nia, id,
                                                Integer.toString(notasID + 1), calificacion)) {
                                            System.out.println("Se creado matricula de " + nombre + " en el módulo "
                                                    + moduloNombre);
                                        } else {
                                            System.out.println(
                                                    "No se ha podido crear la matricula, notas creada ID: " + notasID);
                                        }
                                    } else {
                                        System.out.println("No se ha podido crear notas ID: " + notasID);
                                    }

                                    System.out.println(
                                            "Se creado matricula de " + nombre + " en el módulo " + moduloNombre);

                                    matricula.close();
                                    notas.close();
                                } else {
                                    System.out.println("-No existe un Modulo con este ID.");
                                }
                                modulo.close();
                            } catch (SQLException ex) {
                                System.out.println("Error cerrando ResultSet.\n" + ex);
                            }
                        }
                    } else {
                        System.out.println("-El alumno no existe.");
                    }
                    alumno.close();
                } catch (SQLException ex) {
                    System.out.println("Error cerrando ResultSet alumno.\n" + ex);
                }
            }
        }
        return 0;
    }

    public void listar() {
        System.out.println("\n-Listar Modulos-");
        try {
            ResultSet modulos = CRUD.todoModulo();

            if (modulos.next()) {
                modulos = CRUD.todoModulo();
                while (modulos.next()) {
                    int numMatri = 0;
                    String id = modulos.getString("modulo_id");
                    String nombre = modulos.getString("modulo_nombre");
                    try {
                        ResultSet matriculas = CRUD.buscarMatriculaModID(id);
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (Exception ex) {
                        System.out.println(
                                "Error cerrando el ResultSet de matriculas" + ex);
                    }
                    System.out.printf("\nID:%-10s %-30s ", id, nombre);
                    if (numMatri > 0) {
                        System.out.print("Matriculas: " + numMatri);
                    } else {
                        System.out.print("-Sin Matriculas-");
                    }
                }
                System.out.println("\n--Fin de la lista--");
                modulos.close();
            } else {
                System.out.println("\nLista de modulos vacio");
            }
        } catch (Exception ex) {
            System.out.println("Error cerrando ResultSet de modulos\n" + ex);
        }
    }

    // MONGO
    public int darDeAltaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");

        while (seguir) {
            MongoCursor<Document> modulos = CRUD.todoModuloMongo().find().sort(new Document(CRUD.modulo_id, 1))
                    .iterator();
            int id = 0;
            try {
                while (modulos.hasNext()) {
                    Document modulo = modulos.next();
                    id = Integer.parseInt(modulo.getString(CRUD.modulo_id));
                }
                modulos.close();

                System.out.print("Introduzca el nombre del modulo: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (CRUD.insertarModuloMongo(Integer.toString(id + 1), nombre)) {
                        System.out.println("\tID:" + Integer.toString(id + 1) + " " + nombre + " dado de alta.");
                    } else {
                        System.out.println("No se pudo dar de alta.");
                    }
                }
            } catch (MongoException ex) {
                System.out.println("Error cerrando MongoCursor de modulos\n" + ex);
            }
        }
        System.out.println("Volviendo...");
        return 0;

    }

    public int darDeBajaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja módulo-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca ID del módulo: ");
            String id = sc.leer();
            if (id.equals("0")) {
                seguir = false;
            } else {
                Document modulo = CRUD.buscarModuloIDMongo(id);

                if (modulo == null) {
                    System.out.println("-No existe un módulo con ese ID");
                } else {
                    try {
                        String nombre = modulo.getString(CRUD.modulo_nombre);
                        MongoCursor<Document> matricula = CRUD.buscarMatriculaModIDMongo(id);

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

                        if (CRUD.borrarModuloMongo(id)) {
                            System.out.println("\tID: " + id + " " + nombre + " dado de baja.");
                        } else {
                            System.out.println("No se pudo borrar el modulo");
                        }
                    } catch (Exception ex) {
                        System.out.println("Error cerrando MongoCursor de las matriculas.\n" + ex);
                    }
                }
            }
        }
        return 0;
    }

    public int matricularAlumnoMongo() {
        boolean seguir = true;
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca NIA del alumno: ");
            String nia = sc.leer();

            if (nia.equals("0")) {
                seguir = false;
            } else {
                Document alumno = CRUD.buscarAlumnoIDMongo(nia);

                if (alumno != null) {
                    String nombre = alumno.getString(CRUD.alumno_nombre);
                    String id = "";
                    System.out.print("Introduzca ID del módulo: ");
                    id = sc.leer();

                    if (id.equals("0")) {
                        seguir = false;
                    } else {
                        try {
                            Document modulo = CRUD.buscarModuloIDMongo(id);

                            if (modulo != null) {
                                String moduloNombre = modulo.getString("modulo_nombre");
                                String calificacion = "Sin calificar";
                                int matriID = 0;
                                int notasID = 0;

                                MongoCursor<Document> matriculas = CRUD.todoMatriculaMongo().find()
                                        .sort(new Document(CRUD.matricula_id, 1)).iterator();
                                while (matriculas.hasNext()) {
                                    matriID = Integer.parseInt(matriculas.next().getString(CRUD.matricula_id));
                                }
                                MongoCursor<Document> notas = CRUD.todoNotasMongo().find()
                                        .sort(new Document(CRUD.notas_id, 1)).iterator();
                                while (notas.hasNext()) {
                                    notasID = Integer.parseInt(notas.next().getString(CRUD.notas_id));
                                }

                                if (CRUD.insertarNotasMongo(Integer.toString(notasID + 1), 0, 0, 0)) {
                                    if (CRUD.insertarMatriculaMongo(Integer.toString(matriID + 1), nia, id,
                                            Integer.toString(notasID + 1), calificacion)) {
                                        System.out.println(
                                                "Se creado matricula de " + nombre + " en el módulo " + moduloNombre);
                                    } else {
                                        System.out.println(
                                                "No se ha podido crear la matricula, notas creada ID: " + notasID);
                                    }
                                } else {
                                    System.out.println("No se ha podido crear notas ID: " + notasID);
                                }
                                matriculas.close();
                                notas.close();
                            } else {
                                System.out.println("-No existe un Modulo con este ID.");
                            }
                        } catch (Exception ex) {
                            System.out.println("Error cerrando MongoCursor matriculas/notas.\n" + ex);
                        }
                    }
                } else {
                    System.out.println("-El alumno no existe.");
                }
            }
        }
        return 0;

    }

    public void listarMongo() {
        System.out.println("\n-Listar Modulos-");
        try {
            MongoCursor<Document> modulos = CRUD.todoModuloMongo().find().sort(new Document(CRUD.modulo_id, 1))
                    .iterator();

            if (modulos.hasNext()) {
                while (modulos.hasNext()) {
                    Document modulo = modulos.next();

                    String id = modulo.getString(CRUD.modulo_id);
                    String nombre = modulo.getString(CRUD.modulo_nombre);
                    Long numMatri = CRUD.todoMatriculaMongo()
                            .countDocuments(new Document(CRUD.matricula_modulo_id, id));

                    System.out.printf("ID:%-10s %-30s ", id, nombre);
                    if (numMatri > 0) {
                        System.out.println("Matriculas: " + numMatri);
                    } else {
                        System.out.println("-Sin Matriculas-");
                    }
                }
                System.out.println("--Fin de la lista--");
                modulos.close();
            } else {
                System.out.println("Lista de modulos vacio");
            }
        } catch (Exception ex) {
            System.out.println("Error de SQL al retirar todos los modulos\n" + ex);
        }
    }

    // EXIST
    public int darDeAltaExist() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");
        ResourceIterator iterator;

        while (seguir) {
            try {
                ResourceSet modulos = CRUD_EXIST.recuperarLista(CRUD_EXIST.modulo_tabla);
                iterator = modulos.getIterator();
                int id = 0;

                while (iterator.hasMoreResources()) {
                    XMLResource resource = (XMLResource) iterator.nextResource();
                    Element modulo = (Element) resource.getContentAsDOM();
                    id = Integer.parseInt(modulo.getElementsByTagName(CRUD_EXIST.modulo_id).item(0).getTextContent());
                }

                System.out.print("Introduzca el nombre del modulo: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (CRUD_EXIST.insertarModulo(Integer.toString(id + 1), nombre)) {
                        System.out.println("\tID:" + Integer.toString(id + 1) + " " + nombre + " dado de alta.");
                    } else {
                        System.out.println("No se pudo dar de alta.");
                    }
                }
            } catch (XMLDBException e) {
                System.out.println("Error recuperando el ultimo ID");
                e.printStackTrace();
            }
        }
        System.out.println("Volviendo...");
        return 0;

    }

    public int darDeBajaExist() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de baja módulo-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca ID del módulo: ");
            String id = sc.leer();
            if (id.equals("0")) {
                seguir = false;
            } else {
                Element modulo = CRUD_EXIST.buscarModuloID(id);

                if (modulo == null) {
                    System.out.println("-No existe un módulo con ese ID");
                } else {
                    try {
                        String nombre = modulo.getElementsByTagName(CRUD_EXIST.modulo_nombre).item(0).getTextContent();
                        ResourceSet matriculas = CRUD_EXIST.buscarMatriculaModID(id);
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

                        if (CRUD_EXIST.borrarModulo(id)) {
                            System.out.println("\tID: " + id + " " + nombre + " dado de baja.");
                        } else {
                            System.out.println("No se pudo borrar el modulo");
                        }
                    } catch (XMLDBException e) {
                        System.out.println("Error recuperando elementos con existDB");
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }

    public int matricularAlumnoExist() {
        boolean seguir = true;
        Lector sc = new Lector(System.in);
        System.out.println("\n-Matricular alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca NIA del alumno: ");
            String nia = sc.leer();

            if (nia.equals("0")) {
                seguir = false;
            } else {
                Element alumno = CRUD_EXIST.buscarAlumnoID(nia);

                if (alumno != null) {
                    String nombre = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0).getTextContent();
                    String id = "";
                    System.out.print("Introduzca ID del módulo: ");
                    id = sc.leer();

                    if (id.equals("0")) {
                        seguir = false;
                    } else {
                        try {
                            Element modulo = CRUD_EXIST.buscarModuloID(id);

                            if (modulo != null) {

                                String moduloNombre = modulo.getElementsByTagName(CRUD_EXIST.modulo_nombre).item(0)
                                        .getTextContent();
                                String calificacion = "Sin calificar";
                                int matriID = 0;
                                int notasID = 0;

                                Element matricula = CRUD_EXIST.buscarMatriculaDobleID(nia, id);
                                if (matricula == null) {
                                    // obtener ID para matricula y notas
                                    ResourceSet matriculas = CRUD_EXIST.recuperarLista(CRUD_EXIST.matricula_tabla);
                                    matriID = Long.valueOf(matriculas.getSize()).intValue() + 1;
                                    ResourceSet notas = CRUD_EXIST.recuperarLista(CRUD_EXIST.notas_tabla);
                                    notasID = Long.valueOf(notas.getSize()).intValue() + 1;

                                    if (CRUD_EXIST.insertarNotas(Integer.toString(notasID), 0, 0, 0)) {
                                        if (CRUD_EXIST.insertarMatricula(Integer.toString(matriID), nia, id,
                                                Integer.toString(notasID + 1), calificacion)) {
                                            System.out.println(
                                                    "Se creado matricula de " + nombre + " en el módulo "
                                                            + moduloNombre);
                                        } else {
                                            System.out.println(
                                                    "No se ha podido crear la matricula, notas creada ID: " + notasID);
                                        }
                                    } else {
                                        System.out.println("No se ha podido crear notas ID: " + notasID);
                                    }

                                } else {
                                    System.out.println("Ya existe una matricula de este alumno en este modulo");
                                }
                            } else {
                                System.out.println("-No existe un Modulo con este ID.");
                            }
                        } catch (Exception ex) {
                            System.out.println("Error cerrando MongoCursor matriculas/notas.\n" + ex);
                        }
                    }
                } else {
                    System.out.println("-El alumno no existe.");
                }
            }
        }
        return 0;

    }

    public void listarExist() {
        System.out.println("\n-Listar Modulos-");
        try {
            ResourceSet modulos = CRUD_EXIST.recuperarLista(CRUD_EXIST.modulo_tabla);
            ResourceIterator iterator = modulos.getIterator();

            if (modulos.getSize() == 0){
                while (iterator.hasMoreResources()) {
                XMLResource resource = (XMLResource) iterator.nextResource();
                Element modulo = (Element) resource.getContentAsDOM();

                String id = modulo.getElementsByTagName(CRUD_EXIST.modulo_id).item(0).getTextContent();
                String nombre = modulo.getElementsByTagName(CRUD_EXIST.modulo_nombre).item(0).getTextContent();
                Long numMatri = CRUD_EXIST.buscarMatriculaModID(id).getSize();

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
            System.out.println("Error al imprimir los modulos\n" + ex);
        }
    }
}

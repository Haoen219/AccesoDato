/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;

import persistencia.ORM;
import utilidades.Lector;
import utilidades.SQL;

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

    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();

            ResultSet queryAlu = SQL.buscarAlumnoID(nia);
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
                        if (SQL.insertarAlumno(nia, nombre)) {
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
                ResultSet queryAlu = SQL.buscarAlumnoID(nia);

                try {
                    if (!queryAlu.next()) {
                        System.out.println("-No existe un alumno con ese NIA");
                    } else {
                        try {
                            String nombre = queryAlu.getString(SQL.alumno_nombre);
                            ResultSet queryMat = SQL.buscarMatriculaAluID(nia);

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString(SQL.matricula_id);
                                String idNotas = queryMat.getString(SQL.notas_id);
                                if (!SQL.borrarMatricula(idMatri)) {
                                    System.out.println("\nError borrando matricula ID:" + idMatri);
                                }
                                if (!SQL.borrarNotas(idNotas)) {
                                    System.out.println("\nError borrando notas ID:" + idNotas);
                                }
                            }
                            if (SQL.borrarAlumno(nia)) {
                                System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                            } else {
                                System.out.println("\tError borrando el alumno.");
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

    // IMPRIMIR
    public void listar() {
        System.out.print("\n-Listar Alumnos-");
        try {
            ResultSet alumnos = SQL.todoAlumno();

            if (alumnos.next()) {
                alumnos = SQL.todoAlumno();
                while (alumnos.next()) {
                    int numMatri = 0;
                    String nia = alumnos.getString(SQL.alumno_id);
                    String nombre = alumnos.getString(SQL.alumno_nombre);
                    try {
                        ResultSet matriculas = SQL.buscarMatriculaAluID(nia);
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (SQLException ex) {
                        System.out.println("Error cerrando ResultSet matriculas del alumno ID:"+nia+"\n" + ex);
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










    

    public int darDeAltaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();

            MongoCollection<Document> alumnos = null;
            try {
                alumnos = ORM.getMongoDatabase().getCollection(SQL.alumno_tabla);
                Document alumno = alumnos.find(new Document(SQL.alumno_id, nia)).first();

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
                        try {

                            Document alumnoNuevo = new Document("_id", new ObjectId());
                            alumnoNuevo.append(SQL.alumno_id, nia)
                                    .append(SQL.alumno_nombre, nombre);

                            alumnos.insertOne(alumnoNuevo);

                            System.out.println("\tNIA:" + nia + " " + nombre + " dado de alta.\n");

                        } catch (Exception ex) {
                            System.out.println("Error insertando el alumno\n" + ex);
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error buscando alumno con ese NIA\n" + ex);
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
                try {
                    MongoCollection<Document> alumnos = ORM.getMongoDatabase().getCollection(SQL.alumno_tabla);
                    Document alumno = alumnos.find(new Document(SQL.alumno_id, nia)).first();

                    if (alumno == null) {
                        System.out.println("-No existe un alumno con ese NIA");
                    } else {
                        try {
                            String nombre = alumno.getString(SQL.alumno_nombre);

                            MongoCollection<Document> matriculas = ORM.getMongoDatabase()
                                    .getCollection(SQL.matricula_tabla);
                            FindIterable<Document> matriAlumno = matriculas
                                    .find(new Document(SQL.matricula_alumno_id, new Document("$eq", nia)));
                            MongoCursor<Document> matricula = matriAlumno.iterator();

                            MongoCollection<Document> notas = ORM.getMongoDatabase().getCollection(SQL.notas_tabla);
                            // Borrar notas de cada matricula
                            while (matricula.hasNext()) {
                                Document matri = matricula.next();
                                String idMatri = matri.getString(SQL.matricula_id);
                                String idNotas = matri.getString(SQL.notas_id);
                                try {
                                    Bson filterNotas = new Document(SQL.matricula_notas_id,
                                            new Document("$eq", idNotas));
                                    notas.deleteOne(filterNotas);
                                } catch (Exception ex) {
                                    System.out.println("Error borrando notas de la matricula, ID_Matricula: "
                                            + idMatri + "\n" + ex);
                                }
                            }
                            // Borrar todas las matriculas con alumno_id que coincidan
                            try {
                                Bson filterMatri = new Document(SQL.matricula_alumno_id, new Document("$eq", nia));
                                notas.deleteMany(filterMatri);
                            } catch (Exception ex) {
                                System.out.println("Error borrando las matriculas.\n" + ex);
                            }

                            matricula.close();
                            System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                        } catch (Exception ex) {
                            System.out.println("Error buscando las notas/matricula en la base.\n" + ex);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error buscando el alumno en la base.\n" + ex);
                }
            }
        }
        return 0;
    }

    // IMPRIMIR
    public void listarMongo() {
        System.out.print("\n-Listar Alumnos-");
        try {
            ResultSet alumnos = ORM.getConnection().createStatement().executeQuery(ORM.todoAlumno);

            if (alumnos.next()) {
                alumnos = ORM.getConnection().createStatement().executeQuery(ORM.todoAlumno);
                while (alumnos.next()) {
                    int numMatri = 0;
                    String nia = alumnos.getString("alumno_nia");
                    String nombre = alumnos.getString("alumno_nombre");
                    try {
                        ResultSet matriculas = ORM.getConnection().createStatement()
                                .executeQuery(ORM.buscarMatriculaAluID(nia));
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (Exception ex) {
                        System.out.println(
                                "Error de SQL al buscar las matriculas del alumno ." + nombre + " NIA: " + nia + "\n"
                                        + ex);
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
        } catch (Exception ex) {
            System.out.println("Error de SQL al retirar todos los alumnos\n" + ex);
        }
    }
}

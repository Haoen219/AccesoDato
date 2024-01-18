/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

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
                                if (!SQL.borrarMatricula(idMatri)) System.out.println("\nNo se pudó borrar matricula ID:" + idMatri);
                                if (!SQL.borrarNotas(idNotas)) System.out.println("\nNo se pudó borrar notas ID:" + idNotas);
                            }
                            if (SQL.borrarAlumno(nia)) {
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

            Document alumno = SQL.buscarAlumnoIDMongo(nia);

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
                    if (SQL.insertarAlumnoMongo(nia, nombre)){
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
                    Document alumno = SQL.buscarAlumnoIDMongo(nia);

                    if (alumno == null) {
                        System.out.println("-No existe un alumno con ese NIA");
                    } else {
                        try {
                            String nombre = alumno.getString(SQL.alumno_nombre);
                            MongoCursor<Document> matricula = SQL.buscarMatriculaAluIDMongo(nia);
                            // Borrar notas de cada matricula
                            while (matricula.hasNext()) {
                                Document matri = matricula.next();
                                String idMatri = matri.getString(SQL.matricula_id);
                                String idNotas = matri.getString(SQL.notas_id);
                                
                                if (!SQL.borrarNotasMongo(idNotas)) System.out.println("No se pudo borrar Notas ID:"+idNotas);
                                if (!SQL.borrarMatriculaMongo(idMatri)) System.out.println("No se pudo borrar Matricula ID:"+idMatri);
                            }
                            matricula.close();
                            System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                        } catch (Exception ex) {
                            System.out.println("Error cerrando MongoCursor de las matriculas.\n" + ex);
                        }
                    }
            }
        }
        return 0;
    }

    // IMPRIMIR
    public void listarMongo() {
        System.out.print("\n-Listar Alumnos-");
        try {
            MongoCollection<Document> listaAlumnos = SQL.todoAlumnoMongo();
            MongoCursor<Document> alumnos = listaAlumnos.find().iterator();

            if (alumnos.hasNext()) {
                while (alumnos.hasNext()) {
                    Document alumno = alumnos.next();

                    int numMatri = 0;
                    String nia = alumno.getString(SQL.alumno_id);
                    String nombre = alumno.getString(SQL.alumno_nombre);
                    try {
                        MongoCursor<Document> matriculas = SQL.buscarMatriculaAluIDMongo(nia);
                        while (matriculas.hasNext()) {
                            matriculas.next();
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (Exception ex) {
                        System.out.println("Error intentando cerrar cursor de matriculas del alumno ." + nombre + " NIA: " + nia + "\n" + ex);
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

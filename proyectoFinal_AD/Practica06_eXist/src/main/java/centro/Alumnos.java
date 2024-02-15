/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import utilidades.Lector;
import utilidades.CRUD;

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
                    Long numMatri = CRUD.todoMatriculaMongo()
                            .countDocuments(new Document(CRUD.matricula_alumno_id, nia));

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
}

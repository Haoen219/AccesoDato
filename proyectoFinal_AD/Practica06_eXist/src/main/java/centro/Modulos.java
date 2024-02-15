/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;

import utilidades.Lector;
import utilidades.CRUD;

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
}

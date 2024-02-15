package centro;

import org.bson.Document;

import utilidades.Lector;
import utilidades.CRUD;

import com.mongodb.client.MongoCursor;

public class Matriculas {

    public int menu() {
        Lector sc = new Lector(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Modificar              |");
        System.out.println("|2| Calificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|" + "-".repeat(26) + "|");
        int opcion = sc.leerEntero(0, 3);
        return opcion;
    }

    private int menuCalificar() {
        Lector sc = new Lector(System.in);
        int opcion = -1;
        System.out.println("");
        System.out.println("Calificaciones:");
        System.out.println("1- Suspendido");
        System.out.println("2- Bien");
        System.out.println("3- Notable");
        System.out.println("4- Excelente");
        while (opcion < 1 || opcion > 4) {
            opcion = sc.leerEntero(1, 4);
        }
        return opcion;
    }

    // MONGO
    public int modificarNotasMongo() {
        Lector sc2 = new Lector(System.in);

        if (CRUD.todoMatriculaMongo().countDocuments() > 0) {
            System.out.println("\n-Modificar notas-");
            boolean seguir = true;

            while (seguir) {
                String nia = "";
                System.out.println("Introduzca NIA del alumno: (Volver con [0])");
                nia = sc2.leer();

                if (nia.equals("0")) {
                    seguir = false;
                } else {
                    // Buscar Alumno
                    boolean seguirAlumno = true;

                    Document alumno = CRUD.buscarAlumnoIDMongo(nia);

                    if (alumno != null) {
                        String nombreAlu = alumno.getString(CRUD.alumno_nombre);
                        System.out.println(nombreAlu + ":\n----");

                        if (CRUD.todoMatriculaMongo()
                                .countDocuments(new Document(CRUD.matricula_alumno_id, nia)) <= 0) {
                            seguirAlumno = false;
                            System.out.println("Este alumno no tiene matricula aún.");
                        } else {
                            imprimirAlumnoMongo(CRUD.buscarMatriculaAluIDMongo(nia));
                        }
                    } else {
                        seguirAlumno = false;
                        System.out.println("-El alumno no existe.");
                    }

                    while (seguirAlumno) {
                        String id = "";
                        System.out.println("Introduzca ID del módulo: (Volver con [0])");
                        id = sc2.leer();

                        if (id.equals("0")) {
                            seguirAlumno = false;
                        } else {
                            try {
                                Document matricula = CRUD.buscarMatriculaDobleIDMongo(nia, id);

                                if (matricula != null) {
                                    String notasId = matricula.getString(CRUD.matricula_notas_id);

                                    Document notas = CRUD.buscarNotasIDMongo(notasId);
                                    Lector sc = new Lector(System.in);
                                    int nota1 = notas.getInteger(CRUD.notas_nota1);
                                    int nota2 = notas.getInteger(CRUD.notas_nota2);
                                    int nota3 = notas.getInteger(CRUD.notas_nota3);

                                    System.out.println("Introduzca las notas por orden: ");
                                    System.out.print("Nota 1: ");
                                    nota1 = sc.leerEntero(0, 10);
                                    if (nota1 < 0 || nota1 > 10)
                                        nota1 = notas.getInteger(CRUD.notas_nota1);
                                    System.out.print("Nota 2: ");
                                    nota2 = sc.leerEntero(0, 10);
                                    if (nota2 < 0 || nota2 > 10)
                                        nota2 = notas.getInteger(CRUD.notas_nota2);
                                    System.out.print("Nota 3: ");
                                    nota3 = sc.leerEntero(0, 10);
                                    if (nota3 < 0 || nota3 > 10)
                                        nota3 = notas.getInteger(CRUD.notas_nota3);

                                    if (CRUD.actualizarNotaMongo(notasId, nota1, nota2, nota3)) {
                                        System.out.println("Modificaciones realizadas");
                                    } else {
                                        System.out.println("No se ha podido modificar");
                                    }
                                } else {
                                    System.out.println("-No existe matricula con este modulo.");
                                }
                            } catch (Exception ex) {
                                System.out.println("Error cerrando MongoCursor matriculas.\n" + ex);
                            }
                        }
                    }

                }
            }
        } else {
            System.out.println("No tienes ninguna matricula aún.");
        }
        return 0;
    }

    public int evaluarModuloMongo() {
        Lector sc2 = new Lector(System.in);

        if (CRUD.todoMatriculaMongo().countDocuments() > 0) {
            System.out.println("\n-Modificar notas-");
            boolean seguir = true;

            while (seguir) {
                String nia = "";
                System.out.println("Introduzca NIA del alumno: (Volver con [0])");
                nia = sc2.leer();

                if (nia.equals("0")) {
                    seguir = false;
                } else {
                    // Buscar Alumno
                    boolean seguirAlumno = true;

                    Document alumno = CRUD.buscarAlumnoIDMongo(nia);

                    if (alumno != null) {
                        String nombreAlu = alumno.getString(CRUD.alumno_nombre);
                        System.out.println(nombreAlu + ":\n----");
                        if (CRUD.todoMatriculaMongo()
                                .countDocuments(new Document(CRUD.matricula_alumno_id, nia)) <= 0) {
                            seguirAlumno = false;
                            System.out.println("Este alumno no tiene matricula aún.");
                        } else {
                            imprimirAlumnoMongo(CRUD.buscarMatriculaAluIDMongo(nia));
                        }
                    } else {
                        seguirAlumno = false;
                        System.out.println("El alumno no existe.");
                    }

                    while (seguirAlumno) {
                        String id = "";
                        System.out.println("Introduzca ID del módulo: (Volver con [0])");
                        id = sc2.leer();

                        if (id.equals("0")) {
                            seguirAlumno = false;
                        } else {
                            try {
                                Document matricula = CRUD.buscarMatriculaDobleIDMongo(nia, id);

                                if (matricula != null) {
                                    String matriculaID = matricula.getString(CRUD.matricula_id);

                                    String calificacion = "";
                                    switch (menuCalificar()) {
                                        case 1 ->
                                            calificacion = "Suspendido";
                                        case 2 ->
                                            calificacion = "Bien";
                                        case 3 ->
                                            calificacion = "Notable";
                                        case 4 ->
                                            calificacion = "Excelente";
                                    }

                                    if (CRUD.actualizarMatriculaMongo(matriculaID, calificacion)) {
                                        System.out.println("Modificaciones realizadas");
                                    } else {
                                        System.out.println("No se ha podido modificar");
                                    }
                                } else {
                                    System.out.println("-No existe matricula con este modulo.");
                                }
                            } catch (Exception ex) {
                                System.out.println("Error cerrando MongoCursor matriculas.\n" + ex);
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("No tienes ninguna matricula aún.");
        }
        return 0;
    }

    public void mostrarMongo() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Boletín-");
        System.out.print("Introduzca ID del alumno: ");
        String nia = sc.leer();

        Document alumno = CRUD.buscarAlumnoIDMongo(nia);
        if (alumno != null) {
            String nombreAlu = alumno.getString(CRUD.alumno_nombre);

            System.out.println("\nMatricula de " + nombreAlu + ":");

            if (CRUD.todoMatriculaMongo()
                    .countDocuments(new Document(CRUD.matricula_alumno_id, nia)) <= 0) {
                System.out.println("Este alumno no tiene matricula aún.");
            } else {
                imprimirAlumnoMongo(CRUD.buscarMatriculaAluIDMongo(nia));
            }
        } else {
            System.out.println("-El alumno no existe.");
        }
    }

    private void imprimirAlumnoMongo(MongoCursor<Document> matriculas) {
        while (matriculas.hasNext()) {
            Document matricula = matriculas.next();

            String moduloId = matricula.getString(CRUD.matricula_modulo_id);
            String notasId = matricula.getString(CRUD.matricula_notas_id);
            String calificacion = matricula.getString(CRUD.matricula_calificacion);
            Document modulo = CRUD.buscarModuloIDMongo(moduloId);
            Document notas = CRUD.buscarNotasIDMongo(notasId);

            String nombreMod = modulo.getString(CRUD.modulo_nombre);
            int nota1 = notas.getInteger(CRUD.notas_nota1);
            int nota2 = notas.getInteger(CRUD.notas_nota2);
            int nota3 = notas.getInteger(CRUD.notas_nota3);

            System.out.printf("\tID:%-10s %-20s Notas: %-2d|%-2d|%-2d [%s]\n", moduloId, nombreMod, nota1, nota2,
                    nota3, calificacion);
        }
    }
}

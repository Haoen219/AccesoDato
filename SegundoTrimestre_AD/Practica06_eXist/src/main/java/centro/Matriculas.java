package centro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;
import org.w3c.dom.Element;
import org.xmldb.api.modules.XMLResource;

import utilidades.Lector;
import utilidades.CRUD;
import utilidades.CRUD_EXIST;

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

    private boolean hayMatriculas() {
        try (ResultSet matriculas = CRUD.todoMatricula()) {
            if (matriculas.next()) {
                matriculas.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error retirando las matriculas para comprobar si hay instamcias.\n" + e);
        }
        return false;
    }

    public int modificarNotas() {
        Lector sc2 = new Lector(System.in);

        if (hayMatriculas()) {
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

                    try {
                        ResultSet alumno = CRUD.buscarAlumnoID(nia);

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString(CRUD.alumno_nombre);
                            System.out.println(nombreAlu + ":\n----");

                            ResultSet matriculas = CRUD.buscarMatriculaAluID(nia);
                            if (!matriculas.next()) {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            } else {
                                imprimirAlumno(CRUD.buscarMatriculaAluID(nia));
                            }
                            matriculas.close();

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = CRUD.buscarMatriculaDobleID(nia, id);
                                        if (matricula.next()) {
                                            String notasId = matricula.getString(CRUD.matricula_notas_id);

                                            ResultSet notas = CRUD.buscaNotaID(notasId);
                                            notas.next();
                                            Lector sc = new Lector(System.in);
                                            int nota1 = notas.getInt(CRUD.notas_nota1);
                                            int nota2 = notas.getInt(CRUD.notas_nota2);
                                            int nota3 = notas.getInt(CRUD.notas_nota3);

                                            System.out.println("Introduzca las notas por orden: ");
                                            System.out.print("Nota 1: ");
                                            nota1 = sc.leerEntero(0, 10);
                                            if (nota1 < 0 || nota1 > 10)
                                                nota1 = notas.getInt(CRUD.notas_nota1);
                                            System.out.print("Nota 2: ");
                                            nota2 = sc.leerEntero(0, 10);
                                            if (nota2 < 0 || nota2 > 10)
                                                nota2 = notas.getInt(CRUD.notas_nota2);
                                            System.out.print("Nota 3: ");
                                            nota3 = sc.leerEntero(0, 10);
                                            if (nota3 < 0 || nota3 > 10)
                                                nota3 = notas.getInt(CRUD.notas_nota3);

                                            if (CRUD.actualizarNota(notasId, nota1, nota2, nota3)) {
                                                System.out.println("Modificaciones realizadas.");
                                            } else {
                                                System.out.println("No se ha podido modificar.");
                                            }
                                            notas.close();
                                        } else {
                                            System.out.println("-No existe matricula con este modulo.");
                                        }
                                        matricula.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Error cerrando ResultSet matriculas/notas.\n" + ex);
                                    }
                                }
                            }
                        } else {
                            seguirAlumno = false;
                            System.out.println("-El alumno no existe.");
                        }
                        alumno.close();
                    } catch (SQLException ex) {
                        System.out.println("Error cerrando ResultSet alumno/matricula.\n" + ex);
                    }
                }
            }
        } else {
            System.out.println("No tienes ninguna matricula aún.");
        }
        return 0;
    }

    public int evaluarModulo() {
        Lector sc2 = new Lector(System.in);

        if (hayMatriculas()) {
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

                    try {
                        ResultSet alumno = CRUD.buscarAlumnoID(nia);

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString(CRUD.alumno_nombre);
                            System.out.println(nombreAlu + ":");
                            System.out.println("----");

                            ResultSet matriculas = CRUD.buscarMatriculaAluID(nia);
                            if (matriculas.next()) {
                                imprimirAlumno(CRUD.buscarMatriculaAluID(nia));
                            } else {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            }
                            matriculas.close();

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = CRUD.buscarMatriculaDobleID(nia, id);

                                        if (matricula.next()) {
                                            String matriculaId = matricula.getString(CRUD.matricula_id);
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

                                            if (CRUD.actualizarMatricula(matriculaId, calificacion)) {
                                                System.out.println("+Se ha modificado la matricula.");
                                            } else {
                                                System.out.println("No se ha podido modificar.");
                                            }

                                        } else {
                                            System.out.println("-No existe matricula con este modulo.");
                                        }
                                        matricula.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Error buscando matriculas.\n" + ex);
                                    }
                                }
                            }
                        } else {
                            seguirAlumno = false;
                            System.out.println("-El alumno no existe.");
                        }
                        alumno.close();
                    } catch (SQLException ex) {
                        System.out.println("Error cerrando ResultList alumno/matricula.\n" + ex);
                    }
                }
            }
        } else {
            System.out.println("No tienes ninguna matricula aún.");
        }
        return 0;
    }

    public void mostrar() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Boletín-");
        System.out.print("Introduzca ID del alumno: ");
        String nia = sc.leer();

        try {
            ResultSet alumno = CRUD.buscarAlumnoID(nia);
            String nombreAlu = "";
            if (alumno.next()) {
                nombreAlu = alumno.getString("alumno_nombre");
                System.out.println("\nMatricula de " + nombreAlu + ":");

                ResultSet matriculas = CRUD.buscarMatriculaAluID(nia);
                if (matriculas.next()) {
                    imprimirAlumno(CRUD.buscarMatriculaAluID(nia));
                } else {
                    System.out.println("Este alumno no tiene matricula aún.");
                }
                matriculas.close();
            } else {
                System.out.println("-El alumno no existe.");
            }
            alumno.close();
        } catch (SQLException ex) {
            System.out.println("Error cerrando ResultSet alumno/matriculas.\n" + ex);
        }
    }

    private void imprimirAlumno(ResultSet matriculas) {
        try {
            while (matriculas.next()) {
                String moduloId = matriculas.getString("modulo_id");
                String notasId = matriculas.getString("notas_id");
                String calificacion = matriculas.getString("calificacion");
                ResultSet modulo = CRUD.buscarModuloID(moduloId);
                ResultSet notas = CRUD.buscaNotaID(notasId);

                modulo.next();
                notas.next();

                String nombreMod = modulo.getString("modulo_nombre");
                int nota1 = notas.getInt("nota1");
                int nota2 = notas.getInt("nota2");
                int nota3 = notas.getInt("nota3");

                System.out.printf("\tID:%-10s %-20s Notas: %-2d|%-2d|%-2d [%s]\n", moduloId, nombreMod, nota1, nota2,
                        nota3, calificacion);
                modulo.close();
                notas.close();
            }
        } catch (SQLException e) {
            System.out.println("Error imprimiendo matriculas.");
            e.printStackTrace();
        }
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

    // EXIST
    public int modificarNotasExist() {
        Lector sc2 = new Lector(System.in);

        if (hayMatriculas()) {
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

                    Element alumno = CRUD_EXIST.buscarAlumnoID(nia);

                    if (alumno != null) {
                        String alumnoNombre = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0)
                                .getTextContent();
                        System.out.println(alumnoNombre + ":\n----");

                        if (CRUD_EXIST.buscarMatriculaAluID(nia) == null) {
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
                                Element matricula = CRUD_EXIST.buscarMatriculaDobleID(nia, id);

                                if (matricula != null) {
                                    String notasId = matricula.getElementsByTagName(CRUD_EXIST.matricula_notas_id)
                                            .item(0).getTextContent();

                                    Element notas = CRUD_EXIST.buscarNotaID(notasId);
                                    Lector sc = new Lector(System.in);
                                    int nota1 = -1;
                                    int nota2 = -1;
                                    int nota3 = -1;

                                    System.out.println("Introduzca las notas por orden: ");
                                    System.out.print("Nota 1: ");
                                    nota1 = sc.leerEntero(0, 10);
                                    if (nota1 < 0 || nota1 > 10)
                                        nota1 = Integer.getInteger(notas.getElementsByTagName(CRUD_EXIST.notas_nota1)
                                                .item(0).getTextContent());
                                    System.out.print("Nota 2: ");
                                    nota2 = sc.leerEntero(0, 10);
                                    if (nota2 < 0 || nota2 > 10)
                                        nota2 = Integer.getInteger(notas.getElementsByTagName(CRUD_EXIST.notas_nota2)
                                                .item(0).getTextContent());
                                    System.out.print("Nota 3: ");
                                    nota3 = sc.leerEntero(0, 10);
                                    if (nota3 < 0 || nota3 > 10)
                                        nota3 = Integer.getInteger(notas.getElementsByTagName(CRUD_EXIST.notas_nota3)
                                                .item(0).getTextContent());

                                    if (CRUD_EXIST.actualizarNota(notasId, nota1, nota2, nota3)) {
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

    public int evaluarModuloExist() {
        Lector sc2 = new Lector(System.in);

        if (hayMatriculas()) {
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

                    Element alumno = CRUD_EXIST.buscarAlumnoID(nia);

                    if (alumno != null) {
                        String alumnoNombre = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0)
                                .getTextContent();
                        System.out.println(alumnoNombre + ":\n----");

                        if (CRUD_EXIST.buscarMatriculaAluID(nia) == null) {
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
                                Element matricula = CRUD_EXIST.buscarMatriculaDobleID(nia, id);

                                if (matricula != null) {
                                    String matriculaId = matricula.getElementsByTagName(CRUD_EXIST.matricula_id).item(0).getTextContent();
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

                                    if (CRUD_EXIST.actualizarMatricula(matriculaId, calificacion)) {
                                        System.out.println("+Se ha modificado la matricula.");
                                    } else {
                                        System.out.println("No se ha podido modificar.");
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

    public void mostrarExist() {
        Lector sc = new Lector(System.in);
        System.out.println("\n-Boletín-");
        System.out.print("Introduzca ID del alumno: ");
        String nia = sc.leer();

        try {
            Element alumno = CRUD_EXIST.buscarAlumnoID(nia);
            String nombreAlu = "";
            if (alumno != null) {
                nombreAlu = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0).getTextContent();
                System.out.println("\nMatricula de " + nombreAlu + ":");

                ResultSet matriculas = CRUD_EXIST.buscarMatriculaAluID(nia);
                if (matriculas.next()) {
                    imprimirAlumno(CRUD.buscarMatriculaAluID(nia));
                } else {
                    System.out.println("Este alumno no tiene matricula aún.");
                }
                matriculas.close();
            } else {
                System.out.println("-El alumno no existe.");
            }
            alumno.close();
        } catch (SQLException ex) {
            System.out.println("Error cerrando ResultSet alumno/matriculas.\n" + ex);
        }
    }

    private void imprimirAlumnoExist(ResultSet matriculas) {
        try {
            while (matriculas.next()) {
                String moduloId = matriculas.getString("modulo_id");
                String notasId = matriculas.getString("notas_id");
                String calificacion = matriculas.getString("calificacion");
                ResultSet modulo = CRUD.buscarModuloID(moduloId);
                ResultSet notas = CRUD.buscaNotaID(notasId);

                modulo.next();
                notas.next();

                String nombreMod = modulo.getString("modulo_nombre");
                int nota1 = notas.getInt("nota1");
                int nota2 = notas.getInt("nota2");
                int nota3 = notas.getInt("nota3");

                System.out.printf("\tID:%-10s %-20s Notas: %-2d|%-2d|%-2d [%s]\n", moduloId, nombreMod, nota1, nota2,
                        nota3, calificacion);
                modulo.close();
                notas.close();
            }
        } catch (SQLException e) {
            System.out.println("Error imprimiendo matriculas.");
            e.printStackTrace();
        }
    }
}

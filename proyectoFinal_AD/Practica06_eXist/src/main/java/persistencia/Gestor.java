package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import centro.Alumnos;
import centro.Matriculas;
import centro.Modulos;

import utilidades.App;
import utilidades.Lector;
import utilidades.CRUD;

public class Gestor {
    private static Alumnos alumnos = new Alumnos();
    private static Modulos modulos = new Modulos();
    private static Matriculas matriculas = new Matriculas();
    public static final File importFile = new File("import.txt");

    private static MongoDatabase mongoDatabase;

    public Gestor() {
        mongoDatabase = new Conexion().getMongoDatabase();
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    // MENU DE PROGRAMA
    private int menu() {
        int option = -1;
        Lector in = new Lector(System.in);
        while ((option < 0)) {
            System.out.println("");
            System.out.println("|-----------MENÚ-----------|");
            System.out.println("|0|-Salir menú             |");
            System.out.println("|1|-Mantener Alumnos       |");
            System.out.println("|2|-Mantener Asignaturas   |");
            System.out.println("|3|-Mantener Matricula     |");
            System.out.println("|" + "-".repeat(26) + "|");
            System.out.println("|4|-Exportar Datos         |");
            System.out.println("|5|-Importar Datos         |");
            System.out.println("|" + "-".repeat(26) + "|");
            System.out.print("OPCIÓN: ");
            option = in.leerEntero(0, 5);
        }
        return option;
    }

    private int menuImport() {
        Lector sc = new Lector(System.in);
        int opcion = -1;
        System.out.println("Opciones:");
        System.out.println("1- Sustituir");
        System.out.println("2- No importar");
        while (opcion < 1 || opcion > 2) {
            opcion = sc.leerEntero(1, 4);
        }
        return opcion;
    }

    private void realizarOpcion(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                menuAlumnos();
                break;
            case 2:
                menuModulos();
                break;
            case 3:
                menuMatriculas();
                break;
            case 4:
                exportarDatosMongo();
                break;
            case 5:
                importarDatosMongo();
                break;
            default:
                System.out.println(App.ANSI_CYAN + "Hay que elegir una de las opciones (numero entre parentesis)"
                        + App.ANSI_WHITE);
        }
    }

    private static int menuAlumnos() {
        int opcion = 0;
        do {
            try {
                opcion = alumnos.menu();
                switch (opcion) {
                    case 0:
                        System.out.println("Volviendo al menu previo...\n");
                        break;
                    case 1:
                        alumnos.darDeAltaMongo();
                        break;
                    case 2:
                        alumnos.darDeBajaMongo();
                        break;
                    case 3:
                        alumnos.listarMongo();
                        break;
                    default:
                        System.out.println("--Opción no valida");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            } catch (Exception ex) {
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n" + ex);
                // return 0;
            }
        } while (opcion > 0);
        return 0;
    }

    private static int menuModulos() {
        int opcion = 0;
        do {
            try {
                opcion = modulos.menu();
                switch (opcion) {
                    case 0:
                        System.out.println("Volviendo al menu previo...\n");
                        break;
                    case 1:
                        modulos.darDeAltaMongo();
                        break;
                    case 2:
                        modulos.darDeBajaMongo();
                        break;
                    case 3:
                        modulos.listarMongo();
                        break;
                    case 4:
                        modulos.matricularAlumnoMongo();
                        break;
                    default:
                        System.out.println("--Opción no valida");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            } catch (Exception ex) {
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n" + ex);
                // return 0;
            }
        } while (opcion > 0);
        return 0;
    }

    private static int menuMatriculas() {
        int opcion = 0;
        do {
            try {
                opcion = matriculas.menu();
                switch (opcion) {
                    case 0:
                        System.out.println("Volviendo al menu previo...\n");
                        break;
                    case 1:
                        matriculas.modificarNotasMongo();
                        break;
                    case 2:
                        matriculas.evaluarModuloMongo();
                        break;
                    case 3:
                        matriculas.mostrarMongo();
                        break;
                    default:
                        System.out.println("--Opción no valida");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\n###ERROR: ha introducido un valor que no es entero.");
            } catch (Exception ex) {
                System.out.println("\n###ERROR: ha ocurrido un error inesperado.\n" + ex);
            }
        } while (opcion > 0);
        return 0;
    }

    public void haz() {
        int opcion;
        System.out.println("Conectado con MongoDB");

        do {
            opcion = menu();
            realizarOpcion(opcion);
        } while (opcion != 0);
    }

    private void exportarDatosMongo() {
        System.out.println("\nEXPORTANDO...");
        try {
            if (!importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(importFile));

            // ALUMNO
            MongoCollection<Document> listaAlumnos = CRUD.todoAlumnoMongo();
            MongoCursor<Document> alumnos = listaAlumnos.find().iterator();
            while (alumnos.hasNext()) {
                Document alumno = alumnos.next();
                String nia = alumno.getString(CRUD.alumno_id);
                String nombre = alumno.getString(CRUD.alumno_nombre);
                writer.write("Alumno::" + nia + "::" + nombre + "\n");
                System.out.println("Exportando ALUMNO NIA: " + nia + " " + nombre);
            }
            alumnos.close();

            // MODULO
            MongoCollection<Document> listaModulos = CRUD.todoModuloMongo();
            MongoCursor<Document> modulos = listaModulos.find().iterator();
            while (modulos.hasNext()) {
                Document modulo = modulos.next();
                String id = modulo.getString(CRUD.modulo_id);
                String nombre = modulo.getString(CRUD.modulo_nombre);
                writer.write("Modulo::" + id + "::" + nombre + "\n");
                System.out.println("Exportando MODULO ID: " + id + " " + nombre);
            }
            modulos.close();

            // NOTAS
            MongoCollection<Document> listaNotas = CRUD.todoNotasMongo();
            MongoCursor<Document> notas = listaNotas.find().iterator();
            while (notas.hasNext()) {
                Document nota = notas.next();
                String id = nota.getString(CRUD.notas_id);
                int nota1 = nota.getInteger(CRUD.notas_nota1);
                int nota2 = nota.getInteger(CRUD.notas_nota2);
                int nota3 = nota.getInteger(CRUD.notas_nota3);
                writer.write("Notas::" + id + "::" + nota1 + "::" + nota2 + "::" + nota3 + "\n");
                System.out.println("Exportando NOTAS ID: " + id);
            }
            notas.close();

            // MATRICULA
            MongoCollection<Document> listaMatriculas = CRUD.todoMatriculaMongo();
            MongoCursor<Document> matriculas = listaMatriculas.find().iterator();
            while (matriculas.hasNext()) {
                Document matricula = matriculas.next();
                String id = matricula.getString(CRUD.matricula_id);
                String alumno = matricula.getString(CRUD.matricula_alumno_id);
                String modulo = matricula.getString(CRUD.matricula_modulo_id);
                String nota = matricula.getString(CRUD.matricula_notas_id);
                String calificacion = matricula.getString(CRUD.matricula_calificacion);
                writer.write(
                        "Matricula::" + id + "::" + alumno + "::" + modulo + "::" + nota + "::" + calificacion + "\n");
                System.out.println("Exportando MATRICULAS ID: " + id);
            }
            matriculas.close();

            writer.close();
        } catch (IOException ex) {
            System.out.println("Error de tipo IOException. Error al acceder al fichero o al excribir.\n" + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado exportando.\n" + ex);
        }
        System.out.println("Se han exportado los datos.");
    }

    private void importarDatosMongo() {
        System.out.println("\nIMPORTANDO...");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));

            String line;
            int lineNum = 0;

            if (!importFile.exists()) {
                System.out.println("Fichero de importe no existe. Cancelando...");
            } else {
                while ((line = reader.readLine()) != null) {
                    lineNum++;
                    String[] datos = line.split("::");

                    switch (datos[0]) {
                        case "Alumno":
                            System.out.println("Importando ALUMNO NIA: " + datos[1] + " " + datos[2]);
                            Document alumno = CRUD.buscarAlumnoIDMongo(datos[1]);
                            if (alumno != null) {
                                System.out.println("\nExiste un alumno con ese NIA");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarAlumnoMongo(datos[1], datos[2]);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarAlumnoMongo(datos[1], datos[2]);
                            }
                            break;

                        case "Modulo":
                            System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                            Document modulo = CRUD.buscarModuloIDMongo(datos[1]);
                            if (modulo != null) {
                                System.out.println("\nExiste un módulo con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarModuloMongo(datos[1], datos[2]);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarModuloMongo(datos[1], datos[2]);
                            }
                            break;

                        case "Notas":
                            System.out.println("Importando NOTAS ID: " + datos[1]);
                            Document notas = CRUD.buscarNotasIDMongo(datos[1]);
                            if (notas != null) {
                                System.out.println("\nExiste unas notas con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarNotaMongo(datos[1], Integer.parseInt(datos[2]),
                                                Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarNotasMongo(datos[1], Integer.parseInt(datos[2]),
                                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                            }
                            break;

                        case "Matricula":
                            System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: "
                                    + Integer.parseInt(datos[2])
                                    + " Modu: " + Integer.parseInt(datos[3])
                                    + " Notas: " + Integer.parseInt(datos[4]));
                            Document matricula = CRUD.buscarMatriculaIDMongo(datos[1]);
                            if (matricula != null) {
                                System.out.println("\nExiste una matricula con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarMatriculaMongo(
                                                datos[1],
                                                datos[2],
                                                datos[3],
                                                datos[4],
                                                datos[5]);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarMatriculaMongo(
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5]);
                            }
                            break;
                        default:
                            System.out.println("Formato de línea no admitida en la línea " + lineNum);
                    }
                }
                System.out.println("Se han importado los datos.");
            }

            reader.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error de tipo IOException. Error al acceder al fichero o al leer.\nPuede que el fichero no exista.\n"
                            + ex);
            return;
        } catch (Exception ex) {
            System.out.println("Error inesperado importando.\n" + ex);
        }
    }
}
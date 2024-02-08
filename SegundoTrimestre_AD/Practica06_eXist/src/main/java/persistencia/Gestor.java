package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

import org.bson.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import centro.Alumnos;
import centro.Matriculas;
import centro.Modulos;

import utilidades.App;
import utilidades.Lector;
import utilidades.CRUD;
import utilidades.CRUD_EXIST;

public class Gestor {

    private static Alumnos alumnos = new Alumnos();
    private static Modulos modulos = new Modulos();
    private static Matriculas matriculas = new Matriculas();
    public static final File importFile = new File("import.txt");

    private static Connection connection;
    private static MongoDatabase mongoDatabase;
    private static Collection existCollection;

    public Gestor() {
        if (App.getOpcion() == 3) {
            mongoDatabase = new Conexion().getMongoDatabase();
        } else if (App.getOpcion() == 4) {
            existCollection = new Conexion().getExistCollection();
        } else {
            connection = new Conexion().getConnection();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static Collection getExistCollection() {
        return existCollection;
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
            System.out.println("|2|-Mantener Módulos       |");
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
                if (App.getOpcion() == 3) {
                    exportarDatosMongo();
                } else if (App.getOpcion() == 4) {
                    exportarDatosExist();
                } else {
                    exportarDatos();
                }
                break;
            case 5:
                if (App.getOpcion() == 3) {
                    importarDatosMongo();
                } else if (App.getOpcion() == 4) {
                    importarDatosExist();
                } else {
                    importarDatos();
                }
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
                        if (App.getOpcion() == 3) {
                            alumnos.darDeAltaMongo();
                        } else if (App.getOpcion() == 4) {
                            alumnos.darDeAltaExist();
                        } else {
                            alumnos.darDeAlta();
                        }
                        break;
                    case 2:
                        if (App.getOpcion() == 3) {
                            alumnos.darDeBajaMongo();
                        } else if (App.getOpcion() == 4) {
                            alumnos.darDeBajaExist();
                        } else {
                            alumnos.darDeBaja();
                        }
                        break;
                    case 3:
                        if (App.getOpcion() == 3) {
                            alumnos.listarMongo();
                        } else if (App.getOpcion() == 4) {
                            alumnos.listarExist();
                        } else {
                            alumnos.listar();
                        }
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
                        if (App.getOpcion() == 3) {
                            modulos.darDeAltaMongo();
                        } else if (App.getOpcion() == 4) {
                            modulos.darDeAltaExist();
                        } else {
                            modulos.darDeAlta();
                        }
                        break;
                    case 2:
                        if (App.getOpcion() == 3) {
                            modulos.darDeBajaMongo();
                        } else if (App.getOpcion() == 4) {
                            modulos.darDeBajaExist();
                        } else {
                            modulos.darDeBaja();
                        }
                        break;
                    case 3:
                        if (App.getOpcion() == 3) {
                            modulos.listarMongo();
                        } else if (App.getOpcion() == 4) {
                            modulos.listarExist();
                        } else {
                            modulos.listar();
                        }
                        break;
                    case 4:
                        if (App.getOpcion() == 3) {
                            modulos.matricularAlumnoMongo();
                        } else if (App.getOpcion() == 4) {
                            modulos.matricularAlumnoExist();
                        } else {
                            modulos.matricularAlumno();
                        }
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
                        if (App.getOpcion() == 3) {
                            matriculas.modificarNotasMongo();
                        } else if (App.getOpcion() == 4) {
                            matriculas.modificarNotasExist();
                        } else {
                            matriculas.modificarNotas();
                        }
                        break;
                    case 2:
                        if (App.getOpcion() == 3) {
                            matriculas.evaluarModuloMongo();
                        } else if (App.getOpcion() == 4) {
                            matriculas.evaluarModuloExist();
                        } else {
                            matriculas.evaluarModulo();
                        }
                        break;
                    case 3:
                        if (App.getOpcion() == 3) {
                            matriculas.mostrarMongo();
                        } else if (App.getOpcion() == 4) {
                            matriculas.mostrarExist();
                        } else {
                            matriculas.mostrar();
                            ;
                        }
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
        String resultado;

        try {
            if (App.getOpcion() == 3) {
                System.out.println("Conectado con MongoDB");
            } else if (App.getOpcion() == 4) {
                System.out.println("Conectado con ExistDB");
            } else {
                resultado = connection.getMetaData().getDatabaseProductVersion();
                System.out.println("La versión que estás usando es: " + resultado);
            }

            do {
                opcion = menu();
                realizarOpcion(opcion);
                if (App.getOpcion() < 3) {
                    connection.close();
                } else if (App.getOpcion() == 4) {
                    existCollection.close();
                }
            } while (opcion != 0);
        } catch (SQLException e) {
            System.out.println("Error con la conexión a la BDD.");
            e.printStackTrace();
        } catch (XMLDBException e) {
            System.out.println("Error cerrando la colección de ExistDB");
            e.printStackTrace();
        }
    }

    private void exportarDatos() {
        System.out.println("\nEXPORTANDO...");

        try {
            if (!importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(importFile));

            // ALUMNO
            ResultSet queryAlu = CRUD.todoAlumno();
            while (queryAlu.next()) {
                String id = queryAlu.getString("alumno_nia");
                String nombre = queryAlu.getString("alumno_nombre");
                writer.write("Alumno::" + id + "::" + nombre + "\n");
                System.out.println("Exportando ALUMNO NIA: " + id + " " + nombre);
            }
            queryAlu.close();

            // MODULO
            ResultSet queryModu = CRUD.todoModulo();
            while (queryModu.next()) {
                String id = queryModu.getString("modulo_id");
                String nombre = queryModu.getString("modulo_nombre");
                writer.write("Modulo::" + id + "::" + nombre + "\n");
                System.out.println("Exportando MODULO ID: " + id + " " + nombre);
            }
            queryModu.close();

            // NOTAS
            ResultSet queryNota = CRUD.todoNotas();
            while (queryNota.next()) {
                String id = queryNota.getString("notas_id");
                int nota1 = queryNota.getInt("nota1");
                int nota2 = queryNota.getInt("nota2");
                int nota3 = queryNota.getInt("nota3");
                writer.write("Notas::" + id + "::" + nota1 + "::" + nota2 + "::" + nota3 + "\n");
                System.out.println("Exportando NOTAS ID: " + id);
            }
            queryNota.close();

            // MATRICULA
            ResultSet queryMatri = CRUD.todoMatricula();
            while (queryMatri.next()) {
                String id = queryMatri.getString("matricula_id");
                String alumno = queryMatri.getString("alumno_nia");
                String modulo = queryMatri.getString("modulo_id");
                String nota = queryMatri.getString("notas_id");
                String calificacion = queryMatri.getString("calificacion");
                writer.write(
                        "Matricula::" + id + "::" + alumno + "::" + modulo + "::" + nota + "::" + calificacion + "\n");
                System.out.println("Exportando MATRICULAS ID: " + id);
            }
            queryMatri.close();

            writer.close();
        } catch (IOException ex) {
            System.out.println("Error de tipo IOException. Error al acceder al fichero o al excribir.\n" + ex);
        } catch (SQLException ex) {
            System.out.println("Error de SQL.\n" + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado exportando.\n" + ex);
        }
        System.out.println("Se han exportado los datos.");
    }

    private void importarDatos() {
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
                            ResultSet rsAlu = CRUD.buscarAlumnoID(datos[1]);
                            if (rsAlu.next()) {
                                System.out.println("Existe un alumno con ese NIA");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarAlumno(datos[1], datos[2]);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarAlumno(datos[1], datos[2]);
                            }
                            rsAlu.close();
                            break;
                        case "Modulo":
                            System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                            ResultSet rsMod = CRUD.buscarModuloID(datos[1]);
                            if (rsMod.next()) {
                                System.out.println("Existe un módulo con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarModulo(datos[1], datos[2]);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarModulo(datos[1], datos[2]);
                            }
                            rsMod.close();
                            break;
                        case "Notas":
                            System.out.println("Importando NOTAS ID: " + datos[1]);
                            ResultSet rsNot = CRUD.buscaNotaID(datos[1]);
                            if (rsNot.next()) {
                                System.out.println("Existe unas notas con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarNota(datos[1], Integer.parseInt(datos[2]),
                                                Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                CRUD.insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                            }
                            rsNot.close();
                            break;
                        case "Matricula":
                            System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: "
                                    + Integer.parseInt(datos[2])
                                    + " Modu: " + Integer.parseInt(datos[3])
                                    + " Notas: " + Integer.parseInt(datos[4]));
                            ResultSet rsMat = CRUD.buscarMatriculaID(datos[1]);
                            if (rsMat.next()) {
                                System.out.println("Existe una matricula con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        CRUD.actualizarMatricula(
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
                                CRUD.insertarMatricula(
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5]);
                            }
                            rsMat.close();
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

    private void exportarDatosExist() {
        System.out.println("\nEXPORTANDO...");
        try {
            if (!importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(importFile));

            // ALUMNO
            ResourceSet alumnos = CRUD_EXIST.recuperarLista(CRUD_EXIST.alumno_tabla);
            ResourceIterator listaAlumnos = alumnos.getIterator();
            while (listaAlumnos.hasMoreResources()) {
                XMLResource resource = (XMLResource) listaAlumnos.nextResource();
                Element alumno = (Element) resource.getContentAsDOM();
                String nia = alumno.getElementsByTagName(CRUD_EXIST.alumno_id).item(0).getTextContent();
                String nombre = alumno.getElementsByTagName(CRUD_EXIST.alumno_nombre).item(0).getTextContent();

                writer.write("Alumno::" + nia + "::" + nombre + "\n");
                System.out.println("Exportando ALUMNO NIA: " + nia + " " + nombre);
            }

            // MODULO
            ResourceSet modulos = CRUD_EXIST.recuperarLista(CRUD_EXIST.modulo_tabla);
            ResourceIterator listaModulos = modulos.getIterator();
            while (listaModulos.hasMoreResources()) {
                XMLResource resource = (XMLResource) listaModulos.nextResource();
                Element modulo = (Element) resource.getContentAsDOM();
                String id = modulo.getElementsByTagName(CRUD_EXIST.modulo_id).item(0).getTextContent();
                String nombre = modulo.getElementsByTagName(CRUD_EXIST.modulo_nombre).item(0).getTextContent();

                writer.write("Modulo::" + id + "::" + nombre + "\n");
                System.out.println("Exportando MODULO ID: " + id + " " + nombre);
            }

            // NOTAS
            ResourceSet notas = CRUD_EXIST.recuperarLista(CRUD_EXIST.modulo_tabla);
            ResourceIterator listaNotas = notas.getIterator();
            while (listaNotas.hasMoreResources()) {
                XMLResource resource = (XMLResource) listaNotas.nextResource();
                Element nota = (Element) resource.getContentAsDOM();
                String id = nota.getElementsByTagName(CRUD_EXIST.modulo_id).item(0).getTextContent();
                String nota1 = nota.getElementsByTagName(CRUD_EXIST.notas_nota1).item(0).getTextContent();
                String nota2 = nota.getElementsByTagName(CRUD_EXIST.notas_nota2).item(0).getTextContent();
                String nota3 = nota.getElementsByTagName(CRUD_EXIST.notas_nota3).item(0).getTextContent();
                writer.write("Notas::" + id + "::" + nota1 + "::" + nota2 + "::" + nota3 + "\n");
                System.out.println("Exportando NOTAS ID: " + id);
            }

            // MATRICULA
            ResourceSet matriculas = CRUD_EXIST.recuperarLista(CRUD_EXIST.modulo_tabla);
            ResourceIterator listaMatriculas = matriculas.getIterator();
            while (listaMatriculas.hasMoreResources()) {
                XMLResource resource = (XMLResource) listaMatriculas.nextResource();
                Element matricula = (Element) resource.getContentAsDOM();
                String id = matricula.getElementsByTagName(CRUD_EXIST.modulo_id).item(0).getTextContent();
                String alumno = matricula.getElementsByTagName(CRUD_EXIST.matricula_alumno_id).item(0).getTextContent();
                String modulo = matricula.getElementsByTagName(CRUD_EXIST.matricula_modulo_id).item(0).getTextContent();
                String nota = matricula.getElementsByTagName(CRUD_EXIST.matricula_notas_id).item(0).getTextContent();
                String calificacion = matricula.getElementsByTagName(CRUD_EXIST.matricula_calificacion).item(0)
                        .getTextContent();
                writer.write(
                        "Matricula::" + id + "::" + alumno + "::" + modulo + "::" + nota + "::" + calificacion + "\n");
                System.out.println("Exportando MATRICULAS ID: " + id);
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error de tipo IOException. Error al acceder al fichero o al excribir.\n" + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado exportando.\n" + ex);
        }
        System.out.println("Se han exportado los datos.");
    }

    private void importarDatosExist() {
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
                            Element alumno = CRUD_EXIST.buscarAlumnoID(datos[1]);
                            if (alumno != null) {
                                System.out.println("\nExiste un alumno con ese NIA");
                                switch (menuImport()) {
                                    case 1:
                                        if (!CRUD_EXIST.actualizarAlumno(datos[1], datos[2])) {
                                            System.out.println("No se pudo actualizar: " + line);
                                        }
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                if (!CRUD_EXIST.insertarAlumno(datos[1], datos[2]))
                                    System.out.println("No se pudo importar: " + line);
                            }
                            break;

                        case "Modulo":
                            System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                            Element modulo = CRUD_EXIST.buscarModuloID(datos[1]);
                            if (modulo != null) {
                                System.out.println("\nExiste un módulo con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        if (!CRUD_EXIST.actualizarModulo(datos[1], datos[2]))
                                            System.out.println("No se pudo actualizar: " + line);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                if (!CRUD_EXIST.insertarModulo(datos[1], datos[2]))
                                    System.out.println("No se pudo importar: " + line);
                            }
                            break;

                        case "Notas":
                            System.out.println("Importando NOTAS ID: " + datos[1]);
                            Element notas = CRUD_EXIST.buscarNotaID(datos[1]);
                            if (notas != null) {
                                System.out.println("\nExiste unas notas con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        if (!CRUD_EXIST.actualizarNota(datos[1], Integer.parseInt(datos[2]),
                                                Integer.parseInt(datos[3]), Integer.parseInt(datos[4])))
                                            System.out.println("No se pudo actualizar: " + line);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                if (!CRUD_EXIST.insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4])))
                                    System.out.println("No se pudo importar: " + line);
                            }
                            break;

                        case "Matricula":
                            System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: "
                                    + Integer.parseInt(datos[2])
                                    + " Modu: " + Integer.parseInt(datos[3])
                                    + " Notas: " + Integer.parseInt(datos[4]));
                            Element matricula = CRUD_EXIST.buscarMatriculaID(datos[1]);
                            if (matricula != null) {
                                System.out.println("\nExiste una matricula con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        if (!CRUD_EXIST.actualizarMatricula(
                                                datos[1],
                                                datos[2],
                                                datos[3],
                                                datos[4],
                                                datos[5]))
                                            System.out.println("No se pudo actualizar: " + line);
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                if (!CRUD_EXIST.insertarMatricula(
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5]))
                                    System.out.println("No se pudo importar: " + line);
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
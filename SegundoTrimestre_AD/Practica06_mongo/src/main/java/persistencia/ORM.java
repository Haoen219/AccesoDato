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

import com.mongodb.client.MongoDatabase;

import centro.Alumnos;
import centro.Matriculas;
import centro.Modulos;

import utilidades.App;
import utilidades.Lector;

public class ORM {
    // BUSCAR
    public static String todoAlumno = "SELECT * FROM alumno";
    public static String todoModulo = "SELECT * FROM modulo";
    public static String todoMatricula = "SELECT * FROM matricula";
    public static String todoNotas = "SELECT * FROM notas";

    public static String buscarAlumnoID(String id) {
        return "SELECT * FROM alumno WHERE alumno_nia = '" + id + "'";
    }

    public static String buscarModuloID(String id) {
        return "SELECT * FROM modulo WHERE modulo_id = '" + id + "'";
    }

    public static String buscarMatriculaID(String id) {
        return "SELECT * FROM matricula WHERE matricula_id = '" + id + "'";
    }

    public static String buscaNotaID(String id) {
        return "SELECT * FROM notas WHERE notas_id = '" + id + "'";
    }

    public static String buscarMatriculaAluID(String id) {
        return "SELECT * FROM matricula WHERE alumno_nia = '" + id + "'";
    }

    public static String buscarMatriculaModID(String id) {
        return "SELECT * FROM matricula WHERE modulo_id = '" + id + "'";
    }

    public static String buscarMatriculaDobleID(String nia, String id) {
        return "SELECT * FROM matricula WHERE modulo_id = '" + id + "' AND alumno_nia = '" + nia + "'";
    }

    // INSERTAR
    public static String insertarAlumno(String id, String nombre) {
        return "INSERT INTO alumno (alumno_nia, alumno_nombre) VALUES ('" + id + "', '" + nombre + "')";
    }

    public static String insertarModulo(String id, String nombre) {
        return "INSERT INTO modulo (modulo_id, modulo_nombre) VALUES ('" + id + "', '" + nombre + "')";
    }

    public static String insertarNotas(String id, int nota1, int nota2, int nota3) {
        return "INSERT INTO notas (notas_id, nota1, nota2, nota3) VALUES ('" + id + "', " + nota1 + ", " + nota2
                + ", " + nota3 + ")";
    }

    public static String insertarMatricula(String id, String alumno, String modulo, String nota, String calificacion) {
        return "INSERT INTO matricula (matricula_id, alumno_nia, modulo_id, notas_id, calificacion) VALUES ('"
                + id + "', '" + alumno + "', '" + modulo + "', '" + nota + "', '" + calificacion + "')";
    }

    // BORRAR
    public static String borrarAlumno(String id) {
        return "DELETE FROM alumno WHERE alumno_nia = '" + id + "'";
    }

    public static String borrarModulo(String id) {
        return "DELETE FROM modulo WHERE modulo_id = '" + id + "'";
    }

    public static String borrarNotas(String id) {
        return "DELETE FROM notas WHERE notas_id = '" + id + "'";
    }

    public static String borrarMatricula(String id) {
        return "DELETE FROM matricula WHERE matricula_id = '" + id + "'";
    }

    // ACTUALIZAR
    public static String actualizarNota(String id, int nota1, int nota2, int nota3) {
        return "UPDATE notas SET nota1 = " + nota1 + ", nota2 = " + nota2 + ", nota3 =" + nota3 + " WHERE notas_id = '"
                + id + "'";
    }

    public static String actualizarMatricula(String id, String calificacion) {
        return "UPDATE matricula SET calificacion = '" + calificacion + "' WHERE matricula_id = '" + id + "'";
    }

    private static Alumnos alumnos = new Alumnos();
    private static Modulos modulos = new Modulos();
    private static Matriculas matriculas = new Matriculas();
    public static final File importFile = new File("import.txt");
    
    private Connection connection;
    private MongoDatabase mongoDatabase;

    public ORM (){
        if (App.getOpcion() == 3) {
            mongoDatabase = new Conexion().getMongoDatabase();
        } else{
            connection = new Conexion().getConnection();
        }
    }

    

    public Connection getConnection() {
        return connection;
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
        System.out.println("");
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
                alumnos.cerrarConex();
                modulos.cerrarConex();
                matriculas.cerrarConex();
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
                exportarDatos();
                break;
            case 5:
                importarDatos();
                break;
            default:
                System.out.println(App.ANSI_CYAN + "Hay que elegit una de las opciones (numero entre parentesis)"
                        + App.ANSI_WHITE);
        }
    }

    private static int menuAlumnos() {
        int opcion = 0;
        do {
            try {
                opcion = alumnos.menu();
                switch (opcion) {
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> alumnos.darDeAlta();
                    case 2 -> alumnos.darDeBaja();
                    case 3 -> alumnos.listar();
                    default -> System.out.println("--Opción no valida");
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
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> modulos.darDeAlta();
                    case 2 -> modulos.darDeBaja();
                    case 3 -> modulos.listar();
                    case 4 -> modulos.matricularAlumno();
                    default -> System.out.println("--Opción no valida");
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
                    case 0 -> System.out.println("Volviendo al menu previo...\n");
                    case 1 -> matriculas.modificarNotas();
                    case 2 -> matriculas.evaluarModulo();
                    case 3 -> matriculas.mostrar();
                    default -> System.out.println("--Opción no valida");
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
            // connection = new Conexion().getConnection();

            resultado = connection.getMetaData().getDatabaseProductVersion();
            System.out.println("La versión que estás usando es: " + resultado);

            do {
                opcion = menu();
                realizarOpcion(opcion);
                if (opcion == 0) {
                    connection.close();
                }
            } while (opcion != 0);
        } catch (SQLException e) {
            System.out.println("Error con la conexión a la BDD.");
            e.printStackTrace();
        }
    }

    private void exportarDatos() {
        System.out.println("\nEXPORTANDO...");

        try {
            if (!importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(ORM.importFile));

            // ALUMNO
            ResultSet queryAlu = connection.createStatement().executeQuery(todoAlumno);
            while (queryAlu.next()) {
                String id = queryAlu.getString("alumno_nia");
                String nombre = queryAlu.getString("alumno_nombre");
                writer.write("Alumno::" + id + "::" + nombre + "\n");
                System.out.println("Exportando ALUMNO NIA: " + id + " " + nombre);
            }
            queryAlu.close();

            // MODULO
            ResultSet queryModu = connection.createStatement().executeQuery(todoModulo);
            while (queryModu.next()) {
                String id = queryModu.getString("modulo_id");
                String nombre = queryModu.getString("modulo_nombre");
                writer.write("Modulo::" + id + "::" + nombre + "\n");
                System.out.println("Exportando MODULO ID: " + id + " " + nombre);
            }
            queryModu.close();

            // NOTAS
            ResultSet queryNota = connection.createStatement().executeQuery(todoNotas);
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
            ResultSet queryMatri = connection.createStatement().executeQuery(todoMatricula);
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
            System.out.println("Error inesperado.\n" + ex);
        }
        System.out.println("Se han exportado los datos.");
    }

    /*
     * Alumno: Alumno::NIA::NOMBRE
     * Modulo: Modulo::ID::NOMBRE
     * Notas: Notas::ID::NOTA01::NOTA02::NOTA03
     * Matricula: Matricula::ID::ID_ALU::ID_MODU::ID_NOTA::CALIFICACION
     */
    private void importarDatos() {
        System.out.println("\nIMPORTANDO...");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));
            Connection conect = new ORM().getConnection();

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
                            ResultSet rsAlu = conect.createStatement().executeQuery(buscarAlumnoID(datos[1]));
                            if (rsAlu.next()) {
                                System.out.println("Existe un alumno con ese NIA");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarAlumno(datos[1], datos[2]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarAlumno(datos[1], datos[2]));
                            }
                            rsAlu.close();
                            break;
                        case "Modulo":
                            System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                            ResultSet rsMod = conect.createStatement().executeQuery(buscarModuloID(datos[1]));
                            if (rsMod.next()) {
                                System.out.println("Existe un módulo con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarModulo(datos[1], datos[2]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarModulo(datos[1], datos[2]));
                            }
                            rsMod.close();
                            break;
                        case "Notas":
                            System.out.println("Importando NOTAS ID: " + datos[1]);
                            ResultSet rsNot = conect.createStatement().executeQuery(buscaNotaID(datos[1]));
                            if (rsNot.next()) {
                                System.out.println("Existe unas notas con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement()
                                                .execute(insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement()
                                        .execute(insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                                Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                            }
                            rsNot.close();
                            break;
                        case "Matricula":
                            System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: "
                                    + Integer.parseInt(datos[2])
                                    + " Modu: " + Integer.parseInt(datos[3])
                                    + " Notas: " + Integer.parseInt(datos[4]));
                            ResultSet rsMat = conect.createStatement().executeQuery(buscarMatriculaID(datos[1]));
                            if (rsMat.next()) {
                                System.out.println("Existe una matricula con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarMatricula(
                                                datos[1],
                                                datos[2],
                                                datos[3],
                                                datos[4],
                                                datos[5]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarMatricula(
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5]));
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
            conect.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error de tipo IOException. Error al acceder al fichero o al leer.\nPuede que el fichero no exista.\n"
                            + ex);
            return;
        } catch (Exception ex) {
            System.out.println("Error inesperado.\n" + ex);
        }
    }

    private void exportarDatosMongo() {
        System.out.println("\nEXPORTANDO...");

        try {
            if (!importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(ORM.importFile));

            // ALUMNO
            ResultSet queryAlu = connection.createStatement().executeQuery(todoAlumno);
            while (queryAlu.next()) {
                String id = queryAlu.getString("alumno_nia");
                String nombre = queryAlu.getString("alumno_nombre");
                writer.write("Alumno::" + id + "::" + nombre + "\n");
                System.out.println("Exportando ALUMNO NIA: " + id + " " + nombre);
            }
            queryAlu.close();

            // MODULO
            ResultSet queryModu = connection.createStatement().executeQuery(todoModulo);
            while (queryModu.next()) {
                String id = queryModu.getString("modulo_id");
                String nombre = queryModu.getString("modulo_nombre");
                writer.write("Modulo::" + id + "::" + nombre + "\n");
                System.out.println("Exportando MODULO ID: " + id + " " + nombre);
            }
            queryModu.close();

            // NOTAS
            ResultSet queryNota = connection.createStatement().executeQuery(todoNotas);
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
            ResultSet queryMatri = connection.createStatement().executeQuery(todoMatricula);
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
            System.out.println("Error inesperado.\n" + ex);
        }
        System.out.println("Se han exportado los datos.");
    }

    /*
     * Alumno: Alumno::NIA::NOMBRE
     * Modulo: Modulo::ID::NOMBRE
     * Notas: Notas::ID::NOTA01::NOTA02::NOTA03
     * Matricula: Matricula::ID::ID_ALU::ID_MODU::ID_NOTA::CALIFICACION
     */
    private void importarDatosMongo() {
        System.out.println("\nIMPORTANDO...");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));
            Connection conect = new ORM().getConnection();

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
                            ResultSet rsAlu = conect.createStatement().executeQuery(buscarAlumnoID(datos[1]));
                            if (rsAlu.next()) {
                                System.out.println("Existe un alumno con ese NIA");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarAlumno(datos[1], datos[2]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarAlumno(datos[1], datos[2]));
                            }
                            rsAlu.close();
                            break;
                        case "Modulo":
                            System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                            ResultSet rsMod = conect.createStatement().executeQuery(buscarModuloID(datos[1]));
                            if (rsMod.next()) {
                                System.out.println("Existe un módulo con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarModulo(datos[1], datos[2]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarModulo(datos[1], datos[2]));
                            }
                            rsMod.close();
                            break;
                        case "Notas":
                            System.out.println("Importando NOTAS ID: " + datos[1]);
                            ResultSet rsNot = conect.createStatement().executeQuery(buscaNotaID(datos[1]));
                            if (rsNot.next()) {
                                System.out.println("Existe unas notas con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement()
                                                .execute(insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement()
                                        .execute(insertarNotas(datos[1], Integer.parseInt(datos[2]),
                                                Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                            }
                            rsNot.close();
                            break;
                        case "Matricula":
                            System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: "
                                    + Integer.parseInt(datos[2])
                                    + " Modu: " + Integer.parseInt(datos[3])
                                    + " Notas: " + Integer.parseInt(datos[4]));
                            ResultSet rsMat = conect.createStatement().executeQuery(buscarMatriculaID(datos[1]));
                            if (rsMat.next()) {
                                System.out.println("Existe una matricula con ese ID");
                                switch (menuImport()) {
                                    case 1:
                                        conect.createStatement().execute(insertarMatricula(
                                                datos[1],
                                                datos[2],
                                                datos[3],
                                                datos[4],
                                                datos[5]));
                                        break;
                                    case 2:
                                        System.out.println("\tNo se importará");
                                        break;
                                }
                            } else {
                                conect.createStatement().execute(insertarMatricula(
                                        datos[1],
                                        datos[2],
                                        datos[3],
                                        datos[4],
                                        datos[5]));
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
            conect.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error de tipo IOException. Error al acceder al fichero o al leer.\nPuede que el fichero no exista.\n"
                            + ex);
            return;
        } catch (Exception ex) {
            System.out.println("Error inesperado.\n" + ex);
        }
    }
}
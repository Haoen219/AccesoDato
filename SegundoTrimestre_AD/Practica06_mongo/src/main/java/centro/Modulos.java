/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import persistencia.ORM;
import utilidades.Lector;
import utilidades.SQL;

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

    // MODULO
    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");

        while (seguir) {
            try {
                ResultSet queryMod = SQL.todoModulo();
                String id = "";
                try {
                    //HAY QUE COMPROBAR FUNCIONAMIENTO
                    queryMod.last();
                    id = queryMod.getString(SQL.modulo_id)+1;
                } catch (SQLException ex) {
                    System.out.println("Error retirando lista de modulos para asignar último ID\n" + ex);
                }
                queryMod.close();

                System.out.print("Introduzca el nombre del modulo: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                    if (SQL.insertarModulo(id, nombre)){
                        System.out.println("\tID:" + id + " " + nombre + " dado de alta.");
                    }else{
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
                    ResultSet queryMod = SQL.buscarModuloID(id);

                    if (!queryMod.next()) {
                        System.out.println("-No existe un módulo con ese ID");
                    } else {
                        try {
                            String nombre = queryMod.getString(SQL.modulo_nombre);
                            ResultSet queryMat = SQL.buscarMatriculaModID(id);

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString(SQL.matricula_id);
                                String idNotas = queryMat.getString(SQL.notas_id);
                                if (!SQL.borrarMatricula(idMatri)) System.out.println("No se ha borrar matricula ID:"+idMatri);
                                if (SQL.borrarNotas(idNotas)) System.out.println("No se ha borrar notas ID:"+idNotas);
                            }
                            if (SQL.borrarModulo(id)){
                                System.out.println("\tID: " + id + " " + nombre + " dado de baja.");
                            }else{
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

    // MATRICULA
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
                    ResultSet alumno = SQL.buscarAlumnoID(nia);

                    if (alumno.next()) {
                        String nombreAlu = alumno.getString("alumno_nombre");
                        String id = "";
                        System.out.print("Introduzca ID del módulo: ");
                        id = sc.leer();

                        if (id.equals("0")) {
                            seguir = false;
                        } else {
                            try {
                                ResultSet modulo = SQL.buscarModuloID(id);

                                if (modulo.next()) {
                                    String moduloNombre = modulo.getString(SQL.modulo_nombre);
                                    String calificacion = "Sin calificar";

                                    int matriNum = 0;
                                    int notasNum = 0;
                                    ResultSet matricula = SQL.todoMatricula();
                                    ResultSet notas = SQL.todoNotas();

                                    matricula.last();
                                    notas.last();
                                    matriNum = matricula.getInt(SQL.matricula_id);
                                    notasNum = notas.getInt(SQL.notas_id);

                                    SQL.insertarNotas(Integer.toString(notasNum+1), 0, 0, 0);
                                    SQL.insertarMatricula(Integer.toString(matriNum+1), nia, id, Integer.toString(notasNum+1), calificacion);

                                    System.out.println("Se creado matricula de " + nombreAlu + " en el módulo " + moduloNombre);

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

    // IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar Modulos-");
        try {
            ResultSet modulos = SQL.todoModulo();

            if (modulos.next()) {
                modulos = SQL.todoModulo();
                while (modulos.next()) {
                    int numMatri = 0;
                    String id = modulos.getString("modulo_id");
                    String nombre = modulos.getString("modulo_nombre");
                    try {
                        ResultSet matriculas = SQL.buscarMatriculaModID(id);
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (Exception ex) {
                        System.out.println(
                                "Error cerrando el ResultSet de matriculas"+ex);
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









    public int darDeAltaMongo() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta módulo-  (volver con [0])");

        while (seguir) {
            MongoCursor<Document> modulos = SQL.todoAlumnoMongo().find().sort(new Document(SQL.modulo_id, 1)).iterator();

            String id = "";
            Document modulo = new Document();
            try {
                while (modulos.hasNext()) modulo = modulos.next();
                id = modulo.getString(SQL.modulo_id);
                modulos.close();

                System.out.print("Introduzca el nombre del modulo: ");
                String nombre = sc.leer();

                if (nombre.equalsIgnoreCase("0")) {
                    seguir = false;
                } else {
                        if (!SQL.insertarModuloMongo(id, nombre)){
                            System.out.println("\tID:" + id + " " + nombre + " dado de alta.");
                        } else{
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
                    Document modulo = SQL.buscarModuloIDMongo(id);

                    if ( modulo == null ) {
                        System.out.println("-No existe un módulo con ese ID");
                    } else {
                        try {
                            String nombre = queryMod.getString("modulo_nombre");
                            ResultSet queryMat = ORM.getConnection().createStatement()
                                    .executeQuery(ORM.buscarMatriculaModID(id));

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString("matricula_id");
                                String idNotas = queryMat.getString("notas_id");
                                try {
                                    ORM.getConnection().createStatement().execute(ORM.borrarMatricula(idMatri));
                                    ORM.getConnection().createStatement().execute(ORM.borrarNotas(idNotas));
                                } catch (SQLException ex) {
                                    System.out.println("Error borrando notas/matricula del modulo. (id_Matricula:"
                                            + idMatri + ")\n" + ex);
                                }
                            }
                            ORM.getConnection().createStatement().execute(ORM.borrarModulo(id));
                            queryMat.close();
                            queryMod.close();
                            System.out.println("\tID: " + id + " " + nombre + " dado de baja.");
                        } catch (SQLException ex) {
                            System.out.println("Error buscando las notas/matricula en la base.\n" + ex);
                        }
                    }
            }
        }
        return 0;
    }

    // MATRICULA
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
                try {
                    ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));

                    if (alumno.next()) {
                        String nombreAlu = alumno.getString("alumno_nombre");
                        String id = "";
                        System.out.print("Introduzca ID del módulo: ");
                        id = sc.leer();

                        if (id.equals("0")) {
                            seguir = false;
                        } else {
                            try {
                                ResultSet modulo = ORM.getConnection().createStatement()
                                        .executeQuery(ORM.buscarModuloID(id));

                                if (modulo.next()) {
                                    String moduloNombre = modulo.getString("modulo_nombre");
                                    String calificacion = "Sin calificar";

                                    int matriNum = 0;
                                    int notasNum = 0;
                                    ResultSet matricula = ORM.getConnection().createStatement().executeQuery(ORM.todoMatricula);
                                    ResultSet notas = ORM.getConnection().createStatement() .executeQuery(ORM.todoNotas);

                                    while (matricula.next()) matriNum++;
                                    while (notas.next()) notasNum++;

                                    ORM.getConnection().createStatement().execute(ORM.insertarNotas(Integer.toString(notasNum+1), 0, 0, 0));
                                    ORM.getConnection().createStatement().execute(ORM.insertarMatricula(Integer.toString(matriNum+1), nia, id, Integer.toString(notasNum+1), calificacion));

                                    System.out.println("Se creado matricula de " + nombreAlu + " en el módulo " + moduloNombre);

                                    matricula.close();
                                    notas.close();
                                } else {
                                    System.out.println("-No existe un Modulo con este ID.");
                                }
                                modulo.close();
                            } catch (SQLException ex) {
                                System.out.println("Error buscando matriculas.\n" + ex);
                            }
                        }
                    } else {
                        System.out.println("-El alumno no existe.");
                    }
                    alumno.close();
                } catch (SQLException ex) {
                    System.out.println("Error buscando alumno.\n" + ex);
                }
            }
        }
        return 0;

    }

    // IMPRIMIR
    public void listarMongo() {
        System.out.println("\n-Listar Modulos-");
        try {
            ResultSet modulos = ORM.getConnection().createStatement().executeQuery(ORM.todoModulo);

            if (modulos.next()) {
                modulos = ORM.getConnection().createStatement().executeQuery(ORM.todoModulo);
                while (modulos.next()) {
                    int numMatri = 0;
                    String id = modulos.getString("modulo_id");
                    String nombre = modulos.getString("modulo_nombre");
                    try {
                        ResultSet matriculas = ORM.getConnection().createStatement()
                                .executeQuery(ORM.buscarMatriculaModID(id));
                        while (matriculas.next()) {
                            numMatri++;
                        }
                        matriculas.close();
                    } catch (Exception ex) {
                        System.out.println(
                                "Error de SQL al buscar las matriculas del modulo ." + nombre + " ID: " + id + "\n"
                                        + ex);
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
            System.out.println("Error de SQL al retirar todos los modulos\n" + ex);
        }
    }
}

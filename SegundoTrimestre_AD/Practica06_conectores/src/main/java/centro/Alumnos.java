/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistencia.ORM;
import utilidades.Lector;

/**
 *
 * @author haoen
 */
public class Alumnos {

    private static Connection conect = new ORM().getConnection();

    public Alumnos() {
    }

    public int darDeAlta() {
        Lector sc = new Lector(System.in);
        boolean seguir = true;
        System.out.println("\n-Dar de alta alumno-  (volver con [0])");

        
        while (seguir) {
            System.out.print("Introduzca el NIA del alumno: ");
            String nia = sc.leer();
            
            ResultSet queryAlu = null;
            try {
                queryAlu = conect.createStatement().executeQuery(ORM.buscarAlumnoID(nia));

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
                        try {
                            conect.createStatement().execute(ORM.insertarAlumno(nia, nombre));
                            System.out.println("\tNIA:" + nia + " " + nombre + " dado de alta.\n");
                        } catch (SQLException ex) {
                            System.out.println("Error insertando el alumno\n" + ex);
                        }
                    }
                }
                
                queryAlu.close();
            } catch (SQLException ex) {
                System.out.println("Error buscando alumno con ese NIA\n" + ex);
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
                try {
                    ResultSet queryAlu = conect.createStatement().executeQuery(ORM.buscarAlumnoID(nia));

                    if (!queryAlu.next()) {
                        System.out.println("-No existe un alumno con ese NIA");
                    } else {
                        try {
                            String nombre = queryAlu.getString("alumno_nombre");
                            ResultSet queryMat = conect.createStatement()
                                    .executeQuery(ORM.buscarMatriculaAluID(nia));

                            while (queryMat.next()) {
                                String idMatri = queryMat.getString("matricula_id");
                                String idNotas = queryMat.getString("notas_id");
                                try {
                                    conect.createStatement().executeQuery(ORM.borrarNotas(idNotas));
                                    conect.createStatement().executeQuery(ORM.borrarNotas(idMatri));
                                } catch (SQLException ex) {
                                    System.out.println("Error borrando notas/matricula del alumno.\n" + ex);
                                }

                            }
                            queryMat.close();
                            queryAlu.close();
                            System.out.println("\tNIA: " + nia + " " + nombre + " dado de baja.");
                        } catch (SQLException ex) {
                            System.out.println("Error buscando las notas/matricula en la base.\n" + ex);
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Error buscando el alumno en la base.\n" + ex);
                }
            }
        }
        return 0;
    }

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

    // IMPRIMIR
    public void listar() {
        System.out.print("\n-Listar Alumnos-");
        try {
            ResultSet alumnos = conect.createStatement().executeQuery(ORM.todoAlumno);

            if (alumnos.next()) {
                alumnos = conect.createStatement().executeQuery(ORM.todoAlumno);
                while (alumnos.next()) {
                    int numMatri = 0;
                    String nia = alumnos.getString("alumno_nia");
                    String nombre = alumnos.getString("alumno_nombre");
                    try {
                        ResultSet matriculas = conect.createStatement()
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

    public void cerrarConex(){
        try{
            conect.close();
        } catch (SQLException ex){
            System.out.println("Error cerrando el conector de Alumnos.");
            System.out.println(ex);
        }
    }
}

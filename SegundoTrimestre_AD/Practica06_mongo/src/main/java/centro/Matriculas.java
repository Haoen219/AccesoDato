package centro;

import java.sql.ResultSet;
import java.sql.SQLException;

import persistencia.ORM;
import utilidades.Lector;

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
        try (ResultSet matriculas = ORM.getConnection().createStatement().executeQuery(ORM.todoMatricula)) {
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
                        ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString("alumno_nombre");
                            System.out.println(nombreAlu + ":");
                            System.out.println("----");

                            ResultSet matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                            if (!matriculas.next()) {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            } else {
                                matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                                imprimirAlumno(matriculas);
                            }

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscarMatriculaDobleID(nia, id));
                                        if (matricula.next()) {
                                            String notasId = matricula.getString("notas_id");

                                            ResultSet notas = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscaNotaID(notasId));

                                            notas.next();


                                            Lector sc = new Lector(System.in);
                                            int nota1 = 0;
                                            int nota2 = 0;
                                            int nota3 = 0;

                                            System.out.println("Introduzca las notas por orden: ");
                                            System.out.print("Nota 1: ");
                                            nota1 = sc.leerEntero(0, 10);
                                            if(nota1 < 0 || nota1 >10) nota1 = notas.getInt("nota1");
                                            System.out.print("Nota 2: ");
                                            nota2 = sc.leerEntero(0, 10);
                                            if(nota2 < 0 || nota2 >10) nota2 = notas.getInt("nota2");
                                            System.out.print("Nota 3: ");
                                            nota3 = sc.leerEntero(0, 10);
                                            if(nota3 < 0 || nota3 >10) nota3 = notas.getInt("nota3");

                                            ORM.getConnection().createStatement()
                                                    .execute(ORM.actualizarNota(notasId, nota1, nota2, nota3));
                                            System.out.println("Modificaciones añadido a la espera.");

                                            notas.close();
                                        } else {
                                            System.out.println("-No existe matricula con este modulo.");
                                        }
                                        matricula.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Error buscando matriculas.\n" + ex);
                                    }
                                }
                            }
                            matriculas.close();
                        } else {
                            seguirAlumno = false;
                            System.out.println("-El alumno no existe.");
                        }
                        alumno.close();
                    } catch (SQLException ex) {
                        System.out.println("Error buscando alumno.\n" + ex);
                    }
                }
            }
            System.out.println("Se han guardado las modificaciones.");
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
                        ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));
                        ResultSet matriculas = ORM.getConnection().createStatement()
                                .executeQuery(ORM.buscarMatriculaAluID(nia));

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString("alumno_nombre");
                            System.out.println(nombreAlu + ":");
                            System.out.println("----");
                            if (matriculas.next()) {
                                matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                                imprimirAlumno(matriculas);
                            } else {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            }

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscarMatriculaDobleID(nia, id));

                                        if (matricula.next()) {
                                            String matriculaId = matricula.getString("matricula_id");
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

                                            ORM.getConnection().createStatement()
                                                    .execute(ORM.actualizarMatricula(matriculaId, calificacion));
                                            System.out.println("+Se ha modificado la matricula.");
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
                        matriculas.close();
                    } catch (SQLException ex) {
                        System.out.println("Error buscando alumno.\n" + ex);
                    }
                }
            }
            System.out.println("Se han guardado las modificaciones.");
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
            ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));
            String nombreAlu = "";
            if (alumno.next()) {
                nombreAlu = alumno.getString("alumno_nombre");
                System.out.println("\nMatricula de " + nombreAlu + ":");
                ResultSet matriculas = ORM.getConnection().createStatement()
                        .executeQuery(ORM.buscarMatriculaAluID(nia));

                if (matriculas.next()) {
                    matriculas = ORM.getConnection().createStatement()
                            .executeQuery(ORM.buscarMatriculaAluID(nia));
                    imprimirAlumno(matriculas);
                } else {
                    System.out.println("Este alumno no tiene matricula aún.");
                }
            } else {
                System.out.println("-El alumno no existe.");
            }
            alumno.close();

        } catch (SQLException ex) {
            System.out.println("Error buscando alumno.\n" + ex);
        }
    }

    private void imprimirAlumno(ResultSet matriculas) {
        try {
            while (matriculas.next()) {
                String moduloId = matriculas.getString("modulo_id");
                String notasId = matriculas.getString("notas_id");
                String calificacion = matriculas.getString("calificacion");
                ResultSet modulo = ORM.getConnection().createStatement().executeQuery(ORM.buscarModuloID(moduloId));
                ResultSet notas = ORM.getConnection().createStatement().executeQuery(ORM.buscaNotaID(notasId));

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









    private boolean hayMatriculasMongo() {
        try (ResultSet matriculas = ORM.getConnection().createStatement().executeQuery(ORM.todoMatricula)) {
            if (matriculas.next()) {
                matriculas.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error retirando las matriculas para comprobar si hay instamcias.\n" + e);
        }
        return false;
    }

    public int modificarNotasMongo() {
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
                        ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString("alumno_nombre");
                            System.out.println(nombreAlu + ":");
                            System.out.println("----");

                            ResultSet matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                            if (!matriculas.next()) {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            } else {
                                matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                                imprimirAlumno(matriculas);
                            }

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscarMatriculaDobleID(nia, id));
                                        if (matricula.next()) {
                                            String notasId = matricula.getString("notas_id");

                                            ResultSet notas = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscaNotaID(notasId));

                                            notas.next();


                                            Lector sc = new Lector(System.in);
                                            int nota1 = 0;
                                            int nota2 = 0;
                                            int nota3 = 0;

                                            System.out.println("Introduzca las notas por orden: ");
                                            System.out.print("Nota 1: ");
                                            nota1 = sc.leerEntero(0, 10);
                                            if(nota1 < 0 || nota1 >10) nota1 = notas.getInt("nota1");
                                            System.out.print("Nota 2: ");
                                            nota2 = sc.leerEntero(0, 10);
                                            if(nota2 < 0 || nota2 >10) nota2 = notas.getInt("nota2");
                                            System.out.print("Nota 3: ");
                                            nota3 = sc.leerEntero(0, 10);
                                            if(nota3 < 0 || nota3 >10) nota3 = notas.getInt("nota3");

                                            ORM.getConnection().createStatement()
                                                    .execute(ORM.actualizarNota(notasId, nota1, nota2, nota3));
                                            System.out.println("Modificaciones añadido a la espera.");

                                            notas.close();
                                        } else {
                                            System.out.println("-No existe matricula con este modulo.");
                                        }
                                        matricula.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Error buscando matriculas.\n" + ex);
                                    }
                                }
                            }
                            matriculas.close();
                        } else {
                            seguirAlumno = false;
                            System.out.println("-El alumno no existe.");
                        }
                        alumno.close();
                    } catch (SQLException ex) {
                        System.out.println("Error buscando alumno.\n" + ex);
                    }
                }
            }
            System.out.println("Se han guardado las modificaciones.");
        } else {
            System.out.println("No tienes ninguna matricula aún.");
        }
        return 0;
    }

    public int evaluarModuloMongo() {
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
                        ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));
                        ResultSet matriculas = ORM.getConnection().createStatement()
                                .executeQuery(ORM.buscarMatriculaAluID(nia));

                        if (alumno.next()) {
                            String nombreAlu = alumno.getString("alumno_nombre");
                            System.out.println(nombreAlu + ":");
                            System.out.println("----");
                            if (matriculas.next()) {
                                matriculas = ORM.getConnection().createStatement().executeQuery(ORM.buscarMatriculaAluID(nia));
                                imprimirAlumno(matriculas);
                            } else {
                                seguirAlumno = false;
                                System.out.println("Este alumno no tiene matricula aún.");
                            }

                            while (seguirAlumno) {
                                String id = "";
                                System.out.println("Introduzca ID del módulo: (Volver con [0])");
                                id = sc2.leer();

                                if (id.equals("0")) {
                                    seguirAlumno = false;
                                } else {
                                    try {
                                        ResultSet matricula = ORM.getConnection().createStatement()
                                                .executeQuery(ORM.buscarMatriculaDobleID(nia, id));

                                        if (matricula.next()) {
                                            String matriculaId = matricula.getString("matricula_id");
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

                                            ORM.getConnection().createStatement()
                                                    .execute(ORM.actualizarMatricula(matriculaId, calificacion));
                                            System.out.println("+Se ha modificado la matricula.");
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
                        matriculas.close();
                    } catch (SQLException ex) {
                        System.out.println("Error buscando alumno.\n" + ex);
                    }
                }
            }
            System.out.println("Se han guardado las modificaciones.");
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

        try {
            ResultSet alumno = ORM.getConnection().createStatement().executeQuery(ORM.buscarAlumnoID(nia));
            String nombreAlu = "";
            if (alumno.next()) {
                nombreAlu = alumno.getString("alumno_nombre");
                System.out.println("\nMatricula de " + nombreAlu + ":");
                ResultSet matriculas = ORM.getConnection().createStatement()
                        .executeQuery(ORM.buscarMatriculaAluID(nia));

                if (matriculas.next()) {
                    matriculas = ORM.getConnection().createStatement()
                            .executeQuery(ORM.buscarMatriculaAluID(nia));
                    imprimirAlumno(matriculas);
                } else {
                    System.out.println("Este alumno no tiene matricula aún.");
                }
            } else {
                System.out.println("-El alumno no existe.");
            }
            alumno.close();

        } catch (SQLException ex) {
            System.out.println("Error buscando alumno.\n" + ex);
        }
    }

    private void imprimirAlumnoMongo(ResultSet matriculas) {
        try {
            while (matriculas.next()) {
                String moduloId = matriculas.getString("modulo_id");
                String notasId = matriculas.getString("notas_id");
                String calificacion = matriculas.getString("calificacion");
                ResultSet modulo = ORM.getConnection().createStatement().executeQuery(ORM.buscarModuloID(moduloId));
                ResultSet notas = ORM.getConnection().createStatement().executeQuery(ORM.buscaNotaID(notasId));

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

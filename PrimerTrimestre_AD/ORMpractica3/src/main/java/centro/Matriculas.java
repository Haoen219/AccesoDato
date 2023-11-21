package centro;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import practica.iv._sisform_fichero.Matricula;

public class Matriculas {
    Matricula matriculaComodin;

    public buscarMatricula(){
        //IMPLEMENTAR
    }

    public int eliminarMatricula(int nia) {
        if (buscarMatricula(nia) != null) {
            String comodin = "";
            try {
                Scanner lectorTemporal = new Scanner(this.baseDeMatriculas);
                if (lectorTemporal.hasNextLine()) {
                    comodin = lectorTemporal.nextLine();
                }
                //COPIAR Y ESCRIBIR DE NUEVO
                String aCopiar = "";
                while (lectorTemporal.hasNextLine()) {
                    String matricula = lectorTemporal.nextLine();
                    String[] data = matricula.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        aCopiar += ("\n" + matricula);
                    }
                }
                this.lector.close();

                this.escritor = new PrintWriter(new FileWriter(this.baseDeMatriculas, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
                return 0;
            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha eliminar una matricula, error dando de Baja");
            } catch (IOException ex) {
                System.out.println("--No se ha eliminar una matricula, error dando de Baja");
            } catch (Exception ex) {
                System.out.println("--No se ha podido eliminar una matricula, error inesperado dando de Baja\n" + ex);
            }
        } else {
            System.out.println("--Ese alumno no existe");
        }
        return -1;
    }

    public int matricularModulo(int id, int nia) {
        transformarAlumno(buscarAlumno(nia));

        if (this.alumnoComodin != null) {
            if (this.alumnoComodin.matricularModulo(id) == 0) {
                if (escribirMatricula(nia) == 0 && escribirAlumno(nia) == 0) {
                    this.alumnoComodin = null;
                    return 0;
                } else {
                    System.out.println("--No se ha podido agredar modulo al alumno");
                }
            }
            return 0;
        }
        return -1;
    }

    public int modificarNota() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Modificar notas-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();
        transformarAlumno(buscarAlumno(nia));

        if (this.alumnoComodin != null) {
            if (this.alumnoComodin.comprobarMatricula()) {
                this.alumnoComodin.imprimirModulos();
                System.out.print("Introduzca ID del m?dulo a modificar: ");
                int id = sc.nextInt();
                if (this.alumnoComodin.comprobarModulo(id)) {
                    int posicion = -1;
                    while (posicion < 1 || posicion > 3) {
                        System.out.print("La posición a modificar [1, 2, 3]: ");
                        posicion = sc.nextInt();
                        if (posicion < 1 || posicion > 3) {
                            System.out.println("--Se ha introducido una posici?n no v?lida");
                        }
                    }

                    double nota = -1;

                    while (nota < 0 || nota > 10) {
                        System.out.print("Introduzca la nueva nota: ");
                        try {
                            nota = sc.nextDouble();
                            if (nota < 0 || nota > 10) {
                                System.out.println("--Se ha introducido una nota no v?lida");
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("-Se ha introducido letras");
                        }
                    }
                    if (this.alumnoComodin.modificarNota(id, posicion, nota) == 0) {

                        if (escribirAlumno(nia) == 0 && escribirMatricula(nia) == 0) {
                            this.alumnoComodin = null;
                            System.out.println("++Se ha modificado la nota");
                            return 0;
                        }

                    } else {
                        System.out.println("--No se ha modificado la nota");
                    }
                }
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        } else {
            System.out.println("--No existe ese alumno en la base");
        }
        return -1;
    }

    public int evaluarModulo() {
        Scanner sc = new Scanner(System.in);
        String calificacion = "";
        System.out.println("\n-Evaluar modulos-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();
        transformarAlumno(buscarAlumno(nia));

        if (this.alumnoComodin != null) {
            transformarMatricula(buscarMatricula(nia));
            if (this.alumnoComodin.comprobarMatricula()) {
                this.alumnoComodin.imprimirModulos();
                System.out.print("Introduzca ID del m?dulo a modificar: ");
                int id = sc.nextInt();
                if (this.alumnoComodin.comprobarModulo(id)) {
                    switch (menuCalificar()) {
                        case 1 ->
                            calificacion = "Suspendido";
                        case 2 ->
                            calificacion = "Bien";
                        case 3 ->
                            calificacion = "Notable";
                        case 4 ->
                            calificacion = "Excelente";
                        default -> {
                            System.out.println("No se introdujo un valor valido");
                            return 0;
                        }
                    }
                    if (this.alumnoComodin.evaluarModulo(id, calificacion) == 0) {
                        if (escribirMatricula(nia) == 0) {
                            this.alumnoComodin = null;
                            System.out.println("++Se ha evaluado el modulo");
                            return 0;
                        }
                    } else {
                        System.out.println("--No se ha podido evaluar");
                    }
                }
            } else {
                System.out.println("--El alumno no tiene Matricula a?n");
            }
        } else {
            System.out.println("--Ese alumno no existe");
        }
        return -1;
    }

    public int menuEvaluar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Calificar              |");
        System.out.println("|2| Modificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.nextInt();
        return opcion;
    }

    private int menuCalificar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("Calificaciones:");
        System.out.println("1- Suspendido");
        System.out.println("2- Bien");
        System.out.println("3- Notable");
        System.out.println("4-Excelente");
        System.out.print("OPCI?N: ");
        int opcion = sc.nextInt();
        return opcion;
    }
}

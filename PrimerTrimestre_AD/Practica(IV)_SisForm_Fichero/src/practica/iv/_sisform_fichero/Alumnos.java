/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.iv._sisform_fichero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author haoen
 */
public class Alumnos implements BDDAlumnosModulos {

    Alumno alumnoComodin;
    Matricula matriculaComodin;

    File baseDeAlumnos = new File("Alumnos.txt");
    File baseDeMatriculas = new File("Matriculas.txt");

    PrintWriter escritor = null;
    Scanner lectorSecundario = null;
    Scanner lector = null;

    //FILE
    public boolean comprobarLista() {
        if (this.baseDeAlumnos.exists() && !this.baseDeAlumnos.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeAlumnos);
                this.lector.nextLine();
                if (this.lector.hasNextLine()) {
                    return true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("--Error leyendo Alumno,el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error leyendo Alumno, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error leyendo Alumno, error inesperado%n" + ex.getMessage());
            } finally {
                this.lector.close();
            }
        }
        return false;
    }

    private Alumno transformarAlumno(String alumno) {
        String[] informacion = alumno.split(" +");
        return new Alumno(informacion[1], Integer.parseInt(informacion[0]));
    }

    public String buscarAlumno(int nia) {
        String alumno;
        if (this.baseDeAlumnos.exists() && !this.baseDeAlumnos.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeAlumnos);
                this.lector.nextLine();

                if (this.lector.hasNextLine()) {
                    alumno = this.lector.nextLine();
                    String[] informacion = alumno.split(" +");
                    if (nia == Integer.parseInt(informacion[0])) {
                        return alumno;
                    }
                }
                System.out.println("--No se ha encontrado ese alumno");
                return null;
            } catch (InputMismatchException ex) {
                System.out.println("--El elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--No hay más líneas en el archivo");
            } catch (NumberFormatException ex) {
                System.out.println("--No se pudo convertir el String en entero");
            } catch (Exception ex) {
                System.out.println("--Error inesperado%n" + ex.getMessage());
            } finally {
                this.lector.close();
            }
        }
        return null;
    }

    private void transformarMatricula(String matricula) {
        if (matricula != null) {
            String[] datos = matricula.split(" +");
            int nia = Integer.parseInt(datos[0]);

            for (int i = 1; i < datos.length; i++) {
                //ID_NOTA-NOTA-NOTA_CALIFICACION
                String[] datosModulo = datos[i].split("_");
                int id = Integer.parseInt(datosModulo[0]);
                String[] notas = datosModulo[1].split("-");
                if (BaseDeDatos.modulos.buscarModulo(id) != null) {
                    this.alumnoComodin.matricularModulo(id);
                    for (int j = 0; j < notas.length; j++) {
                        this.alumnoComodin.modificarNota(id, (j + 1), Double.parseDouble(notas[j]));
                    }
                    this.alumnoComodin.evaluarModulo(id, datosModulo[2]);
                }
            }
        }
    }

    public String buscarMatricula(int nia) {
        String matricula;
        if (this.baseDeMatriculas.exists() && !this.baseDeMatriculas.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeMatriculas);
                this.lector.nextLine();

                if (this.lector.hasNextLine()) {
                    matricula = this.lector.nextLine();
                    String[] informacion = matricula.split(" +");
                    if (nia == Integer.parseInt(informacion[0])) {
                        return matricula;
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("--El elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--No hay más líneas en el archivo");
            } catch (NumberFormatException ex) {
                System.out.println("--No se pudo convertir el String en entero");
            } catch (Exception ex) {
                System.out.println("--Error inesperado%n" + ex.getMessage());
            } finally {
                this.lector.close();
            }
        }
        return null;
    }

    public int escribirAlumno(int nia) {
        File copiaAlumnos = new File("alumnosCopia.txt");
        String nombreOriginal = this.baseDeAlumnos.getName();
        try {
            this.escritor = new PrintWriter(new FileWriter(copiaAlumnos, true));
            this.lector = new Scanner(this.baseDeAlumnos);
            this.escritor.write(this.lector.nextLine());

            this.alumnoComodin = transformarAlumno(buscarAlumno(nia));
            if (this.alumnoComodin != null) {
                transformarMatricula(buscarMatricula(nia));

                while (this.lector.hasNextLine()) {
                    String alumno = this.lector.nextLine();
                    String[] data = alumno.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        this.escritor.write(this.alumnoComodin.formatoFichero());
                    }
                }

                this.escritor.close();
                this.lector.close();

                this.baseDeAlumnos = copiaAlumnos;
                if (copiaAlumnos.renameTo(new File("comodin.txt"))) {
                    System.out.println("Se ha cambiado alumnosCopia.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }

                if (this.baseDeAlumnos.renameTo(new File(nombreOriginal))) {
                    System.out.println("Se ha cambiado alumno.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }
            }
            return 0;
        } catch (FileNotFoundException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (IOException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (Exception ex) {
            System.out.println("--Error inesperado dando de Baja%n" + ex.getMessage());
        } finally {
            this.alumnoComodin = null;
            this.matriculaComodin = null;
        }
        return -1;
    }

    public int escribrirMatricula(int nia) {
        File copiaMatricula = new File("matriculasCopia.txt");
        String nombreOriginal = this.baseDeMatriculas.getName();
        try {
            this.escritor = new PrintWriter(new FileWriter(copiaMatricula, true));
            this.lector = new Scanner(this.baseDeMatriculas);
            this.escritor.write(this.lector.nextLine());

            while (this.lector.hasNextLine()) {
                String matricula = this.lector.nextLine();
                String[] data = matricula.split(" +");
                if (Integer.parseInt(data[0]) != nia) {
                    this.alumnoComodin.formatoFicheroMatricula();
                } else {
                    this.escritor.write(matricula);
                }
            }
            this.escritor.close();
            this.lector.close();

            this.baseDeMatriculas.delete();
            copiaMatricula.renameTo(new File(nombreOriginal));

            this.baseDeMatriculas = copiaMatricula;
            if (copiaMatricula.renameTo(new File("comodin.txt"))) {
                System.out.println("Se ha cambiado copiaMatricula.txt");
            } else {
                System.out.println("No se ha cambiado");
            }

            if (this.baseDeMatriculas.renameTo(new File(nombreOriginal))) {
                System.out.println("Se ha cambiado matricula.txt");
            } else {
                System.out.println("No se ha cambiado");
            }

            return 0;
        } catch (FileNotFoundException ex) {
            System.out.println("--No se ha podido abrir un archivo, error escribiendo Matricula");
        } catch (IOException ex) {
            System.out.println("--No se ha podido abrir un archivo, error escribiendo Matricula");
        } catch (Exception ex) {
            System.out.println("--Error inesperado escribiendo Matricula%n" + ex.getMessage());
        } finally {
            this.alumnoComodin = null;
            this.matriculaComodin = null;
        }
        return -1;
    }

    //ALUMNO
    @Override
    public int darDeAlta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre = sc.nextLine();
        System.out.print("Introduzca NIA del alumno: ");
        int nia;
        do {
            nia = sc.nextInt();
            if (nia <= 0 || nia > 99999999) {
                System.out.println("--Formato de NIA incorrecto, reintente");
            }
        } while (nia <= 0 || nia > 99999999);

        String alumno = String.format("%n%-4d %-25s -Sin-Matricula-", nia, nombre.replace(' ', '-'));

        if (buscarAlumno(nia) != null) {
            System.out.println("--Ya existe un alumno con ese NIA");
        } else {
            try {
                this.escritor = new PrintWriter(new FileOutputStream(this.baseDeAlumnos, true));
                escritor.write(alumno);
            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (IOException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (Exception ex) {
                System.out.println("--Error inesperado dando de Alta%n" + ex.getMessage());
            } finally {
                this.escritor.close();
            }
        }
        return -1;
    }

    @Override
    public int darDeBaja() {
        Scanner sc = new Scanner(System.in);
        File copiaAlumnos = new File("alumnosCopia.txt");
        String nombreOriginal = this.baseDeAlumnos.getName();
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();

        if (buscarAlumno(nia) != null) {
            try {
                this.escritor = new PrintWriter(new FileWriter(copiaAlumnos, true));
                this.lector = new Scanner(this.baseDeAlumnos);
                this.escritor.write(this.lector.nextLine());

                //COPIAR Y ESCRIBIR DE NUEVO
                while (this.lector.hasNextLine()) {
                    String alumno = this.lector.nextLine();
                    String[] data = alumno.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        this.escritor.write(alumno);
                    }
                }

                this.baseDeAlumnos = copiaAlumnos;
                if (copiaAlumnos.renameTo(new File("comodin.txt"))) {
                    System.out.println("Se ha cambiado alumnosCopia.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }

                if (this.baseDeAlumnos.renameTo(new File(nombreOriginal))) {
                    System.out.println("Se ha cambiado alumno.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }
                return 0;
            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
            } catch (IOException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
            } catch (Exception ex) {
                System.out.println("--Error inesperado dando de Baja%n" + ex.getMessage());
            } finally {
                this.escritor.close();
                this.lector.close();
            }
        }
        return -1;
    }

    //MATRICULA
    public int matricularModulo(int id, int nia) {
        this.alumnoComodin = transformarAlumno(buscarAlumno(nia));
        transformarMatricula(buscarMatricula(nia));
        if (this.alumnoComodin.matricularModulo(id) == 0) {
            this.alumnoComodin = null;
            return 0;
        }
        return -1;
    }

    public int modificarNota() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Modificar notas-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();
        this.alumnoComodin = transformarAlumno(buscarAlumno(nia));

        if (buscarAlumno(nia) != null) {
            transformarMatricula(buscarMatricula(nia));
            if (this.matriculaComodin != null) {
                this.alumnoComodin.imprimirModulos();
                System.out.print("Introduzca ID del m?dulo a modificar: ");
                int id = sc.nextInt();
                if (this.alumnoComodin.comprobarModulo(id)) {
                    int posicion = -1;
                    while (posicion < 0 || posicion > 3) {
                        System.out.print("La posición a modificar [1, 2, 3]: ");
                        posicion = sc.nextInt();
                        if (posicion < 0 || posicion > 3) {
                            System.out.println("--Se ha introducido una posici?n no v?lida");
                        }
                    }
                    System.out.print("Introduzca la nueva nota: ");
                    double nota = sc.nextDouble();
                    if (this.alumnoComodin.modificarNota(id, posicion, nota) == 0) {
                        System.out.println("++Se ha modificado la nota");
                    } else {
                        System.out.println("--No se ha modificado la nota");
                    }
                } else {
                    System.out.println("--Se ha introducido una posici?n no v?lida");
                }
                this.matriculaComodin = null;
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
        if (escribrirMatricula(nia) == 0) {
            return 0;
        }
        return -1;
    }

    public int evaluarModulo() {
        Scanner sc = new Scanner(System.in);
        String calificacion = "";
        System.out.println("\n-Evaluar modulos-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();
        this.alumnoComodin = transformarAlumno(buscarAlumno(nia));

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
                        System.out.println("++Se ha evaluado el modulo");
                        return 0;
                    } else {
                        System.out.println("--No se ha podido evaluar");
                    }
                }
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
        if (escribrirMatricula(nia) == 0) {
            return 0;
        }
        return -1;
    }

    //ACTUALIZAR
    @Override
    public int actualizar(int id) {
        try {
            Scanner lectorTemporal = new Scanner(this.baseDeAlumnos);
            this.lector.nextLine();

            if (this.lector.hasNextLine()) {
                this.alumnoComodin = transformarAlumno(this.lector.nextLine());
                transformarMatricula(buscarAlumno(this.alumnoComodin.getIDENTIFICADOR()));
                if (this.matriculaComodin != null) {

                    if (this.matriculaComodin.eliminarModulo(id) == -1) {
                        System.out.println("*Ha ocurrido un error, no se ha podido eliminar el modulo con ID de una matricula");
                    }
                    if (escribrirMatricula(this.alumnoComodin.getIDENTIFICADOR()) == 0) {
                        System.out.println("--Error actualizando la matricula del alumno con NIA:" + this.alumnoComodin.getIDENTIFICADOR());
                    }
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("--El elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--No hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error inesperado%n" + ex.getMessage());
        } finally {
            this.lector.close();
            this.alumnoComodin = null;
            this.matriculaComodin = null;
        }
        return 0;
    }

    //MENU
    @Override
    public int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----Mantener Alumnos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.nextInt();
        return opcion;
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

    //IMPRIMIR
    @Override
    public void listar() {
        System.out.println("\n-Listar alumnos-");
        try {
            this.lector = new Scanner(this.baseDeAlumnos);
            this.lectorSecundario = new Scanner(this.baseDeMatriculas);
            this.lector.nextLine();
            this.lectorSecundario.nextLine();

            if (this.lector.hasNextLine()) {
                this.alumnoComodin = transformarAlumno(this.lector.nextLine());
                transformarMatricula(buscarAlumno(this.alumnoComodin.getIDENTIFICADOR()));
                this.alumnoComodin.imprimir();
            }
        } catch (InputMismatchException ex) {
            System.out.println("--El elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--No hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error inesperado%n" + ex.getMessage());
        } finally {
            this.lector.close();
            this.lectorSecundario.close();
            this.alumnoComodin = null;
            this.matriculaComodin = null;
        }
        System.out.println("--Fin de la lista");
    }

    public void imprimirBoletin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Imprimir Bolet?n-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();
        if (buscarAlumno(nia) != null) {
            this.alumnoComodin = transformarAlumno(buscarAlumno(nia));
            transformarMatricula(buscarMatricula(nia));
            if (this.alumnoComodin.comprobarMatricula()) {
                this.alumnoComodin.imprimirBoletin();
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
            this.alumnoComodin = null;
            this.matriculaComodin = null;
        }
    }
}

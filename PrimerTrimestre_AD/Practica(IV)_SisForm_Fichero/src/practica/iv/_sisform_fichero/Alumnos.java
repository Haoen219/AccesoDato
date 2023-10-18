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
    //Matricula matriculaComodin;

    File baseDeAlumnos = new File("Alumnos.txt");
    File baseDeMatriculas = new File("Matriculas.txt");

    PrintWriter escritor = null;
    Scanner lectorSecundario = null;
    Scanner lector = null;

    public Alumnos() {
        instanciarFile();
    }

    //FILE
    private void instanciarFile() {
        try {
            if (!this.baseDeAlumnos.exists()) {
                this.escritor = new PrintWriter(new FileWriter(this.baseDeAlumnos, true));
                String alumnoFormato = String.format("%-8s %-25s NumModulos", "NIA", "NOMBRE");
                this.escritor.write(this.lector.nextLine());
                this.escritor.close();
            }
            if (!this.baseDeAlumnos.exists()) {
                this.escritor = new PrintWriter(new FileWriter(this.baseDeAlumnos, true));
                String alumnoFormato = String.format("%-8s Modulos(ID_NOTA-NOTA-NOTA-_CALIFICACION)", "NIA");
                this.escritor.write(this.lector.nextLine());
                this.escritor.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (IOException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (Exception ex) {
            System.out.println("--Error inesperado dando de Baja\n" + ex);
        }
    }

    public boolean comprobarLista() {
        if (this.baseDeAlumnos.exists() && !this.baseDeAlumnos.isDirectory()) {
            try {
                Scanner lectorTemporal = new Scanner(this.baseDeAlumnos);
                String comodin = lectorTemporal.nextLine();
                if (lectorTemporal.hasNextLine()) {
                    return true;
                }
                lectorTemporal.close();
            } catch (InputMismatchException ex) {
                System.out.println("--Error leyendo Alumno,el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error leyendo Alumno, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error leyendo Alumno, error inesperado\n" + ex);
            }
        }
        return false;
    }

    private Alumno transformarAlumno(String alumno) {
        String[] informacion = alumno.split(" +");
        int nia = Integer.parseInt(informacion[0]);
        transformarMatricula(buscarMatricula(nia));
        return new Alumno(informacion[1], nia);
    }

    public String buscarAlumno(int nia) {
        String alumno;
        if (this.baseDeAlumnos.exists() && !this.baseDeAlumnos.isDirectory()) {
            try {
                Scanner lectorTemporal = new Scanner(this.baseDeAlumnos);
                lectorTemporal.nextLine();

                while (lectorTemporal.hasNextLine()) {
                    alumno = lectorTemporal.nextLine();
                    String[] informacion = alumno.split(" +");
                    if (nia == Integer.parseInt(informacion[0])) {
                        return alumno;
                    }
                }
                lectorTemporal.close();
            } catch (InputMismatchException ex) {
                System.out.println("--Error buscando alumno, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error buscando alumno, no hay más líneas en el archivo");
            } catch (NumberFormatException ex) {
                System.out.println("--Error buscando alumno, no se pudo convertir el String en entero");
            } catch (Exception ex) {
                System.out.println("--Error buscando alumno, error inesperado\n" + ex);
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
                Scanner lectorTemporal = new Scanner(this.baseDeMatriculas);
                lectorTemporal.nextLine();

                while (lectorTemporal.hasNextLine()) {
                    matricula = lectorTemporal.nextLine();
                    String[] informacion = matricula.split(" +");
                    if (nia == Integer.parseInt(informacion[0])) {
                        return matricula;
                    }
                }
                lectorTemporal.close();
            } catch (InputMismatchException ex) {
                System.out.println("--Error buscando matricula, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error buscando matricula, no hay más líneas en el archivo");
            } catch (NumberFormatException ex) {
                System.out.println("--Error buscando matricula, no se pudo convertir el String en entero");
            } catch (Exception ex) {
                System.out.println("--Error buscando matricula, Error inesperado\n" + ex);
            }
        }
        return null;
    }

    public int escribirAlumno(int nia) {
        String comodin = "";
        try {
            this.lector = new Scanner(this.baseDeAlumnos);
            comodin = (this.lector.nextLine());

            if (this.alumnoComodin != null) {
                String aCopiar = "";
                while (this.lector.hasNextLine()) {
                    String alumno = this.lector.nextLine();
                    String[] data = alumno.split(" +");
                    if (Integer.parseInt(data[0]) == nia) {
                        aCopiar += this.alumnoComodin.formatoFichero();
                    } else {
                        aCopiar += ("\n" + alumno);
                    }
                }
                this.lector.close();

                this.escritor = new PrintWriter(new FileWriter(this.baseDeAlumnos, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
            }
            return 0;
        } catch (FileNotFoundException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (IOException ex) {
            System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
        } catch (Exception ex) {
            System.out.println("--Error inesperado dando de Baja\n" + ex);
        } finally {
            //this.alumnoComodin = null;
        }
        return -1;
    }

    public int escribirMatricula(int nia) {
        String comodin = "";
        try {
            this.lector = new Scanner(this.baseDeMatriculas);
            comodin = (this.lector.nextLine());

            if (this.alumnoComodin != null) {

                String aCopiar = "";
                while (this.lector.hasNextLine()) {
                    String matricula = this.lector.nextLine();
                    String[] data = matricula.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        aCopiar += this.alumnoComodin.formatoFicheroMatricula();
                    } else {
                        aCopiar += (matricula);
                    }
                }
                this.lector.close();

                this.escritor = new PrintWriter(new FileWriter(this.baseDeMatriculas, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
                return 0;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("--No se ha podido abrir un archivo, error escribiendo Matricula");
        } catch (IOException ex) {
            System.out.println("--No se ha podido abrir un archivo, error escribiendo Matricula");
        } catch (Exception ex) {
            System.out.println("--Error inesperado escribiendo Matricula\n" + ex);
        } finally {
            //this.alumnoComodin = null;
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

        String alumno = String.format("%n%-8d %-25s -Sin-Matricula-", nia, nombre.replace(' ', '-'));

        if (buscarAlumno(nia) != null) {
            System.out.println("--Ya existe un alumno con ese NIA");
        } else {
            try {
                this.escritor = new PrintWriter(new FileOutputStream(this.baseDeAlumnos, true));
                escritor.write(alumno);
                System.out.println("++Se ha dado de alta");
            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (IOException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (Exception ex) {
                System.out.println("--Error inesperado dando de Alta\n" + ex);
            } finally {
                this.escritor.close();
            }
        }
        return -1;
    }

    @Override
    public int darDeBaja() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();

        if (buscarAlumno(nia) != null) {
            try {
                
                this.lector = new Scanner(this.baseDeAlumnos);
                String comodin=this.lector.nextLine();
                //alumno
                String aCopiar = "";
                while (this.lector.hasNextLine()) {
                    String alumno = this.lector.nextLine();
                    String[] data = alumno.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        aCopiar += ("\n" + alumno);
                    }
                }
                this.lector.close();
                this.escritor = new PrintWriter(new FileWriter(this.baseDeAlumnos, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
                
                
                this.lector = new Scanner(this.baseDeMatriculas);
                String comodinMatricula=this.lector.nextLine();
                //matricula
                String aCopiarMatricula = "";
                while (this.lector.hasNextLine()) {
                    String matricula = this.lector.nextLine();
                    String[] data = matricula.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        aCopiar += ("\n" + matricula);
                    }
                }
                this.lectorSecundario.close();
                this.escritor = new PrintWriter(new FileWriter(this.baseDeMatriculas, false));
                this.escritor.write(comodinMatricula);
                this.escritor.write(aCopiarMatricula);
                this.escritor.close();
                
                
                if (BaseDeDatos.modulos.actualizar(nia) == 0) {
                    System.out.println("++Se ha dado de baja");
                    return 0;
                }else{
                    System.out.println("--No se ha dado de baja");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
            } catch (IOException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Baja");
            } catch (Exception ex) {
                System.out.println("--Error inesperado dando de Baja\n" + ex);
            }
        } else {
            System.out.println("--Ese alumno no existe");
        }
        return -1;
    }
    //MATRICULA

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
        String comodin = "";
        this.alumnoComodin = transformarAlumno(buscarAlumno(nia));

        if (this.alumnoComodin != null) {
            if (this.alumnoComodin.matricularModulo(id) == 0) {
                if (escribirMatricula(nia) == 0 && escribirAlumno(nia) == 0) {
                    return 0;
                }else{
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
        this.alumnoComodin = transformarAlumno(buscarAlumno(nia));

        if (this.alumnoComodin != null) {
            if (this.alumnoComodin.comprobarMatricula()) {
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
                        
                        if(escribirAlumno(nia)==0 && escribirMatricula(nia)==0){
                            System.out.println("++Se ha modificado la nota");
                            return 0;
                        }
                        
                    } else {
                        System.out.println("--No se ha modificado la nota");
                    }
                } else {
                    System.out.println("--Se ha introducido una posici?n no v?lida");
                }
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
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
                        if (escribirMatricula(nia) == 0) {
                            System.out.println("++Se ha evaluado el modulo");
                            return 0;
                        }
                    } else {
                        System.out.println("--No se ha podido evaluar");
                    }
                } else {
                    System.out.println("--El alumno no esta matriculado en ese módulo");
                }
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
        }
        return -1;
    }

    //ACTUALIZAR
    @Override
    public int actualizar(int id) {
        try {
            Scanner lectorTemporal = new Scanner(this.baseDeAlumnos);
            lectorTemporal.nextLine();

            if (lectorTemporal.hasNextLine()) {
                this.alumnoComodin = transformarAlumno(lectorTemporal.nextLine());

                if (this.alumnoComodin.comprobarMatricula()) {
                    if (this.alumnoComodin.comprobarModulo(id)) {
                        if (this.alumnoComodin.eliminarModulos(id) == -1) {
                            System.out.println("*Ha ocurrido un error, no se ha podido eliminar el modulo con ID de una matricula");
                        } else {
                            if(escribirAlumno(this.alumnoComodin.getIDENTIFICADOR())==0 &&
                                    escribirMatricula(this.alumnoComodin.getIDENTIFICADOR())==0)
                                    {
                               return 0; 
                            }
                        }
                    }
                }
            }
            lectorTemporal.close();
        } catch (InputMismatchException ex) {
            System.out.println("--Error en actualizar modulos, el elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--Error en actualizar modulos, no hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error en actualizar modulos, error inesperado\n" + ex);
        } finally {
            this.alumnoComodin = null;
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
            this.lector.nextLine();
            this.lectorSecundario = new Scanner(this.baseDeMatriculas);
            this.lectorSecundario.nextLine();

            while (this.lector.hasNextLine()) {
                this.alumnoComodin = transformarAlumno(this.lector.nextLine());
                this.alumnoComodin.imprimir();
                this.alumnoComodin = null;
            }
        } catch (InputMismatchException ex) {
            System.out.println("--Error listando alumnos, el elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--Error listando alumnos, no hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error listando alumnos, error inesperado\n" + ex);
        } finally {
            this.lector.close();
            this.lectorSecundario.close();
            this.alumnoComodin = null;
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
            if (this.alumnoComodin.comprobarMatricula()) {
                this.alumnoComodin.imprimirBoletin();
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
            this.alumnoComodin = null;
        } else {
            System.out.println("-NIA no existe");
        }
    }
}

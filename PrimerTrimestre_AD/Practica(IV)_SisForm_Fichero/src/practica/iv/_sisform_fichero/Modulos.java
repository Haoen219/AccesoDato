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
import java.util.TreeSet;

/**
 *
 * @author haoen
 */
public class Modulos implements BDDAlumnosModulos {

    Modulo moduloComodin;

    File baseDeModulos = new File("Modulos.txt");

    PrintWriter escritor = null;
    Scanner lector = null;

    //FILE
    public boolean comprobarLista() {
        if (this.baseDeModulos.exists() && !this.baseDeModulos.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeModulos);
                this.lector.nextLine();
                if (this.lector.hasNextLine()) {
                    return true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("--Error leyendo Modulo, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error leyendo Modulo, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error leyendo Modulo, error inesperado%n" + ex.getMessage());
            } finally {
                this.lector.close();
            }
        }
        return false;
    }

    public int ultimoID() {
        int id = -1;
        TreeSet<Integer> ids = new TreeSet();
        if (this.baseDeModulos.exists() && !this.baseDeModulos.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeModulos);
                this.lector.nextLine();
                if (this.lector.hasNextLine()) {
                    String modulo = this.lector.nextLine();
                    String[] informacion = modulo.split(" +");
                    ids.add(Integer.valueOf(informacion[0]));
                }
                id = ids.last();
            } catch (InputMismatchException ex) {
                System.out.println("--Error leyendo Modulo, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error leyendo Modulo, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error leyendo Modulo, error inesperado%n" + ex.getMessage());
            } finally {
                this.lector.close();
            }
        }
        return id;
    }

    private void transformarModulo(String modulo) {
        String[] informacion = modulo.split(" +");
        this.moduloComodin = new Modulo(informacion[1], Integer.parseInt(informacion[0]));
        String[] nias = informacion[2].split("-");
        for (int i = 0; i < nias.length; i++) {
            //ID_NOMBRE_NIA-NIA-NIA-...
            int nia = Integer.parseInt(nias[i]);
            this.moduloComodin.matricularAlumno(nia);
        }
    }

    public String buscarModulo(int id) {
        String modulo;
        try {
            if (this.baseDeModulos.exists() && !this.baseDeModulos.isDirectory()) {
                this.lector = new Scanner(this.baseDeModulos);
                if (this.lector.hasNextLine()) {
                    modulo = this.lector.nextLine();
                    String[] informacion = modulo.split(" +");
                    if (id == Integer.parseInt(informacion[0])) {
                        return modulo;
                    }
                }
                System.out.println("--No se ha encontrado ese modulo");
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
        return null;
    }

    public int escribirModulo(int id) {
        File copiaModulo = new File("modulosCopia.txt");
        String nombreOriginal = this.baseDeModulos.getName();
        try {
            this.escritor = new PrintWriter(new FileWriter(copiaModulo, true));
            this.lector = new Scanner(this.baseDeModulos);
            this.escritor.write(this.lector.nextLine());

            transformarModulo(buscarModulo(id));
            if (this.moduloComodin != null) {
                while (this.lector.hasNextLine()) {
                    String modulo = this.lector.nextLine();
                    String[] data = modulo.split(" +");
                    if (Integer.parseInt(data[0]) != id) {
                        this.escritor.write(this.moduloComodin.formatoFichero());
                    }
                }
                this.escritor.close();
                this.lector.close();

                this.baseDeModulos = copiaModulo;
                if (copiaModulo.renameTo(new File("comodin.txt"))) {
                    System.out.println("Se ha cambiado modulosCopia.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }

                if (this.baseDeModulos.renameTo(new File(nombreOriginal))) {
                    System.out.println("Se ha cambiado modulos.txt");
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
            this.moduloComodin = null;
        }
        return -1;
    }

//MODULO
    @Override
    public int darDeAlta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Dar de alta m?dulo-");
        System.out.print("Introduzca el nombre del m?dulo: ");
        String nombre = sc.nextLine();
        int id = ultimoID();

        if (id == -1) {
            id = 1;
        } else {
            id += 1;
        }
        if (buscarModulo(id) != null) {
            System.out.println("--Ya existe un modulo con ese ID");
        } else {
            this.moduloComodin = new Modulo(nombre, id);

            try {
                this.escritor = new PrintWriter(new FileOutputStream(this.baseDeModulos, true));
                escritor.write(this.moduloComodin.formatoFichero());
                if (buscarModulo(id) != null) {
                    System.out.println("++Se ha dado de alta al m?dulo (ID:" + id + ")");
                    return 0;
                } else {
                    System.out.println("--No se ha dado de alta");
                }

            } catch (FileNotFoundException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (IOException ex) {
                System.out.println("--No se ha podido abrir un archivo, error dando de Alta");
            } catch (Exception ex) {
                System.out.println("--Error inesperado dando de Alta%n" + ex.getMessage());
            } finally {
                this.escritor.close();
                this.moduloComodin = null;
            }
        }
        return -1;
    }

    @Override
    public int darDeBaja() {
        Scanner sc = new Scanner(System.in);
        File copiaModulos = new File("modulosCopia.txt");
        String nombreOriginal = this.baseDeModulos.getName();
        System.out.println("\n-Dar de baja m?dulo-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id = sc.nextInt();
        if (buscarModulo(id) != null) {
            try {
                this.escritor = new PrintWriter(new FileWriter(copiaModulos, true));
                this.lector = new Scanner(this.baseDeModulos);
                this.escritor.write(this.lector.nextLine());

                //COPIAR Y ESCRIBIR DE NUEVO
                while (this.lector.hasNextLine()) {
                    String modulo = this.lector.nextLine();
                    String[] data = modulo.split(" +");
                    if (Integer.parseInt(data[0]) != id) {
                        this.escritor.write(modulo);
                    }
                }

                this.baseDeModulos = copiaModulos;
                if (copiaModulos.renameTo(new File("comodin.txt"))) {
                    System.out.println("Se ha cambiado modulosCopia.txt");
                } else {
                    System.out.println("No se ha cambiado");
                }

                if (this.baseDeModulos.renameTo(new File(nombreOriginal))) {
                    System.out.println("Se ha cambiado modulo.txt");
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
    public int matricularAlumno() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id = sc.nextInt();

        transformarModulo(buscarModulo(id));
        if (this.moduloComodin != null) {
            System.out.print("Introduzca NIA del alumno: ");
            int nia = sc.nextInt();
            if (BaseDeDatos.alumnos.buscarAlumno(nia) != null) {
                if (this.moduloComodin.matricularAlumno(nia) == 0 && BaseDeDatos.alumnos.matricularModulo(id, nia) == 0) {
                    System.out.println("++Se ha matriculado el alumno (NIA:" + nia + ")");
                    return 0;
                } else {
                    System.out.println("--No se ha podido matricular al alumno (NIA:" + nia + ")");
                }
            }
        }
        this.moduloComodin = null;
        return -1;
    }

    //ACTUALIZAR
    @Override
    public int actualizar(int nia) {
        File copiaModulo = new File("modulosCopia.txt");
        String nombreOriginal = this.baseDeModulos.getName();
        try {
            Scanner lectorTemporal = new Scanner(this.baseDeModulos);
            this.lector.nextLine();

            if (this.lector.hasNextLine()) {
                transformarModulo(this.lector.nextLine());

                if (this.moduloComodin.anularMatriculaAlumno(nia) == -1) {
                    System.out.println("*Ha ocurrido un error, no se ha podido eliminar el alumno con nia " + nia
                            + " del m?dulo " + this.moduloComodin.getIDENTIFICADOR());
                }

                this.escritor = new PrintWriter(new FileWriter(this.baseDeModulos, true));
                this.escritor.write(this.moduloComodin.formatoFichero());
                this.escritor.close();
            }
            return 0;
        } catch (NoSuchElementException ex) {
            System.out.println("--No hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error inesperado%n" + ex.getMessage());
        } finally {
            //COPIAR
            this.baseDeModulos = copiaModulo;
            if (copiaModulo.renameTo(new File("comodin.txt"))) {
                System.out.println("Se ha cambiado modulosCopia.txt");
            } else {
                System.out.println("No se ha cambiado");
            }
            if (this.baseDeModulos.renameTo(new File(nombreOriginal))) {
                System.out.println("Se ha cambiado modulos.txt");
            } else {
                System.out.println("No se ha cambiado");
            }
            
            this.lector.close();
            this.moduloComodin = null;
        }
        return 0;
    }

    //MENU
    @Override
    public int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("|-----Mantener M?dulos-----|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Dar de alta            |");
        System.out.println("|2| Dar de baja            |");
        System.out.println("|3| Listar                 |");
        System.out.println("|4| Matricular Alumno      |");
        System.out.println("|" + "-".repeat(26) + "|");
        System.out.print("OPCI?N: ");
        int opcion = sc.nextInt();
        return opcion;
    }

    //IMPRIMIR
    public void listar() {
        System.out.println("\n-Listar modulos-");
        try {
            this.lector = new Scanner(this.baseDeModulos);
            this.lector.nextLine();

            if (this.lector.hasNextLine()) {
                transformarModulo(this.lector.nextLine());
                this.moduloComodin.imprimir();
            }
        } catch (InputMismatchException ex) {
            System.out.println("--El elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--No hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error inesperado%n" + ex.getMessage());
        } finally {
            this.lector.close();
            this.moduloComodin = null;
        }
        System.out.println("--Fin de la lista");
    }

    //GETTER
    public Modulo getModulo(int id) {
        transformarModulo(buscarModulo(id));
        try {
            return this.moduloComodin;
        } finally {
            this.moduloComodin = null;
        }
    }

}

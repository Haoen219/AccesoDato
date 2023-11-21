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

    public Modulos() {
        instanciarFile();
    }

    //FILE
    private void instanciarFile() {
        try {
            if (!this.baseDeModulos.exists()) {
                this.escritor = new PrintWriter(new FileWriter(this.baseDeModulos, true));
                String moduloFormato = String.format("%-4s %-25s NIA_Alumnos", "ID", "NOMBRE");
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
        if (this.baseDeModulos.exists() && !this.baseDeModulos.isDirectory()) {
            try {
                this.lector = new Scanner(this.baseDeModulos);
                this.lector.nextLine();
                while (this.lector.hasNextLine()) {
                    return true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("--Error leyendo Modulo, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error leyendo Modulo, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error leyendo Modulo, error inesperado\n" + ex);
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
                while (this.lector.hasNextLine()) {
                    String modulo = this.lector.nextLine();
                    String[] informacion = modulo.split(" +");
                    ids.add(Integer.valueOf(informacion[0]));
                }
                if (!ids.isEmpty()) {
                    id = ids.last();
                }
            } catch (InputMismatchException ex) {
                System.out.println("--Error buscando ultimo ID, el elemento no era del tipo esperado");
            } catch (NoSuchElementException ex) {
                System.out.println("--Error buscando ultimo ID, no hay más líneas en el archivo");
            } catch (Exception ex) {
                System.out.println("--Error buscando ultimo ID, error inesperado\n" + ex);
            } finally {
                this.lector.close();
            }
        }
        return id;
    }

    private void transformarModulo(String modulo) {
        if (modulo != null) {
            String[] informacion = modulo.split(" +");
            this.moduloComodin = new Modulo(informacion[1].replace('-', ' '), Integer.parseInt(informacion[0]));
            if (informacion.length > 2) {
                String[] nias = informacion[2].split("-");
                for (int i = 0; i < nias.length; i++) {
                    //ID_NOMBRE_NIA-NIA-NIA-...
                    int nia = Integer.parseInt(nias[i]);
                    this.moduloComodin.matricularAlumno(nia);
                }
            }
        }
    }

    public String buscarModulo(int id) {
        String modulo;
        try {
            if (this.baseDeModulos.exists() && !this.baseDeModulos.isDirectory()) {
                Scanner lectorTemporal = new Scanner(this.baseDeModulos);
                lectorTemporal.nextLine();
                while (lectorTemporal.hasNextLine()) {
                    modulo = lectorTemporal.nextLine();
                    String[] informacion = modulo.split(" +");
                    if (id == Integer.parseInt(informacion[0])) {
                        lectorTemporal.close();
                        return modulo;
                    }
                }
                lectorTemporal.close();
            }
        } catch (InputMismatchException ex) {
            System.out.println("--Error buscando módulo, el elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--Error buscando módulo, no hay más líneas en el archivo");
        } catch (NumberFormatException ex) {
            System.out.println("--Error buscando módulo, no se pudo convertir el String en entero");
        } catch (Exception ex) {
            System.out.println("--Error buscando módulo, error inesperado\n" + ex);
        }
        return null;
    }

    public int escribirModulo(int id) {
        String comodin = "";
        if (this.moduloComodin != null) {
            try {
                Scanner lectorTemporal = new Scanner(this.baseDeModulos);
                comodin = lectorTemporal.nextLine();

                String aCopiar = "";
                while (lectorTemporal.hasNextLine()) {
                    String modulo = lectorTemporal.nextLine();
                    String[] data = modulo.split(" +");
//                    String[] alumno= data[2].split("-");
//                    System.out.println(data[0]);
//                    System.out.println(id);
                    if (Integer.parseInt(data[0]) == id) {
                        aCopiar += this.moduloComodin.formatoFichero();
                    } else {
                        aCopiar += "\n" + modulo;
                    }
                }
                lectorTemporal.close();

                this.escritor = new PrintWriter(new FileWriter(this.baseDeModulos, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
                return 0;
            } catch (FileNotFoundException ex) {
                System.out.println("--Error escribiendo módulo, no se ha podido abrir un archivo");
            } catch (IOException ex) {
                System.out.println("--Error escribiendo módulo, no se ha podido abrir un archivo");
            } catch (Exception ex) {
                System.out.println("--Error escribiendo módulo, error inesperado\n" + ex);
            } finally {
                this.moduloComodin = null;
            }
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
                if (buscarModulo(id) == null) {
                    System.out.println("++Se ha dado de alta al m?dulo (ID:" + id + ")");
                    return 0;
                } else {
                    System.out.println("--No se ha dado de alta");
                }

            } catch (FileNotFoundException ex) {
                System.out.println("--Error dando de alta módulo, no se ha podido abrir un archivo");
            } catch (IOException ex) {
                System.out.println("--Error dando de alta módulo, no se ha podido abrir un archivo");
            } catch (Exception ex) {
                System.out.println("--Error dando de alta módulo, error inesperado\n" + ex);
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
        System.out.println("\n-Dar de baja m?dulo-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id = sc.nextInt();
        String comodin = "";
        if (buscarModulo(id) != null) {
            try {
                this.lector = new Scanner(this.baseDeModulos);
                comodin = this.lector.nextLine();

                transformarModulo(buscarModulo(id));
                String aCopiar = "";
                if (this.moduloComodin != null) {
                    while (this.lector.hasNextLine()) {
                        String modulo = this.lector.nextLine();
                        String[] data = modulo.split(" +");
                        if (Integer.parseInt(data[0]) != id) {
                            aCopiar += "\n" + modulo;
                        }
                    }
                }
                this.lector.close();

                this.escritor = new PrintWriter(new FileWriter(this.baseDeModulos, false));
                this.escritor.write(comodin);
                this.escritor.write(aCopiar);
                this.escritor.close();
                if (BaseDeDatos.alumnos.actualizar(id) == 0) {
                    System.out.println("++Se ha dado de baja");
                    return 0;
                }
                System.out.println("--No se ha dado de baja");
            } catch (FileNotFoundException ex) {
                System.out.println("--Error escribiendo módulo, no se ha podido abrir un archivo");
            } catch (IOException ex) {
                System.out.println("--Error escribiendo módulo, no se ha podido abrir un archivo");
            } catch (Exception ex) {
                System.out.println("--Error escribiendo módulo, error inesperado\n" + ex);
            } finally {
                this.moduloComodin = null;
            }
        } else {
            System.out.println("--No existe ese Módulo");
        }
        return -1;
    }

//MATRICULA
    public int matricularAlumno() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Matricular alumno-");
        System.out.print("Introduzca ID del m?dulo: ");
        int id = sc.nextInt();
        if (buscarModulo(id) != null) {
            transformarModulo(buscarModulo(id));
            System.out.print("Introduzca NIA del alumno: ");
            int nia = sc.nextInt();
            if (BaseDeDatos.alumnos.buscarAlumno(nia) != null) {
                if (this.moduloComodin.matricularAlumno(nia) == 0 && BaseDeDatos.alumnos.matricularModulo(id, nia) == 0) {
                    escribirModulo(id);
                    
                    System.out.println("++Se ha matriculado el alumno (NIA:" + nia + ")");
                    return 0;
                } else {
                    System.out.println("--No se ha podido matricular al alumno (NIA:" + nia + ")");
                }
            } else {
                System.out.println("--No existe ese alumno");
            }
        } else {
            System.out.println("--No existe ese modulo");
        }
        this.moduloComodin = null;
        return -1;
    }

    //ACTUALIZAR
    @Override
    public int actualizar(int nia) {
        try {
            Scanner lectorTemporal = new Scanner(this.baseDeModulos);
            lectorTemporal.nextLine();

            while (lectorTemporal.hasNextLine()) {
                transformarModulo(lectorTemporal.nextLine());
                if (this.moduloComodin.buscarAlumno(nia)) {
                    if (this.moduloComodin.anularMatriculaAlumno(nia) == -1) {
                        System.out.println("*Ha ocurrido un error, no se ha podido eliminar el alumno con nia " + nia
                                + " del m?dulo " + this.moduloComodin.getIDENTIFICADOR());
                    }else{
                        escribirModulo(this.moduloComodin.getIDENTIFICADOR());
                        return 0;
                    }
                }
            }
            lectorTemporal.close();
        } catch (NoSuchElementException ex) {
            System.out.println("--Error actualizando alumnos, no hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error actualizando alumnos, error inesperado\n" + ex);
        } finally {
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

            while (this.lector.hasNextLine()) {
                transformarModulo(this.lector.nextLine());
                if (this.moduloComodin != null) {
                    this.moduloComodin.imprimir();
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("--Error listando, el elemento no era del tipo esperado");
        } catch (NoSuchElementException ex) {
            System.out.println("--Error listando, no hay más líneas en el archivo");
        } catch (Exception ex) {
            System.out.println("--Error listando, error inesperado\n" + ex);
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

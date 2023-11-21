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

    File baseDeAlumnos = new File("Alumnos.txt");
    File baseDeMatriculas = new File("Matriculas.txt");

    PrintWriter escritor = null;
    Scanner lectorSecundario = null;
    Scanner lector = null;

    public Alumnos() {
        instanciarFile();
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

    private void transformarAlumno(String alumno) {
        if (alumno != null) {
            String[] informacion = alumno.split(" +");
            int nia = Integer.parseInt(informacion[0]);
            this.alumnoComodin = new Alumno(informacion[1], nia);
            transformarMatricula(buscarMatricula(nia));
        }
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

    @Override
    public int darDeAlta() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de alta alumno-");
        System.out.print("Introduzca el nombre del alumno: ");
        String nombre=sc.leer();
        /*
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,15);
        */
        
        Alumno alumno = new Alumno(nombre);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(alumno);

        session.getTransaction().commit();
        session.close();
        System.out.println("Se ha dado de alta al alumno "+ nombre);
        return 0;
    }

    @Override
    public int darDeBaja() {
        Lector sc= new Lector(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia=sc.leerEntero(0,15);
            
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        Alumno deBaja = session.get(Alumno.class, (short)nia);

        if(deBaja!=null){
            session.delete(deBaja);
            session.getTransaction().commit();
            System.out.println("Se ha dado de baja al alumno "+ deBaja.nombre);
        }
        
        session.close();
        return 0;
    }
/*
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

*/

    @Override
    public int darDeBaja() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n-Dar de baja alumno-");
        System.out.print("Introduzca NIA del alumno: ");
        int nia = sc.nextInt();

        if (buscarAlumno(nia) != null) {
            try {

                this.lector = new Scanner(this.baseDeAlumnos);
                String comodin = this.lector.nextLine();
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
                String comodinMatricula = this.lector.nextLine();
                //matricula
                String aCopiarMatricula = "";
                while (this.lector.hasNextLine()) {
                    String matricula = this.lector.nextLine();
                    String[] data = matricula.split(" +");
                    if (Integer.parseInt(data[0]) != nia) {
                        aCopiar += ("\n" + matricula);
                    }
                }
                this.lector.close();
                this.escritor = new PrintWriter(new FileWriter(this.baseDeMatriculas, false));
                this.escritor.write(comodinMatricula);
                this.escritor.write(aCopiarMatricula);
                this.escritor.close();

                if (BaseDeDatos.modulos.actualizar(nia) == 0 && eliminarMatricula(nia) == 0) {
                    System.out.println("++Se ha dado de baja");
                    return 0;
                } else {
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

    //ACTUALIZAR
    @Override
    public int actualizar(short nia, int ID){
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        alumnoComodin = session.get(Alumno.class, (short)nia);

        if(alumnoComodin!=null){
            for (Matricula x : aCambiar.matriculas){
                if(x.idModulo==ID){
                    alumnoComodin.desmatricularModulo(ID);
                }
            }
            session.update(alumnoComodin);
            session.getTransaction().commit();
            //System.out.println("Se ha dado de baja al alumno "+ deBaja.nombre);
        }
        
        session.close();
        return 0;
    }
/* 
    public int actualizar(int id) {
        try {
            Scanner lectorTemporal = new Scanner(this.baseDeAlumnos);
            lectorTemporal.nextLine();

            while (lectorTemporal.hasNextLine()) {
                transformarAlumno(lectorTemporal.nextLine());

                if (this.alumnoComodin.comprobarMatricula()) {
                    if (this.alumnoComodin.comprobarModulo(id)) {
                        if (this.alumnoComodin.eliminarModulos(id) == -1) {
                            System.out.println("*Ha ocurrido un error, no se ha podido eliminar el modulo con ID de una matricula");
                        } else {
                            if (escribirAlumno(this.alumnoComodin.getIDENTIFICADOR()) == 0
                                    && escribirMatricula(this.alumnoComodin.getIDENTIFICADOR()) == 0) {
                                return 0;
                            }
                        }
                    }
                }
            }
            this.alumnoComodin = null;
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
    */

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

    //IMPRIMIR
    @Override
    public void listar() {
        System.out.println("\n-Listar alumnos-");
        try {
            this.lector = new Scanner(this.baseDeAlumnos);
            this.lector.nextLine();

            while (this.lector.hasNextLine()) {
                transformarAlumno(this.lector.nextLine());
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
            transformarAlumno(buscarAlumno(nia));
            if (this.alumnoComodin.comprobarMatricula()) {
                this.alumnoComodin.imprimirBoletin();
            } else {
                System.out.println("--Este alumno no tiene Matricula a?n");
            }
            this.alumnoComodin = null;
        } else {
            System.out.println("--No existe ese alumno en la base");
        }
    }
}

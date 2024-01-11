package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import centro.Alumno;
import centro.Alumnos;
import centro.Matricula;
import centro.Matriculas;
import centro.Modulo;
import centro.Modulos;
import centro.Notas;

import utilidades.App;
import utilidades.Lector;

public class ORM {
    private static Alumnos alumnos = new Alumnos();
    private static Modulos modulos = new Modulos();
    private static Matriculas matriculas = new Matriculas();
    public static final File importFile = new File("import.txt");

    private static Conexion conexion = new Conexion();

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

    private void exportarDatos() {
        System.out.println("\nEXPORTANDO...");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        Query queryAlu = session.createQuery("FROM Alumno", Alumno.class);
        Query queryModu = session.createQuery("FROM Modulo", Modulo.class);
        Query queryMatri = session.createQuery("FROM Matricula", Matricula.class);
        ArrayList<Notas> listaNotas = new ArrayList<>();

        try {
            if (!ORM.importFile.exists()) {
                System.out.println("Creando archivo de importe...");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(ORM.importFile));

            List<Alumno> alumnos = queryAlu.getResultList();
            for (Alumno alumno : alumnos) {
                writer.write(alumno.toString() + "\n");
                System.out.println("Exportando ALUMNO ID: " + alumno.getId() + " " + alumno.getNombre());
            }
            alumnos = null;

            List<Modulo> modulos = queryModu.getResultList();
            for (Modulo modulo : modulos) {
                writer.write(modulo.toString() + "\n");
                System.out.println("Exportando MODULO ID: " + modulo.getId() + " " + modulo.getNombre());
            }
            modulos = null;

            List<Matricula> matriculas = queryMatri.getResultList();
            for (Matricula matricula : matriculas) {
                listaNotas.add(matricula.getNotas());
                writer.write(matricula.toString() + "\n");
                System.out.println("Exportando MATRICULA ID: " + matricula.getId() + " Alu: "
                        + matricula.getAlumno().getNombre() + " Modu:" + matricula.getAlumno().getNombre());
            }
            matriculas = null;

            for (Notas nota : listaNotas) {
                writer.write(nota.toString() + "\n");
                System.out.println("Exportando NOTAS ID: " + nota.getId());
            }

            writer.close();
        } catch (IOException ex) {
            System.out.println("Error de tipo IOException. Error al acceder al fichero o al excribir.\n" + ex);
        } catch (Exception ex) {
            System.out.println("Error inesperado.\n" + ex);
        }
        session.close();
        System.out.println("Se han exportado los datos.");
    }

    /*
     * Alumno: Alumno::ID::NOMBRE
     * Modulo: Modulo::ID::NOMBRE
     * Notas: Notas::ID::NOTA01::NOTA02::NOTA03
     * Matricula: Matricula::ID::ID_ALU::ID_MODU::ID_NOTA::CALIFICACION
     */
    private void importarDatos() {
        System.out.println("\nIMPORTANDO...");
        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        ArrayList<String> matriculas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(importFile));
            String line;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                String[] datos = line.split("::");

                switch (datos[0]) {
                    case "Alumno":
                        Alumno alu = new Alumno(datos[2]);
                        alu.setId(Integer.parseInt(datos[1]));
                        System.out.println("Importando ALUMNO ID: " + datos[1] + " " + datos[2]);
                        session.saveOrUpdate(alu);
                        break;
                    case "Modulo":
                        Modulo modu = new Modulo(datos[2]);
                        modu.setId(Integer.parseInt(datos[1]));
                        System.out.println("Importando MODULO ID: " + datos[1] + " " + datos[2]);
                        session.saveOrUpdate(modu);
                        break;
                    case "Matricula":
                        matriculas.add(line); // sacamos las matriculas para más tarde porque hay que importar las notas
                                              // primero
                        break;
                    case "Notas":
                        Notas notas = new Notas();
                        notas.setId(Integer.parseInt(datos[1]));
                        notas.setNota1(Integer.parseInt(datos[2]));
                        notas.setNota2(Integer.parseInt(datos[3]));
                        notas.setNota3(Integer.parseInt(datos[4]));
                        System.out.println("Importando NOTAS ID: " + datos[1]);
                        session.saveOrUpdate(notas);
                        break;
                    default:
                        System.out.println("Formato de línea no admitida en la línea " + lineNum);
                }
            }
            session.getTransaction().commit();

            if (!matriculas.isEmpty()) {
                System.out.println("Importando matrículas...");

                session.beginTransaction();
                for (String matricula : matriculas) {
                    String[] datos = matricula.split("::");
                    Matricula matri = new Matricula();

                    System.out.println("Importando MATRICULA ID: " + datos[1] + " Alu: " + Integer.parseInt(datos[2])
                            + " Modu: " + Integer.parseInt(datos[3]) + " Notas: " + Integer.parseInt(datos[4]));

                    Query queryAlu = session.createQuery("FROM Alumno WHERE id = :nia").setParameter("nia",
                            Integer.parseInt(datos[2]));
                    Alumno alu = (Alumno) queryAlu.uniqueResult();
                    System.out.println(alu);
                    Query queryModu = session.createQuery("FROM Modulo WHERE id = :id").setParameter("id",
                            Integer.parseInt(datos[3]));
                    Modulo modu = (Modulo) queryModu.uniqueResult();
                    System.out.println(modu);
                    Query queryNota = session.createQuery("FROM Notas WHERE id = :id").setParameter("id",
                            Integer.parseInt(datos[4]));
                    Notas nota = (Notas) queryNota.uniqueResult();
                    System.out.println("Notas::" + nota.getId());

                    matri.setId(Integer.parseInt(datos[1]));
                    matri.setAlumno(alu);
                    matri.setModulo(modu);
                    matri.setNotas(nota);
                    matri.setCalificacion((datos[5]));

                    session.saveOrUpdate(matri);
                }
                session.getTransaction().commit();
            }

            System.out.println("Se han importado los datos.");
            reader.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error de tipo IOException. Error al acceder al fichero o al leer.\nPuede que el fichero no exista.\n"
                            + ex);
            return;
        } catch (Exception ex) {
            System.out.println("Error inesperado.\n" + ex);
        }
        session.close();
    }

    private void realizarOpcion(int choice) {
        Lector sc = new Lector(System.in);
        String opcion= "";
        switch (choice) {
            case 0:
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
                System.out.println("Esto sustituirá los datos previos si existen.\n¿Quieres continuar? [0] para contiuar, otra tecla para cancelar.");
                opcion=sc.leer();
                if(opcion.equals("0")){
                    exportarDatos();
                }else{
                    System.out.println("No se va a realizar acción, volviendo...");
                }
                break;
            case 5:
                System.out.println("Esto sustituirán los elementos que coincidan en ID.\n¿Quieres continuar? [0] para contiuar, otra tecla para cancelar.");
                opcion=sc.leer();
                if(opcion.equals("0")){
                    importarDatos();
                }else{
                    System.out.println("No se va a realizar acción, volviendo...");
                }
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
        try {
            conexion = new Conexion();
        } catch (IllegalStateException ex) {
            System.out.println(
                    "Fallo al conectarse con el servidor, comprueba que esté activo o que el driver sea correcto.\n"
                            + ex);
            return;
        } catch (Exception ex) {
            System.out.println("Error inesperado.\n" + ex);
            return;
        }

        int opcion;
        Session sesion = conexion.getSessionFactory().openSession();
        String resultado = (String) sesion.createNativeQuery("SELECT VERSION()").getSingleResult();
        System.out.println("La versión que estás usando es: " + resultado);

        do {
            opcion = menu();
            realizarOpcion(opcion);
            if (opcion == 0) {
                sesion.close();
            }
        } while (opcion != 0);
    }

    public Conexion conexion() {
        return conexion;
    }
}
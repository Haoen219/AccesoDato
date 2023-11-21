package centro;

import java.io.Serializable;

import persistencia.ORM;
import utilidades.App;
import utilidades.Lector;
import utilidades.TextoValor;

public class Notas extends Mantenimiento<Nota> implements Serializable {
    private long matricula;

    public Notas(long matricula) {
        this.matricula = matricula;
    }

    @Override
    public void Haz(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                Alta();
                break;
            case 2:
                Listado(1);
                break;
            case 3:
                ListadoModulo(1);
                break;
            default:
                System.out.println(App.ANSI_CYAN + "Hay que elegit una de las opciones (numero entre parentesis)"
                        + App.ANSI_WHITE);
        }
    }

    @Override
    public int Menu() {
        int option = -1;
        Lector in = new Lector(System.in);
        while ((option < 0)) {
            System.out.println("(0)- Salir");
            System.out.println("(1)- Calificar modulo");
            System.out.println("(2)- Boletin de notas");
            System.out.println("(3)- Acta de notas");
            option = in.leerEntero(0, 3);
        }
        in = null;
        System.gc();
        return option;
    }

    @Override
    public int Alta() {
        int retorno = 0;
        Lector in = new Lector(System.in);
        TextoValor par;
        for (Mantenible entrada : ORM.matriculas().elementos()) {
            float calificacion = -1;
            while (calificacion < 0) {
                System.out.println(((Matricula) entrada).modulo() + ": ");
                par = in.obtener(true);
                calificacion = par.real();
            }
            Nota nota = new Nota(matricula, 1, calificacion);
            if (((Matricula) entrada).notas().elementos().get(1) != null) {
                ((Nota) ((Matricula) entrada).notas().elementos().get(1)).calificacion(calificacion);
            } else {
                ((Matricula) entrada).notas().elementos().add(1, nota);
            }
            nota = new Nota(matricula, 2, calificacion);
            if (((Matricula) entrada).notas().elementos().get(2) != null) {
                ((Nota) ((Matricula) entrada).notas().elementos().get(2)).calificacion(calificacion);
            } else {
                ((Matricula) entrada).notas().elementos().add(2, nota);
            }
            nota = new Nota(matricula, 3, calificacion);
            if (((Matricula) entrada).notas().elementos().get(2) != null) {
                ((Nota) ((Matricula) entrada).notas().elementos().get(3)).calificacion(calificacion);
            } else {
                ((Matricula) entrada).notas().elementos().add(1, nota);
            }
            nota = new Nota(matricula, 0, calificacion);
            if (((Matricula) entrada).notas().elementos().get(0) != null) {
                ((Nota) ((Matricula) entrada).notas().elementos().get(1)).calificacion(calificacion);
            } else {
                ((Matricula) entrada).notas().elementos().add(0, nota);
            }
        }
        return retorno;
    }

    public int CalificaModulo() {
        int retorno = 0;
        Lector in = new Lector(System.in);
        int codigoModulo = 0;
        int codigoEvaluacion = -1;
        System.out.print("Dame el codigo del modulo: ");
        TextoValor par = in.obtener();
        codigoModulo = par.entero();
        System.out.print(
                "Dame el codigo de la evaluacion (Primera:1,Segunda:2,Tercera:3,Final:0 (Extraordinaria: Sobreescribe la final): ");
        par = in.obtener();
        codigoEvaluacion = par.entero();
        for (Mantenible entrada : ORM.matriculas().elementos()) {
            float calificacion = -1;
            if (((Matricula) entrada).modulo() == codigoModulo) {
                while (calificacion < 0) {
                    System.out.println(((Matricula) entrada).alumno() + ": ");
                    par = in.obtener(true);
                    calificacion = par.real();
                }
                Nota nota = new Nota(matricula, codigoEvaluacion, calificacion);
                if (((Matricula) entrada).notas().elementos().get(codigoEvaluacion) != null) {
                    ((Nota) ((Matricula) entrada).notas().elementos().get(codigoEvaluacion)).calificacion(calificacion);
                } else {
                    ((Matricula) entrada).notas().elementos().add(codigoEvaluacion, nota);
                }
            }
        }
        return retorno;
    }

    public int CalificaAlumno() {
        int retorno = 0;
        Lector in = new Lector(System.in);
        int codigoAlunmo;
        int codEvaluacion = -1;
        System.out.print("Dame el NIA del alumno: ");
        TextoValor par = in.obtener();
        codigoAlunmo = par.entero();
        System.out.print(
                "Dame el codigo de la evaluacion (Primera:1,Segunda:2,Tercera:3,Final:0 (Extraordinaria: Sobreescribe la final): ");
        par = in.obtener();
        codEvaluacion = par.entero();
        for (Mantenible entrada : ORM.matriculas().elementos()) {
            float calificacion = -1;
            if (((Matricula) entrada).alumno() == codigoAlunmo) {
                while (calificacion < 0) {
                    System.out.println(((Matricula) entrada).modulo() + ": ");
                    par = in.obtener(true);
                    calificacion = par.real();
                }
                Nota nota = new Nota(matricula, codEvaluacion, calificacion);
                if (((Matricula) entrada).notas().elementos().get(codEvaluacion) != null) {
                    ((Matricula) entrada).notas().elementos().set(codEvaluacion, nota);
                } else {
                    ((Matricula) entrada).notas().elementos().add(codEvaluacion, nota);
                }
            }
        }
        return retorno;
    }

    @Override
    public int Baja() {
        System.err.println("Unimplemented method 'Baja'");
        return -1;
    }

    @Override
    public int Modificacion() {
        System.err.println("Unimplemented method 'Modificacion'");
        return -1;
    }

    @Override
    public int Listado(int tabs) {
        int retorno = 0;
        Lector in = new Lector(System.in);
        int NIA;
        int codEvaluacion = -1;
        System.out.print("Dame el NIA del alumno: ");
        TextoValor par = in.obtener();
        NIA = par.entero();
        System.out.print("Dame el codigo de la evaluacion (Primera:1,Segunda:2,Tercera:3,Final (Extraordinaria):0 ");
        par = in.obtener();
        codEvaluacion = in.leerEntero(0, 3);
        for (Mantenible entrada : ORM.matriculas().elementos()) {
            String color = App.ANSI_PURPLE;
            if (((Matricula) entrada).alumno() == NIA) {
                boolean hayNota = true;
                System.out.print(App.ANSI_GREEN + ((Matricula) entrada).modulo() + App.ANSI_WHITE + ": -");
                hayNota &= (((Matricula) entrada).notas().elementos().get(codEvaluacion) != null);
                if (hayNota) {
                    hayNota &= (((Matricula) entrada).notas().elementos().get(codEvaluacion) != null);
                }
                if (hayNota) {
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 4f) {
                        color = App.ANSI_YELLOW;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 5f) {
                        color = App.ANSI_CYAN;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 7f) {
                        color = App.ANSI_BLUE;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 9f) {
                        color = App.ANSI_GREEN;
                    }
                    System.out.println(color
                            + ((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion()
                            + App.ANSI_WHITE);
                } else {
                    System.out.println(" -");
                }
            }
        }
        return retorno;
    }

    public int ListadoModulo(int tabs) {
        int retorno = 0;
        Lector in = new Lector(System.in);
        int Codigo;
        int codEvaluacion = -1;
        System.out.print("Dame el Codigo del modulo: ");
        TextoValor par = in.obtener();
        Codigo = par.entero();
        System.out.print("Dame el codigo de la evaluacion (Primera:1,Segunda:2,Tercera:3,Final (Extraordinaria):0 ");
        codEvaluacion = in.leerEntero(0, 3);
        for (Mantenible entrada : ORM.matriculas().elementos()) {
            if (((Matricula) entrada).modulo() == Codigo) {
                boolean hayNota = true;
                String color = App.ANSI_PURPLE;
                System.out.print(App.ANSI_GREEN + ((Matricula) entrada).alumno() + App.ANSI_WHITE + ": ");
                hayNota &= (((Matricula) entrada).notas().elementos().get(codEvaluacion) != null);
                if (hayNota) {
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 4f) {
                        color = App.ANSI_YELLOW;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 5f) {
                        color = App.ANSI_CYAN;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 7f) {
                        color = App.ANSI_BLUE;
                    }
                    if (((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion() > 9f) {
                        color = App.ANSI_GREEN;
                    }
                    System.out.println(color
                            + ((Nota) (((Matricula) entrada).notas().elementos().get(codEvaluacion))).calificacion()
                            + App.ANSI_WHITE);
                } else {
                    System.out.println("-");
                }
            }
        }
        return retorno;
    }

    @Override
    public int Importa(String nombreArchivo) {
        return 0;// A implementar
    }

    @Override
    public int Exporta(String nombreArchivo) {
        return 0;// A implementar
    }

    @Override
    public int Alta(Nota nuevo, Agrupacion[] dependencias, Integer id) {
        return 0;// A implementar
    }

    @Override
    public int Baja(Nota abandono, Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Modificacion(Nota modificado) {
        return 0;// A implementar
    }

    @Override
    public int Listado(Agrupacion[] dependencias) {
        return 0;// A implementar
    }
}
package centro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import utilidades.Lector;

/**
 *
 * @author haoen
 */
@Entity
@Table(name="Matricula")
public class Matricula {
    @Id
    @Column(name = "ID")
    private short ID;
    /*
    @OneToMany
    @JoinColumn(name ="Matricula_FK_alumno")
    short idAlumno;
    */
    @Column(name="ID Modulo")
    short idModulo;
    @Column(name="ID Notas")
    short idDetalle;
    @Column(name="Calificación")
    private String calificacion;

    public Matricula(){}

    public int modificarNota(){

        return 0;
    }

    public int calificar(){
        
        return 0;
    }

    public int mostrar(){

        return 0;
    }

    public int menu() {
        Lector sc= new Lector(System.in);
        System.out.println("");
        System.out.println("|---------Evaluar----------|");
        System.out.println("|0| Volver al menu previo  |");
        System.out.println("|1| Calificar              |");
        System.out.println("|2| Modificar              |");
        System.out.println("|3| Extraer bolet?n        |");
        System.out.println("|"+"-".repeat(26)+"|");
        System.out.print("OPCI?N: ");
        int opcion=sc.leerEntero(0,5);
        return opcion;
    }
}

package centro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utilidades.Lector;

@Entity
@Table(name = "Notas")
public class Notas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notas_id")
    int id;

    @Column(name = "nota1")
    private int nota1;
    @Column(name = "nota2")
    private int nota2;
    @Column(name = "nota3")
    private int nota3;

    public Notas() {
    }

    public void instanciarNotas() {
        this.nota1 = 0;
        this.nota2 = 0;
        this.nota3 = 0;
    }

    public void modificarNota(){
        Lector sc = new Lector(System.in);
        System.out.println("Notas:");
        System.out.println("\t1- Nota: "+this.nota1);
        System.out.println("\t2- Nota: "+this.nota2);
        System.out.println("\t3- Nota: "+this.nota3);
        System.out.println("Indica la nota a modificar:");
        int posicion = -1;
        while(posicion < 1 || posicion > 3){
            posicion = sc.leerEntero(1, 3);
            if(posicion < 1 || posicion > 3){
                System.out.println("No valida, intente de nuevo");
            }
        }
        
        System.out.println("Introduzca la nota deseada (0 al 10):");
        int nota = -1;
        while(nota < 0 || nota > 10){
            nota = sc.leerEntero(0, 10);
            if(nota < 0 || nota > 10){
                System.out.println("No valida, intente de nuevo");
            }
        }

        switch(posicion){
            case 1 -> this.nota1 = nota;
            case 2 -> this.nota2 = nota;
            case 3 -> this.nota3 = nota;
        }

    }

    //SETTERS
    public void setNota1(int nota){
        this.nota1=nota;
    }
    public void setNota2(int nota){
        this.nota2=nota;
    }
    public void setNota3(int nota){
        this.nota3=nota;
    }
    //GETTERS
    public int getId(){
        return this.id;
    }
    public int getNota1(){
        return this.nota1;
    }
    public int getNota2(){
        return this.nota2;
    }
    public int getNota3(){
        return this.nota3;
    }
}

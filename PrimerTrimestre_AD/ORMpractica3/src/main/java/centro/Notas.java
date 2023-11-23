package centro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Notas")
public class Notas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notas_id")
    int id;

    @Column(name = "nota1")
    private double nota1;
    @Column(name = "nota2")
    private double nota2;
    @Column(name = "nota3")
    private double nota3;

    public Notas() {
    }

    public void instanciarNotas() {
        this.nota1 = 0.0;
        this.nota2 = 0.0;
        this.nota3 = 0.0;
    }

    //SETTERS
    public void setNota1(Double nota){
        this.nota1=nota;
    }
    public void setNota2(Double nota){
        this.nota2=nota;
    }
    public void setNota3(Double nota){
        this.nota3=nota;
    }
    //GETTERS
    public double getId(){
        return this.id;
    }
    public double getNota1(){
        return this.nota1;
    }
    public double getNota2(){
        return this.nota2;
    }
    public double getNota3(){
        return this.nota3;
    }
}

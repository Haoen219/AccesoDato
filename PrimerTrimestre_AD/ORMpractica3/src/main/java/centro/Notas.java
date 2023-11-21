package centro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Notas")
public class Notas {
    @Id
    @Column(name="ID")
    short id;

    @Column(name="Nota1")
    private double nota1;
    @Column(name="Nota2")
    private double nota2;
    @Column(name="Nota3")
    private double nota3;

    public Notas(){}
}

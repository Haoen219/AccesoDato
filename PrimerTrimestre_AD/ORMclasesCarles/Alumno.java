package centro;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity()
@Table(name = "ALUMNES")
public class Alumno extends Mantenible {

    @Id
    @Column(name = "ID")
    private long id;// referencia a Persona
    @Column(name = "NIA")
    private String nia;

    public long id() {
        return id;
    }

    public String nia() {
        return nia;
    }

    public void nia(String nia) {
        this.nia = nia;
    }

    public Alumno(long id, String nia) {
        this.id = id;
        this.nia = nia;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Persona persona;

    public Persona persona() {
        return persona;
    }

    public void persona(Persona persona) {
        this.persona = persona;
    }
}

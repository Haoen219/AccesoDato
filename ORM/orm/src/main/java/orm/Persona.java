package orm;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="PERSONES")
public class Persona
{
    @Id
    @GeneratedValue
    private long id;
    @Column(name="DNI")
    private String nia;
    @Column(name="NOM")
    private String nombre;
    @Colum(name="COGNOM1")
    private String apellido1;
    @Colum(name="COGNOM2")
    private String apellido2;
    @Colum(name="DATANAIXEMENT")
    private String fechaNacimiento;
    @Colum(name="EDAD")
    private int edad;
    
    public long id()
    {
        return id;
    }
    public Persona(String nombre,String apellido1,String apellido2, String fechaNacimiento)
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        this.nombre=nombre;
        this.apellido1=apellido1;
        this.apellido2=apellido2;
        this.fechaNacimiento=fechaNacimiento;
        this.edad = periodo.getYears();
    }
    public void fechaNacimiento(String fechaNacimiento)
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        this.fechaNacimiento=fechaNacimiento;
        this.edad=periodo.getYears();
    }
    public int edad()
    {
        return edad;
    }
    public String fechaNacimiento()
    {
        return fechaNacimiento;
    }
    public String nombre(boolean dePila)
    {
        if (dePila)       
        {
            return nombre+" "+apellido1+" "+apellido2;
        }
        else
        {
            return apellido1+" "+apellido2+"; "+nombre;
        }
    }
    public String nombre()
    {
        return nombre(false);
    }
    public void nombre(String nombre)
    {
        this.nombre=nombre;
    }
    public String nia()
    {
        return nia;
    }
    public void nia(String nia)
    {
        this.nia=nia;
    }
    public void apellido1(String apellido)
    {
        this.apellido1=apellido;
    }
    public void apellido2(String apellido)
    {
        this.apellido2=apellido;
    }
    public void apellidos(String apellido1,String apellido2)
    {
        this.apellido1=apellido1;
        this.apellido2=apellido2;
    }
    public void nombre(String nombre,String apellido1,String apellido2)
    {
        this.nombre=nombre;
        this.apellido1=apellido1;
        this.apellido2=apellido2;
    }
}
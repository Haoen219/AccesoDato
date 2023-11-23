/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package centro;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 *
 * @author haoen
 */
@Entity
@Table(name="Alumno")
public class Alumno{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alumno_id")
    private int ID;
    @Column(name="alumno_nombre")
    private String nombre;
    @ManyToOne(targetEntity = Matricula.class, cascade = CascadeType.ALL)
    @JoinColumn(name="matricula_id")
    private Set<Matricula> matriculas;

    public Alumno(){}
    public Alumno(String nombre){
        this.nombre=nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    public int getID() {
        return this.ID;
    }
    
    public Set<Matricula> getMatriculas(){
        return this.matriculas;
    }
    
    //MODULO
    //instanciar nuevo matricula en "matriculas" con el ID_MODULO de id
    public int matricularModulo(int id){
        if(this.matriculas==null){
            this.matriculas=new HashSet<Matricula>();
        }
        Matricula matricula = new Matricula();
        matricula.setAlumno(this.ID);
        matricula.setModulo(id);

        if(this.matriculas.add(matricula)){
            return 0;
        }
        return -1;
    }
    
    //IMPRIMIR
    public void imprimir() {
        System.out.printf("NIA: %-8d Nombre: %-30s",this.getID(), this.getNombre());
        if(this.matriculas!=null){
            System.out.printf(" Modulos: %-2d\n",this.matriculas.size());
        }else{
            System.out.println(" -Sin matricula-");
        }
    }
//    public void imprimirBoletin() {
//        imprimir();
//        imprimirModulos();
//    }
//    public void imprimirModulos() {
//        System.out.println("Matricula: ");
//        this.matricula.imprimirModulos();
//    }
}

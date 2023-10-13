/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.iv._sisform_fichero;

/**
 *
 * @author haoen
 */
public class Alumno extends BaseAlumnoModulo{
    private Matricula matricula;
    
    //Builder
    public Alumno(String nombre, int NIA){
        super(nombre, NIA);
    }
    
    //MATRICULA
    public boolean comprobarMatricula(){
        if(this.matricula==null){
            return false;
        }
        return true;
    }
    public boolean comprobarModulo(int id){
        return this.matricula.comprobarModulo(id);
    }
    public int eliminarModulos(int id){                    //sirve para eliminar un modulo de la matricula
        if(this.matricula.eliminarModulo(id)==0){
            return 0;
        }
        return -1;
    }
    public int modificarNota(int id, int posicion, double nota){
        if(this.matricula.modificarNota(id, posicion, nota)==0){
            return 0;
        }
        return -1;
    }
    public int evaluarModulo(int id, String calificacion){
        if(this.matricula.evaluarModulo(id, calificacion)==0){
            return 0;
        }
        return -1;
    }
    
    
    //MODULO
    public int matricularModulo(int id){        //no necesita comprobar porque es llamado de otra funcion comprobada
        if (this.matricula==null){
            this.matricula= new Matricula();        //instancia una matricula si no tiene, haciendo que
        }                                           //un alumno no tenga matricula hasta no tener un modulo
        if(this.matricula.matricularModulo(id)==0){
            return 0;
        }
        return -1;
    }
    
    //GUARDAR EN FICHERO
    public String formatoFichero(){
        //NIA NOMBRE-APELLIDO1-APELLIDO2 NumMODULOS
        String alumno= String.format("%n%-4d %-25s ", this.getIDENTIFICADOR(), this.getNombre().replace(' ', '-'));
        if(comprobarMatricula()){
            alumno+=this.matricula.getNumeroModulos();
        }else{
            alumno+="-Sin-Matricula-";
        }
        return alumno;
    }
    public String formatoFicheroMatricula(){
        //NIA ID_NOTA-NOTA-NOTA_CALIFICACION ID_NOTA-NOTA-NOTA_CALIFICACION ...
        String matricula=String.format("%n%-8d %s", this.getIDENTIFICADOR(), this.matricula.formatoFichero());
        return matricula;
    }
    
    
    //IMPRIMIR
    public void imprimir() {
        System.out.printf("NIA: %-8d Nombre: %-30s",this.getIDENTIFICADOR(), this.getNombre());
        if(comprobarMatricula()){
            System.out.printf(" Modulos: %-2d\n",this.matricula.getNumeroModulos());
        }else{
            System.out.println(" -Sin matricula-");
        }
    }
    public void imprimirBoletin() {
        imprimir();
        imprimirModulos();
    }
    public void imprimirModulos() {
        System.out.println("Matricula: ");
        this.matricula.imprimirModulos();
    }
}

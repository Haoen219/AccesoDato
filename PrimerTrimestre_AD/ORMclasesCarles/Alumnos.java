package centro;

import org.hibernate.Session;

import persistencia.ORM;
import utilidades.Lector;

public class Alumnos extends Mantenimiento<Alumno> {
    public Alumnos() {
        // A implementar
    }

    @Override
    public int Alta() {
        Lector lector = new Lector(System.in);
        Persona persona = new Persona(lector.obtener("Introduce el nombre: ", "%s").cadena(),
                lector.obtener("Introduce el primer apellido: ", "%s").cadena(),
                lector.obtener("Introduce el segundo apellido: ", "%s").cadena(),
                lector.obtener("Introduce la fecha de nacimiento (DD/MM/AAAA): ", "%s").cadena());
        Alumno alumno = new Alumno(persona.id(), lector.obtener("Introduce el NIA: ", "%s").cadena());
        alumno.persona(persona);

        Session session = new ORM().conexion().getSessionFactory().openSession();
        session.beginTransaction();

        session.save(alumno);

        session.getTransaction().commit();
        session.close();
        return 0;
    }

    @Override
    public int Baja() {
        return 0;// A implementar
    }

    @Override
    public int Modificacion() {
        return 0;// A implementar
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
    public int Alta(Alumno nuevo, Agrupacion[] dependencias, Integer id) {
        return 0;// A implementar
    }

    @Override
    public int Baja(Alumno abandono, Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Modificacion(Alumno modificado) {
        return 0;// A implementar
    }

    @Override
    public int Listado(Agrupacion[] dependencias) {
        return 0;// A implementar
    }

    @Override
    public int Listado(int tabs) {
        return 0;// A implementar
    }
}
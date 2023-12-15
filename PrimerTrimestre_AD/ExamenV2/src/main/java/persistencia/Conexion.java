package persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class Conexion
{
    private final SessionFactory sessionFactory;
    public Conexion()
    {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(centro.Alumno.class);
        configuration.addAnnotatedClass(centro.Profesor.class);
        configuration.addAnnotatedClass(centro.Modulo.class);
        configuration.addAnnotatedClass(centro.Matricula.class);
        configuration.addAnnotatedClass(centro.Notas.class);
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
    }
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.hibernate.Configuration;
import org.hibernate.SessionFactory;

/**
 *
 * @author 2DAM_Zhang_Haoen
 */
public class Conection {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try{
            return new Configuration().configurations().buildSessionFactory();
        }catch (Throwable e){
            System.out.println("Error al crear sessionFactory");
            throw new ExceptionInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}

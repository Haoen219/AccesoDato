package persistencia;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import utilidades.App;

public class Conexion {
    final static String mySQL_URL = "java:comp/env/jdbc:mysql://127.0.0.1:3306/haoen";
    final static String postgresSQL_URL = "java:comp/env/jdbc:postgresql://127.0.0.1:5432/Haoen";
    final static String mySQL_USER = "root";
    final static String mySQL_PASS = "";
    final static String postgresSQL_USER = "postgres";
    final static String postgresSQL_PASS = "user";

    Context ctx;
    DataSource dataSource;

    public Conexion() {
        try {
            ctx = new InitialContext();

            switch (App.getOpcion()) {
                case 1:
                    dataSource = (DataSource) ctx.lookup(mySQL_URL);
                    break;
                case 2:
                    dataSource = (DataSource) ctx.lookup(postgresSQL_URL);
                    break;

            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public DataSource getDataSource(){
        return dataSource;
    }

    public Connection getConnection(){
        try{
            switch (App.getOpcion()) {
                case 1: return this.dataSource.getConnection(mySQL_USER, mySQL_PASS);
                case 2: return this.dataSource.getConnection(postgresSQL_USER, postgresSQL_PASS);
            }
        }catch(Exception ex){
            System.out.println("Error\n"+ex);
        }
        return null;
    }
}
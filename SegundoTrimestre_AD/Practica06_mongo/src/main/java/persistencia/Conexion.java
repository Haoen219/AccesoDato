package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import utilidades.App;

public class Conexion {
    final static String mySQL_URL = "jdbc:mysql://127.0.0.1:3306/";
    final static String mySQL_BDD = "haoen";
    final static String mySQL_USER = "root";
    final static String mySQL_PASS = "";

    final static String postgresSQL_URL = "jdbc:postgresql://127.0.0.1:5432/";
    final static String postgreSQL_BDD = "Haoen";
    final static String postgresSQL_USER = "postgres";
    final static String postgresSQL_PASS = "user";

    // SQL
    //final private String borrarTablas = "DROP TABLE IF EXISTS alumno, modulo, notas, matricula;";
    // final private String borrarFk = "ALTER TABLE IF EXISTS matricula DROP
    // CONSTRAINT alumno_nia, DROP CONSTRAINT modulo_id, DROP CONSTRAINT notas_id;";
    final private String crearTabAlu = "CREATE TABLE IF NOT EXISTS alumno (alumno_nia VARCHAR(20) PRIMARY KEY, alumno_nombre VARCHAR(75))";
    final private String crearTabMod = "CREATE TABLE IF NOT EXISTS modulo (modulo_id VARCHAR(20) PRIMARY KEY, modulo_nombre VARCHAR(50))";
    final private String crearTabNot = "CREATE TABLE IF NOT EXISTS notas (notas_id VARCHAR(20) PRIMARY KEY, nota1 INT, nota2 INT, nota3 INT)";
    final private String crearTabMat = "CREATE TABLE IF NOT EXISTS matricula (" +
            "matricula_id VARCHAR(20) PRIMARY KEY," +
            "alumno_nia VARCHAR(20)," +
            "modulo_id VARCHAR(20)," +
            "notas_id VARCHAR(20)," +
            "calificacion VARCHAR(255)," +
            "FOREIGN KEY (alumno_nia) REFERENCES alumno(alumno_nia)," +
            "FOREIGN KEY (modulo_id) REFERENCES modulo(modulo_id)," +
            "FOREIGN KEY (notas_id) REFERENCES notas(notas_id))";

    private Connection conection;

    public Conexion() {
        try {
            switch (App.getOpcion()) {
                case 1:
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conection = DriverManager.getConnection(mySQL_URL + mySQL_BDD, mySQL_USER, mySQL_PASS);
                    break;
                case 2:
                    Class.forName("org.postgresql.Driver");
                    conection = DriverManager.getConnection(postgresSQL_URL + postgreSQL_BDD, postgresSQL_USER,
                            postgresSQL_PASS);
                    break;
            }

            Statement statement = conection.createStatement();

            //statement.execute(borrarTablas);
            statement.execute(crearTabAlu);
            statement.execute(crearTabMod);
            statement.execute(crearTabNot);
            statement.execute(crearTabMat);

            statement.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error en foreignkey");
            System.out.println(e);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return this.conection;
    }
}
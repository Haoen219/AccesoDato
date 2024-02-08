package persistencia;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import utilidades.App;
import utilidades.CRUD;
import utilidades.CRUD_EXIST;

public class Conexion {
    final static String mySQL_URL = "jdbc:mysql://127.0.0.1:3306/";
    final static String mySQL_BDD = "haoen";
    final static String mySQL_USER = "root";
    final static String mySQL_PASS = "";

    final static String postgresSQL_URL = "jdbc:postgresql://127.0.0.1:5432/";
    final static String postgreSQL_BDD = "Haoen";
    final static String postgresSQL_USER = "postgres";
    final static String postgresSQL_PASS = "user";

    // private static String mongoDB_URL(String user, String password) {
    //     return "mongodb://" + user + ":" + password + "@127.0.0.1:27017/";
    // }

    final static String mongoDB_URL = "mongodb://127.0.0.1:27017/";
    final static String mongoDB_BDD = "Haoen";
    final static String mongoDB_USER = "";
    final static String mongoDB_PASS = "";

    // ExistDB
    final private String exist_URL = "xmldb:exist://localhost:8080/exist/xmlrpc/haoen";
    final private String exist_BDD = "/haoen"; // por alguna razon tengo que poner el nombre de BDD 2 veces
    final private String exist_USER = "admin";
    final private String exist_PASS = "admin";

    private Connection conection;
    private MongoClient mongoClient;
    Collection existCollection;

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
                case 3:
                    // DESCOMENTAR SEGUN SI TIENE CONTRASEÑA O NO
                    // mongoClient = MongoClients.create(mongoDB_URL(mongoDB_USER, mongoDB_PASS));
                    // Con Contraseña:
                    mongoClient = MongoClients.create(mongoDB_URL); // Sin contraseña
                    break;
                case 4:
                    // INSTANCIAR DRIVER
                    Database database = (Database) Class.forName("org.exist.xmldb.DatabaseImpl")
                            .getDeclaredConstructor().newInstance();
                    DatabaseManager.registerDatabase(database);
                    existCollection = DatabaseManager.getCollection(exist_URL + exist_BDD, exist_USER, exist_PASS);
                    break;

            }

            if (App.getOpcion() < 3) {
                Statement statement = conection.createStatement();
                statement.execute(CRUD.crearTabAlu);
                statement.execute(CRUD.crearTabMod);
                statement.execute(CRUD.crearTabNot);
                statement.execute(CRUD.crearTabMat);
                statement.close();
            }

            if (App.getOpcion() == 4) {
                if (existCollection.getResourceCount() < 4) {
                    crearTabla(CRUD.alumno_tabla, CRUD_EXIST.alumno_raiz);
                    crearTabla(CRUD.modulo_tabla, CRUD_EXIST.modulo_raiz);
                    crearTabla(CRUD.matricula_tabla, CRUD_EXIST.matricula_raiz);
                    crearTabla(CRUD.notas_tabla, CRUD_EXIST.notas_raiz);
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error en foreignkey");
            System.out.println(e);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (XMLDBException e) {
            System.out.println("Error con el BDD ExistDB" + e);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.out.println("Error capturado accediendo a existDB");
            e.printStackTrace();
        }
    }

    public Collection getExistCollection() {
        return existCollection;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoClient.getDatabase(mongoDB_BDD);
    }

    public Connection getConnection() {
        return conection;
    }

    public boolean crearTabla(String tabla, String raizBase) {
        try {
            XMLResource resource = (XMLResource) existCollection.createResource(tabla + ".xml", "XMLResource");
            String baseRoot = "<"+raizBase+"></"+raizBase+">";
            resource.setContent(baseRoot);

            existCollection.storeResource(resource);
            System.out.println("La colección " + tabla + " ha sido creada.");
            return true;
        } catch (XMLDBException e) {
            System.out.println("No se ha podido crear la colección: " + tabla);
            e.printStackTrace();
        }
        return false;
    }
}
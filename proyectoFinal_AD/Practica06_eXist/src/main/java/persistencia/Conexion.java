package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
    // private static String mongoDB_URL(String user, String password) {
    // return "mongodb://" + user + ":" + password + "@127.0.0.1:27017/";
    // }

    final static String mongoDB_URL = "mongodb://127.0.0.1:27017/";
    final static String mongoDB_BDD = "Haoen";
    final static String mongoDB_USER = "";
    final static String mongoDB_PASS = "";

    private MongoClient mongoClient;

    public Conexion() {
        // DESCOMENTAR SEGUN SI TIENE CONTRASEÑA O NO
        // mongoClient = MongoClients.create(mongoDB_URL(mongoDB_USER, mongoDB_PASS));
        // Con Contraseña:
        mongoClient = MongoClients.create(mongoDB_URL); // Sin contraseña
    }

    public MongoDatabase getMongoDatabase() {
        return mongoClient.getDatabase(mongoDB_BDD);
    }
}
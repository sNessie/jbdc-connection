package connections;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        //Connection - java.sql
        //ResultSet
        //PrepareStatement

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/exchange", "postgres", "postgres" );
            getDbInfo(connection);
        } catch (ClassNotFoundException  | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getDbInfo(Connection connection){
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(
                    "Database name: " + metaData.getDatabaseProductName() + "\n" +
                            "database version: " + metaData.getDatabaseProductVersion() + "\n" +
                            "driver version: " + metaData.getDriverVersion()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

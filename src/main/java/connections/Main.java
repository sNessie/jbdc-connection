package connections;

import javax.xml.crypto.Data;
import java.sql.*;

import static connections.Properties.*;

public class Main {

    public static void main(String[] args) {
        //Connection - java.sql
        //ResultSet
        //PrepareStatement
        Connection connection = createConnection();
        query(connection, "select * from exchanges");
    }

    public static Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            getDbInfo(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            getDbInfo(connection);
        }
        return connection;
    }

    public static void query(Connection connection, String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getDbInfo(Connection connection) {
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

package connections;

import models.ExchangeModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connections.Properties.*;

public class Main {

    public static void main(String[] args) {
        //Connection - java.sql
        //ResultSet
        //PrepareStatement
        Connection connection = createConnection();
//        List<ExchangeModel> exchanges = query(connection, "select * from exchanges");
//        exchanges.forEach(System.out::println);
        ExchangeModel exchangeModel = new ExchangeModel
                .Builder()
                .id(3)
                .name("Bogusia")
                .amount(new BigDecimal(1300))
                .build();
        insertIntoDb(connection, exchangeModel);
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

    public static List<ExchangeModel> query(Connection connection, String sql) {
        List<ExchangeModel> results = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                results.add(new ExchangeModel
                        .Builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .amount(new BigDecimal(resultSet.getString("amount")))
                        .build());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;

    }

    public static int insertIntoDb(Connection connection, ExchangeModel record){
        String sql = "insert into exchanges(id, name, amount) values(?, ?, ?)";
        int result = 0;
        // PreparedStatement in try() because then it's autoclosable
        try(final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, record.getId());
            preparedStatement.setString(2, record.getName());
            preparedStatement.setBigDecimal(3, record.getAmount());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

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

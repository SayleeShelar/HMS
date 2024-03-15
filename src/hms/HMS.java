package hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HMS {

    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error establishing connection: " + e.getMessage());
        }
        return null;
    }

    public static void displayTableData(Connection connection) {
        String query = "SELECT * FROM PATIENT";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("PATIENT_ID\tNAME\tCONTACT_NO\tAGE\tADDRESS\tANY_MOJOR_DISEASE_SUFFERED_EARLIER");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("PATIENT_ID") + "\t" +
                        resultSet.getString("NAME") + "\t" +
                        resultSet.getString("CONTACT_NO") + "\t" +
                        resultSet.getInt("AGE") + "\t" +
                        resultSet.getString("ADDRESS") + "\t" +
                        resultSet.getString("ANY_MAJOR_DISEASE_SUFFERED_EARLIER")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching table data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "Shriram@11";

        Connection connection = getConnection(url, username, password);
        if (connection != null) {
            System.out.println("Connection to the database was successful!");
            displayTableData(connection);

            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}

package com.uzair.java;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/*
   * Matillion Java Test
   * 20/04/2019
   * Uzair Foolat
   * Database Retrieval
 */

public class Main {

    public static void main(String[] args) {
        //The program requires three arguments with spaces to split them. A lack of arguments or typing help will trigger this action.
        if (args.length != 3 || args[0].equalsIgnoreCase("help")) {
            System.out.println("This application requires three parameters for operation which are split using spaces.");
            System.out.println("The first parameter needs to be a numerical value for department ID");
            System.out.println("The second parameter needs to be the pay type");
            System.out.println("The final parameter needs to be the education level");
            System.out.println("For example, type 'java Main 18 Hourly Graduate'");
            return;
        }
        Connection connection = null;

        //String query = "SELECT * FROM position";

        //SQL statement which will select the values based on department ID, pay type and education level.
        String query = "SELECT * FROM employee INNER JOIN department " +
                "ON employee.department_id = department.department_id " +
                "INNER JOIN position ON employee.position_id = position.position_id " +
                "WHERE department.department_id = '" + args[0] +
                "' AND pay_type = '" + args[1] +
                "' AND education_level LIKE '" + args[2] + "%'";

        //Assigns the properties of the database from a file allowing developers to change them if necessary.
        try (FileInputStream fileInputStream = new FileInputStream("database.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            // Database parameters
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            // Creates a connection to the database
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Successfully connected");
            } catch (SQLException s) {
                System.out.println(s.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // If there is a database connection available, execute the query or throw an error.
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    if (!resultSet.next()) {
                        System.out.println("Unfortunately no results were returned with the given parameters.");
                    } else {
                        System.out.println("Total columns: " + resultSetMetaData.getColumnCount() + " - Application by Uzair Foolat");
                    }
                    while (resultSet.next()) {
                        // Will print each line of the results based on the size of the returned data.
                        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                            System.out.println(resultSetMetaData.getColumnName(i) + ": " + resultSet.getString(i) + ", ");
                        }
                        //System.out.println(resultSetMetaData.getColumnName(1) + ": " + resultSet.getString(1)+ "," + resultSetMetaData.getColumnName(2) + ": " + resultSet.getString(2));
                    }
                }
                //connection.close(); - This is not required as the connection closes upon execution of the program results.
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
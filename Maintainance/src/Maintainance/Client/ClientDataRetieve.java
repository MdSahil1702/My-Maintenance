package Maintainance.Client;


import Maintainance.User.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;


public class ClientDataRetieve {

    public static void clientDataRetieve(List<Client> clients) {

clients.clear();
        String url = "jdbc:mysql://localhost:3306/Maintenaince";
        String username = "root";
        String password = "Sahilrida@1729";

        String query1 = "SELECT state, district, city, apartment, pincode, uniquekey, username,password,AccountNumber,AccountName FROM buildingregistered";

        String add[][] = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            // Fetch data from buildingregistered
            try (ResultSet resultSet1 = statement.executeQuery(query1)) {
                // Use ResultSet.TYPE_SCROLL_INSENSITIVE to enable bidirectional navigation


                // Get the number of rows in resultSet1


                while (resultSet1.next()) {
                    // Retrieve data from each row and store it in a 2D array
                    String state = resultSet1.getString("state");
                    String district = resultSet1.getString("district");
                    String city = resultSet1.getString("city");
                    String apartment = resultSet1.getString("apartment");
                    String pincode = resultSet1.getString("pincode");
                    String uniqueKey = resultSet1.getString("uniquekey");
                    String userpassword = resultSet1.getString("password");
                    String accountnumber = resultSet1.getString("AccountNumber");
                    String accountname = resultSet1.getString("AccountName");


                    String username1 = resultSet1.getString("username");


                    clients.add(new Client(state, district, city, apartment, pincode, username1, userpassword, uniqueKey, accountnumber, accountname));


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



package Maintainance.User;


import java.sql.*;

import java.util.List;


public  class UserDataRetieve {

    public static void loginuser(List<User> users) {


users.clear();
        String url = "jdbc:mysql://localhost:3306/Maintenaince";
        String username = "root";
        String password = "Sahilrida@1729";

        String query1 = "SELECT state, district, city, apartment, pincode, uniquekey FROM buildingregistered";
        String query2 = "SELECT firstname, lastname, username, password, phoneno, roomno, aptkey, wing  FROM buildingmember";

        String add[][]=null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            // Fetch data from buildingregistered
            try (ResultSet resultSet1 = statement.executeQuery(query1)) {
                // Use ResultSet.TYPE_SCROLL_INSENSITIVE to enable bidirectional navigation
                int rowCount = 0;

                // Get the number of rows in resultSet1
                if (resultSet1.last()) {
                    rowCount = resultSet1.getRow();
                    resultSet1.beforeFirst(); // Move the cursor back to before the first row
                }

                add = new String[rowCount][6]; // Initialize the array
                int l = 0;
                while (resultSet1.next()) {
                    // Retrieve data from each row and store it in a 2D array
                    add[l][0] = resultSet1.getString("state");
                    add[l][1] = resultSet1.getString("district");
                    add[l][2] = resultSet1.getString("city");
                    add[l][3] = resultSet1.getString("apartment");
                    add[l][4] = resultSet1.getString("pincode");
                    add[l][5] = resultSet1.getString("uniquekey");
                    l++;
                }
            }

            // Fetch data from buildingmember and create User objects
            try (ResultSet resultSet2 = statement.executeQuery(query2)) {

                while (resultSet2.next()) {
                    String aptkey = resultSet2.getString("aptkey");
                    String state1 = "", district1 = "", city1 = "", apartment1 = "", pincode1 = "";

                    // Find matching data from buildingregistered based on aptkey
                    for (int j = 0; j < add.length; j++) {
                        if (aptkey.equals(add[j][5])) {
                            state1 = add[j][0];
                            district1 = add[j][1];
                            city1 = add[j][2];
                            apartment1 = add[j][3];
                            pincode1 = add[j][4];
                            break; // Exit loop once the matching data is found
                        }
                    }

                    // Create User object and add it to the list
                    String fname = resultSet2.getString("firstname");
                    String lname = resultSet2.getString("lastname");
                    String username1 = resultSet2.getString("username");
                    String userpassword = resultSet2.getString("password");
                    String roomno = resultSet2.getString("roomno");
                    String phoneno = resultSet2.getString("phoneno");
                    String wing= resultSet2.getString("wing");
                    String uniquekey=resultSet2.getString("aptkey");


                        users.add(new User(state1, district1, city1, apartment1, fname, lname, username1, userpassword, pincode1, phoneno, roomno,wing,uniquekey));

//


                }

                // Now 'users' list contains all the User objects mapped from the data retrieved from buildingmember and buildingregistered tables
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
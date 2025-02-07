package Maintainance.Database;

import java.sql.*;

public class Database {

    public static void DatabaseInsert(String statement) throws Exception {

        String sql = statement;
        // Correct JDBC URL format
        String url = "jdbc:mysql://localhost:3306/Maintenaince"; // Replace "your_database_name"
        String username = "root";
        String password = "Sahilrida@1729";

        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establishing the connection
        Connection con = DriverManager.getConnection(url, username, password);

        // Creating the statement
        Statement st = con.createStatement();

        // Executing the statement
        st.executeUpdate(sql);

        // Close resources
        st.close();
        con.close();
    }
}

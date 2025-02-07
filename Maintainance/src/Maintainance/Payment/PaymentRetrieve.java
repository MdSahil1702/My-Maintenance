package Maintainance.Payment;


import java.sql.*;
import java.util.List;

public class PaymentRetrieve {
    public static void paymentRetrieve(List<Payment> payments) {
payments.clear();
        String url = "jdbc:mysql://localhost:3306/Maintenaince";
        String username = "root";
        String password = "Sahilrida@1729";

        String query1 = "SELECT uk,uid,amount,date,month,year,status FROM payment";


        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {


            try (ResultSet resultSet1 = statement.executeQuery(query1)) {


                while (resultSet1.next()) {

                    String uid = resultSet1.getString("uid");
                    String month = resultSet1.getString("month");
                    String date = resultSet1.getString("date");
                    String status = resultSet1.getString("status");
                    float amount = resultSet1.getFloat("amount");
                    int year = resultSet1.getInt("year");
                    String uk = resultSet1.getString("uk");
                    payments.add(new Payment(uk, uid, month, status, date, amount, year));

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

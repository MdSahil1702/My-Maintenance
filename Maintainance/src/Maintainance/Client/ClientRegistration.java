package Maintainance.Client;
import Maintainance.Database.Database;
import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;
import Maintainance.User.User;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientRegistration {

    public static void clientRegistration(List<Client>clients, List<User> users, List<Payment>payments) {
        Scanner sc = new Scanner(System.in);


        System.out.println("Client Registration");

        if (!TablePresent("buildingregistered")) {
            try {
                Database.DatabaseInsert("CREATE TABLE BuildingRegistered(UniqueKey VARCHAR(20) PRIMARY KEY, Pincode INT, State VARCHAR(50), District VARCHAR(50), City VARCHAR(50), Apartment VARCHAR(50), Username VARCHAR(50) UNIQUE, Password VARCHAR(50), AccountNumber VARCHAR(15) UNIQUE, AccountName VARCHAR(50))");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


//
        if (!TablePresent("buildingmember")) {
            try {
                Database.DatabaseInsert("CREATE TABLE BuildingMember(AptKey VARCHAR(20), FOREIGN KEY(AptKey) REFERENCES BuildingRegistered(UniqueKey), Wing VARCHAR(20), RoomNo Int, FirstName VARCHAR(50), LastName VARCHAR(50), PhoneNo DOUBLE, Username VARCHAR(50) UNIQUE, Password VARCHAR(50))");



            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            System.out.println("Enter 'exit' to stop registration");
            System.out.print("Enter Your Address Pincode: ");
            String pincode = sc.nextLine().trim();
            if (pincode.equalsIgnoreCase("exit")) break;
            if (pincode.length() != 6 || !pincode.matches("\\d+")) {
                System.out.println("Pincode should be 6 digits. Please enter again.");
                continue;
            }

            System.out.print("Enter Your State: ");
            String state = sc.nextLine().trim().toLowerCase();
            if (state.equalsIgnoreCase("exit")) break;

            System.out.print("Enter Your District: ");
            String district = sc.nextLine().trim().toLowerCase();
            if (district.equalsIgnoreCase("exit")) break;

            System.out.print("Enter Your City: ");
            String city = sc.nextLine().trim().toLowerCase();
            if (city.equalsIgnoreCase("exit")) break;

            System.out.print("Enter Your Apartment: ");
            String apartment = sc.nextLine().trim().toLowerCase();
            if (apartment.equalsIgnoreCase("exit")) break;

            String username;
            while (true) {
                System.out.print("Create Your Username: ");
                username = sc.nextLine().trim().toLowerCase();
                if (username.equalsIgnoreCase("exit")) break;
                boolean found = false;

                for (Client c : clients) {

                    if (username.equals(c.getUsername())) {
                        System.out.println("Username already exists. Please choose another one.");
                        found = true;
                        break;
                    }
                }
                if (!found) break;
            }


            if (username.equalsIgnoreCase("exit")) break;

            System.out.print("Create Your Password: ");
            String userpassword = sc.nextLine().trim();
            if (userpassword.equalsIgnoreCase("exit")) break;

            String uniqueKey;

            while (true) {
                System.out.print("Create Your Unique Key (Building members can directly register using this key): ");
                uniqueKey = sc.nextLine().trim();
                if (uniqueKey.equalsIgnoreCase("exit")) break;
                boolean found = false;

                for (Client c : clients) {

                    if (uniqueKey.equals(c.getUniqueKey())) {
                        System.out.println("This key already exists. Please choose another one.");
                        found = true;
                        break;
                    }
                }
                if (!found) break;
            }


            try {
                Database.DatabaseInsert("INSERT INTO BuildingRegistered(UniqueKey, Pincode, State, District, City, Apartment, Username, Password) VALUES ('" + uniqueKey + "', " + pincode + ", '" + state + "', '" + district + "', '" + city + "', '" + apartment + "', '" + username + "', '" + userpassword + "')");
                RetrievingAll.retrieveAll(users,clients,payments);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.print("How Many Wings Does the Apartment Have? (If no wings, enter - 0): ");
            int wingCount = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            String[] wings = new String[wingCount];
            if (wingCount > 0) {
                for (int i = 0; i < wingCount; i++) {
                    System.out.print("Enter Name of Wing " + (i + 1) + ": ");
                    wings[i] = sc.nextLine().trim().toLowerCase();
                }

                System.out.print("How Many Floors Does Each Wing Have? ");
                int floorCount = sc.nextInt();
                System.out.print("How Many Rooms at Each Floor? ");
                int roomCount = sc.nextInt();

                for (String wing : wings) {
                    for (int floor = 1; floor <= floorCount; floor++) {
                        for (int room = 1; room <= roomCount; room++) {
                            String roomNumber = String.format("%d%02d", floor, room);

                            try {
                                Database.DatabaseInsert("INSERT INTO BuildingMember(AptKey, Wing, RoomNo) VALUES ('" + uniqueKey + "', '" + wing + "', " + roomNumber + ")");
                                RetrievingAll.retrieveAll(users,clients,payments);
                                if (!TablePresent("payment")) {
                                    Database.DatabaseInsert("CREATE TABLE Payment (UK VARCHAR(20), FOREIGN KEY (UK) REFERENCES BuildingMember(AptKey), UID VARCHAR(20) PRIMARY KEY, Amount DECIMAL(10, 2), Date DATE, Month VARCHAR(20), Year INT, Status ENUM('paid', 'pending'))");
                                }
                                Database.DatabaseInsert("INSERT INTO Payment (UK, UID) VALUES ('" + uniqueKey + "', '" + uniqueKey + wing + roomNumber + "')");
                                RetrievingAll.retrieveAll(users,clients,payments);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } else {
                System.out.print("How Many Floors Does the Building Have? ");
                int floorCount = sc.nextInt();
                System.out.print("How Many Rooms at Each Floor? ");
                int roomCount = sc.nextInt();

                for (int floor = 1; floor <= floorCount; floor++) {
                    for (int room = 1; room <= roomCount; room++) {
                        String roomNumber = String.format("%d%02d", floor, room);

                        try {
                            Database.DatabaseInsert("INSERT INTO BuildingMember(AptKey, Wing, RoomNo) VALUES ('" + uniqueKey + "', 'null', " + roomNumber + ")");
                            RetrievingAll.retrieveAll(users,clients,payments);
                            if (!TablePresent("payment")) {
                                Database.DatabaseInsert("CREATE TABLE Payment (UK VARCHAR(20), FOREIGN KEY (UK) REFERENCES BuildingMember(AptKey), UID VARCHAR(20) PRIMARY KEY, Amount DECIMAL(10, 2), Date DATE, Month VARCHAR(20), Year INT, Status ENUM('paid', 'pending'))");
                            }
                            Database.DatabaseInsert("INSERT INTO Payment (UK, UID) VALUES ('" + uniqueKey + "', '" + uniqueKey + "null" + roomNumber + "')");
                            RetrievingAll.retrieveAll(users,clients,payments);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            System.out.println("Maintainance.User Registered Successfully!");
            break;
        }
    }

    public static boolean TablePresent(String tablename) throws RuntimeException {
        String url = "jdbc:mysql://localhost:3306/Maintenaince";
        String username = "root";
        String password = "Sahilrida@1729";
        String tableName = tablename;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, tableName, null);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

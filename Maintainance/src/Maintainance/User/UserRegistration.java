package Maintainance.User;

import Maintainance.Client.Client;
import Maintainance.Database.Database;
import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;

import javax.xml.crypto.Data;
import java.util.*;

import static Maintainance.Client.ClientRegistration.TablePresent;

public class UserRegistration {

    public static void userRegistration(List<Client> clients, List<User> users, List<Payment> payments) {
        if (TablePresent("buildingregistered")) {
            RetrievingAll.retrieveAll(users, clients, payments);
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("User Registration:");

        while (true) {
            System.out.println("Enter 'exit' to stop registration");
            System.out.println("Do You Have UniqueKey of Apartment (Enter Yes or No)");
            String key = sc.nextLine().trim().toLowerCase();

            if (key.equalsIgnoreCase("exit")) {
                break;
            } else if (key.equals("yes")) {
                registerWithUniqueKey(sc, clients, users, payments);
            } else if (key.equals("no")) {
                registerWithoutUniqueKey(sc, clients, users, payments);
            } else {
                System.out.println("Wrong Input, Try Again");
            }
        }
    }

    private static void registerWithUniqueKey(Scanner sc, List<Client> clients, List<User> users, List<Payment> payments) {
        System.out.println("Enter 'exit' to stop registration");
        System.out.println("Enter The UniqueKey");
        String key = sc.nextLine();
        boolean registered = false;
        for (Client c :
                clients) {

            if (key.equals(c.getUniqueKey())) {
                System.out.println("Your Apartment is register here, You can enrolled your Identities");
                System.out.print("Apartment Information-");

                System.out.println(c.getState() + "," + c.getDistrict() + "," + c.getCity() + "," + c.getApartment() + "," + c.getPincode());
                registered = true;

                String state = c.getState(), district = c.getDistrict(), city = c.getCity(), apartment = c.getApartment(), pincode = c.getPincode();

                System.out.println("Enter Your Wing ( if no wing then enter - 0 )");
                String wing = sc.nextLine();
                if (wing.equals("0")) wing = "null";
                if (wing.equalsIgnoreCase("exit")) break;

                else {
                    registered = false;

                    for (User e :
                            users) {

                        if (wing.equals(e.getWing())) {
                            registered = true;


                        }
                    }
                    if (!registered) {
                        System.out.println("Sorry! Your Wing isnt Register here..\nTry Again");
                        break;
                    }
                }

                System.out.println("Enter Your Room No");
                String roomno = sc.nextLine();
                if (roomno.equalsIgnoreCase("exit")) break;
                else {
                    registered = false;
                    for (User e :
                            users) {

                        if (roomno.equals(e.getRoomno())) {
                            registered = true;


                        }
                    }
                    if (!registered) {
                        System.out.println("Sorry! Your RoomNo isnt Register here..\nTry Again");
                        break;
                    }
                }

                System.out.println("Enter Your First Name");
                String fname = sc.nextLine();
                if (fname.equalsIgnoreCase("exit")) break;

                System.out.println("Enter Your Last Name");
                String lname = sc.nextLine();
                if (lname.equalsIgnoreCase("exit")) break;

                System.out.println("Enter Your Phone Number");
                String phoneno = sc.nextLine();
                if (phoneno.equalsIgnoreCase("exit")) break;
                if (phoneno.length() != 10 || !phoneno.matches("\\d+")) {
                    System.out.println("Phone number should be 10 digits. Please enter again.");
                    continue;
                }

                System.out.println("Create Your UserName");
                String username = sc.nextLine().trim().toLowerCase();
                if (username.equalsIgnoreCase("exit")) break;
                registered = false;
                for (User e :
                        users) {

                    if (username.equals(e.getUsername())) {
                        registered = true;


                    }
                }
                if (registered) {
                    System.out.println("Username already taken try again \nTry Again");
                    break;
                }

                System.out.println("Create Your Userpassword");
                String userpassword = sc.nextLine();
                if (userpassword.equalsIgnoreCase("exit")) break;


                try {
                    Database.DatabaseInsert("update Buildingmember set  firstname ='" + fname + "', lastname='" + lname + "',phoneno=" + phoneno + ", username='" + username + "', password='" + userpassword + "'  where aptkey='" + c.getUniqueKey() + "' and wing='" + wing + "' and roomno=" + roomno);
                    RetrievingAll.retrieveAll(users, clients, payments);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                User newUser = new User(state, district, city, apartment, fname, lname, username, userpassword, key, phoneno, roomno, wing, c.getUniqueKey());
                users.add(newUser);
                System.out.println("User Registered Successfully!");
registered=true;

            }
        }
        if (!registered) {
            System.out.println("Sorry! Your Apartment isnt Register here..");
        }

    }

    private static void registerWithoutUniqueKey(Scanner sc, List<Client> clients, List<User> users, List<Payment> payments) {

        while(true) {
            System.out.println("Enter 'exit' to stop registration");
            boolean registered = false;
            String key1 = "";
            System.out.println("Enter Your State");
            String state = sc.nextLine();
            if (state.equalsIgnoreCase("exit")) break ;
            else {
                registered = false;

                for (Client c :
                        clients) {

                    if (state.equals(c.getState())) registered = true;
                }

                if (!registered) {
                    System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");

                }
            }

            System.out.println("Enter Your District");
            String district = sc.nextLine();
            if (district.equalsIgnoreCase("exit")) break;
            else {
                registered = false;
                for (Client c :
                        clients) {

                    if (district.equals(c.getDistrict())) {
                        registered = true;
                    }
                }
                if (!registered) {
                    System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
                    break;
                }
            }

            System.out.println("Enter Your City");
            String city = sc.nextLine();
            if (city.equalsIgnoreCase("exit")) break;
            else {

                registered = false;
                for (Client c :
                        clients) {

                    if (city.equals(c.getCity())) {
                        registered = true;
                    }
                }
                if (!registered) {
                    System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
                    break;
                }
            }


            System.out.println("Enter Your Apartment");
            String apartment = sc.nextLine();
            if (apartment.equalsIgnoreCase("exit")) break;
            else {
                registered = false;
                for (Client c :
                        clients) {

                    if (apartment.equals(c.getApartment())) {
                        registered = true;
                        key1 = c.getUniqueKey();

                    }
                }
                if (!registered) {
                    System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
                    break;
                }
            }

            System.out.println("Enter Your Wing ( if no wing enter -0)");
            String wing = sc.nextLine();
            if (wing.equalsIgnoreCase("exit")) break;
            if (wing.equals("0")) wing = "null";
            else {
                registered = false;

                for (User c :
                        users) {

                    if (wing.equals(c.getWing())) {
                        registered = true;


                    }
                }
                if (!registered) {
                    System.out.println("Sorry! Your Wing isnt Register here..\nTry Again");
                    break;
                }
            }

            System.out.println("Enter Your Room No");
            String roomno = sc.nextLine();
            if (roomno.equalsIgnoreCase("exit")) break;
            else {
                registered = false;

                for (User c :
                        users) {

                    if (roomno.equals(c.getRoomno())) {
                        registered = true;


                    }
                }
                if (!registered) {
                    System.out.println("Sorry! Your RoomNo isnt Register here..\nTry Again");
                    break;
                }
            }
            System.out.println("Enter Your First Name");
            String fname = sc.nextLine();

            if (fname.equalsIgnoreCase("exit")) break;

            System.out.println("Enter Your Last Name");
            String lname = sc.nextLine();
            if (lname.equalsIgnoreCase("exit")) break;

            System.out.println("Enter Your Phone Number");
            String phoneno = sc.nextLine();
            if (phoneno.equalsIgnoreCase("exit")) break;
            if (phoneno.length() != 10 || !phoneno.matches("\\d+")) {
                System.out.println("Phone number should be 10 digits. Please enter again.");
                continue;
            }

            System.out.println("Create Your UserName");
            String username = sc.nextLine().trim().toLowerCase();
            if (username.equalsIgnoreCase("exit")) break;
            registered = false;
            for (User e :
                    users) {

                if (username.equals(e.getUsername())) {
                    registered = true;
break;

                }
            }
            if (registered) {
                System.out.println("Username already taken try again \nTry Again");
                break;
            }


            System.out.println("Create Your Userpassword");
            String userpassword = sc.nextLine();
            if (userpassword.equalsIgnoreCase("exit")) break;


            try {
                Database.DatabaseInsert("update Buildingmember set  firstname ='" + fname + "', lastname='" + lname + "',phoneno=" + phoneno + ", username='" + username + "', password='" + userpassword + "'  where aptkey='" + key1 + "' and wing='" + wing + "' and roomno=" + roomno);
                RetrievingAll.retrieveAll(users, clients, payments);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            System.out.println("User Registered Successfully!");


        }



    }
}










//public class UserRegistration {
//
//
//    public static void userRegistration(List<Client> clients, List<User>users, List<Payment>payments) {
//
//
//        if (TablePresent("buildingregistered")) RetrievingAll.retrieveAll(users,clients,payments);
//
//        Scanner sc = new Scanner(System.in);
//
//
//        System.out.println("User Registration:");
//
//        while (true) {
//
////            System.out.println(clients.get(j).getUniqueKey()+"hello");
//
//
//            System.out.println("Enter 'exit' to stop registration");
//            System.out.println("Do You Hava UniqueKey of Apartment (Enter Yes or No)");
//            String key = sc.nextLine().trim().toLowerCase();
//
//            if (key.equalsIgnoreCase("exit")) {
//                break;
//            } else if (key.equals("yes")) {
//                System.out.println("Enter The UniqueKey");
//                key = sc.nextLine();
//                boolean registered = false;
//                for (Client c :
//                        clients) {
//
//                    if (key.equals(c.getUniqueKey())) {
//                        System.out.println("Your Apartment is register here, You can enrolled your Identities");
//                        System.out.println("Apartment Information");
//
//                        System.out.println(c.getState() + "," + c.getDistrict() + "," + c.getCity() + "," + c.getApartment() + "," + c.getPincode());
//                        registered = true;
//
//                        String state = c.getState(), district = c.getDistrict(), city = c.getCity(), apartment = c.getApartment(), pincode = c.getPincode();
//
//                        System.out.println("Enter Your Wing ( if no wing then enter - )");
//                        String wing = sc.nextLine();
//                        if (wing.equals("0")) wing = "null";
//                        if (wing.equalsIgnoreCase("exit")) break;
//
//                        else {
//                            registered = false;
//
//                            for (User e :
//                                    users) {
//
//                                if (wing.equals(e.getWing())) {
//                                    registered = true;
//
//
//                                }
//                            }
//                            if (!registered) {
//                                System.out.println("Sorry! Your Wing isnt Register here..\nTry Again");
//                                break;
//                            }
//                        }
//
//                        System.out.println("Enter Your Room No");
//                        String roomno = sc.nextLine();
//                        if (roomno.equalsIgnoreCase("exit")) break;
//                        else {
//                            registered = false;
//                            for (User e :
//                                    users) {
//
//                                if (roomno.equals(e.getRoomno())) {
//                                    registered = true;
//
//
//                                }
//                            }
//                            if (!registered) {
//                                System.out.println("Sorry! Your RoomNo isnt Register here..\nTry Again");
//                                break;
//                            }
//                        }
//
//                        System.out.println("Enter Your First Name");
//                        String fname = sc.nextLine();
//                        if (fname.equalsIgnoreCase("exit")) break;
//
//                        System.out.println("Enter Your Last Name");
//                        String lname = sc.nextLine();
//                        if (lname.equalsIgnoreCase("exit")) break;
//
//                        System.out.println("Enter Your Phone Number");
//                        String phoneno = sc.nextLine();
//                        if (phoneno.equalsIgnoreCase("exit")) break;
//
//                        System.out.println("Create Your UserName");
//                        String username = sc.nextLine();
//                        if (username.equalsIgnoreCase("exit")) break;
//
//                        System.out.println("Create Your Userpassword");
//                        String userpassword = sc.nextLine();
//                        if (userpassword.equalsIgnoreCase("exit")) break;
//
//
//                        try {
//                            Database.DatabaseInsert("update Buildingmember set  firstname ='" + fname + "', lastname='" + lname + "',phoneno=" + phoneno + ", username='" + username + "', password='" + userpassword + "'  where aptkey='" + c.getUniqueKey() + "' and wing='" + wing + "' and roomno=" + roomno);
//                            RetrievingAll.retrieveAll(users,clients,payments);
//
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        User newUser = new User(state, district, city, apartment, fname, lname, username, userpassword, key, phoneno, roomno,wing,c.getUniqueKey());
//                        users.add(newUser);
//                        System.out.println("User Registered Successfully!");
//
//
//                    }
//                }
//                if (!registered) {
//                    System.out.println("Sorry! Your Apartment isnt Register here..");
//                }
//            } else if (key.equals("no")) {
//                boolean registered = false;
//                String key1 = "";
//                System.out.println("Enter Your State");
//                String state = sc.nextLine();
//                if (state.equalsIgnoreCase("exit")) break;
//                else {
//                    registered = false;
//
//                    for (Client c :
//                            clients) {
//
//                        if (state.equals(c.getState())) registered = true;
//                    }
//
//                    if (!registered) {
//                        System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//
//                System.out.println("Enter Your District");
//                String district = sc.nextLine();
//                if (district.equalsIgnoreCase("exit")) break;
//                else {
//                    registered=false;
//                    for (Client c :
//                            clients) {
//
//                        if (district.equals(c.getDistrict())) {
//                            registered = true;
//                        }
//                    }
//                    if (!registered) {
//                        System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//
//                System.out.println("Enter Your City");
//                String city = sc.nextLine();
//                if (city.equalsIgnoreCase("exit")) break;
//                else {
//
//                    registered=false;
//                    for (Client c :
//                            clients) {
//
//                        if (city.equals(c.getCity())) {
//                            registered = true;
//                        }
//                    }
//                    if (!registered) {
//                        System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//
//
//                System.out.println("Enter Your Apartment");
//                String apartment = sc.nextLine();
//                if (apartment.equalsIgnoreCase("exit")) break;
//                else {
//                    registered=false;
//                    for (Client c :
//                            clients) {
//
//                        if (apartment.equals(c.getApartment())) {
//                            registered = true;
//                            key1 = c.getUniqueKey();
//
//                        }
//                    }
//                    if (!registered) {
//                        System.out.println("Sorry! Your Apartment isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//
//                System.out.println("Enter Your Wing ( if no wing enter -0)");
//                String wing = sc.nextLine();
//                if (wing.equalsIgnoreCase("exit")) break;
//                if (wing.equals("0")) wing="null" ;
//                else {
//                    registered = false;
//
//                    for (User c :
//                            users) {
//
//                        if (wing.equals(c.getWing())) {
//                            registered = true;
//
//
//                        }
//                    }
//                    if (!registered) {
//                        System.out.println("Sorry! Your Wing isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//
//                System.out.println("Enter Your Room No");
//                String roomno = sc.nextLine();
//                if (roomno.equalsIgnoreCase("exit")) break;
//                else {
//                    registered = false;
//
//                    for (User c :
//                            users) {
//
//                        if (roomno.equals(c.getRoomno())) {
//                            registered = true;
//
//
//                        }
//                    }
//                    if (!registered) {
//                        System.out.println("Sorry! Your RoomNo isnt Register here..\nTry Again");
//                        break;
//                    }
//                }
//                System.out.println("Enter Your First Name");
//                String fname = sc.nextLine();
//
//                if (fname.equalsIgnoreCase("exit")) break;
//
//                System.out.println("Enter Your Last Name");
//                String lname = sc.nextLine();
//                if (lname.equalsIgnoreCase("exit")) break;
//
//                System.out.println("Enter Your Phone Number");
//                String phoneno = sc.nextLine();
//                if (phoneno.equalsIgnoreCase("exit")) break;
//
//                System.out.println("Create Your UserName");
//                String username = sc.nextLine();
//                if (username.equalsIgnoreCase("exit")) break;
//
//                System.out.println("Create Your Userpassword");
//                String userpassword = sc.nextLine();
//                if (userpassword.equalsIgnoreCase("exit")) break;
//
//
//                try {
//                    Database.DatabaseInsert("update Buildingmember set  firstname ='" + fname + "', lastname='" + lname + "',phoneno=" + phoneno + ", username='" + username + "', password='" + userpassword + "'  where aptkey='" + key1 + "' and wing='" + wing + "' and roomno=" + roomno);
//                    RetrievingAll.retrieveAll(users,clients,payments);
//
//
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//
//                System.out.println("User Registered Successfully!");
//
//
//            } else {
//                System.out.println("Wrong Input Try Again");
//
//            }
//        }
//
//    }
//}




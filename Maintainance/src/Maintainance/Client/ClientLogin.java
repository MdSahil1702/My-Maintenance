package Maintainance.Client;


import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;
import Maintainance.User.User;

import java.util.List;
import java.util.Scanner;

public class ClientLogin {
    public static void clientLogin(List<Client> clients, List<User> users, List<Payment>payments) {
        RetrievingAll.retrieveAll(users,clients,payments);
        Scanner sc = new Scanner(System.in);



        System.out.println("\nMaintainance Client Login:");
        System.out.print("Enter Username: ");
        String loginUsername = sc.nextLine();
        System.out.print("Enter Userpassword: ");
        String loginPassword = sc.nextLine();
        boolean loggedIn = false;

        for (Client c : clients) {


            if (c.getUsername().equals(loginUsername) && c.getUserpassword().equals(loginPassword)) {
                loggedIn = true;

                break;
            }
        }

        if (loggedIn) {
            System.out.println("Login Successful!");
            ClientDashboard.clientDashboard(clients,loginUsername,loginPassword,users,payments);
        } else {
            System.out.println("Login Failed! Invalid Username or Password.");
        }
    }

}

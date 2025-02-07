package Maintainance.User;


import Maintainance.Client.Client;
import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;

import java.util.List;
import java.util.Scanner;

public class UserLogin {

    public static void userLogin(List<Client> clients, List<User> users, List<Payment>payments) {
RetrievingAll.retrieveAll(users,clients,payments);
        Scanner sc = new Scanner(System.in);

        System.out.println("\nMaintainance.User Login:");

        System.out.print("Enter Username: ");
        String loginUsername = sc.nextLine();
        System.out.print("Enter Userpassword: ");
        String loginPassword = sc.nextLine();
        boolean loggedIn = false;

        for (User user : users) {
            if (user.getUsername()!=null) {
                if (loginUsername.equals(user.getUsername()) && loginPassword.equals(user.getUserpassword())) {
                    loggedIn = true;
                    break;
                }
            }

        }

        if (loggedIn) {
            System.out.println("Login Successful!");
            UserDashboard.userDashboard(clients,users,payments,loginUsername,loginPassword);
        } else {
            System.out.println("Login Failed! Invalid Username or Password.");
        }
    }

}

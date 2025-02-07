package Maintainance.Running;

import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;
import Maintainance.User.*;
import Maintainance.Client.*;

import java.io.IOException;
import java.util.*;

import static Maintainance.Client.ClientRegistration.TablePresent;


public class run {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();

     if (TablePresent("buildingregistered"))RetrievingAll.retrieveAll(users, clients, payments);

//
//        for (User u :
//                users) {
//            System.out.println(u.getRoomno());
//        }
        while (true) {
            System.out.println("Who Are You?");
            System.out.println("1. Apartment Administrator");
            System.out.println("2. Apartment Member");
            System.out.println("3. Exit");

            Scanner sc = new Scanner(System.in);
            int a = sc.nextInt();

            if (a == 1) {
                apartment(clients, sc, users, payments);
            }

            if (a == 2) {
                apartmentMember(clients, sc, users, payments);


            }
            if (a == 3) {
                System.out.println("Exiting the program...");
                break;
            }
            else {
                System.out.println("Wrong input. Try Again.");
            }
        }
    }

    public static void apartment(List<Client> clients, Scanner sc, List<User> users, List<Payment> payments) {

        while(true) {
            System.out.println("Choose An Action");
            System.out.println("1. Registration");
            System.out.println("2. Login");
            System.out.println("3. Back");

            int b = sc.nextInt();
            if (b == 1) {
                ClientRegistration.clientRegistration(clients,users,payments);
            } else if (b == 2) {
                ClientLogin.clientLogin(clients, users, payments);
            } else if (b == 3) {
break;
            } else {
                System.out.println("Wrong Input. Try Again.");

            }
        }
    }

    public static void apartmentMember(List<Client> clients, Scanner sc, List<User> users, List<Payment> payments) {

        System.out.println("Choose An Action");
        System.out.println("1. Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        int b = sc.nextInt();
        if (b == 1) {
            UserRegistration.userRegistration(clients, users, payments);
        } else if (b == 2) {
            UserLogin.userLogin(clients, users, payments);
        } else {
            System.out.println("Wrong Input. Try Again.");
            apartmentMember(clients, sc, users, payments);
        }
    }



}



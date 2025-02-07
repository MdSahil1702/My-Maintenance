package Maintainance.User;

import Maintainance.Client.Client;
import Maintainance.Database.Database;
import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Maintainance.Client.ClientRegistration.TablePresent;

public class UserDashboard {
    public static int index;
    public static int indexl;
    public static void userDashboard(List<Client> clients, List<User> users, List<Payment> payments, String loginusername, String loginpassword) {


        Boolean run = true;



        int i = 0;
        int j=0;
        for (User u :
                users) {



           if (u.getUserpassword()!=null) {

               if (loginusername.equals(u.getUsername()) && loginpassword.equals(u.getUserpassword())){
                   index = i;
                   break;
               }


           }
           i++;
            }
        for (Client c:
            clients ) {
            if (c.getUniqueKey().equals(users.get(index).getUniquekey())){
                indexl=j;
 break;
            }
            j++;
        }

        while (run) {

            System.out.println("1.Update Password/Username  2.Pay Bill  3.Paid Bill Record  4.Exit");
            Scanner sc = new Scanner(System.in);
            int a = sc.nextInt();
            switch (a) {
                case 4:
                    run = false;
                    break;
                case 1:
                    updateUserPassword(sc, loginusername, loginpassword,users,clients,payments);
                default:
                    System.out.println("wrong input try again");
                    break;
                case 2:
                    try {
                        payBills(sc, clients,payments, users,loginpassword,loginusername);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    paidrecord(payments,clients);


            }

        }
    }

    private static void paidrecord(List<Payment>payments,List<Client>clients) {
        boolean found=false;
        int sr=1;
        System.out.println("sr      Paid Date       Bill Month      Bill Year       Bill Amount     Status");
        for (Payment p :
                payments) {
            if (p.getUk().equals(clients.get(indexl).getUniqueKey()) && p.getStatus().equals("paid"))
            {
                found=true;
                System.out.println(sr+"      "+p.getDate()+"           "+p.getMonth()+"        "+p.getYear()+"            "+p.getAmount()+"       "+p.getStatus());

                sr++;

            }
        }
    }

    private static void payBills(Scanner sc,List<Client>clients, List<Payment> payments, List<User> users,String loginpassword,String loginusername) {
        int i = 1;
        boolean found = false;
        System.out.println("Here are your pending bills");
        System.out.println("sr      uid           Month       Year      Amount      Status");

        for (Payment p :
                payments) {
            if (p.getUid().equals(users.get(index).getUniquekey() + users.get(index).getWing() + users.get(index).getRoomno()) && p.getStatus().equals("pending")) {
                found = true;
                System.out.println(i + "      " + p.getUid() + "      " + p.getMonth() + "        " + p.getYear() + "     "+p.getAmount()+"         " + p.getStatus());
                i++;
            }
        }
        if (!found) System.out.println("No Pending bills");
        else {
            System.out.println("Enter the uid of the bill you want to pay");
            String uid = sc.nextLine();
            uid = sc.nextLine();
            for (Payment p :
                    payments) {
                if (p.getUid().equals(uid)) {
                    System.out.println("Do you  want to pay the bill of "+p.getUid()+p.getMonth()+p.getYear()+"of amount "+p.getAmount()+"\n to Account Number "+clients.get(indexl).getAccountnumber()+" and Account Name "+clients.get(indexl).getAccountname()+" (Yes/no)");
                    String confirm=sc.nextLine().toLowerCase();
                    if (confirm.equals("yes"))
                    {
                        paymentMethod(sc,p.getAmount(),p.getUid(),users,clients,payments,loginpassword,loginusername);
                    }
                    else if (confirm.equals("no")){
                        System.out.println("Payment cancel");
                    }

                }
            }
        }


    }

    private static void paymentMethod(Scanner sc,float amount,String uid,List<User>users,List<Client>clients,List<Payment>payments,String password,String username) {
        while(true) {
            System.out.println("1.Paytm     2.Phonepay      3.GooglePay     4.Cancel");
            String ar[] = {"Paytm", "Phonepay", "GooglePay"};
            int a = sc.nextInt();
            if (a > 0 && a < 4) {
                a--;
                System.out.println("Enter the " + ar[a] + "Upi id");
                String upi = sc.nextLine();
                upi = sc.nextLine();


                    System.out.println("Request for " + amount + "rupees has been to your " + ar[a] + "Upi id"+ upi+ " (Yes for accepting/no for reject) the Request");
                    String action = sc.nextLine().toLowerCase();
                    if (action.equals("yes")) {
                        LocalDate date = LocalDate.now();

                        System.out.println(date);
                        try {
                            Database.DatabaseInsert("update payment set status='paid',date='" + date + "' where uid ='" + uid + "'");
                            RetrievingAll.retrieveAll(users, clients, payments);
                        } catch (Exception e) {
                            System.out.println("Payment unseccessfull try again!");
                            ;
                            UserDashboard.userDashboard(clients, users, payments, username, password);
                        }
                        System.out.println("Your Payment has been Done Successfully");
                        UserDashboard.userDashboard(clients, users, payments, username, password);

                    }




            } else {
                System.out.println("Wrong input Try Again !");
                break;
            }
        }

    }



    private static void updateUserPassword(Scanner sc, String loginUsername, String loginPassword,List<User>users,List<Client>clients,List<Payment>payments) {

        System.out.println("1.Password  2.Username 3.Exit");
        int a = sc.nextInt();

        switch (a) {
            case 3:
                break;
            case 1:
                System.out.print("Create New Password:  ");
                String newpassword = sc.nextLine();
                newpassword = sc.nextLine();

                System.out.print("\nConfirm New Password:   ");
                String conpass = sc.nextLine();
                conpass = sc.nextLine();


                if (newpassword.equals(conpass)) {
                    try {
                        Database.DatabaseInsert("update buildingmember set password = '" + conpass + "' where username='" + loginUsername + "' and password='" + loginPassword + "'");
                        System.out.println("Password Updated");
                        RetrievingAll.retrieveAll(users,clients,payments);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Password doesnt match try again ");
                    updateUserPassword(sc, loginUsername, loginPassword,users,clients,payments);
                }
                break;

            case 2:
                System.out.print("Create New Username:  ");
                String newusername = sc.nextLine();
                newusername = sc.nextLine();
                System.out.print("Confirm New Username:   ");
                String conusername = sc.nextLine();


                if (newusername.equals(conusername)) {
                    try {
                        Database.DatabaseInsert("update buildingregistered set username = '" + conusername + "' where username='" + loginUsername + "' and password='" + loginPassword + "'");
                        System.out.println("Username Updated");
                        RetrievingAll.retrieveAll(users,clients,payments);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Username doesnt match try again ");
                    updateUserPassword(sc, loginUsername, loginPassword,users,clients,payments);
                }
                break;

            default:
                System.out.println("Wrong input try again ");
                updateUserPassword(sc, loginUsername, loginPassword,users,clients,payments);
                break;

        }


    }
}

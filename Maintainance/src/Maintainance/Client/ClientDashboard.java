package Maintainance.Client;

import Maintainance.Database.Database;
import Maintainance.Database.RetrievingAll;
import Maintainance.Payment.Payment;
import Maintainance.User.User;

import java.util.List;
import java.util.Scanner;

import static Maintainance.Client.ClientRegistration.TablePresent;

public class ClientDashboard {
    protected static int index = 0;


    public static void clientDashboard(List<Client> clients, String loginusername, String loginpassword, List<User> users, List<Payment> payments) {

        boolean run = true;
        while (run) {
            int i = 0;
            for (Client c :
                    clients) {

                if (loginusername.equals(c.getUsername()) && loginpassword.equals(c.getUserpassword())){

                    index = i;

                    break;
                }
                i++;

            }


            System.out.println("1.Update Password/Username\n2.UpdateMaintenaince\n3.Check Payment\n5.Add Bank Account\n4.To Exit");
            Scanner sc = new Scanner(System.in);
            int a = sc.nextInt();
            if (!TablePresent("payment")) {
                try {
                    Database.DatabaseInsert("CREATE TABLE Payment (UK VARCHAR(20), FOREIGN KEY (UK) REFERENCES BuildingMember(AptKey), UID VARCHAR(20) PRIMARY KEY, Amount DECIMAL(10, 2), Date DATE, Month VARCHAR(20), Year INT, Status ENUM('paid', 'pending'))");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            switch (a) {
                case 4:
                    run = false;
                    break;
                case 1:
                    updatePassword(sc, loginusername, loginpassword,clients,users,payments);
                default:
                    System.out.println("wrong input try again");
                    break;
                case 2:
                    try {
                        updateMaintenaince(sc, clients, users,payments);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    checkStatuss(sc, clients, users, payments);
                    break;
                case 5:
                    addBank(sc,clients.get(index).getUniqueKey(),users,clients,payments);

            }
        }
    }

    private static void addBank(Scanner sc, String uniquekey,List<User>users,List<Client>clients,List<Payment>payments) {
        boolean move=false;
        System.out.println("Enter the Account Number");
        String accnumber=sc.nextLine();
        accnumber=sc.nextLine();
        System.out.println("Confirm the Account number");
        String accnumber1=sc.nextLine();
        if (accnumber1.equals(accnumber))move=true;
        if (move)
        {
            System.out.println("Enter Account Name");
            String accname=sc.nextLine();
            try {
                Database.DatabaseInsert("update buildingregistered set AccountNumber='"+accnumber1+"',AccountName='"+accname+"' where uniquekey="+uniquekey);
RetrievingAll.retrieveAll(users,clients,payments);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void checkStatuss(Scanner sc, List<Client> clients, List<User> users, List<Payment> payments) {


        System.out.println("1.Pending   2.Paid  3.By Month  4.By Room No");
        int a=sc.nextInt();

        if (a == 1) {
            int serialNumber = 1; // Initialize the serial number

            System.out.println("sr  Wing    RoomNo  Month   Year    Amount  Status");
            boolean paymentMatched = false;
            for (Payment payment : payments) {

                for (User u :
                        users) {
                    if (payment.getUk().equals(clients.get(index).getUniqueKey()) &&payment.getStatus().equals("pending")) {
                        System.out.println(serialNumber + "  " + u.getWing() + " " + u.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                        serialNumber++; // Increment the serial number
                        paymentMatched = true;
                    }
                }



            }
            if (!paymentMatched) {
                System.out.println("No matching user found for payment");
            }
        }

        if (a == 2) {
            int serialNumber = 1; // Initialize the serial number

            System.out.println("sr  Wing    RoomNo  Month   Year    Amount  Status");
            boolean paymentMatched = false;
            for (Payment payment : payments) {

                for (User user : users) {
                    if (payment.getUk().equals(clients.get(index).getUniqueKey())&&payment.getStatus().equals("paid")) {
                        System.out.println(serialNumber + "  " + user.getWing() + " " + user.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                        serialNumber++; // Increment the serial number
                        paymentMatched = true;
                    }
                }

            }
            if (!paymentMatched) {
                System.out.println("No matching user found for payment");
            }
        }
        if (a == 3) {
            System.out.println("1.Pending 2.Paid 3.Together");
            int b = sc.nextInt();
            if (b == 2) {
                int serialNumber=1;
                System.out.println("Enter the Month Name");
                String month=sc.nextLine();
                month=sc.nextLine();

                System.out.println("Enter the year");
                int year=sc.nextInt();
                boolean paymentMatched = false;
                for (Payment payment : payments) {

                    for (User user : users) {
                        if ( payment.getUid().equals(user.getUniquekey() + user.getWing() + user.getRoomno()) && payment.getMonth().equals(month) && payment.getYear()==year &&payment.getStatus().equals("paid")) {
                            System.out.println(serialNumber + "  " + user.getWing() + " " + user.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                            serialNumber++; // Increment the serial number
                            paymentMatched = true;
                        }
                    }

                }
                if (!paymentMatched) {
                    System.out.println("No matching user found for payment");
                }
            }
            if (b==1){
                int serialNumber=1;
                System.out.println("Enter the Month Name");
                String month=sc.nextLine();
                month=sc.nextLine();

                System.out.println("Enter the year");
                int year=sc.nextInt();
                boolean paymentMatched = false;
                for (Payment payment : payments) {

                    for (User user : users) {
                        if ( payment.getUid().equals(user.getUniquekey() + user.getWing() + user.getRoomno()) && payment.getMonth().equals(month) && payment.getYear()==year && payment.getStatus().equals("pending")) {
                            System.out.println(serialNumber + "  " + user.getWing() + " " + user.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                            serialNumber++; // Increment the serial number
                            paymentMatched = true;
                        }
                    }

                }
                if (!paymentMatched) {
                    System.out.println("No matching user found for payment");
                }
            }

            if (b==3){
                int serialNumber=1;
                System.out.println("Enter the Month Name");
                String month=sc.nextLine();
                month=sc.nextLine();

                System.out.println("Enter the year");
                int year=sc.nextInt();
                boolean paymentMatched = false;
                for (Payment payment : payments) {

                    for (User user : users) {
                        if ( payment.getUid().equals(user.getUniquekey() + user.getWing() + user.getRoomno()) && payment.getMonth().equals(month) && payment.getYear()==year) {
                            System.out.println(serialNumber + "  " + user.getWing() + " " + user.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                            serialNumber++; // Increment the serial number
                            paymentMatched = true;
                        }
                    }

                }
                if (!paymentMatched) {
                    System.out.println("No matching user found for payment");
                }
            }

        }
      if (a==4)
      {
          int serialNumber=1;
          System.out.println("Enter the Wing( if no wing enter -0)");
          String wing=sc.nextLine();
          wing=sc.nextLine();
          if (wing.equals("0"))wing="null";

          System.out.println("Enter the Roomno");
         String roomno=sc.nextLine();
          boolean paymentMatched = false;
          for (Payment payment : payments) {

              for (User user : users) {
                  if (user.getWing().equals(wing) && user.getRoomno().equals(roomno) && payment.getUid().equals(user.getUniquekey() + user.getWing() + user.getRoomno()) ) {
                      System.out.println(serialNumber + "  " + user.getWing() + " " + user.getRoomno() + "      " + payment.getMonth() + "    " + payment.getYear() + "    " + payment.getAmount() + "   " + payment.getStatus());
                      serialNumber++; // Increment the serial number
                      paymentMatched = true;
                  }
              }

          }
          if (!paymentMatched) {
              System.out.println("No matching user found for payment");
          }
      }
      else if (a>4){
          System.out.println("Wrong Input Try Again ");
      }


    }

    private static void updateMaintenaince(Scanner sc, List<Client> clients, List<User> users,List<Payment>payments) throws Exception {
        System.out.println("1.Maintenaince for All at a time\n2.Maintenaine for Particular Member\n3.Updating status of Member(Paid or Pending)");
        int a = sc.nextInt();
        switch (a) {
            case 2:
                System.out.println("Enter Wing ( if no enter -0)");
                String wing = sc.nextLine();
                wing = sc.nextLine();

                if (wing.equals("0")) wing = "null";
                System.out.println("Enter RoomNo");
                String roomno = sc.nextLine();
                String uniquekey = clients.get(index).getUniqueKey();
                System.out.println(uniquekey);

                boolean found = false;

                for (User u :
                        users) {

                    if (u.getWing().equals(wing) && u.getRoomno().equals(roomno) && uniquekey.equals(u.getUniquekey())) {
                        found = true;
                        System.out.println("Enter the Amount");
                        float amount = sc.nextFloat();
                        System.out.println(amount);
                        String amount1 = String.format("%.1f", amount);
                        System.out.println("Enter the Month Name");
                        String month = sc.nextLine();
                        month = sc.nextLine();
                        System.out.println("Enter the Year");
                        int year = sc.nextInt();
                        Database.DatabaseInsert("update payment set amount=" + amount1 + ", month='" + month + "', year=" + year + ", status= 'pending' where uid='" + uniquekey + wing + roomno + "'");
                    RetrievingAll.retrieveAll(users,clients,payments);
                    }

                }
                if (!found) {
                    System.out.println("The particular roomno doesnt found try again ");
                    updateMaintenaince(sc, clients, users,payments);
                }
                break;
            case 1:
                System.out.println("Enter the Amount");
                float amount = sc.nextFloat();
                System.out.println(amount);
                String amount1 = String.format("%.1f", amount);
                System.out.println("Enter the Month Name");
                String month = sc.nextLine();
                month = sc.nextLine();
                System.out.println("Enter the Year");
                int year = sc.nextInt();

                Database.DatabaseInsert("update payment set amount=" + amount1 + ", month='" + month + "', year=" + year + ", status= 'pending' where uid like '" + clients.get(index).getUniqueKey() + "%'");
                RetrievingAll.retrieveAll(users,clients,payments);
                break;
            case 3:


                System.out.println("Enter Wing ( if no enter -0)");
                String wing1 = sc.nextLine();
                wing1 = sc.nextLine();

                if (wing1.equals("0")) wing1 = "null";
                System.out.println("Enter RoomNo");
                String roomno1 = sc.nextLine();
                String uniquekey1 = clients.get(index).getUniqueKey();


                boolean found1 = false;

                for (User u :
                        users) {

                    if (u.getWing().equals(wing1) && u.getRoomno().equals(roomno1) && uniquekey1.equals(u.getUniquekey())) {
                        found1 = true;

                    }


                }
                if (found1){
                    System.out.println("Enter the Month Name");
                    String month1 = sc.nextLine();

                    System.out.println("Enter the Year");
                    int year1 = sc.nextInt();

                    Database.DatabaseInsert("update payment set  status= 'paid' where uid='" + uniquekey1 + wing1 + roomno1 + "' and month='" + month1 + "' and year=" + year1);
                    RetrievingAll.retrieveAll(users,clients,payments);
                }
                else{
                    System.out.println("No such room found");
                }
                break;
            default:
                System.out.println("wrong input try again");
                updateMaintenaince(sc, clients, users,payments);

        }
    }

    private static void updatePassword(Scanner sc, String loginUsername, String loginPassword,List<Client> clients, List<User> users,List<Payment>payments) {

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

                System.out.println("\n" + newpassword + "," + conpass);
                if (newpassword.equals(conpass)) {
                    try {
                        Database.DatabaseInsert("update buildingregistered set password = '" + conpass + "' where username='" + loginUsername + "' and password='" + loginPassword + "'");
                        System.out.println("Password Updated");
                        RetrievingAll.retrieveAll(users,clients,payments);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Password doesnt match try again ");
                    updatePassword(sc, loginUsername, loginPassword,clients,users,payments);
                }
                break;

            case 2:
                System.out.print("Create New Username:  ");
                String newusername = sc.nextLine();
                sc.nextLine();
                System.out.print("\nConfirm New Username:   ");
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
                    updatePassword(sc, loginUsername, loginPassword,clients,users,payments);
                }
                break;

            default:
                System.out.println("Wrong input try again ");
                updatePassword(sc, loginUsername, loginPassword,clients,users,payments);
                break;

        }


    }

}

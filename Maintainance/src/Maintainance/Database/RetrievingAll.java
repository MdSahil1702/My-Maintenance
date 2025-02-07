package Maintainance.Database;

import Maintainance.Client.Client;
import Maintainance.Client.ClientDataRetieve;
import Maintainance.Payment.Payment;
import Maintainance.Payment.PaymentRetrieve;
import Maintainance.User.User;
import Maintainance.User.UserDataRetieve;
import Maintainance.User.UserRegistration;

import java.util.List;

import static Maintainance.Client.ClientRegistration.TablePresent;

public class RetrievingAll {
    public static void retrieveAll(List<User> users, List<Client> clients, List<Payment> payments) {
        if (TablePresent("buildingregistered")) {
            try {
                ClientDataRetieve.clientDataRetieve(clients);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (!TablePresent("buildingregistered")) ;

        if (TablePresent("buildingmember")) try {
            UserDataRetieve.loginuser(users);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        else if (!TablePresent("buildingmember")) ;


        if (TablePresent("payment")) {
            try {
                PaymentRetrieve.paymentRetrieve(payments);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (!TablePresent("Payment")) ;

    }
}

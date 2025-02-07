package Maintainance.Client;

public class Client {
    protected String state,
            district,
            city,
            apartment,
            uniqueKey,
            username,
            userpassword,
            pincode,
    accountnumber,
    accountname;


    public Client(String state, String district, String city, String apartment, String pincode, String username, String userpassword, String uniqueKey,String accountnumber, String accountname) {
        this.state = state;
        this.district = district;
        this.city = city;
        this.apartment = apartment;

        this.username = username;
        this.userpassword = userpassword;
        this.pincode = pincode;

        this.uniqueKey = uniqueKey;
        this.accountnumber=accountnumber;
        this.accountname=accountname;

    }
    public String getAccountname() {
        return accountname;
    }
    public String getAccountnumber() {
        return accountnumber;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getApartment() {
        return apartment;
    }


    public String getUsername() {
        return username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public String getPincode() {
        return pincode;
    }


    public String getUniqueKey() {
        return uniqueKey;
    }

}

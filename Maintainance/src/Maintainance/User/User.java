package Maintainance.User;

public class User {
    protected String state,
            district,
            city,
            apartment,
            fname,
            lname,
            username,
            userpassword,
            pincode,
            roomno,
            phoneno,
    wing,
    uniquekey;


    public User(String state, String district, String city, String apartment, String fname, String lname, String username, String userpassword, String pincode, String phoneno, String roomno,String wing,String uniquekey) {
        this.state = state;
        this.district = district;
        this.city = city;
        this.apartment = apartment;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.userpassword = userpassword;
        this.pincode = pincode;
        this.roomno = roomno;
        this.phoneno = phoneno;
        this.wing=wing;
        this.uniquekey=uniquekey;

    }
    public String getUniquekey(){return  uniquekey;}
public String getWing(){return  wing;}
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

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
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

    public String getPhoneno() {
        return phoneno;
    }

    public String getRoomno() {
        return roomno;
    }


}

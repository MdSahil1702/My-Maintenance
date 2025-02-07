package Maintainance.Payment;



import java.time.LocalDate;
import java.util.Date;

public class Payment {
   private String uid,
            month,
            status
    ,date,
    uk;
   private  int year;
    private float amount;

    public Payment(String uk, String uid, String month,String status,String date,float amount,int year)
    {
        this.uid=uid;
        this.month=month;
        this.status=status;
        this.date=date;
        this.amount=amount;
        this.year=year;
        this.uk=uk;

    }
    public  String getUid(){return uid;}
    public String getMonth(){return month;}
    public String getStatus(){return status;}
    public String getDate(){return date;}
    public float getAmount(){return amount;}
    public int getYear(){return year;}
    public String getUk(){return  uk;}




}

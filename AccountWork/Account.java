package AccountWork;

import java.time.LocalDate;  
import java.util.*;
import Database.DatabaseSingleton;
import SendEmails.SendEmail;
public class Account
{
    private boolean registered = false;
    private String email;

    private DatabaseSingleton access = DatabaseSingleton.getOnlyInstance();
    
    
    public void Register(String address, String name, String email, String password, String phoneNumber, String cardNumber, String cardName, 
                         String billingName, String billingAddress)
    {
        this.email = email;
      access.addNewUser(name, address, cardNumber, billingName, billingAddress, email, password, phoneNumber);
      this.registered = true;
      //pay 20 dollar fee
    }
    public void login(String email, String password) throws Exception{
        if(access.getSpecificUser(email, password)){
            registered = true;
            this.email = email;
        }else{
            throw new Exception("The email or the password is wrong");
        }
    }

    public boolean isRegistered(){
        
        if(access.getSpecificUser(this.email)){
            return true;
        }else{
            return false;
        }
        
    }

    public void cancelTheTicket(int uniqueTicket, String email) throws Exception{
        if(access.getSpecificTicket(uniqueTicket, email) == null){  //If there is no ticket found in database return nothing there
            return;
        }
        LocalDate time = java.time.LocalDate.now();
        Float cost = (float) 0;
        String[] tmp = access.getSpecificTicket(uniqueTicket, email).split("/");
        LocalDate ticket = LocalDate.of(Integer.parseInt(tmp[5]), Integer.parseInt(tmp[4]), Integer.parseInt(tmp[3]));
        String compare = time.until(ticket).toString();
        Integer date = Integer.parseInt(compare.substring(1, compare.length()-1));
        double refundAmount = 0;
        if(date > 3){
            
            cost = Float.parseFloat(tmp[0]);
            access.removeSpecificTicket(Integer.toString(uniqueTicket));    
            
        }else{
            throw new Exception();
        }
        if(tmp[9].equals("false")){
            //Guest user
            double fee = cost * 0.15;
            refundAmount = cost - fee;
            
            //pay fee
        }else{
            //Registered user does not have to pay fee
            refundAmount = cost;
        }
        //Send email with coupon with refundAmount and the date it expires
        
        int yearPlusOne = time.getYear() + 1;
        access.setSpecificSeat(tmp[2], Integer.parseInt(tmp[1]), tmp[8], Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]), Integer.parseInt(tmp[5]), 1);
        new SendEmail(tmp[6], "Refund credit", "The credit that has been refunded is: " + refundAmount + ". It will expire on " + yearPlusOne 
        + "/" + time.getMonthValue() + "/" + time.getDayOfMonth());
        access.removeSpecificTicket(String.valueOf(uniqueTicket));
    }
    public Vector<String> displayMovieNews(){
        if(this.registered == true){
            return access.getAllNews();
            //display movie news
        }else{
            return new Vector<>();
            //display that the user is not registered
        }
    }

    public String getEmail(){
        return this.email;
    }

    public void setReg(){
        this.registered = true;
    }

    public boolean getReg(){
        return this.registered;
    }
}


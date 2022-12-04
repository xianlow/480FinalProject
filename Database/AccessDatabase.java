package Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;  
public class AccessDatabase {
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;

    private Connection dbConnect;
    private ResultSet results;
/*
    private int totalFoodItems;

    public int getTotalFoodItems(){
        try {
            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM " + "available_food");
            while(results.next()){
                if(results.isLast()){
                    this.totalFoodItems = results.getInt("ItemID");
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }
        return this.totalFoodItems;
    }

    public String getSpecificFood(int itemID){
        StringBuffer full = new StringBuffer();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "available_food");
            while(results.next()){
                if(results.getInt("ItemID") == itemID){
                    full.append(results.getString("Name") + "/" + results.getString("GrainContent") + "/"
                            + results.getString("FVContent") + "/" + results.getString("ProContent") + "/"
                            + results.getString("Other") + "/" + results.getString("Calories"));
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }

        return full.toString();
    }
    */
    public boolean getSpecificUser(String email, String password){
        boolean isRegistered = false;
        try {

            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "account");
            while(results.next()){
                if(results.getString("Email").equals(email) && results.getString("Password").equals(password)){
                    isRegistered = true;
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }
        return isRegistered;
    }
    public boolean getSpecificUser(String email){
        boolean isRegistered = false;
        try {

            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "account");
            while(results.next()){
                if(results.getString("Email").equals(email)){
                    isRegistered = true;
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }
        return isRegistered;
    }
    public void addNewUser(String name, String address, String cardInfo, String billingName, String billingAddress, String email, String password, String phoneNumber){
        
        try {
            
            Statement myStmt = dbConnect.createStatement();
            String tmp = String.format("INSERT INTO account " + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
                name, address, cardInfo, billingName, billingAddress, email, password, phoneNumber);
                
            myStmt.executeUpdate(tmp);
            
            myStmt.close();
        }catch(SQLException e){

        }
        
    }

    public void addNewMovie(String title, String genre, String length, String releaseTime){
        
        try {
            
            Statement myStmt = dbConnect.createStatement();
            
            String tmp = String.format("INSERT INTO movie " + "VALUES ('%s', '%s', '%s', '%s')", 
                title, genre, length, releaseTime);
                
            myStmt.executeUpdate(tmp);
            myStmt.close();
        }catch(SQLException e){

        }
        
    }
    public boolean getSpecificMovie(String movieName){
        boolean isThere = false;
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "movie");
            
            while(results.next()){
                if(results.getString("Title").equals(movieName)){
                    
                    isThere = true;
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }
        return isThere;
    }
    public void addNewTicket(int seatNum, String showTime, String title, double cost, int day, int month, int year, int unique, String email){
        
        try {
            
            Statement myStmt = dbConnect.createStatement();
            
            String tmp = String.format("INSERT INTO ticket " + "VALUES (%d, '%s', '%s', %f, %d, %d, %d, %d, '%s')", 
                seatNum, showTime, title, cost, day, month, year, unique, email);
            myStmt.executeUpdate(tmp);
            myStmt.close();
        }catch(SQLException e){

        }
        
    }

    public String getSpecificTicket(int unique, String email){
        StringBuffer full = new StringBuffer();
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "ticket");
            
            while(results.next()){
                
                if(results.getInt("UniqueTicket") == unique && results.getString("Email").equals(email)){

                        boolean registered = false;
                        full.append(results.getFloat("Cost")+ "/" + results.getInt("SeatNumber") + "/" + results.getString("Title") + "/" 
                        + results.getInt("Day") + "/" + results.getInt("Month") + "/" + results.getInt("Year") + "/" + results.getString("Email") + "/");
                        
                        ResultSet checkRegistered = myStmt.executeQuery("SELECT * FROM " + "account");
                        while(checkRegistered.next()){
                            
                            if(checkRegistered.getString("Email").equals(email)){
                                registered = true;
                                break;
                            }
                        }
                        
                        checkRegistered.close();
                        full.append(registered);

                }
            }
            myStmt.close();
            
        }catch(SQLException e){

        }
        return full.toString();
    }

    public void removeSpecificTicket(String unique){
        try {
            String query = "DELETE FROM ticket WHERE UniqueTicket = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(9,unique);
            myStmt.executeUpdate();
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewShowTime(String title, String showTime, int day, int month, int year){
        try {
            
            Statement myStmt = dbConnect.createStatement();
            
            String tmp = String.format("INSERT INTO showtimes " + "VALUES ('%s', '%s', %d, %d, %d)", 
                title, showTime, day, month, year);
            myStmt.executeUpdate(tmp);
            myStmt.close();
        }catch(SQLException e){

        }
    }

    public String getSpecificShowTime(String title, String showTime, int day, int month, int year){
        StringBuffer full = new StringBuffer();
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "showtimes");
            
            while(results.next()){
                
                if(results.getString("Title").equals(title) && results.getString("ShowTime").equals(showTime) &&
                    results.getInt("Day") == day && results.getInt("Month") == month && results.getInt("Year") == year){
                    full.append(results.getInt("Day") + "/" + results.getInt("Month") + "/" + results.getInt("Year")
                    + "/" + results.getString("ShowTime") + "/" + results.getString("Title"));
                    
                }
            }
            myStmt.close();
            
        }catch(SQLException e){

        }
        return full.toString();
    }
    public String getAllShowTimes(String title){
        StringBuffer full = new StringBuffer();
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "showtimes");
            
            while(results.next()){
                
                if(results.getString("Title").equals(title)){
                    full.append(results.getInt("Day") + "/" + results.getInt("Month") + "/" + results.getInt("Year")
                    + "/" + results.getString("ShowTime") + "/" + results.getString("Title") + "\n");
                    
                }
            }
            myStmt.close();
            
        }catch(SQLException e){

        }
        
        return full.toString().strip();
    }

    public void addNewNews(String news, int day, int month, int year){
        try {
            
            Statement myStmt = dbConnect.createStatement();
            
            String tmp = String.format("INSERT INTO news " + "VALUES ('%s', %d, %d, %d)", 
                news, day, month, year);
            myStmt.executeUpdate(tmp);
            myStmt.close();
        }catch(SQLException e){

        }
    }

    public Vector<String> getAllNews(){
        Vector<String> full = new Vector<String>();
        LocalDate theDate = java.time.LocalDate.now();
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "showtimes");
            
            while(results.next()){
                LocalDate tmpDate = LocalDate.of(results.getInt("Year"), results.getInt("Month"), results.getInt("Day"));
                if(tmpDate.isBefore(theDate)){
                    full.add(results.getString("News") + "/" + tmpDate.toString());
                    
                }
            }
            myStmt.close();
            
        }catch(SQLException e){

        }
        
        return full;
    }

    public boolean getSpecificSeat(String title, int seatNumber, String showTime){
        boolean available = true;
        try {
            
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "seat");
            
            while(results.next()){
                if(results.getString("Title").equals(title) && results.getInt("SeatNumber") == seatNumber && results.getString("ShowTime").equals(showTime)){
                    
                    available = results.getBoolean("Vacant");
                }
            }
            myStmt.close();
        }catch(SQLException e){

        }
        return available;
    }
    /* 
    public void deleteAvailableFood(String id){
        try {
            String query = "DELETE FROM available_food WHERE ItemID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1,id);
            myStmt.executeUpdate();
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    */
    public void dbConnectClose(){
        try {
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public AccessDatabase(String url, String user, String pw){

        // Database URL
        this.DBURL = url;

        //  Database credentials
        this.USERNAME = user;
        this.PASSWORD = pw;
    }


    //Must create a connection to the database, no arguments, no return value
    public void initializeConnection(){
        try {
            dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    String getDburl(){
        return this.DBURL;
    }

    String getUsername(){
        return this.USERNAME;
    }

    String getPassword(){
        return this.PASSWORD;
    }

/*
    public String selectAllAvailableFood(){

        StringBuffer full = new StringBuffer();
        try {

            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM " + "available_food");

            while(results.next()){

                full.append(results.getString("ItemID") + ", "  + results.getString("Name"));
                full.append("\n");
            }
            full.deleteCharAt(full.length() - 1);
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return full.toString();
    }
    public String selectAllDailyClientNeeds(){

        StringBuffer full = new StringBuffer();
        try {

            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM " + "daily_client_needs");

            while(results.next()){

                full.append(results.getString("ClientID") + ", "  + results.getString("Client"));
                full.append("\n");
            }
            full.deleteCharAt(full.length() - 1);
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return full.toString();
    }
*/
    public void close() {

        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
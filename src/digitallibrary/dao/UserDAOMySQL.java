package digitallibrary.dao;


import java.sql.*;
//import java.text.*;

import digitallibrary.model.User;

public class UserDAOMySQL implements UserDAO {
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public Connection getConnection() {
        return con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public void setResultSet(ResultSet rs) {
        this.rs = rs;
    }

    private boolean nextExist = true;
    private boolean previousExist = true;

    public boolean isNextExist() {
        try {
            if (rs.next()) {// If the next record exists
                nextExist = true;  // set the nextExist to true
                rs.previous();  //Then go back to the current user
            }
            else {
                nextExist = false; // Reset the nextExist to false
            }
        } catch (SQLException e) {
            System.out.println("UserDAOMySQL / isNextExist ==> SQL Exception in checking the existence of the next user.");
            e.printStackTrace();
        }
        return nextExist;
    }

    public void setNextExist(boolean nextExists) {
        this.nextExist = nextExists;
    }

    public boolean isPreviousExist() {
        try {
            if (rs.previous()) {// If the previous record exists
                previousExist = true;  // set the nextExist to true
                rs.next();  //Then go forward to the current user
            }
            else {
                previousExist = false; // Reset the nextExist to false
            }
        } catch (SQLException e) {
            System.out.println("UserDAOMySQL / isPreviousExist ==> SQL Exception in checking the existence of the previous user.");
            e.printStackTrace();
        }
        return previousExist;
    }

    public void setPreviousExist(boolean previousExists) {
        this.previousExist= previousExists;
    }

    public User getUser () {
        User user = new User();
        String sql = "SELECT idusers,FirstName,LastName,AES_DECRYPT(username,'eD#178DT*2g31_V')," +
                "AES_DECRYPT(password,'eD#178DT*2g31_V'),AccessLevel,BirthYear FROM users";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setUserID(rs.getInt("idusers"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("AES_DECRYPT(username,'eD#178DT*2g31_V')"));
                user.setPassword(rs.getString("AES_DECRYPT(password,'eD#178DT*2g31_V')"));
                user.setAccessLevel(rs.getInt("AccessLevel"));
                user.setBirthYear(rs.getInt("BirthYear"));
            }
        } catch (SQLException ex) {
            System.out.println("UserDAOMySQL / getUser ==> SQL Exception in Readiing User from DB.");
            ex.printStackTrace();
        } /*finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }*/
        return user;
    }  // End of public User getUser ()

     public User getUserBySql (String sql){
         User user = new User();
         try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setUserID(rs.getInt("idusers"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("AES_DECRYPT(username,'eD#178DT*2g31_V')"));
                user.setPassword(rs.getString("AES_DECRYPT(password,'eD#178DT*2g31_V')"));
                user.setAccessLevel(rs.getInt("AccessLevel"));
                user.setBirthYear(rs.getInt("BirthYear"));
            }
        } catch (SQLException ex) {
            System.out.println("UserDAOMySQL / getUser ==> SQL Exception in Readiing User from DB.");
            ex.printStackTrace();
        } /*finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }*/
        return user;
    }  // End of public User getUserBySql ()


    public boolean insert(User u) {
        boolean inserted = false;
        try {
            String sql = "INSERT INTO users (FirstName,LastName,username,password,AccessLevel,BirthYear)" +
                         " values (?,?,?,?,?,?)";
            // System.out.println(sql);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, u.getFirstName());
            pstmt.setString(2, u.getLastName());
            pstmt.setString(3, "AES_ENCRYPT('" + u.getUserName() + "','eD#178DT*2g31_V')");
            pstmt.setString(4, "AES_ENCRYPT('" + u.getPassword() + "','eD#178DT*2g31_V')");
            pstmt.setInt(5, u.getAccessLevel());
            pstmt.setInt(6, u.getBirthYear());
            pstmt.executeUpdate();
            inserted = true;

        } catch(SQLException e) {
            inserted = false;
            System.out.println("UserDAOMySQL/insert ==> An Exception occured in user insertion.");
            e.printStackTrace();
        } finally {
            try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }            
        }
        return inserted;
    }  // End of insert(User u)
    
    public boolean delete(User u) {
        boolean deleted = false;
        try {
            stmt = con.createStatement();
            String sql = "DELETE FROM users WHERE idusers=" + u.getUserID();
            // System.out.println(sql);
            stmt.executeUpdate(sql);
            deleted = true;
        } catch (SQLException e) {
            deleted = false;
            System.out.println("UserDAOMySQL/delete ==> An Exception occured in user deletion.");
            e.printStackTrace();            
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            //try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return deleted;
    }

    public boolean update (int originalID, User u){
        boolean updated = false;
        try {
            stmt = con.createStatement();
            String sql = "UPDATE users SET idusers=" + u.getUserID()+ ",FirstName='" + u.getFirstName() +
                     "', LastName='" + u.getLastName() + "', username=" + "AES_ENCRYPT('" + u.getUserName() + "','eD#178DT*2g31_V')" +
                     ",Password=" + "AES_ENCRYPT('" + u.getPassword() + "','eD#178DT*2g31_V')" +
                     ", AccessLevel=" + u.getAccessLevel() + ",BirthYear=" + u.getBirthYear() + " WHERE idusers=" + originalID;
            // System.out.println(sql);
            stmt.executeUpdate(sql);
            updated = true;
        } catch (SQLException e) {
            updated = false;
            System.out.println("UserDAOMySQL ==> An Exception occured in user update.");
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            //try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return updated;
    }


    public User loadByID(int userID) {
        User u = null;            

        try {
            String sql =  "SELECT idusers,FirstName,LastName,AES_DECRYPT(username,'eD#178DT*2g31_V')," +
                "AES_DECRYPT(password,'eD#178DT*2g31_V'),AccessLevel,BirthYear FROM users";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            // System.out.println(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUserID(rs.getInt("idusers"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                u.setUserName(rs.getString("AES_DECRYPT(username,'eD#178DT*2g31_V')"));
                u.setPassword(rs.getString("AES_DECRYPT(password,'eD#178DT*2g31_V')"));
                u.setAccessLevel(rs.getInt("AccessLevel"));
                u.setBirthYear(rs.getInt("BirthYear"));          
            }

        } catch(SQLException e) {
            System.out.println ("UserDAOMySQL / loadByID ==> SQL Exception Occured in User loading." );
            e.printStackTrace();
        } finally {
            try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return u;
    }  // End of public User loadByID(int userID)

    public User nextUser() {  // Go to the next user using the given ResultSet
        User u = null;
        try {
            if (rs.next()) {    // If the next User Exists
                u = new User(); // Construct a new user and set values of its field
                u.setUserID(rs.getInt("idusers"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                String username = rs.getString("AES_DECRYPT(username,'eD#178DT*2g31_V')");
                u.setUserName(username);
                String password = rs.getString("AES_DECRYPT(password,'eD#178DT*2g31_V')");
                u.setPassword(password);
                u.setAccessLevel(rs.getInt("AccessLevel"));
                u.setBirthYear(rs.getInt("BirthYear"));
            }
            else{ //If the next User does not exist
                nextExist = false;
                //rs.previous();  // Go back to the current user
            }
        } catch (SQLException e){
            System.out.println("UserDAOMySQL / nextUser ==> SQL Exception in going to the next user.");
            e.printStackTrace();
        }
        return u;
    }

     public User previousUser() {
        User u = null;
        try {
            if (rs.previous()) {  // If the previous User Exists
                u = new User();   // Construct a new user and set values of its field
                u.setUserID(rs.getInt("idusers"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                u.setUserName(rs.getString("AES_DECRYPT(username,'eD#178DT*2g31_V')"));
                u.setPassword(rs.getString("AES_DECRYPT(password,'eD#178DT*2g31_V')"));
                u.setAccessLevel(rs.getInt("AccessLevel"));
                u.setBirthYear(rs.getInt("BirthYear"));

            }
            else { //If the previous User does not exist
                previousExist = false;
                //rs.next();  // Go back to the current user
            }
        } catch (SQLException e){
            System.out.println("UserDAOMySQL / previousUser ==> SQL Exception in going to the previous user.");
            e.printStackTrace();
        }
        return u;
    }

    public int getNumberOfSearchedUsers (String searchSql) {
        int n=0;
        try {
            stmt = con.createStatement();
            // String for Select Users
            String strSelect = "idusers,FirstName,LastName,AES_DECRYPT(username,'eD#178DT*2g31_V')," +
                    "AES_DECRYPT(password,'eD#178DT*2g31_V'),AccessLevel,BirthYear";
            // String for Count Users
            String strCount = "COUNT(idusers)";
            // Replace Select String By Count String to count users
            String countSql = searchSql.replace(strSelect, strCount);
            System.out.println(countSql);
            rs = stmt.executeQuery(countSql);
            if (rs.next())
                n = rs.getInt(strCount);
        } catch (SQLException e) {
            System.out.println("UserDAOMySQL / getNumberOfSearchedUsers ==> A SQL Exception in counting serached books.");
            e.printStackTrace();
        }
        return n;
    }

    public void closeDBConnection () {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("BookDAOMySQL / closeConnection ==> SQL Exception in closing the DB Connection.");
            e.printStackTrace();
        }
    }


}

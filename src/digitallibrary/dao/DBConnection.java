package digitallibrary.dao;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        String username = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/digital_library";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        } catch(Exception e) {
             e.printStackTrace();
        }

        return con;

    }

      public static Connection borrowConnection() {

           Connection con = null;
           try {
             Context initContext = new InitialContext();
             Context envContext  = (Context)initContext.lookup("java:/comp/env");
             DataSource ds = (DataSource)envContext.lookup("jdbc/myDB");
             con = ds.getConnection();
           } catch (Exception e) {
               e.printStackTrace();
           }
           return con;

    }


}

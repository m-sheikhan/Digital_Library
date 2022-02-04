package digitallibrary.dao;

import java.sql.*;

public class UserDAOFactory {

    public static UserDAO getDAO() {

        UserDAO dao = null;

        //dao = new BookDAOHibernate();


        Connection con = DBConnection.getConnection();

        String DBName="";
        try {
            DBName = con.getMetaData().getDatabaseProductName();
        } catch(Exception e) {
            System.out.println("UserDAOFactory ==> Cannot recognize DataBase!");
            e.printStackTrace();
        }
        //System.out.println(DBName);
        if (DBName.equals("MySQL")) { // For MySQL
            dao = new UserDAOMySQL();
            dao.setConnection(con);
        }  // For MySQL
        else if ((DBName.equals("Oracle"))){ // For Oracle
           // dao = new UserDAOOracle();
        }

        return dao;

    }


}
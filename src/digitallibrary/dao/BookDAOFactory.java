package digitallibrary.dao;

import java.sql.*;

public class BookDAOFactory {

    public static BookDAO getDAO() {

        BookDAO dao = null;

        //dao = new BookDAOHibernate();


        Connection con = DBConnection.getConnection();

        String DBName="";
        try {
            DBName = con.getMetaData().getDatabaseProductName();
        } catch(Exception e) {
            System.out.println("BookDAOFactory ==> Cannot recognize DataBase!");
            e.printStackTrace();
        }
        //System.out.println(DBName);
        if (DBName.equals("MySQL")) { // For MySQL
            dao = new BookDAOMySQL();
            dao.setConnection(con);
        }
        else if ((DBName.equals("Oracle"))){ // For Oracle
            //dao = new BookDAOOracle();
        }

        return dao;

    }


}
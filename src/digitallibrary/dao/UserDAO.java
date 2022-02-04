package digitallibrary.dao;

import java.sql.*;
import digitallibrary.model.User;

public interface UserDAO {
    User getUser ();
    User getUserBySql (String sql);
    boolean insert(User u);
    boolean delete(User u);
    boolean update(int originalID, User u);
    User loadByID(int id);
    User previousUser();
    User nextUser();
    void setConnection(Connection con);
    Connection getConnection();
    void setResultSet(ResultSet rs);
    ResultSet getResultSet();
    boolean isNextExist();
    void setNextExist(boolean nextExists);
    boolean isPreviousExist();
    void setPreviousExist(boolean previousExists);
    void closeDBConnection();
    int getNumberOfSearchedUsers (String searchSql);
}

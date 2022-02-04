package digitallibrary.dao;

import java.sql.*;
import digitallibrary.model.Book;

public interface BookDAO {
    Book getBook ();
    Book getBookBySql (String sql);
    boolean insert(Book b, boolean isBookFileAttached );
    boolean delete(Book b);
    boolean update(int originalID, Book b, boolean isBookFileAttached);
    Book loadByID(int id);
    Book previousBook();
    Book nextBook();
    void setConnection(Connection con);
    Connection getConnection();
    void setResultSet(ResultSet rs);
    ResultSet getResultSet();
    boolean isNextExist();
    void setNextExist(boolean nextExists);
    boolean isPreviousExist();
    void setPreviousExist(boolean previousExists);
    void closeDBConnection();
    void saveBookFile(int id, String fileName);
    void uploadBookFile(int id, String fileName);
    int getNumberOfSearchedBooks (String searchSql);
}

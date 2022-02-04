package digitallibrary.dao;

import java.io.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import digitallibrary.model.Book;


public class BookDAOMySQL implements BookDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private boolean nextExist = true;
    private boolean previousExist = true;
    private String title, docType, volumeLabel, authors, publisher, isbn, category, keywords;
    private String fileName, fileFormat, bookText ;

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
    
    public boolean isNextExist() {
        try {
            if (rs.next()) {// If the next record exists
                nextExist = true;  // set the nextExist to true
                rs.previous();  //Then go back to the current book
            }
            else {
                nextExist = false; // Reset the nextExist to false
            }
        } catch (SQLException e) {
            System.out.println("BookDAOMySQL / isNextExist ==> SQL Exception in checking the existence of the next book.");
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
                rs.next();  //Then go forward to the current book
            }
            else {
                previousExist = false; // Reset the nextExist to false
            }
        } catch (SQLException e) {
            System.out.println("BookDAOMySQL / isPreviousExist ==> SQL Exception in checking the existence of the previous book.");
            e.printStackTrace();
        }
        return previousExist;
    }

    public void setPreviousExist(boolean previousExists) {
        this.previousExist= previousExists;
    }

    public Book getBook () {
        Book book = null;
        //con = DBConnection.getConnection();
        String sql = "SELECT * FROM books";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("idbooks"));
                book.setTitle(rs.getString("Title"));
                book.setDocumentType(rs.getString("DocumentType"));
                book.setVolumeNo(rs.getInt("VolumeNo"));
                book.setVolumeLabel(rs.getString("VolumeLabel"));
                book.setEdition(rs.getInt("Edition"));
                book.setAuthors(rs.getString("Authors"));
                book.setYear(rs.getInt("Year"));
                book.setPublisher(rs.getString("Publisher"));
                book.setCategory(rs.getString("Category"));
                book.setIsbn(rs.getString("ISBN"));
                book.setKeywords(rs.getString("Keywords"));
                book.setNumberOfPages(rs.getInt("NumberOfPages"));
                book.setFileFormat(rs.getString("FileFormat"));
            }
        } catch (SQLException ex) {
            System.out.println("BookDAOMySQL / getBook ==> SQL Exception in Readiing Book from DB.");
            ex.printStackTrace();
        } 
        return book;
    }  // End of public Book getBook ()

     public Book getBookBySql (String sql) {
        Book book = new Book();
        //con = DBConnection.getConnection();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {                 
                book.setId(rs.getInt("idbooks"));
                book.setTitle(rs.getString("Title"));
                book.setDocumentType(rs.getString("DocumentType"));
                book.setVolumeNo(rs.getInt("VolumeNo"));
                book.setVolumeLabel(rs.getString("VolumeLabel"));
                book.setEdition(rs.getInt("Edition"));
                book.setAuthors(rs.getString("Authors"));
                book.setYear(rs.getInt("Year"));
                book.setPublisher(rs.getString("Publisher"));
                book.setCategory(rs.getString("Category"));
                book.setIsbn(rs.getString("ISBN"));
                book.setKeywords(rs.getString("Keywords"));
                book.setNumberOfPages(rs.getInt("NumberOfPages"));
                book.setFileFormat(rs.getString("FileFormat"));
            }
        } catch (SQLException ex) {
            System.out.println("BookDAOMySQL / getBook ==> SQL Exception in Readiing Book from DB.");
            ex.printStackTrace();
        }
        return book;
    }  // End of public Book getBook ()

    public boolean insert(Book book, boolean isBookFileAttached) {
        boolean inserted = false;
        try {
            stmt = con.createStatement();
            // Replace all "'" characters with "''" in order to adapt with MySql Syntax
            title = book.getTitle().replace("'", "''");
            docType = book.getDocumentType().replace("'", "''");
            volumeLabel = book.getVolumeLabel().replace("'", "''");
            authors = book.getAuthors().replace("'", "''");
            publisher = book.getPublisher().replace("'", "''");
            isbn = book.getIsbn().replace("'", "''");
            category = book.getCategory().replace("'", "''");
            keywords = book.getKeywords().replace("'", "''");
            
            String sql = "INSERT INTO books SET Title='" + title +"',DocumentType='" + docType +
                         "',VolumeNo=" + book.getVolumeNo() + ",VolumeLabel='" + volumeLabel +
                         "',Edition=" + book.getEdition() +",Authors='"+ authors + "',Year="+ book.getYear()+
                         ",Publisher='"+ publisher + "',ISBN='" + isbn + "',Category='" + category +
                         "',Keywords='" + keywords + "',NumberOfPages=" + book.getNumberOfPages();

            if (isBookFileAttached) {  // If a file selected, load it and its text into the DB
                // Replace all "'" characters with "''" in order to adapt with MySql Syntax
                fileFormat = book.getFileFormat().replace("'", "''");
                fileName = book.getFileName().replace("'", "''");  
                bookText = book.getBookText().replace("'", "''");  
                sql += ",FileFormat='" + fileFormat +"',BookFile=LOAD_FILE('" + fileName +"'), BookText='" + bookText +"'";
            }
            //System.out.println(sql);
            stmt.executeUpdate(sql);
            inserted = true;
        } catch(SQLException e) {
            inserted = false;
            System.out.println("BookDAOMySQL / insert ==> SQL Exception during Book insertion into DB.");
            e.printStackTrace();
        }/*finally {
            //try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        }*/
        return inserted;
    }

    public boolean delete(Book book) {
        boolean deleted = false;
        try {
            stmt = con.createStatement();
            String sql = "DELETE FROM books WHERE idbooks=" + book.getId();
            //System.out.println(sql);
            stmt.executeUpdate(sql);
            deleted =true;
        } catch (SQLException e) {
            deleted = false;
            System.out.println("BookDAOMySQL/delete ==> An Exception occured during book deletion.");
            e.printStackTrace();
        } /*finally {
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        }*/
        return deleted;
    }

    public boolean update (int originalID, Book book, boolean isBookFileAttached){
        boolean updated = false;
        //StringBuffer sql = new StringBuffer();
        try {
            stmt = con.createStatement();
            // Replace all "'" characters with "''" in order to adapt with MySql Syntax
            title = book.getTitle().replace("'", "''");
            docType = book.getDocumentType().replace("'", "''");
            volumeLabel = book.getVolumeLabel().replace("'", "''");
            authors = book.getAuthors().replace("'", "''");
            publisher = book.getPublisher().replace("'", "''");
            isbn = book.getIsbn().replace("'", "''");
            category = book.getCategory().replace("'", "''");
            keywords = book.getKeywords().replace("'", "''");
            fileFormat = book.getFileFormat().replace("'", "''");

            String sql = "UPDATE books SET idbooks=" + book.getId()+ ",Title='" + title +
                     "',DocumentType='" + docType + "',VolumeNo=" + book.getVolumeNo() +
                     ",VolumeLabel='" + volumeLabel + "',Edition=" + book.getEdition() +
                     ",Authors='" + authors + "',Year=" + book.getYear() +
                     ", Publisher='" + publisher  + "',ISBN='" + isbn +
                      "',Category='" + category + "',Keywords='" + keywords +
                     "',NumberOfPages=" + book.getNumberOfPages();

            if (isBookFileAttached) {  // If a file selected, load it and its text into the DB
                // Replace all "'" characters with "''" in order to adapt with MySql Syntax                
                fileName = book.getFileName().replace("'", "''");  
                bookText = book.getBookText().replace("'", "''");  
                sql += ",FileFormat='" + fileFormat +"',BookFile=LOAD_FILE('" + fileName +"'), BookText='" + bookText +"'";
            }
            sql +=" WHERE idbooks=" + originalID;
            //System.out.println(sql);
            stmt.executeUpdate(sql);
            updated = true;
        } catch (SQLException e) {
            updated = false;
            System.out.println("BookDAOMySQL ==> An Exception occured during book update.");
            e.printStackTrace();
        } /* finally {
            //try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        } */
        return updated;
    }

    public Book loadByID(int number) {
        Book book = null;
        try {
            String sql = "SELECT * FROM books WHERE idbooks=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                //book.setNumber(number);
                book.setTitle(rs.getString("Title"));
                book.setDocumentType(rs.getString("DocumentType"));
                book.setVolumeNo(rs.getInt("VolumeNo"));
                book.setVolumeLabel(rs.getString("VolumeLabel"));
                book.setEdition(rs.getInt("Edition"));
                book.setAuthors(rs.getString("Authors"));
                book.setYear(rs.getInt("Year"));
                book.setPublisher(rs.getString("Publisher"));
                book.setCategory(rs.getString("Category"));
                book.setIsbn(rs.getString("ISBN"));
                book.setKeywords(rs.getString("Keywords"));
                book.setNumberOfPages(rs.getInt("NumberOfPages"));
                book.setFileFormat(rs.getString("FileFormat"));
                //book.setFileName(rs.getString("FileName"));
            }
        } catch(SQLException e) {
            System.out.println ("BookDAOMySQL / loadByID ==> SQL Exception Occured in Book loading." );
            e.printStackTrace();
        } finally {
            try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return book;
    }
    
    public Book nextBook() {  // Go to the next book using the given ResultSet
        Book book = null;
        try {
            if (rs.next()) {    // If the next Book Exists
                book = new Book(); // Construct a new book and set values of its field
                book.setId(Integer.parseInt(rs.getString("idbooks")));
                book.setTitle(rs.getString("Title"));
                book.setDocumentType(rs.getString("DocumentType"));
                book.setVolumeNo(rs.getInt("VolumeNo"));
                book.setVolumeLabel(rs.getString("VolumeLabel"));
                book.setEdition(rs.getInt("Edition"));
                book.setAuthors(rs.getString("Authors"));
                book.setYear(rs.getInt("Year"));
                book.setPublisher(rs.getString("Publisher"));
                book.setCategory(rs.getString("Category"));
                book.setIsbn(rs.getString("ISBN"));
                book.setKeywords(rs.getString("Keywords"));
                book.setNumberOfPages(rs.getInt("NumberOfPages"));
                book.setFileFormat(rs.getString("FileFormat"));
            }
            else{ //If the next Book does not exist
                nextExist = false;
                //rs.previous();  // Go back to the current book
            }
        } catch (SQLException e){
            System.out.println("BookDAOMySQL / nextBook ==> SQL Exception in going to the next book.");
            e.printStackTrace();
        }
        return book;
    }

    public Book previousBook() {
        Book book = null;
        try {
            if (rs.previous()) {  // If the previous Book Exists
                book = new Book();   // Construct a new book and set values of its field
                book.setId(Integer.parseInt(rs.getString("idbooks")));
                book.setTitle(rs.getString("Title"));
                book.setDocumentType(rs.getString("DocumentType"));
                book.setVolumeNo(rs.getInt("VolumeNo"));
                book.setVolumeLabel(rs.getString("VolumeLabel"));
                book.setEdition(rs.getInt("Edition"));
                book.setAuthors(rs.getString("Authors"));
                book.setYear(rs.getInt("Year"));
                book.setPublisher(rs.getString("Publisher"));
                book.setCategory(rs.getString("Category"));
                book.setIsbn(rs.getString("ISBN"));
                book.setKeywords(rs.getString("Keywords"));
                book.setNumberOfPages(rs.getInt("NumberOfPages"));
                book.setFileFormat(rs.getString("FileFormat"));
            }
            else { //If the previous Book does not exist
                previousExist = false;
                //rs.next();  // Go back to the current book
            }
        } catch (SQLException e){
            System.out.println("BookDAOMySQL / previousBook ==> SQL Exception in going to the previous book.");
            e.printStackTrace();
        }
        return book;
    }

    public void closeDBConnection () {
       try {
            con.close();
       } catch (SQLException e) {
           System.out.println("BookDAOMySQL / closeConnection ==> SQL Exception in closing the DB Connection.");
           e.printStackTrace();
       }
    }

    public void saveBookFile(int id, String fileName) {
        String sql = "SELECT * FROM books WHERE idBooks=" + id;
        //System.out.println(sql);
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
             if ( rs.next() && rs.getString("BookFile")!= null ) { // If a Book found and it has a stored file
                 InputStream in = rs.getBinaryStream("BookFile");
                 try {  // Try to Save the File
                     OutputStream out = new FileOutputStream(fileName);
                     byte[] block = new byte[256*1024];
                     int counter;
                     try {   //Read the File From DB as a stream of 256K Blocks and store it in a file
                         while ( (counter = in.read(block)) != -1 ){
                             out.write(block,0,counter);
                         }
                     } catch (IOException e) {  // Catch for try Reading the File
                         System.out.printf("BookDAOMySQL / SaveBookFile ==> Error in Reading File!\n");
                         e.printStackTrace();
                     } finally {  // Close Input and Output Streams
                         try {in.close();} catch (IOException e) {e.printStackTrace(); }
                         try {out.close();} catch (IOException e) {e.printStackTrace(); }
                     }  // End of finally
                 // Catch for trying to save file
                 } catch (FileNotFoundException fnf){  // Catch the FileNotFoundException and Display an Error Message
                     System.out.println("BookDAOMySQL / SaveBookFile ==> File Can not be saved!");
                     fnf.printStackTrace();
                 }  //catch (FileNotFoundException fnf)
             } // End of  if ( rs.next() && rs.getString("BookFile")!= null ), When a Book found and it has a stored file
        } catch (SQLException e) {
            System.out.println("BookDAOMySQL / saveBookFile ==> A SQL Exception in Saving Book File.");
            e.printStackTrace();
        }
    }  // End of public void saveBookFile

    public void uploadBookFile(int id, String fileName){

    }

    public int getNumberOfSearchedBooks (String searchSql){
        int n=0;
        try {
            stmt = con.createStatement();
            String countSql = searchSql.replace("SELECT *", "SELECT COUNT(*)");
            rs = stmt.executeQuery(countSql);
            if (rs.next())  n = rs.getInt("COUNT(*)");
        } catch (SQLException e) {
            System.out.println("BookDAOMySQL / getNumberOfSearchedBooks ==> A SQL Exception in counting serached books.");
            e.printStackTrace();
        }
        return n;
    }
}

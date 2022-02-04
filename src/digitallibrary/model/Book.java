package digitallibrary.model;
/*
    Data Model
 */

import digitallibrary.dao.BookDAO;
import digitallibrary.dao.BookDAOFactory;
import digitallibrary.dao.BookDAOMySQL;


public class Book  implements java.io.Serializable {

    //Fields
    private int id;
    private String title;
    private String documentType;
    private int volumeNo;
    private String volumeLabel;
    private String authors;
    private int year;
    private String publisher;
    private int edition;
    private String isbn;
    private String keywords;
    private String category;
    private int numberOfPages;
    private String fileFormat;
    private String fileName;
    private String bookText;



    //Constructors
    public Book() {
    }

    public Book (int id,  String title, String documentType, int volumeNo, String volumeLabel, String authors,
                 int year, String publisher,  int edition, String isbn, String keywords, String category,
                 int numberOfPages, String fileFormat, String fileName)
    {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.volumeNo = volumeNo;
        this.volumeLabel = volumeLabel;
        this.authors = authors;
        this.year = year;
        this.publisher = publisher;
        this.edition = edition;
        this.isbn = isbn;
        this.keywords = keywords;
        this.category = category;
        this.numberOfPages = numberOfPages;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }
    
    //Setters and Getters
     public void setId(int id) {
        this.id = id;
    }  // ID is set by DB
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setVolumeNo(int volumeNo) {
        this.volumeNo = volumeNo;
    }
     public int getVolumeNo() {
        return volumeNo;
    }

    public void setVolumeLabel(String volumeLabel) {
        this.volumeLabel = volumeLabel;
    }
    public String getVolumeLabel() {
        return volumeLabel;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public String getAuthors() {
        return authors;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPublisher() {
        return publisher;
    }
    
    public void setEdition(int edition) {
        this.edition = edition;
    }
    public int getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getKeywords() {
        return keywords;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    
    public String getBookText() {
        return bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }

    // Persistence Methods
    public void save() {

       //BookDAOMySQL dao = new BookDAOMySQL();
       BookDAO dao = BookDAOFactory.getDAO();
       dao.insert(this, true);

    }

    public void findByNumber(int number) throws Exception {

        BookDAO dao = BookDAOFactory.getDAO();
        Book b = dao.loadByID(number);
        if (b == null) throw new Exception();
        setTitle(b.getTitle());
        setVolumeNo(b.getVolumeNo());
        setVolumeLabel(b.getVolumeLabel());
        setAuthors(b.getAuthors());
        setYear(b.getYear());
        setPublisher(b.getPublisher());
        setEdition(b.getEdition());
        setIsbn(b.getIsbn());
        setKeywords(b.getKeywords());
        setNumberOfPages(b.getNumberOfPages());
        setFileFormat(b.getFileFormat());
        setFileName(b.getFileName());
    }

    

   

    

    

    

    

    

    














    

    

    

    

   

    





}

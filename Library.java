
package librarymanagment;

import java.util.Scanner;
import java.sql.*;
public class Library extends DbConnection{
    private int num;
    private int bookID;
    private String bookName;
    private String authorName;
    private String subject;
    private int totalNumber;
    private int count,totalPiece;
    private boolean flag,flag1;
    private final Scanner sc = new Scanner(System.in);
    private int names;
    public void start(){
        System.out.print("\n1)Store new book\n2)Issue a book\n3)Return a book\n"
                                + "4)Go Back\n");
        System.out.print("Enter your choice:-");
        num = sc.nextInt();
        switch(num){
            case 1: storeNewBook();
                    break;
            case 2: issueBook();
                    break;
            case 3: returnBook();
                    break;
            case 4: LibraryManagment lm = new LibraryManagment();
                    lm.mainMenu();
                    break;
        }
    }
    
    private boolean checkBook(){
        try{
            super.initializeDbConnection();
            try {
                rs = st.executeQuery("SELECT * from Books WHERE BookId = "
                                    +bookID);
                while (rs.next()) {
                    names = rs.getInt("BookId");
                    System.out.println(names+"  ");
                }
                if(names == bookID)
                    flag1=Boolean.FALSE;
                else
                    flag1=Boolean.TRUE;
            }
            catch(SQLException e){
                System.out.print("\nError:-"+e);
            }
            catch (Exception e) {
                System.out.print("\nError:-"+e);
            }
            finally{
                closeDbConnection(c);
            }
        }
        catch(SQLException e){
            System.out.print("\nError:-"+e);
        }
        catch (ClassNotFoundException e) {
            System.out.print("\nError:-"+e);
        }
        if(flag1)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;                    
    }
    
    private int totalPiece(){
        try{
            initializeDbConnection();
            try{
                rs = st.executeQuery("SELECT * from Books WHERE BookId = "
                                    +bookID);
                while (rs.next()) {
                    totalPiece = rs.getInt("TotalNo");
                    System.out.println(totalPiece+"  ");
                }
            }
            catch(SQLException e){
                System.out.print("\nError:-"+e);
            }
            finally{
                closeDbConnection(c);
            }
        }
        catch(SQLException e){
             System.out.print("\nError:-"+e);
        }
        catch(ClassNotFoundException e){
            System.out.print("\nError:-"+e);
        }
        
        return totalPiece;
    }
    
    private void storeBookDb(int bookID,String bookName,String authorName,
                             String subject,int totalNumber,int choice){
        try{
            initializeDbConnection();
            try{
                switch(choice){
                    case 1:
                        System.out.print("\nSaving info:-");
                        st.executeUpdate("INSERT INTO `Books` (BookId,BookName,AuthorName,Subject,TotalNo)"+
                    "VALUES('"+bookID+"','"+bookName+"','"+authorName+"','"+subject+"','"+totalNumber+"')");
                    break;
                    case 2:
                        System.out.print("\nUpdating info:-");
                        st.executeUpdate("UPDATE Books SET TotalNo="+totalNumber+" WHERE BookId in ("+bookID+")");
                        break;
                }
            }
            catch(SQLException e){
                System.out.print("\nError:-"+e);
            }
            finally{
                closeDbConnection(c);
            }
        }
        catch(SQLException e){
             System.out.print("\nError:-"+e);
        }
        catch(ClassNotFoundException e){
            System.out.print("\nError:-"+e);
        }
        
    }
    
    public void storeNewBook(){
        System.out.print("\nBook ID:-");
        bookID = sc.nextInt();
        flag = checkBook();
        if(flag){
            System.out.print("Book Name:-");
            sc.nextLine();
            bookName = sc.nextLine();
            System.out.print("Author name:-");
            authorName = sc.nextLine();
            System.out.print("Subject:-");
            subject = sc.nextLine();
            System.out.print("Number of books:-");
            totalNumber = sc.nextInt();
            storeBookDb(bookID, bookName, authorName, subject, totalNumber,1);
        }
        else{
            System.out.print("Number of books:-");
            count = sc.nextInt();
            totalNumber = totalPiece();
            totalNumber = totalNumber+count;
            System.out.println(totalNumber);
            storeBookDb(bookID, null, null, null, totalNumber, 2);
        }
        try {
            //query for insertion of the data
        } catch (Exception e) {
        }
    }
    
    
    
    public void issueBook(){
        
    }
    public void returnBook(){
        
    }
}


package librarymanagment;
import java.util.InputMismatchException;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;
public class Student extends DbConnection{
    private int studentId;
    private final Scanner sc = new Scanner(System.in);
    
    public void start(){
        int choice;
        System.out.print("\n1)Display all books in library\n2)Books you hold\n3)Go back\n");
        System.out.print("Enter your choice:-");
        try{
            choice = sc.nextInt();
            switch(choice){
                case 1: displayAllBook();
                        break;
                case 2: personalDetails();
                        break;
                case 3: LibraryManagment libraryManagment = new LibraryManagment();
                        libraryManagment.mainMenu();
                        break;
                default:
                        System.out.println("Invalid input..try again..");
                        start();
            }
        }catch(InputMismatchException i){
            System.out.println("Invalid input..try again..");
            sc.next();
            start();
        }
    }
    public void displayAllBook(){
         try{
            initializeDbConnection();
            try{ 
                resultSet = statement.executeQuery("SELECT * from Books");
                System.out.println("BookId\tBookName\tAuthorName\tSubject\t\tTotalNo");
                while (resultSet.next()) {
                   int id = resultSet.getInt("BookId");
                   String bookName= resultSet.getString("BookName");
                   String authorName=resultSet.getString("AuthorName");
                   String subject = resultSet.getString("Subject");
                   int totalNo = resultSet.getInt("TotalNo");
                   System.out.println(id+"\t"+bookName+"\t\t"+authorName+"\t\t"+subject+"\t\t"+totalNo);
                }
            }
            catch(SQLException e){
                System.out.print("\nError:-"+e);
            }
            finally{
                closeDbConnection(connect);
            }
        }
        catch(SQLException e){
             System.out.print("\nError:-"+e);
        }
        catch(ClassNotFoundException e){
            System.out.print("\nError:-"+e);
        }
        start();
    }
    public void personalDetails(){
        boolean input=false;
        boolean flag=false;
        boolean negativeNumber=false;
        while(!input){
            try{
                while(!negativeNumber){
                    System.out.print("\nStudent id:-");
                    studentId = sc.nextInt();
                    if(studentId>0){
                        negativeNumber=true;
                        break;
                    }
                    else{
                        System.out.println("Negative not allowed....");
                    }
                }
                input=true;
            }catch(InputMismatchException i){
                System.out.println("Invalid input...enter again..");
                sc.nextLine();
                personalDetails();
            }
        }
        flag=findStudentBooks();
        if(flag){
            displayRecord();
            start();
        }
        else{
            System.out.println("You dont contain any book....");
            start();
        }
    }
    
    private void displayRecord(){
        int id,bookId=0;
        String bookName=null,authorName=null,subject=null;
        Date issueDate=null,returnDate=null;
        try{
            initializeDbConnection();
            rs1 = statement1.executeQuery("SELECT * from IssueBook WHERE LibraryId ="+studentId);
            System.out.println("\n\nBookid\tBook\tAuthor\tSubject\t   IssueDate\tReturnDate\n");
            while(rs1.next()){
                bookId = rs1.getInt("BookID");
                issueDate = rs1.getDate("IssueDate");
                returnDate = rs1.getDate("ReturnDate");
                
                resultSet = statement.executeQuery("SELECT * from  Books WHERE BookId ="+bookId);
                while(resultSet.next()){
                    bookName = resultSet.getString("BookName");
                    authorName = resultSet.getString("AuthorName");
                    subject = resultSet.getString("Subject");
                }
                System.out.println(bookId+"\t"+bookName+"\t"+authorName+"\t"+subject+"\t   "+issueDate+"\t"+returnDate);
            }
            
        }catch(SQLException e){
            System.out.print("\nError:-" + e);
        }catch(ClassNotFoundException e){
            System.out.print("\nError:-" + e);
        }
        finally{
            closeDbConnection(connect);
        }
    }
    
    
    private boolean findStudentBooks(){
        int id=0;int book=0;
        boolean flag=false;
        try{
            initializeDbConnection();
            resultSet = statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId ="+studentId);
            while(resultSet.next()){
                id = resultSet.getInt("LibraryId");
                book = resultSet.getInt("TotalBooks");
            }
            if(id==studentId && book>0){
                flag = true;
            }
            else
                flag=false;
        }catch(SQLException e){
            System.out.print("\nError:-" + e);
        }catch(ClassNotFoundException e){
            System.out.print("\nError:-" + e);
        }
        finally{
            closeDbConnection(connect);
        }
        if(flag)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}

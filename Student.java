
package librarymanagment;
import java.util.InputMismatchException;
import java.sql.SQLException;
import java.util.Scanner;

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
                rs = st.executeQuery("SELECT * from Books");
                System.out.println("BookId\tBookName\tAuthorName\tSubject\t\tTotalNo");
                while (rs.next()) {
                   int id = rs.getInt("BookId");
                   String bookName= rs.getString("BookName");
                   String authorName=rs.getString("AuthorName");
                   String subject = rs.getString("Subject");
                   int totalNo = rs.getInt("TotalNo");
                   System.out.println(id+"\t"+bookName+"\t\t"+authorName+"\t\t"+subject+"\t\t"+totalNo);
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
        start();
    }
    public void personalDetails(){
        System.out.print("\nStudent id:-");
        studentId = sc.nextInt();
        try{
            initializeDbConnection();
            try{ 
                rs = st.executeQuery("SELECT * from IssueBook WHERE LibraryId = "+studentId);
                while (rs.next()) {
                    int id = rs.getInt("BookID");
                    rs1 = st.executeQuery("SELECT * from Books WHERE BookId = "+id);
                    while (rs.next()) {
                        String bookName= rs.getString("BookName");
                        String authorName=rs.getString("AuthorName");
                        String subject = rs.getString("Subject");
                        System.out.print("\n"+bookName+"\t\t"+authorName+"\t\t"+subject+"\t\t");
                    }
                }
                System.out.println("BookId\tBookName\tAuthorName\tSubject\t\tTotalNo");
                while (rs.next()) {
                   int id = rs.getInt("BookId");
                   String bookName= rs.getString("BookName");
                   String authorName=rs.getString("AuthorName");
                   String subject = rs.getString("Subject");
                   int totalNo = rs.getInt("TotalNo");
                   System.out.println(id+"\t"+bookName+"\t\t"+authorName+"\t\t"+subject+"\t\t"+totalNo);
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
        start();
    }
}

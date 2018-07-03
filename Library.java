
package librarymanagment;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Library extends DbConnection{
    
    private final Scanner sc = new Scanner(System.in);
    private int num,id;
    private int bookID;
    private String bookName,studentName;
    private String authorName;
    private String subject;
    private int totalNumber;
    private int count,totalPiece;
    private boolean flag,flag1;
    private int names,num1,num2,num3;
    private int studentId,totalBooks,totalFine,fineAmount,indicate;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime issueDate = null;
    LocalDateTime returnDate = null;
    
    public void start(){
        System.out.print("\n1)Store new book\n2)Issue a book\n3)Return a book\n"
                                + "4)Register Student\n5)Go Back\n");
        System.out.print("Enter your choice:-");
        num = sc.nextInt();
        switch(num){
            case 1: storeNewBook();
                    break;
            case 2: issueBook();
                    break;
            case 3: returnBook();
                    break;
            case 4: registerNewStudent();
                    break;
            case 5: LibraryManagment lm = new LibraryManagment();
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
                             String subject,int totalNumber,int fineamount,int choice){
        try{
            initializeDbConnection();
            try{
                switch(choice){
                    case 1:
                        System.out.print("\nSaving info..");
                        st.executeUpdate("INSERT INTO `Books` (BookId,BookName,AuthorName,Subject,TotalNo,Fine)"+
                    "VALUES('"+bookID+"','"+bookName+"','"+authorName+"','"+subject+"','"+totalNumber+"','"+fineamount+"')");
                    break;
                    case 2:
                        System.out.print("\nUpdating info..");
                        st.executeUpdate("UPDATE Books SET TotalNo="+totalNumber+" WHERE BookId in ("+bookID+")");
                        break;
                }
                start();
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
    
    private boolean checkStudentId(){
        System.out.print("\nStudent id:-");
        studentId = sc.nextInt();
        try{
            super.initializeDbConnection();
            try {
                rs = st.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "
                                    +studentId);
                while (rs.next()) {
                    names = rs.getInt("LibraryId");
                }
                if(names == studentId)
                    flag1=Boolean.TRUE;
                else
                    flag1=Boolean.FALSE;
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
    
    private void registerStudent(){
        System.out.print("Student name:-");
        sc.nextLine();
        studentName = sc.nextLine();
        try{
            initializeDbConnection();
            try{
                st.executeUpdate("INSERT INTO `StudentRecord` (StudentName)"+
                "VALUES('"+studentName+"')"); 
                rs = st.executeQuery("SELECT * from StudentRecord");
                while (rs.next()) {
                   id = rs.getInt("LibraryId");
                }
                System.out.println("Student id generated is: "+id);
                issueBook();
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
    
    private void insertBook(){
        try{
            initializeDbConnection();
            try{
                rs = st.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "+studentId);
                while (rs.next()) {
                    totalNumber = rs.getInt("TotalBooks");
                }
                rs = st.executeQuery("SELECT * from Books WHERE BookId = "+bookID);
                while (rs.next()) {
                    totalBooks = rs.getInt("TotalNo");
                }
                if(totalNumber<15 && totalBooks!=0){
                    
                    totalNumber = totalNumber+1;
                    
                    st.executeUpdate("INSERT INTO `IssueBook` (LibraryId,BookID,IssueDate,ReturnDate)"+
                    "VALUES('"+studentId+"','"+bookID+"','"+issueDate+"','"+returnDate+"')");
                    
                    st.executeUpdate("UPDATE StudentRecord SET TotalBooks="+totalNumber+" WHERE LibraryId in ("+studentId+")");
                    
                    rs = st.executeQuery("SELECT * from Books WHERE BookId = "+bookID);
                    while (rs.next()) {
                        totalBooks = rs.getInt("TotalNo");
                    }
                    totalBooks = totalBooks-1;
                    st.executeUpdate("UPDATE Books SET TotalNo="+totalBooks+" WHERE BookId in ("+bookID+")");
                    System.out.print("\nBook issued....");
                    
                    start();
                }
                else{
                    System.out.print("\nCannot issue book as book limit exceeds");
                    start();
                }
            }
            catch(SQLException e){
                System.out.print("\nAllready consist one book...");
                start();
            }
            finally{
                closeDbConnection(c);
            }
        }
        catch(SQLException e){
             System.out.print("\nError5:-"+e);
        }
        catch(ClassNotFoundException e){
            System.out.print("\nError6:-"+e);
        }
    }
    
    private void findRecord(){
        flag1=false;
        try{
            initializeDbConnection();
            try{
                rs = st.executeQuery("SELECT * from IssueBook");
                while(rs.next()){
                    num1=rs.getInt("BookID");
                    num2=rs.getInt("LibraryId");
                    num3=rs.getInt("id");
                    Date returndate = rs.getDate("ReturnDate");
                    if(num1==bookID && num2==studentId){
                        flag1=false;
                        java.util.Date today =new java.util.Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        if(formatter.format(returndate).compareTo(formatter.format(today))==0)
                            break;
                        else if(formatter.format(returndate).compareTo(formatter.format(today))>=0)
                            break;
                        else{
                            putFine();
                            break;
                        }
                    }
                    else
                        flag1 = true;
                }
                if (flag1) {
                    System.out.println("Record not found..");
                    start();
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
    
    private void putFine(){
        try{
            initializeDbConnection();
            try{
                totalFine=0;
                rs = st.executeQuery("SELECT * from Books WHERE BookId = "+bookID);
                while (rs.next()) {
                    fineAmount = rs.getInt("Fine");
                }
                
                rs = st.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "+studentId);
                while (rs.next()) {
                    totalFine = rs.getInt("TatalFine");
                }
                
                st.executeUpdate("INSERT INTO `fineRecord`(`BookId`, `LibraryId`, `Amount`)"+
                    "VALUES('"+bookID+"','"+studentId+"','"+fineAmount+"')");
                
                fineAmount = totalFine+fineAmount;
                st.executeUpdate("UPDATE StudentRecord SET TatalFine="+fineAmount+" WHERE LibraryId in ("+studentId+")");
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
    
    private void checkCredentials(int indicate){
        try{
            initializeDbConnection();
            try{
                rs = st.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "+studentId);
                while (rs.next()) {
                    totalFine = rs.getInt("TatalFine");
                }
                if(totalFine==0 || indicate==0){
                    rs = st.executeQuery("SELECT * from Books WHERE BookId = "+bookID);
                    while (rs.next()) {
                        totalBooks = rs.getInt("TotalNo");
                    }
                    totalBooks = totalBooks+1;
                    st.executeUpdate("UPDATE Books SET TotalNo="+totalBooks+" WHERE BookId in ("+bookID+")");
                    
                    rs = st.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "+studentId);
                    while (rs.next()) {
                        totalNumber = rs.getInt("TotalBooks");
                    }
                    totalNumber=totalNumber-1;
                    st.executeUpdate("UPDATE StudentRecord SET TotalBooks="+totalNumber+" WHERE LibraryId in ("+studentId+")");
                    
                    st.executeUpdate("DELETE FROM `IssueBook` WHERE id ="+num3);
                    System.out.print("\nBook returned..");
                    start();
                }
                else{
                    payFine();
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
    
    private void payFine(){
        System.out.print("\nYou have to pay:-"+totalFine);
        System.out.print("\n1)Pay\n2)Dont pay:-");
        num=sc.nextInt();
        switch(num){
            case 1:
                try{
                    initializeDbConnection();
                    try{
                        totalFine=0;
                        st.executeUpdate("UPDATE StudentRecord SET TatalFine="+totalFine+
                                " WHERE LibraryId in ("+studentId+")");
                        rs = st.executeQuery("SELECT * from fineRecord");
                        while(rs.next()){
                            num1=rs.getInt("BookId");
                            num2=rs.getInt("LibraryId");
                            num3=rs.getInt("id");
                        }
                        if(num1==bookID && num2==studentId){
                           st.executeUpdate("DELETE FROM `fineRecord` WHERE id ="+num3); 
                        }
                        checkCredentials(1);
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
                break;
            case 2:
                if(totalFine<500)
                    checkCredentials(0);
                else{
                    System.out.println("\nCannot issue book...pay fine first to issue new book");
                    start();
                }
                break;
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
            System.out.print("Fine to be taken:-");
            fineAmount = sc.nextInt();
            storeBookDb(bookID, bookName, authorName, subject, totalNumber,fineAmount,1);
        }
        else{
            System.out.print("Number of books:-");
            count = sc.nextInt();
            totalNumber = totalPiece();
            totalNumber = totalNumber+count;
            storeBookDb(bookID, null, null, null, totalNumber,0, 2);
        }
    }
    
    
    public void issueBook(){
        System.out.print("\nBook id:-");
        bookID = sc.nextInt();
        flag = checkBook();
        if(flag){
            System.out.print("Invalid Book id...\n");
            issueBook();
        }
        else{
            System.out.print("valid Book id...");
            flag = checkStudentId();
            if(flag){
                issueDate = LocalDateTime.now();
                returnDate = issueDate.plusDays(15);
                insertBook();
            }
            else{
                System.out.print("Student not registered.\nRegistring..\n");
                registerStudent();
            }
        }
    }
    
    
    public void returnBook(){
        System.out.print("\nBook id:-");
        bookID = sc.nextInt();
        flag = checkBook();
        if(flag){
            System.out.print("Invalid Book id...\n");
            returnBook();
        }
        else{
            System.out.print("valid Book id...");
            System.out.print("\nStudent id:-");
            studentId = sc.nextInt();
            findRecord();
            checkCredentials(1);
        }
    }
    
    public void registerNewStudent(){
        System.out.print("Student name:-");
        sc.nextLine();
        studentName = sc.nextLine();
        try{
            initializeDbConnection();
            try{
                st.executeUpdate("INSERT INTO `StudentRecord` (StudentName)"+
                "VALUES('"+studentName+"')"); 
                rs = st.executeQuery("SELECT * from StudentRecord");
                while (rs.next()) {
                   id = rs.getInt("LibraryId");
                }
                System.out.println("Student id generated is: "+id);
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


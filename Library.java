package librarymanagment;

import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;

public class Library extends DbConnection {

    private int bookID;
    private String bookName, studentName;
    private String authorName;
    private String subject;
    private int totalNumber;
    private int studentId, totalBooks, totalFine, fineAmount;
    LocalDateTime issueDate = null;
    LocalDateTime returnDate = null;

    public void start() {
        
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.print("\n1)Store new book\n2)Issue a book\n3)Return a book\n"
                + "4)Register Student\n5)Go Back\n");
        System.out.print("Enter your choice:-");

        try {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    storeNewBook();
                    break;
                case 2:
                    issueBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    registerNewStudent();
                    break;
                case 5:
                    LibraryManagment libraryManagment = new LibraryManagment();
                    libraryManagment.mainMenu();
                    break;
                default:
                    System.out.println("Invalid input..try again..");
                    start();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..try again..");
            sc.next();
            start();
        }
    }

    private boolean checkBook() {
        boolean flag = Boolean.FALSE;
        int bookid=0;
        try {
            super.initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = "
                        + bookID);
                while (resultSet.next()) {
                    bookid = resultSet.getInt("BookId");
                }
                if (bookid == bookID) {
                    flag = Boolean.FALSE;
                } else {
                    flag = Boolean.TRUE;
                }
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } catch (Exception e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
        if (flag) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private int totalPiece() {
        int totalPiece=0;
        try {
            initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = "
                        + bookID);
                while (resultSet.next()) {
                    totalPiece = resultSet.getInt("TotalNo");
                }
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }

        return totalPiece;
    }

    private void storeBookDb(int fineamount, int choice) {
        try {
            initializeDbConnection();
            try {
                switch (choice) {
                    case 1:
                        System.out.print("\nSaving info..");
                        statement.executeUpdate("INSERT INTO `Books` (BookId,BookName,AuthorName,Subject,TotalNo,Fine)"
                                + "VALUES('" + bookID + "','" + bookName + "','" + authorName + "','" + subject + "','" + totalNumber + "','" + fineamount + "')");
                        break;
                    case 2:
                        System.out.print("\nUpdating info..");
                        statement.executeUpdate("UPDATE Books SET TotalNo=" + totalNumber + " WHERE BookId in (" + bookID + ")");
                        break;
                }
                start();
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }

    }

    private boolean checkStudentId() {
        Scanner sc = new Scanner(System.in);
        boolean flag=Boolean.FALSE;
        boolean input=false;
        boolean negativeNumber=false;
        int libraryId=0;
        
        while(!input){
            try{
                while(!negativeNumber){
                    System.out.print("\nStudent id:-");
                    studentId = sc.nextInt();
                    if(studentId>0){
                        negativeNumber = true;
                        break;
                    }
                    else{
                        System.out.println("Negative id not allowed...enter again..");
                        
                    }
                }
                input=true;
            }catch(InputMismatchException i){
                System.out.println("Invalid input...enter again..");
                sc.nextLine();
            }
        }
        
        try {
            initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId = "
                        + studentId);
                while (resultSet.next()) {
                    libraryId = resultSet.getInt("LibraryId");
                }
                if (libraryId == studentId) {
                    flag = Boolean.TRUE;
                } else {
                    flag = Boolean.FALSE;
                }
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } catch (Exception e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
            
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
        
        if (flag) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void registerStudent() {
        Scanner sc = new Scanner(System.in);
        int id=0;
        System.out.print("Student name:-");
        sc.nextLine();
        studentName = sc.nextLine();
        try {
            initializeDbConnection();
            try {
                statement.executeUpdate("INSERT INTO `StudentRecord` (StudentName)"
                        + "VALUES('" + studentName + "')");
                resultSet =    statement.executeQuery("SELECT * from StudentRecord");
                while (resultSet.next()) {
                    id = resultSet.getInt("LibraryId");
                }
                System.out.println("Student id generated is: " + id);
                issueBook();
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
    }

    private void insertBook() {
        try {
            initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId = " + studentId);
                while (resultSet.next()) {
                    totalNumber = resultSet.getInt("TotalBooks");
                }
                resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = " + bookID);
                while (resultSet.next()) {
                    totalBooks = resultSet.getInt("TotalNo");
                }
                if (totalNumber < 15 && totalBooks != 0) {

                    totalNumber = totalNumber + 1;

                    statement.executeUpdate("INSERT INTO `IssueBook` (LibraryId,BookID,IssueDate,ReturnDate)"
                            + "VALUES('" + studentId + "','" + bookID + "','" + issueDate + "','" + returnDate + "')");

                    statement.executeUpdate("UPDATE StudentRecord SET TotalBooks=" + totalNumber + " WHERE LibraryId in (" + studentId + ")");

                    resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = " + bookID);
                    while (resultSet.next()) {
                        totalBooks = resultSet.getInt("TotalNo");
                    }
                    totalBooks = totalBooks - 1;
                    statement.executeUpdate("UPDATE Books SET TotalNo=" + totalBooks + " WHERE BookId in (" + bookID + ")");
                    System.out.print("\nBook issued....");

                    start();
                } else {
                    System.out.print("\nCannot issue book as book limit exceeds");
                    start();
                }
            } catch (SQLException e) {
                System.out.print("\nAllready consist one book...");
                start();
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError5:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError6:-" + e);
        }
    }

    private int findRecord() {
        int num1,num2,num3=0;
        boolean flag = false;
        try {
            initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from IssueBook");
                while (resultSet.next()) {
                    num1 = resultSet.getInt("BookID");
                    num2 = resultSet.getInt("LibraryId");
                    num3 = resultSet.getInt("id");
                    Date returndate = resultSet.getDate("ReturnDate");
                    if (num1 == bookID && num2 == studentId) {
                        flag = false;
                        java.util.Date today = new java.util.Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        if (formatter.format(returndate).compareTo(formatter.format(today)) == 0) {
                            break;
                        } else if (formatter.format(returndate).compareTo(formatter.format(today)) >= 0) {
                            break;
                        } else {
                            putFine();
                            break;
                        }
                    } else {
                        flag = true;
                    }
                }
                if (flag) {
                    System.out.println("Record not found..");
                    start();
                }
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
        return num3;
    }

    private void putFine() {
        try {
            initializeDbConnection();
            try {
                totalFine = 0;
                resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = " + bookID);
                while (resultSet.next()) {
                    fineAmount = resultSet.getInt("Fine");
                }

                resultSet = statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId = " + studentId);
                while (resultSet.next()) {
                    totalFine = resultSet.getInt("TatalFine");
                }

                statement.executeUpdate("INSERT INTO `fineRecord`(`BookId`, `LibraryId`, `Amount`)"
                        + "VALUES('" + bookID + "','" + studentId + "','" + fineAmount + "')");

                fineAmount = totalFine + fineAmount;
                statement.executeUpdate("UPDATE StudentRecord SET TatalFine=" + fineAmount + " WHERE LibraryId in (" + studentId + ")");
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
    }

    private void checkCredentials(int indicate,int id) {
        try {
            initializeDbConnection();
            try {
                resultSet =    statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId = " + studentId);
                while (resultSet.next()) {
                    totalFine = resultSet.getInt("TatalFine");
                }
                if (totalFine == 0 || indicate == 0) {
                    resultSet =    statement.executeQuery("SELECT * from Books WHERE BookId = " + bookID);
                    while (resultSet.next()) {
                        totalBooks = resultSet.getInt("TotalNo");
                    }
                    totalBooks = totalBooks + 1;
                    statement.executeUpdate("UPDATE Books SET TotalNo=" + totalBooks + " WHERE BookId in (" + bookID + ")");

                    resultSet =    statement.executeQuery("SELECT * from StudentRecord WHERE LibraryId = " + studentId);
                    while (resultSet.next()) {
                        totalNumber = resultSet.getInt("TotalBooks");
                    }
                    totalNumber = totalNumber - 1;
                    statement.executeUpdate("UPDATE StudentRecord SET TotalBooks=" + totalNumber + " WHERE LibraryId in (" + studentId + ")");

                    statement.executeUpdate("DELETE FROM `IssueBook` WHERE id =" + id);
                    System.out.print("\nBook returned..");
                    start();
                } else {
                    payFine();
                }
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
    }

    private void payFine() {
        Scanner sc = new Scanner(System.in);
        int num1=0,num2=0,num3=0;
        int choice;
        System.out.print("\nYou have to pay:-" + totalFine);
        System.out.print("\n1)Pay\n2)Dont pay:-");
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                try {
                    initializeDbConnection();
                    try {
                        totalFine = 0;
                        statement.executeUpdate("UPDATE StudentRecord SET TatalFine=" + totalFine
                                + " WHERE LibraryId in (" + studentId + ")");
                        resultSet =    statement.executeQuery("SELECT * from fineRecord");
                        while (resultSet.next()) {
                            num1 = resultSet.getInt("BookId");
                            num2 = resultSet.getInt("LibraryId");
                            num3 = resultSet.getInt("id");
                        }
                        if (num1 == bookID && num2 == studentId) {
                            statement.executeUpdate("DELETE FROM `fineRecord` WHERE id =" + num3);
                        }
                        checkCredentials(1,num3);
                    } catch (SQLException e) {
                        System.out.print("\nError:-" + e);
                    } finally {
                        closeDbConnection(connect);
                    }
                } catch (SQLException e) {
                    System.out.print("\nError:-" + e);
                } catch (ClassNotFoundException e) {
                    System.out.print("\nError:-" + e);
                }
                break;
            case 2:
                if (totalFine < 500) {
                    checkCredentials(0,num3);
                } else {
                    System.out.println("\nCannot issue book...pay fine first to issue new book");
                    start();
                }
                break;
        }
    }

    public void storeNewBook() {
        Scanner sc = new Scanner(System.in);
        boolean flag;
        int count = 0;
        boolean input = false;
        boolean negativeNumber=false;
        
        while (!input) {
            try {
                while(!negativeNumber){
                    System.out.print("\nBook ID:-");
                    bookID = sc.nextInt();
                    if(bookID>=0){
                        negativeNumber=true;
                            break;
                        }
                    else{
                        System.out.println("Negative id not allowed...enter again..");
                    }
                }
                input = true;
            } catch (InputMismatchException i) {
                System.out.println("\nWrong value...enter integer value...");
                sc.nextLine();
            }
        }
        input = false;
        negativeNumber=false;
        flag = checkBook();
        if (flag) {
            System.out.print("Book Name:-");
            sc.nextLine();
            bookName = sc.nextLine();
            System.out.print("Author name:-");
            authorName = sc.nextLine();
            System.out.print("Subject:-");
            subject = sc.nextLine();
            while (!input) {
                try {
                    while(!negativeNumber){
                        System.out.print("Number of books:-");
                        totalNumber = sc.nextInt();
                        System.out.print("Fine to be taken:-");
                        fineAmount = sc.nextInt();
                        if(totalNumber>0 && fineAmount>=0){
                            negativeNumber=true;
                            break;
                        }
                        else{
                            System.out.println("Negative or zero not allowed...enter again..");
                        }
                    }
                    input = true;
                } catch (InputMismatchException i) {
                    System.out.println("\nWrong value...enter integer value...");
                    sc.nextLine();
                }
            }
            storeBookDb(fineAmount, 1);
        } 
        else {
            while (!input) {
                try {
                    while(!negativeNumber){
                        System.out.print("Number of books:-");
                        count = sc.nextInt();
                        if(count>0){
                            negativeNumber=true;
                            break;
                        }
                        else{
                            System.out.println("Negative or zero not allowed...enter again..");
                        }
                    }
                    input = true;
                } catch (InputMismatchException i) {
                    System.out.println("\nWrong value...enter integer value...");
                    sc.nextLine();
                }
            }
            totalNumber = totalPiece();
            totalNumber = totalNumber + count;
            storeBookDb(0, 2);
        }
    }

    public void issueBook() {
        
        Scanner sc = new Scanner(System.in);
        boolean negativeNumber=false;
        boolean input=false;
        boolean flag;
        
        while(!input){
            try{
                while(!negativeNumber){
                    System.out.print("\nBook id:-");
                    bookID = sc.nextInt();
                    if(bookID>0){
                        negativeNumber=true;
                        break;
                    }
                    else{
                        System.out.println("Negative id not allowed...enter again..");
                    }
                }
                input = true;
            }catch(InputMismatchException i){
                System.out.println("\nWrong value...enter integer value...");
                sc.nextLine();
            }
        }
        flag = checkBook();
        if (flag) {
            System.out.print("Book not present...\n");
            issueBook();
        } else {
            System.out.print("Book present...");
            flag = checkStudentId();
            if (flag) {
                issueDate = LocalDateTime.now();
                returnDate = issueDate.plusDays(15);
                insertBook();
            } else {
                System.out.print("Student not registered.\nRegistring..\n");
                registerStudent();
            }
        }
    }

    public void returnBook() {
        Scanner sc = new Scanner(System.in);
        boolean flag;
        boolean input=false;
        boolean negativeNumber=false;
        while(!input){
            try{
                while(!negativeNumber){
                    System.out.print("\nBook id:-");
                    bookID = sc.nextInt();
                    if(bookID>0){
                        negativeNumber=true;
                        break;
                    }
                    else{
                        System.out.println("Negative id not allowed...enter again..");
                    }
                }
                input = true;
            }catch(InputMismatchException i){
                System.out.println("\nWrong value...enter integer value...");
                sc.nextLine();
            }
        }
        flag = checkBook();
        input=false;
        negativeNumber=false;
        if (flag) {
            System.out.print("Wrong book id..enter again\n");
            returnBook();
        } else {
            System.out.print("valid Book id...");
            while(!input){
                try{
                    while(!negativeNumber){
                        System.out.print("\nStudent id:-");
                        studentId = sc.nextInt();
                        if(studentId>0){
                            negativeNumber = true;
                            break;
                        }
                        else{
                            System.out.println("Negative id not allowed...enter again..");
                        }
                    }
                    input=true;
                }catch(InputMismatchException i){
                    System.out.println("Invalid input...enter again..");
                    sc.nextLine();
                }
            }
            int idValue = findRecord();
            checkCredentials(1,idValue);
        }
    }

    public void registerNewStudent() {
        Scanner sc = new Scanner(System.in);
        int id=0;
        System.out.print("Student name:-");
        sc.nextLine();
        studentName = sc.nextLine();
        try {
            initializeDbConnection();
            try {
                statement.executeUpdate("INSERT INTO `StudentRecord` (StudentName)"
                        + "VALUES('" + studentName + "')");
                resultSet =    statement.executeQuery("SELECT * from StudentRecord");
                while (resultSet.next()) {
                    id = resultSet.getInt("LibraryId");
                }
                System.out.println("Student id generated is: " + id);
            } catch (SQLException e) {
                System.out.print("\nError:-" + e);
            } finally {
                closeDbConnection(connect);
            }
        } catch (SQLException e) {
            System.out.print("\nError:-" + e);
        } catch (ClassNotFoundException e) {
            System.out.print("\nError:-" + e);
        }
        start();
    }
}

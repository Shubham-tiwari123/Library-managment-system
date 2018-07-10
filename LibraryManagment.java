
package librarymanagment;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagment {
    
    public void mainMenu(){
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.print("\n1)Library\n2)Student Personal Details\n3)Exit");
        System.out.print("\nEnter your choice:-");
        try{
            choice = sc.nextInt();
            switch(choice){
                case 1: Library libraryObj = new Library();
                        libraryObj.start();
                        break;
                case 2: Student studentObj = new Student();
                        studentObj.start();
                        break;
                case 3: System.out.print("Exiting the system...\n");
                        break;
                default:
                        System.out.println("Invalid input..try again..");
                        mainMenu();
            }
        }catch(InputMismatchException i){
            System.out.println("Invalid input..try again..");
            sc.next();
            mainMenu();
        }
    }

    public static void main(String[] args) {
        LibraryManagment lm = new LibraryManagment();
        lm.mainMenu();
    }
    
}

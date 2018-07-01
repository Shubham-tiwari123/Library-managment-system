
package librarymanagment;

import java.util.Scanner;

public class Student {
    private int num;
    private final Scanner sc = new Scanner(System.in);
    
    public void start(){
        System.out.println("\f");
        System.out.print("\n1)Display all books in library\n2)Books you hold\n3)Go back\n");
        System.out.print("Enter your choice:-");
        num = sc.nextInt();
        switch(num){
            case 1: displayAllBook();
                    break;
            case 2: personalDetails();
                    break;
            case 3: LibraryManagment lm = new LibraryManagment();
                    lm.mainMenu();
                    break;
        }
    }
    public void displayAllBook(){
        
    }
    public void personalDetails(){
        
    }
}

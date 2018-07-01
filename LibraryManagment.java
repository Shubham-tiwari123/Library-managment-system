
package librarymanagment;

import java.util.Scanner;

public class LibraryManagment {
    
    public void mainMenu(){
        int num;
        Scanner sc = new Scanner(System.in);
        System.out.print("\n1)Library\n2)Student Personal Details\n3)Exit");
        System.out.print("\nEnter your choice:-");
        num = sc.nextInt();
        switch(num){
            case 1: Library l = new Library();
                    l.start();
                    break;
            case 2: Student s = new Student();
                    s.start();
                    break;
            case 3: System.out.print("Exiting the system...\n");
                    break;
        }
    }

    public static void main(String[] args) {
        LibraryManagment lm = new LibraryManagment();
        lm.mainMenu();
    }
    
}

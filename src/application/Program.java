package application;

import model.entities.Department;

public class Program {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        
        Department obj = new Department(1, "Books");
        System.err.println(obj);
    }
}

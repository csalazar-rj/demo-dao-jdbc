package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        
        Department obj = new Department(1, "Books");

        Seller seller = new Seller(21, "Carlos", "csalazar@gmail.com", new Date(), 3000.00, obj);
        System.err.println(obj);
        System.err.println(seller);
    }
}

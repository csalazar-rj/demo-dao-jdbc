package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) throws ParseException {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        
        Scanner scan = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

        // Department obj = new Department(1, "Books");
        // Seller seller = new Seller(21, "Carlos", "csalazar@gmail.com", new Date(), 3000.00, obj);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("<<< findByID() Validation >>>");
        Seller seller = sellerDao.findById(1);

        System.out.println(seller);

        System.out.println("\n<<< findByDepartment() Validation >>>");
        Department department = new Department(1, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        
        for (Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n<<< findAll() Validation >>>");
        //List<Seller> listAll = sellerDao.findAll();
        list = sellerDao.findAll();
        
        for (Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n<<< Seller INSERT Validation >>>");
        Date d1 = df.parse("09-19-1973");
        //Seller newSeller = new Seller("Jean Hinton", "jhinton@wexinc.om", new Date(), 6500.00, department);
        Seller newSeller = new Seller("Jean Hinton", "jhinton@wexinc.om", d1, 6500.00, department);

        sellerDao.insert(newSeller);
        System.out.println("Inserted New Id: " + newSeller.getId());

        
        System.out.println("\n<<< Seller UPDATE Validation >>>");
        seller = sellerDao.findById(5);
        seller.setBase_salary(7300.00);
        sellerDao.update(seller);
        System.out.println("Update Completed!");

        System.out.println("\n<<< Seller DELETE Validation >>>");
        System.out.print("Enter the ID to delete: ");
        int idDel = scan.nextInt();
        sellerDao.deleteById(idDel);
        System.out.println("Delete completed.");
        scan.close();

    }
}

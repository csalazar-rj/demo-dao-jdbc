package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        
        // Department obj = new Department(1, "Books");
        // Seller seller = new Seller(21, "Carlos", "csalazar@gmail.com", new Date(), 3000.00, obj);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("<<< findByID() Validation >>>");
        Seller seller = sellerDao.findById(1);

        System.out.println(seller);

        System.out.println("\n<<< findByDepartment() Validation >>>");
        Department department = new Department(2, null);
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

    }
}

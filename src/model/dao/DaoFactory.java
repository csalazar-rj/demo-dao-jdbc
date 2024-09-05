package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

// Classe possui operações staticas para instaciar os Daos
public class DaoFactory {

    // abaixo nós temos a interface SellerDao que instancia uma implementação
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());                     
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}

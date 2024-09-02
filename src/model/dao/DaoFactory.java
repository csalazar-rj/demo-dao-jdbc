package model.dao;

import model.dao.impl.SellerDaoJDBC;

// Classe possui operações staticas para instaciar os Daos
public class DaoFactory {

    // abaixo nós temos a interface SellerDao que instancia uma implementação
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC();                     
    }
}

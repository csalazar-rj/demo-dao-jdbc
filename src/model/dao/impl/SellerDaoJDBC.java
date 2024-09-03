package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

// A classe abaixo é um implementação JDBC do SellerDao
public class SellerDaoJDBC implements SellerDao{

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Seller findById(Integer id) {
        // TODO Auto-generated method stub
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                " SELECT SELLER.*, DEPT.NAME AS DEP_NAME"  
                + " FROM SELLER, DEPARTMENT DEPT" 
                + " WHERE SELLER.DEPTID = DEPT.ID"
                + "   AND SELLER.ID = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            // veriifca se houve retorno do banco
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }
            return null;
        }

        catch (SQLException e){
            throw new DbException(e.getMessage());            
        }

        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setBirthdate(rs.getDate("birthdate"));
        obj.setBase_salary(rs.getDouble("base_salary"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("deptid"));
        dep.setName(rs.getString("dep_name"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                " SELECT SELLER.*, DEPT.NAME AS DEP_NAME"  
                + " FROM SELLER, DEPARTMENT DEPT" 
                + " WHERE SELLER.DEPTID = DEPT.ID"
                + "   AND DEPT.ID = ?");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            // cria um map vazio para incluir qualquer entrada do resultSet
            Map<Integer, Department> map = new HashMap<>();

            // veriifca se houve retorno do banco
            while (rs.next()) {
                // carrega no map o deptid retornado para evitar a duplicidade de retorno da mesma chave
                // quanto estiver realizando o relacionamento das tabelas
                Department dep = map.get(rs.getInt("deptid"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("deptid"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);                
            }
            return list;
        }

        catch (SQLException e){
            throw new DbException(e.getMessage());            
        }

        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}

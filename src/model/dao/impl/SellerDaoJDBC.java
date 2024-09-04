package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                " INSERT INTO SELLER "
                + " VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
    
                st.setNull(1, java.sql.Types.NULL);
                st.setString(2, obj.getName());
                st.setString(3, obj.getEmail());
                st.setDate(4, new java.sql.Date(obj.getBirthdate().getTime()));
                st.setDouble(5, obj.getBase_salary());
                st.setInt(6, obj.getDepartment().getId());

                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0){
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()){
                        int id = rs.getInt(1);
                        obj.setId(id);
                    }
                    DB.closeResultSet(rs);
                } else {
                    throw new DbException("Unexpected error! No Rows affected.");
                }

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                " UPDATE SELLER "
                + " SET BASE_SALARY = ?"
                + " WHERE ID = ? ");
    
                st.setDouble(1, obj.getBase_salary());
                st.setInt(2, obj.getId());
                
                st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

        finally {
            DB.closeStatement(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                " SELECT SELLER.*, DEPT.NAME AS DEP_NAME"  
                + " FROM SELLER, DEPARTMENT DEPT" 
                + " WHERE SELLER.DEPTID = DEPT.ID");

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

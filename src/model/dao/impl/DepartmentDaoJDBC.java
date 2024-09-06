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
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao{

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
       PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                " INSERT INTO DEPARTMENT "
                + " VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
    
                st.setNull(1, java.sql.Types.NULL);
                st.setString(2, obj.getName());                

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
    public void update(Department obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                " UPDATE DEPARTMENT "
                + " SET NAME = ?"
                + " WHERE ID = ? ");
                    
                st.setString(1, obj.getName());
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
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                " SELECT ID, NAME "
              + " FROM DEPARTMENT "
              + " WHERE ID = ? "
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            // verifica se houve retorno do banco
            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("name"));
        return dep;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                " SELECT ID, NAME "  
                + " FROM DEPARTMENT ");

            rs = st.executeQuery();
            
            List<Department> list = new ArrayList<>();
            // cria um map vazio para incluir qualquer entrada do resultSet
            // Map<Integer, Department> map = new HashMap<>();

            // veriifca se houve retorno do banco
            while (rs.next()) {
                // carrega no map o deptid retornado para evitar a duplicidade de retorno da mesma chave
                // quanto estiver realizando o relacionamento das tabelas
               // Department dep = map.get(rs.getInt("deptid"));
                
                Department obj = instantiateDepartment(rs);
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

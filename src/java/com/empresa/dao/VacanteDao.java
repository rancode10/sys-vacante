
package com.empresa.dao;

import com.empresa.model.Vacante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VacanteDao {

    DbConnection conn;

    public VacanteDao(DbConnection conn) {
        this.conn = conn;
    }
    
    public boolean insert(Vacante vacante){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into vacante values (?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.getConnection().prepareStatement(sql);
            ps.setInt(1, vacante.getId());
            ps.setString(2, format.format(vacante.getFechaPublicacion()));
            ps.setString(3, vacante.getNombre());
            ps.setString(4, vacante.getDescripcion());
            ps.setString(5, vacante.getDetalle());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error VacanteDao.insert: " + ex.getMessage());
            return false;
        }
    }
    
    
    public List<Vacante> getUltimas() {

        try {
            String sql = "select * from Vacante order by id desc limit 3";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));                
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getUltimas: " + e.getMessage());
            return null;
        }
    }
    
    public Vacante getById(int idVacante){
        try {
            String sql = "select * from Vacante where id=? limit 1";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idVacante); // Set idVacante
            ResultSet rs = preparedStatement.executeQuery();
            Vacante vacante = new Vacante(0);
            while (rs.next()) {                
                vacante.setId(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));
            }
            return vacante;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getById: " + e.getMessage());
            return null;
        }
    }
        
    public List<Vacante> getAll() {

        try {
            String sql = "select * from Vacante order by id desc";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));                
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getAll: " + e.getMessage());
            return null;
        }
    }    
    
    public List<Vacante> getByQuery(String query){

        try {
            String sql = "select * from Vacante where (descripcion like ? or nombre like ?) order by id desc";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            ResultSet rs = preparedStatement.executeQuery();
            List<Vacante> list = new LinkedList<>();
            Vacante vacante;
            while (rs.next()) {
                vacante = new Vacante(rs.getInt("id"));
                vacante.setFechaPublicacion(rs.getDate("fechaPublicacion"));
                vacante.setNombre(rs.getString("nombre"));
                vacante.setDescripcion(rs.getString("descripcion"));
                vacante.setDetalle(rs.getString("detalle"));                                
                list.add(vacante);
            }
            return list;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.getByQuery: " + e.getMessage());
            return null;
        }
    }
    
    public int delete(int idVacante) {
        try {
            String sql = "delete from Vacante where id=?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idVacante);
            int rows = preparedStatement.executeUpdate();
            return rows;

        } catch (SQLException e) {            
            System.out.println("Error VacanteDao.eliminar: " + e.getMessage());
            return 0;
        }
    }
    
    
}

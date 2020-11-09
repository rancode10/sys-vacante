
package com.empresa.dao;

import com.empresa.model.Vacante;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
}

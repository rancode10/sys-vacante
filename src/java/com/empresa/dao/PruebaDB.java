
package com.empresa.dao;

import com.empresa.model.Usuario;

public class PruebaDB {
    
    public static void main(String[] args) {
        
        DbConnection conn = new DbConnection();        
        UsuarioDao dao = new UsuarioDao(conn);
        //Usuario usuario = dao.login("randy", "123"); //prueba de verificacion de usuario
        //System.out.println(usuario);
        
    }
    
}

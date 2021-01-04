/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.controller;

import com.empresa.dao.DbConnection;
import com.empresa.dao.VacanteDao;
import com.empresa.model.Vacante;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacanteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("ver")){
            this.verDetalle(request, response);
        }
    }

    protected void verDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                        
        int idVacante = Integer.parseInt(request.getParameter("id"));                
        DbConnection conn = new DbConnection();
        VacanteDao vacanteDao = new VacanteDao(conn);
        Vacante vacante = vacanteDao.getById(idVacante);
        conn.disconnect();                
        
        request.setAttribute("vacante", vacante);
        RequestDispatcher rd;        
        
        rd = request.getRequestDispatcher("/detalle.jsp");
        rd.forward(request, response);
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //recibir parametros
        String nombreParam = request.getParameter("nombre");
        String descripcionParam = request.getParameter("descripcion");
        String detalleParam = request.getParameter("detalle");
        
        Vacante vacante = new Vacante(0);
        vacante.setNombre(nombreParam);
        vacante.setDescripcion(descripcionParam);
        vacante.setDetalle(detalleParam);
        
        System.out.println(vacante);
                
        DbConnection conn = new DbConnection();
        vacante.setDetalle(detalleParam);
        VacanteDao vacanteDao = new VacanteDao(conn);
        boolean status = vacanteDao.insert(vacante);
                
        String msg = "";
        if (status) {
            msg = "La vacante fue guardada correctamente.";
        } else {
            msg = "Ocurrio un error. La vacante no fue guardada.";
        }
        conn.disconnect();        
        RequestDispatcher rd;        
        request.setAttribute("message", msg);        
        rd = request.getRequestDispatcher("/mensaje.jsp");
        rd.forward(request, response);

    }


}

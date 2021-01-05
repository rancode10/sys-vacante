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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Randy
 */
public class BusquedaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recibimos la cadena de busqueda del usuario
        String q = request.getParameter("query");               
        List<Vacante> lista = null;
        DbConnection conn = new DbConnection();
        // Con nuestro objeto DAO, hacemos la busqueda de vacantes del usaurio
        VacanteDao vacanteDao = new VacanteDao(conn);
        lista = vacanteDao.getByQuery(q);
        conn.disconnect();
        RequestDispatcher rd;
        request.setAttribute("vacantes", lista);
        rd = request.getRequestDispatcher("/vacantes.jsp");
        rd.forward(request, response);
    }
}

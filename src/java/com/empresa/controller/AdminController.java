/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.controller;

import com.empresa.dao.DbConnection;
import com.empresa.dao.UsuarioDao;
import com.empresa.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Randy
 */
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recibimos el parametro action, el cual servira para saber que accion GET se ejecutara
        String action = request.getParameter("action");

        // Recuperamos la session activa que viene junto con el request
        HttpSession session = request.getSession();
        RequestDispatcher rd;
        String msg = "";

        switch (action) {
            case "login":
                // Aqui no existe todavia una sesion para el usuario, lo mandamos al form de login
                if (session.getAttribute("usuario") == null) {
                    request.setAttribute("message", msg);
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else { // Ya esta logueado, lo mandamos al index.jsp, pero de la administracion
                    rd = request.getRequestDispatcher("/admin.jsp");
                    rd.forward(request, response);
                }
                break;

            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/homepage");
                break;
            case "crear":
                if (session.getAttribute("usuario") == null) {
                    msg = "Acceso Denegado.";
                    request.setAttribute("message", msg);
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else {
                    rd = request.getRequestDispatcher("/frmvacante.jsp");
                    rd.forward(request, response);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibimos parametros del formulario de login
        String userParam = request.getParameter("user");
        String passParam = request.getParameter("pass");
        String msg = "";
        // Recuperamos una instancia del objeto HttpSession
        HttpSession session = request.getSession();

        DbConnection conn = new DbConnection();
        UsuarioDao usuarioDao = new UsuarioDao(conn);
        // Verificamos en la BD, si es un usuario correcto.
        Usuario usuario = usuarioDao.login(userParam, passParam);
        conn.disconnect();

        RequestDispatcher rd;
        if (usuario.getId() > 0) {
            // Creamos una variable de session, con el registro de usuario (Bean)
            // Verificar en el administrador de aplicaciones de tomcat.
            session.setAttribute("usuario", usuario);
            rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);

        } else {
            msg = "Usuario y/o password incorrectos";
            request.setAttribute("message", msg);
            rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.BancoClienteControlador;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Isaac
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        BancoClienteControlador controlador = new BancoClienteControlador();
        boolean ok = controlador.login(usuario, clave);

        if (ok) {
            request.getSession().setAttribute("usuario", usuario);
            response.sendRedirect("menu.jsp");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

  
}

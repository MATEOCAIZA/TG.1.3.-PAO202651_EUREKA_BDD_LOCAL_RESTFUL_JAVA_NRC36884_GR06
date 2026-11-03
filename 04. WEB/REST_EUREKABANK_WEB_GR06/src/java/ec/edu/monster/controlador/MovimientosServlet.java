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
@WebServlet(name = "MovimientosServlet", urlPatterns = {"/MovimientosServlet"})
public class MovimientosServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cuenta = request.getParameter("cuenta");

        if (cuenta == null || cuenta.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar el número de cuenta.");
        } else {
            BancoClienteControlador controlador = new BancoClienteControlador();
            String movimientos = controlador.consultarMovimientos(cuenta.trim());

            if (movimientos != null && !movimientos.equals("[]")) {
                request.setAttribute("movimientos", movimientos);
            } else {
                request.setAttribute("error", "No se encontraron movimientos para esta cuenta.");
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("movimientos.jsp");
        rd.forward(request, response);
    }
}

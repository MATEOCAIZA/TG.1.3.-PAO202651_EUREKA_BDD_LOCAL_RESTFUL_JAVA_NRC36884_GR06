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
@WebServlet(name = "ConsultarSaldoServlet", urlPatterns = {"/ConsultarSaldoServlet"})
public class ConsultarSaldoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cuenta = request.getParameter("cuenta");

        if (cuenta == null || cuenta.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar el número de cuenta.");
        } else {
            BancoClienteControlador controlador = new BancoClienteControlador();
            double saldo = controlador.consultarSaldo(cuenta.trim());

            if (saldo >= 0) {
                request.setAttribute("saldo", saldo);
            } else {
                request.setAttribute("error", "Cuenta no válida o inactiva.");
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("consultarSaldo.jsp");
        rd.forward(request, response);
    }
}

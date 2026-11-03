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
@WebServlet(name = "DepositoServlet", urlPatterns = {"/DepositoServlet"})
public class DepositoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cuenta = request.getParameter("cuenta");
        String empleado = request.getParameter("empleado");
        String importeStr = request.getParameter("importe");

        if (cuenta == null || empleado == null || importeStr == null ||
            cuenta.trim().isEmpty() || empleado.trim().isEmpty() || importeStr.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
        } else {
            try {
                double importe = Double.parseDouble(importeStr);

                if (importe <= 0) {
                    request.setAttribute("error", "El importe debe ser mayor a cero.");
                } else {
                    BancoClienteControlador controlador = new BancoClienteControlador();
                    boolean ok = controlador.realizarDeposito(cuenta.trim(), importe, empleado.trim());

                    if (ok) {
                        request.setAttribute("mensaje", "✅ Depósito realizado con éxito.");
                    } else {
                        request.setAttribute("error", "❌ No se pudo realizar el depósito. Verifique la cuenta.");
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Importe inválido.");
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("depositar.jsp");
        rd.forward(request, response);
    }
}

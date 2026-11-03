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
@WebServlet(name = "TransferenciaServlet", urlPatterns = {"/TransferenciaServlet"})
public class TransferenciaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String empleado = request.getParameter("empleado");
        String importeStr = request.getParameter("importe");

        if (origen == null || destino == null || empleado == null || importeStr == null ||
            origen.trim().isEmpty() || destino.trim().isEmpty() ||
            empleado.trim().isEmpty() || importeStr.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
        } else if (origen.trim().equals(destino.trim())) {
            request.setAttribute("error", "La cuenta origen y destino no pueden ser iguales.");
        } else {
            try {
                double importe = Double.parseDouble(importeStr);

                if (importe <= 0) {
                    request.setAttribute("error", "El importe debe ser mayor que cero.");
                } else {
                    BancoClienteControlador controlador = new BancoClienteControlador();
                    boolean ok = controlador.realizarTransferencia(origen.trim(), destino.trim(), importe, empleado.trim());

                    if (ok) {
                        request.setAttribute("mensaje", "✅ Transferencia realizada con éxito.");
                    } else {
                        request.setAttribute("error", "❌ No se pudo realizar la transferencia. Verifique las cuentas o el saldo.");
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Importe inválido.");
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("transferencia.jsp");
        rd.forward(request, response);
    }
}

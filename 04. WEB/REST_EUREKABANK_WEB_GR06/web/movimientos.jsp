<%@ page import="org.json.JSONArray, org.json.JSONObject, java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Movimientos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="panel-wrapper">
        <div class="movimientos-panel">
            <div class="movimientos-left">
                <img src="images/sullivan.png" alt="Sullivan" class="movimientos-img">
            </div>
            <div class="movimientos-right">
                <h2 class="movimientos-title">Consulta de Movimientos</h2>

                <form method="post" action="MovimientosServlet" class="mb-4">
                    <input type="text" name="cuenta" placeholder="Ingrese número de cuenta" class="form-control movimientos-input mb-3" required>
                    <input type="submit" value="Buscar" class="btn movimientos-button w-100">
                </form>

                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                    <p class="error mt-2">❌ <%= error %></p>
                <%
                    }

                    String json = (String) request.getAttribute("movimientos");
                    if (json != null) {
                        JSONArray array = new JSONArray(json);
                        if (array.length() > 0) {
                %>
                <div class="table-responsive mt-4">
                    <table class="table table-hover text-center tabla-movimientos" id="tablaMovimientos">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Fecha</th>
                                <th>Tipo</th>
                                <th>Acción</th>
                                <th>Importe</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject mov = array.getJSONObject(i);
                                String fecha = mov.has("fecha") && !mov.isNull("fecha") ? mov.getString("fecha") : "";
                                String tipo = mov.has("tipo") && !mov.isNull("tipo") ? mov.getString("tipo") : "";
                                String accion = mov.has("accion") && !mov.isNull("accion") ? mov.getString("accion") : tipo;
                                double importe = 0;
                                if (mov.has("importe") && !mov.isNull("importe")) {
                                    importe = mov.getDouble("importe");
                                } else if (mov.has("monto") && !mov.isNull("monto")) {
                                    importe = mov.getDouble("monto");
                                } else if (mov.has("valor") && !mov.isNull("valor")) {
                                    importe = mov.getDouble("valor");
                                }
                        %>
                            <tr>
                                <td><%= mov.has("nromov") && !mov.isNull("nromov") ? mov.getInt("nromov") : i + 1 %></td>
                                <td><%= fecha %></td>
                                <td><%= tipo %></td>
                                <td><%= accion %></td>
                                <td>$<%= String.format(Locale.US, "%.2f", importe) %></td>
                            </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
                <%
                        } else {
                %>
                    <p class="error">No hay movimientos para esta cuenta.</p>
                <%
                        }
                    }
                %>

                <a href="menu.jsp" class="btn button-alt mt-4">← Volver al Menú</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const tabla = document.getElementById("tablaMovimientos");
        if (!tabla) return;

        const obtenerCeldaValor = (fila, indice) => fila.cells[indice].innerText.trim();
        const comparar = (a, b, tipo) => {
            if (tipo === 'number') return parseFloat(a) - parseFloat(b);
            if (tipo === 'date') return new Date(a) - new Date(b);
            return a.localeCompare(b);
        };
        const detectarTipo = (valor) => {
            if (!isNaN(parseFloat(valor))) return 'number';
            if (!isNaN(Date.parse(valor))) return 'date';
            return 'string';
        };

        tabla.querySelectorAll("th").forEach((th, i) => {
            th.addEventListener("click", () => {
                const filas = Array.from(tabla.querySelectorAll("tbody tr"));
                const tipo = detectarTipo(obtenerCeldaValor(filas[0], i));
                const ordenAscendente = !th.classList.contains("asc");

                filas.sort((filaA, filaB) => {
                    const valA = obtenerCeldaValor(filaA, i);
                    const valB = obtenerCeldaValor(filaB, i);
                    return ordenAscendente ? comparar(valA, valB, tipo) : -comparar(valA, valB, tipo);
                });

                tabla.querySelectorAll("th").forEach(th => th.classList.remove("asc", "desc"));
                th.classList.add(ordenAscendente ? "asc" : "desc");

                const tbody = tabla.querySelector("tbody");
                filas.forEach(fila => tbody.appendChild(fila));
            });
        });
    });
    </script>
</body>
</html>

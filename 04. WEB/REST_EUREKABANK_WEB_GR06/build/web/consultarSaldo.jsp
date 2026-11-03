<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Saldo - Monsters Inc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="panel-wrapper">
        <div class="saldo-panel">
            <div class="saldo-left">
                <img src="images/sullivan.png" alt="Sullivan" class="saldo-mascot">
            </div>
            <div class="saldo-right">
                <h2 class="saldo-title">Consulta de Saldo</h2>
                <form action="ConsultarSaldoServlet" method="post">
                    <input type="text" name="cuenta" placeholder="Número de cuenta" class="form-control saldo-input" required>
                    <input type="submit" value="Consultar" class="btn saldo-button mt-3">
                </form>

                <%
                    String error = (String) request.getAttribute("error");
                    Double saldo = (Double) request.getAttribute("saldo");
                    if (error != null) {
                %>
                    <p class="error mt-3">❌ <%= error %></p>
                <%
                    } else if (saldo != null) {
                %>
                    <p class="success mt-3">💰 Saldo actual: $<%= String.format("%.2f", saldo) %></p>
                <%
                    }
                %>

                <a href="menu.jsp" class="btn button-alt mt-4">← Volver al Menú</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

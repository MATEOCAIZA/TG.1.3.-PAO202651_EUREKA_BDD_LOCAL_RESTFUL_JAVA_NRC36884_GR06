<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Menú Principal - Monsters Inc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="panel-wrapper">
        <div class="menu-panel">
            <div class="menu-left">
                <img src="images/sullivan.png" alt="Sullivan" class="menu-img">
            </div>
            <div class="menu-right">
                <h2 class="menu-title">Bienvenido al Banco - Monsters Inc</h2>
                <p class="top-label">👤 Usuario: <strong><%= usuario %></strong></p>

                <div class="menu-grid mt-4">
                    <a href="consultarSaldo.jsp" class="menu-button">Consultar Saldo</a>
                    <a href="retirar.jsp" class="menu-button">Retirar Dinero</a>
                    <a href="depositar.jsp" class="menu-button">Depositar Dinero</a>
                    <a href="transferencia.jsp" class="menu-button">Transferir</a>
                    <a href="movimientos.jsp" class="menu-button">Consultar Movimientos</a>
                    <a href="login.jsp" class="menu-button button-alt">Cerrar Sesión</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

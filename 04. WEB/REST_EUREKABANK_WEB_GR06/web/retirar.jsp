<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retiro de Dinero - Monsters Inc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/eureka-hud.css">
</head>
<body>
    <div class="panel-wrapper">
        <div class="retiro-panel">
            <div class="retiro-left">
                <img src="images/hud.jpg" alt="Sullivan" class="retiro-img">
            </div>
            <div class="retiro-right">
                <h2 class="retiro-title">Retiro de Dinero</h2>

                <form action="RetiroServlet" method="post">
                    <input type="text" name="cuenta" placeholder="Cuenta" class="form-control retiro-input mb-3" required>
                    <input type="text" name="empleado" placeholder="Código de empleado" class="form-control retiro-input mb-3" required>
                    <input type="text" name="importe" placeholder="Importe a retirar" class="form-control retiro-input mb-3" required>
                    <input type="submit" value="Retirar" class="btn retiro-button w-100">
                </form>

                <%
                    String mensaje = (String) request.getAttribute("mensaje");
                    if (mensaje != null) {
                %>
                    <p class="success mt-3">✅ <%= mensaje %></p>
                <%
                    }
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                    <p class="error mt-3">❌ <%= error %></p>
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

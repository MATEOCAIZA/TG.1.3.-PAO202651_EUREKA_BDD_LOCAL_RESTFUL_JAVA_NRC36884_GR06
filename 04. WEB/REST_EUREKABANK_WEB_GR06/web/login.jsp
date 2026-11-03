<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Monsters Inc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="login-wrapper">
        <div class="login-panel">
            <div class="login-left d-none d-md-flex">
                <img src="images/sullivan.png" alt="Sullivan" class="login-img">
            </div>
            <div class="login-right">
                <h2 class="login-title">Bienvenido a Monsters Bank</h2>

                <form action="LoginServlet" method="post">
                    <div class="mb-3 input-group">
                        <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                        <input type="text" class="form-control login-input" name="usuario" placeholder="Usuario" required>
                    </div>

                    <div class="mb-3 input-group">
                        <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                        <input type="password" class="form-control login-input" name="clave" placeholder="Contraseña" required>
                    </div>

                    <button type="submit" class="btn login-button w-100">Ingresar</button>
                </form>

                <p class="error mt-3">
                    <%= request.getAttribute("error") != null ? "❌ " + request.getAttribute("error") : "" %>
                </p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

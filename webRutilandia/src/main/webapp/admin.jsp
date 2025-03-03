<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administraci�n - Rutilandia</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='estilos.css' />">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Rutilandia Admin</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="modificar.jsp">Modificar Usuario</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/listar">Ver Usuarios</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/cerrarSesion">Cerrar Sesi�n</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contenido Principal -->
    <div class="container d-flex justify-content-center align-items-center flex-grow-1">
        <div class="text-center">
            <h1 class="mb-4">Bienvenido, Administrador</h1>
            <p class="lead">Hola, <strong>${sessionScope.usuario.nombre}</strong>.</p>

            <!-- Botones de acciones -->
            <div class="d-flex flex-wrap justify-content-center">
                <a href="modificar.jsp" class="btn btn-warning mx-2 mb-2">Modificar Usuario</a>
                <a href="${pageContext.request.contextPath}/listar" class="btn btn-primary mx-2 mb-2">Ver Usuarios</a>
                <a href="registroMesa.jsp" class="btn btn-success mx-2 mb-2">Registrar Mesa</a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer text-center py-3 mt-auto bg-light">
        � 2025 Rutilandia
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
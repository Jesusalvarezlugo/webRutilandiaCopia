<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rutilandia - ${pageTitle}</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='estilos.css' />">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

    <!-- Encabezado -->
    <header class="header">
        <div class="title">Rutilandia</div>
        <div class="welcome-message">Bienvenido a Rutilandia</div>
        <div class="icons">
            <i class="fas fa-user"></i>
            <i class="fas fa-shopping-basket"></i>
        </div>
    </header>

    <!-- Barra de Navegación -->
    <nav class="navbar navbar-expand-lg navbar-light">
        <div class="container">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/index.jsp' />">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/mesas.jsp' />">Mesas</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/productos.jsp' />">Productos</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/pedidos.jsp' />">Pedidos</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/contacto.jsp' />">Contactar</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contenido dinámico -->
    <main class="container main-content">
        <jsp:include page="${pageContent}" />
    </main>

    <!-- Footer -->
    <footer class="footer">
        © 2025 Rutilandia - Todos los derechos reservados
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

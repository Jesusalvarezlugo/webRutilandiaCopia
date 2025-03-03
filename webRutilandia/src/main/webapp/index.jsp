<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido - Rutilandia</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='estilos.css' />">
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Contenedor Principal -->
    <div class="container d-flex justify-content-center align-items-center flex-grow-1">
        <div class="text-center">
            <h1 class="mb-4">Bienvenido a Rutilandia</h1>
            <p class="lead">Explora nuestra tienda y accede a tu cuenta</p>

            <!-- Botones de Navegación -->
            <div class="d-flex justify-content-center">
                <a href="registro.jsp" class="btn btn-success mx-2">Registro</a>
                <a href="login.jsp" class="btn btn-primary mx-2">Login</a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer text-center py-3 mt-auto bg-light">
        © 2025 Rutilandia
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
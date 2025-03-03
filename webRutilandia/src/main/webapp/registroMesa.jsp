<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Mesa - Rutilandia</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='estilos.css' />">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Contenedor Principal -->
    <div class="container d-flex justify-content-center align-items-center flex-grow-1">
        <div class="card p-4 shadow-lg" style="max-width: 400px; width: 100%;">
            <h2 class="text-center mb-4">Registrar Mesa</h2>

            <!-- Mensaje de error o éxito -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">${mensaje}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <!-- Formulario de Registro -->
            <form action="registrarMesa" method="post">
                <div class="mb-3">
                    <label for="nombreMesa" class="form-label">Nombre de la Mesa</label>
                    <input type="text" class="form-control" id="nombreMesa" name="nombreMesa" required>
                </div>
                <div class="mb-3">
                    <label for="descripcionMesa" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcionMesa" name="descripcionMesa" rows="3" required></textarea>
                </div>
                <button type="submit" class="btn btn-success w-100">Registrar</button>
            </form>

            <!-- Botón de regreso -->
            <div class="text-center mt-3">
                <a href="admin.jsp" class="btn btn-secondary">Volver</a>
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

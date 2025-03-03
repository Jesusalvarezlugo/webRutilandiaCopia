<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Usuario - Rutilandia</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='estilos.css' />">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="admin.jsp">Rutilandia Admin</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="admin.jsp">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/cerrarSesion">Cerrar Sesión</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contenido Principal -->
    <div class="container mt-5">
        <div class="card p-4 shadow-lg mx-auto" style="max-width: 500px;">
            <h2 class="text-center mb-4">Modificar Perfil</h2>

            <!-- Mensajes de error o éxito -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">${mensaje}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <!-- Formulario de Modificación -->
            <form action="/modificar" method="POST">
                <input type="hidden" name="_method" value="PUT">

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre:</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}" required>
                </div>

                <div class="mb-3">
                    <label for="apellido1" class="form-label">Primer Apellido:</label>
                    <input type="text" class="form-control" id="apellido1" name="apellido1" value="${usuario.apellido1}" required>
                </div>

                <div class="mb-3">
                    <label for="apellido2" class="form-label">Segundo Apellido:</label>
                    <input type="text" class="form-control" id="apellido2" name="apellido2" value="${usuario.apellido2}" required>
                </div>

                <div class="mb-3">
                    <label for="telefono" class="form-label">Teléfono:</label>
                    <input type="tel" class="form-control" id="telefono" name="telefono" value="${usuario.telefono}" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Correo Electrónico:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${usuario.email}" readonly>
                </div>

                <hr class="my-4">

                <div class="mb-3">
                    <label for="nuevaContrasenia" class="form-label">Nueva Contraseña (opcional):</label>
                    <input type="password" class="form-control" id="nuevaContrasenia" name="nuevaContrasenia">
                </div>

                <div class="mb-3">
                    <label for="repNuevaContrasenia" class="form-label">Confirmar Nueva Contraseña:</label>
                    <input type="password" class="form-control" id="repNuevaContrasenia" name="repNuevaContrasenia">
                </div>

                <div class="mb-3">
                    <label for="contraseniaActual" class="form-label">Contraseña Actual (obligatoria si cambia la contraseña):</label>
                    <input type="password" class="form-control" id="contraseniaActual" name="contraseniaActual">
                </div>

                <button type="submit" class="btn btn-primary w-100">Guardar Cambios</button>
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

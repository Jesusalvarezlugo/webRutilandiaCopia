<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- exito.jsp -->
<h1>¡Login exitoso!</h1>
<p>Bienvenido, te has registrado con éxito.</p>
<a href="modificar.jsp">modificar</a>
<a href="${pageContext.request.contextPath}/listar">Ver usuarios</a>
</body>
</html>
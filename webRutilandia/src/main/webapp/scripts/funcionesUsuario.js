/**
 * archivo para la gestion de usuaarios
 */

// Función para confirmar la eliminación de un usuario
function confirmarEliminacion(url) {
    let respuesta = prompt("Para confirmar la eliminación, escriba 'CONFIRMAR'");

    if (respuesta !== null && respuesta.toUpperCase() === "CONFIRMAR") {
        window.location.href = url; // Redirige a la eliminación si la entrada es válida
    } else {
        alert("Eliminación cancelada. Escriba 'CONFIRMAR' para eliminar.");
    }
}
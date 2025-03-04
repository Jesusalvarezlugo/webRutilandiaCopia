package com.webRutilandia.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webRutilandia.dtos.UsuarioDto;

import jakarta.servlet.http.HttpSession;
@Service
public class UsuarioServicio {
	
	@Autowired
	private ApiServicio apiServicio;

public String enviarRegistroUsuario(UsuarioDto nuevoUsuario,HttpSession session) throws URISyntaxException, IOException {
		
		URI uri = new URI("http://localhost:8082/api/usuarios/registroUsuario");
		URL url = uri.toURL();
		
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setDoOutput(true);
		
		// Pasar el dto a json para enviarlo a la api

		ObjectMapper mapper = new ObjectMapper();

		String dtoAJson = mapper.writeValueAsString(nuevoUsuario);
		System.out.println(dtoAJson);
		
		//se envian los datos al servidor
		
		OutputStream os = conexion.getOutputStream();
		
		os.write(dtoAJson.getBytes());
		os.flush();

		// Leer la respuesta del servidor
		int codigoRespuesta = conexion.getResponseCode();
		System.out.println("Codigo:"+codigoRespuesta);
		if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			UsuarioDto usuario = mapper.readValue(response.toString(), UsuarioDto.class);

			if (usuario != null) {
				// Guardar el objeto UsuarioDto en la sesión

				session.setAttribute("usuario", usuario);

				return "success";
			} else {
				System.out.println("Error: La respuesta de la API no contiene un usuario válido.");
				return "error";
			}
				
		}
		
		return "error";
	}

/**
 * Método para mandar el usuario actualizado a la api
 * @param usuario
 * @return
 * @throws URISyntaxException
 * @throws IOException
 */
public String actualizarUsuario(UsuarioDto usuario) throws URISyntaxException, IOException {
    // Convertir el objeto usuario actualizado a JSON
    ObjectMapper mapper = new ObjectMapper();
    String usuarioJson = mapper.writeValueAsString(usuario);

    // Configurar la conexión HTTP para la solicitud PUT
    URI uri = new URI("http://localhost:8082/api/usuarios/actualizarUsuario");
	URL url = uri.toURL();
    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
    conexion.setRequestMethod("PUT");
    conexion.setRequestProperty("Content-Type", "application/json");
    conexion.setDoOutput(true);

    // Enviar el JSON en el cuerpo de la solicitud
    OutputStream os = conexion.getOutputStream();
    os.write(usuarioJson.getBytes());
    os.flush();
    os.close();

    // Leer la respuesta del servidor
    int codigoRespuesta = conexion.getResponseCode();
    if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
        // Si la respuesta es exitosa, retornamos un mensaje de éxito
        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Respuesta de la API: " + response.toString());
        return "success"; // Retornar el éxito si la API responde OK
    } else {
        // Si la respuesta no es OK, procesamos el error
        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
        StringBuilder errorResponse = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            errorResponse.append(inputLine);
        }
        in.close();
        System.out.println("Error al actualizar el usuario: " + errorResponse.toString());
        return "error"; // Si la actualización falla, retornar "error"
    }
}
/**
 * Método que verifica un email desde la api
 * @param email
 * @return
 * @throws IOException
 * @throws URISyntaxException
 */
public boolean emailExiste(String email) throws IOException, URISyntaxException {
    // Lógica para verificar si el email ya existe en la base de datos a través de la API.
    URI uri = new URI("http://localhost:8082/api/usuarios/emailExiste?email=" + email);
    URL url = uri.toURL();
    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
    conexion.setRequestMethod("GET");

    int codigoRespuesta = conexion.getResponseCode();
    if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
        // Si la respuesta es OK, significa que el email ya está en uso
        return true;
    }
    return false; // Si no es OK, el email no está en uso
}
/**
 * Método que verifica la contraseña con la api
 * @param email
 * @param contrasenia
 * @return
 * @throws IOException
 * @throws URISyntaxException
 */
public boolean verificarContrasenia(String email, String contrasenia) throws IOException, URISyntaxException {
    URI uri = new URI("http://localhost:8082/api/usuarios/verificarContrasenia?email=" + email + "&contrasenia=" + contrasenia);
    URL url = uri.toURL();
    
    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
    conexion.setRequestMethod("GET");

    int codigoRespuesta = conexion.getResponseCode();
    if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String response = in.readLine();
        in.close();
        return Boolean.parseBoolean(response);
    }
    return false;
}
/**
 * Método que me trae detalles del usuario
 * @param email
 * @param token
 * @return
 * @throws IOException
 * @throws URISyntaxException
 */
public UsuarioDto obtenerDetallesUsuario(String email, String token) throws IOException, URISyntaxException {
    // Construir la URL del endpoint que retorna los detalles del usuario, por ejemplo:
    URI uri = new URI("http://localhost:8082/api/usuarios/detalles?email=" + URLEncoder.encode(email, "UTF-8"));
    URL url = uri.toURL();
    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
    conexion.setRequestMethod("GET");
    // Si la API requiere autenticación, agrega el token en la cabecera:
    conexion.setRequestProperty("Authorization", "Bearer " + token);
    
    int codigoRespuesta = conexion.getResponseCode();
    if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        ObjectMapper mapper = new ObjectMapper();
        UsuarioDto usuario = mapper.readValue(response.toString(), UsuarioDto.class);
        return usuario;
    } else {
        System.out.println("Error al obtener detalles del usuario, código: " + codigoRespuesta);
        return null;
    }
}
/**
 * Método para traer los usuarios desde la api
 * @return
 * @throws Exception
 */
public List<UsuarioDto> obtenerUsuariosDesdeApi() throws Exception {
    return apiServicio.cargarUsuariosDesdeApi(); // Este es el método real que hace GET a la API
}
/**
 * Método que elimina un usuario 
 * @param id
 * @return
 * @throws Exception
 */
public String eliminarUsuario(Long id) throws Exception {
    return apiServicio.eliminarUsuarioPorId(id);
}



}



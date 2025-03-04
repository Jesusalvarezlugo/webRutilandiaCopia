package com.webRutilandia.servicios;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webRutilandia.dtos.UsuarioDto;

import reactor.core.publisher.Mono;
/**
 * Servicio que contiene métodos  para llamar a la api
 */
@Service
public class ApiServicio {

	/**
     * Método para enviar los datos a la api para hacer el login
     * @param usuario
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String enviarLoginUsuario(UsuarioDto usuario) throws URISyntaxException, IOException {
        URI uri = new URI("http://localhost:8082/api/usuarios/login");
        URL url = uri.toURL();

        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json");
        conexion.setDoOutput(true);

        // Convertir el objeto usuario a JSON para enviarlo a la API
        ObjectMapper mapper = new ObjectMapper();
        String usuarioJson = mapper.writeValueAsString(usuario);
        System.out.println("Enviando JSON al backend: " + usuarioJson);
        // Enviar los datos al servidor
        OutputStream os = conexion.getOutputStream();
        os.write(usuarioJson.getBytes());
        os.flush();

        // Leer la respuesta del servidor
        int codigoRespuesta = conexion.getResponseCode();
        System.out.println("Código de respuesta: " + codigoRespuesta);

        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Respuesta de la API: " + response.toString());
            // Extraer el token de la respuesta
            Map<String, Object> responseMap = mapper.readValue(response.toString(), Map.class);
            if (responseMap.containsKey("token")) {
                return (String) responseMap.get("token");
            }
        }

        return null; // Si la API no devuelve un token o hay un error
    }
    
    public String eliminarUsuarioPorId(Long idUsuario) throws IOException, URISyntaxException {
        URI uri = new URI("http://localhost:8082/api/usuarios/eliminar/" + idUsuario);
        HttpURLConnection conexion = (HttpURLConnection) uri.toURL().openConnection();
        conexion.setRequestMethod("DELETE");

        int codigoRespuesta = conexion.getResponseCode();

        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
            return "success";
        } else {
            return "error";
        }
    }
    WebClient webClient = WebClient.create("http://localhost:8082");
    public List<UsuarioDto> cargarUsuariosDesdeApi() {
        Mono<UsuarioDto[]> response = webClient.get()
                .uri("/api/usuarios/obtenerUsuarios")
                .retrieve()
                .bodyToMono(UsuarioDto[].class);

        UsuarioDto[] usuarios = response.block();

        if (usuarios != null) {
            return Arrays.asList(usuarios);
        } else {
            return List.of(); // Devolver lista vacía si no hay usuarios
        }
    }
}

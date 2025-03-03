package com.webRutilandia.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webRutilandia.dtos.MesaDto;
import com.webRutilandia.dtos.UsuarioDto;

import jakarta.servlet.http.HttpSession;
@Service
public class MesaServicio {
	
	/**
     * Metodo para enviar a la api la mesa creada
     * @param nuevaMesa
     * @param session
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String enviarRegistroMesa(MesaDto nuevaMesa, HttpSession session) throws URISyntaxException, IOException {
    	URI uri = new URI("http://localhost:8082/api/mesas/crearMesa");
		URL url = uri.toURL();
		
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setDoOutput(true);
		
		// Pasar el dto a json para enviarlo a la api

		ObjectMapper mapper = new ObjectMapper();

		String dtoAJson = mapper.writeValueAsString(nuevaMesa);
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

}

package com;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStart {

	public static void main(String[] args) throws IOException, URISyntaxException {
		String swaggerUiUrl = "http://localhost:8080/swagger-ui.html";
		Desktop.getDesktop().browse(new URI(swaggerUiUrl));
		SpringApplication.run(SpringStart.class, args);
		System.out.println();
	}
}

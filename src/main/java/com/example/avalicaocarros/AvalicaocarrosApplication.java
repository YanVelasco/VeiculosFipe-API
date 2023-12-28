package com.example.avalicaocarros;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.avalicaocarros.principal.Principal;

@SpringBootApplication
public class AvalicaocarrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AvalicaocarrosApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}

}
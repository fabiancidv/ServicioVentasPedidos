package com.PerfulandiaSPA.ServicioVentasPedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServicioVentasPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioVentasPedidosApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}

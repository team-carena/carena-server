package org.sopt.carena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarenaApplication.class, args);
	}

}

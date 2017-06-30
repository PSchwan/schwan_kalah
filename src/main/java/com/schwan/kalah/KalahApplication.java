package com.schwan.kalah;

import com.schwan.kalah.controller.KalahController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = KalahController.class)
public class KalahApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahApplication.class, args);
	}
}

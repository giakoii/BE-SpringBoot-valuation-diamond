package org.swp391.valuationdiamond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValuationdiamondApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValuationdiamondApplication.class, args);
		for (int i = 0; i <= 5; i++) {
			System.out.println("Success!");
		}
	}

}

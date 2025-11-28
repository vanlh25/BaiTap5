package vn.iotstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"vn.iotstar.controller"})
@ComponentScan
public class BapTap5Application {

	public static void main(String[] args) {
		SpringApplication.run(BapTap5Application.class, args);
	}

}

package com.robot.humanrobot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)

public class HumanRobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanRobotApplication.class, args);

	}

}

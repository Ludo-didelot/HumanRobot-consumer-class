package com.robot.humanrobot;


import com.robot.humanrobot.repo.HumanRobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class HumanRobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanRobotApplication.class, args);

	}

}
